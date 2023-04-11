package rf.back.repo.impl;

import org.springframework.stereotype.Repository;

import rf.domain.entity.RfTask;
import rf.domain.repo.RfTaskRepo;

@Repository
class RfTaskRepoInMemoryImpl extends AbstractInMemoryRepo<RfTask> implements RfTaskRepo {

}
