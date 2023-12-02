package toykiwi.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toykiwi.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subtitles")
@Transactional
public class SubtitleController {

    @Autowired
    SubtitleRepository subtitleRepository;

    @RequestMapping(
        value = "subtitles/{id}/uploadgeneratedsubtitle",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Subtitle uploadGeneratedSubtitle(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /subtitle/uploadGeneratedSubtitle  called #####"
        );
        Optional<Subtitle> optionalSubtitle = subtitleRepository.findById(id);

        optionalSubtitle.orElseThrow(() -> new Exception("No Entity Found"));
        Subtitle subtitle = optionalSubtitle.get();
        subtitle.uploadGeneratedSubtitle();

        subtitleRepository.save(subtitle);
        return subtitle;
    }

    @RequestMapping(
        value = "subtitles/{id}/uploadtranslatedsubtitle",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Subtitle uploadTranslatedSubtitle(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /subtitle/uploadTranslatedSubtitle  called #####"
        );
        Optional<Subtitle> optionalSubtitle = subtitleRepository.findById(id);

        optionalSubtitle.orElseThrow(() -> new Exception("No Entity Found"));
        Subtitle subtitle = optionalSubtitle.get();
        subtitle.uploadTranslatedSubtitle();

        subtitleRepository.save(subtitle);
        return subtitle;
    }
}
//>>> Clean Arch / Inbound Adaptor
