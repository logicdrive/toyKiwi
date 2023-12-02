package toykiwi.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import toykiwi.domain.*;

@Component
public class SubtitleHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Subtitle>> {

    @Override
    public EntityModel<Subtitle> process(EntityModel<Subtitle> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/uploadgeneratedsubtitle"
                )
                .withRel("uploadgeneratedsubtitle")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/uploadtranslatedsubtitle"
                )
                .withRel("uploadtranslatedsubtitle")
        );

        return model;
    }
}
