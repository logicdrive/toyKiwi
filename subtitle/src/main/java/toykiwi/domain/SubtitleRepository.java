package toykiwi.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import toykiwi.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "subtitles", path = "subtitles")
public interface SubtitleRepository
    extends PagingAndSortingRepository<Subtitle, Long> {}
