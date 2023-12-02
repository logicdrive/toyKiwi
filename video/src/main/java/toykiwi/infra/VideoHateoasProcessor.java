package toykiwi.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import toykiwi.domain.*;

@Component
public class VideoHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Video>> {

    @Override
    public EntityModel<Video> process(EntityModel<Video> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/notifyuploadedvideo"
                )
                .withRel("notifyuploadedvideo")
        );

        return model;
    }
}
