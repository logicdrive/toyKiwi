package toykiwi._global.externalSystemProxy;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import toykiwi._global.externalSystemProxy.reqDtos.EchoWithJsonReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.ExternalSystemProxyReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.GenerateSubtitleReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.TranslateSubtitleReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.UploadYoutubeVideoReqDto;
import toykiwi._global.externalSystemProxy.resDtos.EchoWithJsonResDto;
import toykiwi._global.externalSystemProxy.resDtos.ExternalSystemProxyResDto;
import toykiwi._global.externalSystemProxy.resDtos.GenerateSubtitleResDto;
import toykiwi._global.externalSystemProxy.resDtos.TranslateSubtitleResDto;
import toykiwi._global.externalSystemProxy.resDtos.UploadYoutubeVideoResDto;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

@Service
public class ExternalSystemProxyService {

    @Value("${externalSystem.ip}")
    private String externalSystemIp;

    @Value("${externalSystem.port}")
    private String externalSystemPort;

    
    // JSON 송수신 여부를 간편하게 테스트해보기 위해서
    public EchoWithJsonResDto echoWithJson(EchoWithJsonReqDto echoWithJsonReqDto) throws Exception {
        return this.jsonCommunication("/sanityCheck/echoWithJson", echoWithJsonReqDto, EchoWithJsonResDto.class);
    }

    // 주어진 유튜브 URL에서 동영상을 다운로드 받고, 관련 동영상 및 썸네일을 업로드해서 그 정보를 반환시키기 위해서
    public UploadYoutubeVideoResDto uploadYoutubeVideo(UploadYoutubeVideoReqDto uploadYoutubeVideoReqDto) throws Exception {
        return this.jsonCommunication("/s3/uploadYoutubeVideo", uploadYoutubeVideoReqDto, UploadYoutubeVideoResDto.class);
    }

    // 주어진 동영상들을 분석해서 자막들을 추출해서 관련 정보들을 반환시키기 위해서
    public GenerateSubtitleResDto generateSubtitle(GenerateSubtitleReqDto generateSubtitleReqDto) throws Exception {
        return this.jsonCommunication("/openai/generateSubtitle", generateSubtitleReqDto, GenerateSubtitleResDto.class);
    }

    // 주어진 자막에 대한 한글 번역문을 반환시키기 위해서
    public TranslateSubtitleResDto translateSubtitle(TranslateSubtitleReqDto translateSubtitleReqDto) throws Exception {
        return this.jsonCommunication("/deepl/translateSubtitle", translateSubtitleReqDto, TranslateSubtitleResDto.class);
    }


    // ExternalSystem과 JSON을 기반으로 한 일관성 있는 통신을 위해서
    public <S extends ExternalSystemProxyReqDto, R extends ExternalSystemProxyResDto> R jsonCommunication(String requestPath, S reqDto, Class<R> resType) throws Exception {
        try {

            String requestUrl = String.format("http://%s:%s", this.externalSystemIp, this.externalSystemPort) + requestPath;
            CustomLogger.debug(CustomLoggerType.EFFECT, "Request to external system",String.format("{requestUrl: %s, reqDto: %s}", requestUrl, reqDto));

            String resultRawText = WebClient.create(requestUrl)
                .put()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reqDto.hashMap()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            CustomLogger.debug(CustomLoggerType.EFFECT, "Read results from external system", String.format("{resultRawText: %s}", resultRawText));

            ObjectMapper mapper = new ObjectMapper();
            R resDto = mapper.readValue(resultRawText, resType);
            return resDto;

        } catch (Exception e) {
            CustomLogger.error(e, "Error while requesting to externalSystem", String.format("{requestPath: %s, reqDto: %s}", requestPath, reqDto));
            throw e;
        }
    }
}
