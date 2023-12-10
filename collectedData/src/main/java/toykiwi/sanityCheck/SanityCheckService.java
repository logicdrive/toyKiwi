package toykiwi.sanityCheck;

import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.SubtitleMetadataUploaded;
import toykiwi._global.event.TranlatedSubtitleUploaded;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.exceptions.InvalidVideoIdException;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;
import toykiwi.domain.Subtitle;
import toykiwi.domain.SubtitleRepository;
import toykiwi.domain.Video;
import toykiwi.domain.VideoRepository;
import toykiwi.sanityCheck.reqDtos.LogsReqDto;
import toykiwi.sanityCheck.reqDtos.MakeSubtitleSampleReqDto;
import toykiwi.sanityCheck.reqDtos.MockGeneratedSubtitleUploadedReqDto;
import toykiwi.sanityCheck.reqDtos.MockSubtitleMetadataUploadedReqDto;
import toykiwi.sanityCheck.reqDtos.MockTranlatedSubtitleUploadedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoRemoveRequestedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUploadRequestedReqDto;
import toykiwi.sanityCheck.reqDtos.MockVideoUrlUploadedReqDto;
import toykiwi.sanityCheck.resDtos.LogsResDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SanityCheckService {
    private final String logFilePath = "./logs/logback.log";
    private final VideoRepository videoRepository;
    private final SubtitleRepository subtitleRepository;

    // 출력된 로그들 중에서 끝부분 몇라인을 읽어서 반환시키기 위해서
    public LogsResDto logs(LogsReqDto logsReqDto) throws FileNotFoundException {
            List<String> logs = new ArrayList<>();

            try {
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Try to read logs", String.format("{filePath: %s}", logFilePath));
                
                Scanner myReader = new Scanner(new File(logFilePath));
                while (myReader.hasNextLine())
                {
                    String readLog = myReader.nextLine();
                    if (logsReqDto.getRegFilter().isEmpty()) logs.add(readLog);
                    else if(readLog.matches(logsReqDto.getRegFilter())) logs.add(readLog);
                }
                myReader.close();
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read logs", String.format("{logsSize: %d}", logs.size()));

            } catch (FileNotFoundException e) {
                CustomLogger.error(e, "Error while reading logs", String.format("{filePath: %s}", logFilePath));
                throw new FileNotFoundException();
            }

            return new LogsResDto(logs.subList(Math.max(logs.size()-logsReqDto.getLineLength(), 0), logs.size()));
    }

    // 신속한 프론트엔드 테스트를 위해서 샘플을 만들어줌
    public Subtitle makeSamples(MakeSubtitleSampleReqDto makeSubtitleSampleReqDto) {
        List<Video> videos = this.videoRepository.findAllByVideoId(makeSubtitleSampleReqDto.getVideoId());
        if(videos.size() != 1)
            throw new InvalidVideoIdException();
        Video subtitleVideo = videos.get(0);

        // 해당 비디오에 대한 자막 정보를 새로 생성시키기 위해서
        Subtitle subtitleToCreate = new Subtitle();
        subtitleToCreate.setSubtitleId(makeSubtitleSampleReqDto.getSubtitleId());
        subtitleToCreate.setSubtitle(makeSubtitleSampleReqDto.getSubtitle());
        subtitleToCreate.setTranslatedSubtitle(makeSubtitleSampleReqDto.getTranslatedSubtitle());
        subtitleToCreate.setStartSecond(makeSubtitleSampleReqDto.getStartSecond());
        subtitleToCreate.setEndSecond(makeSubtitleSampleReqDto.getEndSecond());
        subtitleToCreate.setVideo(subtitleVideo);
        this.subtitleRepository.save(subtitleToCreate);

        return subtitleToCreate;
    }


    // Policy 테스트용으로 VideoUploadRequested 이벤트를 강제로 발생시키기 위해서
    public void mockVideoUploadRequested(MockVideoUploadRequestedReqDto mockData) {
        (new VideoUploadRequested(mockData)).publish();
    }

    // Policy 테스트용으로 VideoUrlUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockVideoUrlUploaded(MockVideoUrlUploadedReqDto mockData) {
        (new VideoUrlUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 SubtitleMetadataUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockSubtitleMetadataUploaded(MockSubtitleMetadataUploadedReqDto mockData) {
        (new SubtitleMetadataUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 GeneratedSubtitleUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockGeneratedSubtitleUploaded(MockGeneratedSubtitleUploadedReqDto mockData) {
        (new GeneratedSubtitleUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 TranlatedSubtitleUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockTranlatedSubtitleUploaded(MockTranlatedSubtitleUploadedReqDto mockData) {
        (new TranlatedSubtitleUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 VideoRemoveRequested 이벤트를 강제로 발생시키기 위해서
    public void mockVideoRemoveRequested(MockVideoRemoveRequestedReqDto mockData) {
        (new VideoRemoveRequested(mockData)).publish();
    }
}
