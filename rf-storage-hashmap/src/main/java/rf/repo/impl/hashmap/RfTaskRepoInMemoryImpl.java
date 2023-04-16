package rf.repo.impl.hashmap;

import org.springframework.stereotype.Repository;
import rf.domain.entity.RfTask;
import rf.domain.repo.RfTaskRepo;

@Repository
class RfTaskRepoInMemoryImpl extends AbstractInMemoryRepo<RfTask> implements RfTaskRepo {

}
