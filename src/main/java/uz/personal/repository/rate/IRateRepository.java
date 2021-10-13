package uz.personal.repository.rate;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.rate.RateCriteria;
import uz.personal.domain.rate._Rate;
import uz.personal.repository.IGenericCrudRepository;

public interface IRateRepository extends IGenericCrudRepository<_Rate, RateCriteria> {
}
