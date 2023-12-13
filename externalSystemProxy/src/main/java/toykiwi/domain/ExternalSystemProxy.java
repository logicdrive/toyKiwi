package toykiwi.domain;

import toykiwi._global.event.GeneratedSubtitleUploaded;
import toykiwi._global.event.GeneratingQnACompleted;
import toykiwi._global.event.GeneratingSubtitleCompleted;
import toykiwi._global.event.GeneratingSubtitleStarted;
import toykiwi._global.event.TranlatedSubtitleUploaded;
import toykiwi._global.event.TranslatingSubtitleCompleted;
import toykiwi._global.event.UploadingVideoCompleted;
import toykiwi._global.event.VideoRemoveRequested;
import toykiwi._global.event.VideoUploadRequested;
import toykiwi._global.event.VideoUrlUploaded;
import toykiwi._global.externalSystemProxy.ExternalSystemProxyService;
import toykiwi._global.externalSystemProxy.reqDtos.UploadYoutubeVideoReqDto;
import toykiwi._global.externalSystemProxy.resDtos.UploadYoutubeVideoResDto;
import toykiwi._global.externalSystemProxy.reqDtos.GenerateSubtitleReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.GetQnAForSentenceReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.RemoveFileReqDto;
import toykiwi._global.externalSystemProxy.reqDtos.TranslateSubtitleReqDto;
import toykiwi._global.externalSystemProxy.resDtos.GenerateSubtitleResDto;
import toykiwi._global.externalSystemProxy.resDtos.GetQnAForSentenceResDto;
import toykiwi._global.externalSystemProxy.resDtos.SubtitleResDto;
import toykiwi._global.externalSystemProxy.resDtos.TranslateSubtitleResDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExternalSystemProxy {
    private final ExternalSystemProxyService externalSystemProxyService;
    
    // 비디오 업로드 요청관련 이벤트 발생시 비디오를 업로드하고, 관련 정보를 이벤트로 전달하기 위해서
    public void requestUploadingVideo(VideoUploadRequested videoUploadRequested) throws Exception {
        
        UploadYoutubeVideoReqDto uploadYoutubeVideoReqDto = new UploadYoutubeVideoReqDto(
            videoUploadRequested.getYoutubeUrl(), videoUploadRequested.getCuttedStartSecond(), videoUploadRequested.getCuttedEndSecond());

        UploadYoutubeVideoResDto uploadYoutubeVideoResDto = this.externalSystemProxyService.uploadYoutubeVideo(uploadYoutubeVideoReqDto);

        UploadingVideoCompleted uploadingVideoCompleted = new UploadingVideoCompleted();
        uploadingVideoCompleted.setVideoId(videoUploadRequested.getId());
        uploadingVideoCompleted.setVideoTitle(uploadYoutubeVideoResDto.getVideoTitle());
        uploadingVideoCompleted.setUploadedUrl(uploadYoutubeVideoResDto.getUploadedUrl());
        uploadingVideoCompleted.setThumbnailUrl(uploadYoutubeVideoResDto.getThumbnailUrl());
        uploadingVideoCompleted.publishAfterCommit();
        
    }

    // 비디오 URL 업데이트 완료시, 해당 URL에 접속해서 자막을 추출하고, 관련 정보를 이벤트로 전달하기 위해서
    public void requestGeneratingSubtitle(VideoUrlUploaded videoUrlUploaded) throws Exception {

        GenerateSubtitleReqDto generateSubtitleReqDto = new GenerateSubtitleReqDto(
            videoUrlUploaded.getUploadedUrl()
        );
        
        GenerateSubtitleResDto generateSubtitleResDto = this.externalSystemProxyService.generateSubtitle(generateSubtitleReqDto);

        GeneratingSubtitleStarted generatingSubtitleStarted = new GeneratingSubtitleStarted();
        generatingSubtitleStarted.setVideoId(videoUrlUploaded.getId());
        generatingSubtitleStarted.setSubtitleCount(generateSubtitleResDto.getSubtitles().size());
        generatingSubtitleStarted.publishAfterCommit();
        for(SubtitleResDto subtitleResDto : generateSubtitleResDto.getSubtitles()) {
            GeneratingSubtitleCompleted generatingSubtitleCompleted = new GeneratingSubtitleCompleted();
            generatingSubtitleCompleted.setVideoId(videoUrlUploaded.getId());
            generatingSubtitleCompleted.setSubtitle(subtitleResDto.getSubtitle());
            generatingSubtitleCompleted.setStartSecond(subtitleResDto.getStartSecond());
            generatingSubtitleCompleted.setEndSecond(subtitleResDto.getEndSecond());
            generatingSubtitleCompleted.publishAfterCommit();
        }

    }

    // 비디오 자막 업데이트시, 해당 자막에 대한 번역문을 생성하고, 관련 정보를 이벤트로 전달하기 위해서
    public void requestTranslatingSubtitle(GeneratedSubtitleUploaded generatingSubtitleUploaded) throws Exception {

        TranslateSubtitleReqDto translateSubtitleReqDto = new TranslateSubtitleReqDto(
                generatingSubtitleUploaded.getSubtitle()
        );

        TranslateSubtitleResDto translateSubtitleResDto = this.externalSystemProxyService.translateSubtitle(translateSubtitleReqDto);

        TranslatingSubtitleCompleted translatingSubtitleCompleted = new TranslatingSubtitleCompleted();
        translatingSubtitleCompleted.setVideoId(generatingSubtitleUploaded.getVideoId());
        translatingSubtitleCompleted.setSubtitleId(generatingSubtitleUploaded.getId());
        translatingSubtitleCompleted.setTranslatedSubtitle(translateSubtitleResDto.getTranslatedSubtitle());
        translatingSubtitleCompleted.publishAfterCommit();
    
    }

    // 비디오 삭제 요청시 S3에 업로드된 관련된 비디오 및 썸네일을 삭제시키기 위해서
    public void requestRemovingVideo(VideoRemoveRequested videoRemoveRequested) throws Exception {
        this.externalSystemProxyService.removeFile(
            new RemoveFileReqDto(videoRemoveRequested.getUploadedUrl())
        );

        this.externalSystemProxyService.removeFile(
            new RemoveFileReqDto(videoRemoveRequested.getThumbnailUrl())
        );
    }

    // 번역문 업데이트시, 자막에 대한 질문 및 응답 생성 요청을 수행하기 위해서
    public void requestGeneratingQnA(TranlatedSubtitleUploaded tranlatedSubtitleUploaded) throws Exception {

        GetQnAForSentenceReqDto getQnAForSentenceReqDto = new GetQnAForSentenceReqDto(
                tranlatedSubtitleUploaded.getSubtitle()
        );

        GetQnAForSentenceResDto getQnAForSentenceResDto = this.externalSystemProxyService.getQnAForSentence(getQnAForSentenceReqDto);


        GeneratingQnACompleted generatingQnACompleted = new GeneratingQnACompleted();
        generatingQnACompleted.setVideoId(tranlatedSubtitleUploaded.getVideoId());
        generatingQnACompleted.setSubtitleId(tranlatedSubtitleUploaded.getId());
        generatingQnACompleted.setQuestion(getQnAForSentenceResDto.getQuestion());
        generatingQnACompleted.setAnswer(getQnAForSentenceResDto.getAnswer());
        generatingQnACompleted.publishAfterCommit();
    
    }
}
