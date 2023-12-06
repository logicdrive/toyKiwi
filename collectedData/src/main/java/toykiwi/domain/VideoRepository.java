package toykiwi.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "videos",
    path = "videos"
)
public interface VideoRepository
    extends PagingAndSortingRepository<Video, Long> {
    List<Video> findAllByVideoId(Long videoId);
}
