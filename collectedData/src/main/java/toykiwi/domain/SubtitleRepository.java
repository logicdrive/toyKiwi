package toykiwi.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subtitles",
    path = "subtitles"
)
public interface SubtitleRepository
    extends PagingAndSortingRepository<Subtitle, Long> {
    List<Subtitle> findAllByVideo(Video video);
    List<Subtitle> findAllBySubtitleId(Long subtitleId);
}
