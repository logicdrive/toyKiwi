package toykiwi._global.infra;

import toykiwi.domain.Subtitle;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

// REST 응답시에 추가적인 참조 URL들을 포함시키기 위해서
@Component
public class SubtitleHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Subtitle>> {

    @Override
    public EntityModel<Subtitle> process(EntityModel<Subtitle> model) {
        return model;
    }
}
