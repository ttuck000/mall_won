package mallwon.infra;

import mallwon.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="orderstatuses", path="orderstatuses")
public interface OrderstatusRepository extends PagingAndSortingRepository<Orderstatus, Long> {

    

    
}
