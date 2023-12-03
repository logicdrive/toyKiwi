package toykiwi.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import toykiwi.SubtitleApplication;
import toykiwi.domain.GeneratedSubtitleUploaded;
import toykiwi.domain.TranlatedSubtitleUploaded;

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
        GeneratedSubtitleUploaded generatedSubtitleUploaded = new GeneratedSubtitleUploaded(
            this
        );
        generatedSubtitleUploaded.publishAfterCommit();

        TranlatedSubtitleUploaded tranlatedSubtitleUploaded = new TranlatedSubtitleUploaded(
            this
        );
        tranlatedSubtitleUploaded.publishAfterCommit();
    }

    public static SubtitleRepository repository() {
        SubtitleRepository subtitleRepository = SubtitleApplication.applicationContext.getBean(
            SubtitleRepository.class
        );
        return subtitleRepository;
    }

    //<<< Clean Arch / Port Method
    public static void uploadGeneratedSubtitle(
        GeneratingSubtitleCompleted generatingSubtitleCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subtitle subtitle = new Subtitle();
        repository().save(subtitle);

        GeneratedSubtitleUploaded generatedSubtitleUploaded = new GeneratedSubtitleUploaded(subtitle);
        generatedSubtitleUploaded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(generatingSubtitleCompleted.get???()).ifPresent(subtitle->{
            
            subtitle // do something
            repository().save(subtitle);

            GeneratedSubtitleUploaded generatedSubtitleUploaded = new GeneratedSubtitleUploaded(subtitle);
            generatedSubtitleUploaded.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void uploadTranslatedSubtitle(
        TranslatingSubtitleCompleted translatingSubtitleCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subtitle subtitle = new Subtitle();
        repository().save(subtitle);

        TranlatedSubtitleUploaded tranlatedSubtitleUploaded = new TranlatedSubtitleUploaded(subtitle);
        tranlatedSubtitleUploaded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(translatingSubtitleCompleted.get???()).ifPresent(subtitle->{
            
            subtitle // do something
            repository().save(subtitle);

            TranlatedSubtitleUploaded tranlatedSubtitleUploaded = new TranlatedSubtitleUploaded(subtitle);
            tranlatedSubtitleUploaded.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
