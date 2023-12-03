package toykiwi.infra;

import toykiwi.domain.Video;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

// REST 응답시에 추가적인 참조 URL들을 포함시키기 위해서
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
