package toykiwi.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import toykiwi.SubtitleApplication;
import toykiwi.domain.SubtitleUploadRequested;

@Entity
@Table(name = "Subtitle_table")
@Data
//<<< DDD / Aggregate Root
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long videoId;

    private String subtitle;

    private String translatedSubtitle;

    private Integer startSecond;

    private Integer endSecond;

    @PostPersist
    public void onPostPersist() {
        SubtitleUploadRequested subtitleUploadRequested = new SubtitleUploadRequested(
            this
        );
        subtitleUploadRequested.publishAfterCommit();
    }

    public static SubtitleRepository repository() {
        SubtitleRepository subtitleRepository = SubtitleApplication.applicationContext.getBean(
            SubtitleRepository.class
        );
        return subtitleRepository;
    }

    //<<< Clean Arch / Port Method
    public void uploadGeneratedSubtitle() {
        //implement business logic here:

        GeneratedSubtitleUploaded generatedSubtitleUploaded = new GeneratedSubtitleUploaded(
            this
        );
        generatedSubtitleUploaded.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void uploadTranslatedSubtitle() {
        //implement business logic here:

        TranlatedSubtitleUploaded tranlatedSubtitleUploaded = new TranlatedSubtitleUploaded(
            this
        );
        tranlatedSubtitleUploaded.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void requestSubtitleUpload(
        VideoUploadNotified videoUploadNotified
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subtitle subtitle = new Subtitle();
        repository().save(subtitle);

        SubtitleUploadRequested subtitleUploadRequested = new SubtitleUploadRequested(subtitle);
        subtitleUploadRequested.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(videoUploadNotified.get???()).ifPresent(subtitle->{
            
            subtitle // do something
            repository().save(subtitle);

            SubtitleUploadRequested subtitleUploadRequested = new SubtitleUploadRequested(subtitle);
            subtitleUploadRequested.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
