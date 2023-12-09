package toykiwi._global.event;

import toykiwi._global.infra.AbstractEvent;

import toykiwi.sanityCheck.reqDtos.MockSubtitleMetadataUploadedReqDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// 비디오 자막관련 메타데이터가 업데이트되었을 경우, 이를 알리기 위한 이벤트
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class SubtitleMetadataUploaded extends AbstractEvent {
    private Long id;
    private String title;
    private String youtubeUrl;
    private Integer cuttedStartSecond;
    private Integer cuttedEndSecond;
    private String uploadedUrl;
    private Integer subtitleCount;
    private String thumbnailUrl;

    public SubtitleMetadataUploaded(MockSubtitleMetadataUploadedReqDto mockData) {
        super();
        this.id = mockData.getId();
        this.title = mockData.getTitle();
        this.youtubeUrl = mockData.getYoutubeUrl();
        this.cuttedStartSecond = mockData.getCuttedStartSecond();
        this.cuttedEndSecond = mockData.getCuttedEndSecond();
        this.uploadedUrl = mockData.getUploadedUrl();
        this.subtitleCount = mockData.getSubtitleCount();
        this.thumbnailUrl = mockData.getThumbnailUrl();
    }

    public SubtitleMetadataUploaded() {
        super();
    }
}
