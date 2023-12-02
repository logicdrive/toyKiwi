package toykiwi.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import toykiwi.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "collectedData",
    path = "collectedData"
)
public interface CollectedDataRepository
    extends PagingAndSortingRepository<CollectedData, Long> {
    List<CollectedData> findByVideoId(Long videoId);
}
