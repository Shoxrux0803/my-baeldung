package uz.personal.repository.rate.impl;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.rate.RateCriteria;
import uz.personal.domain.rate._Rate;
import uz.personal.repository.GenericDao;
import uz.personal.repository.rate.IRateRepository;

import java.util.List;
import java.util.Map;

@Repository
public class RateRepository extends GenericDao<_Rate, RateCriteria> implements IRateRepository {

    @Override
    protected void defineCriteriaOnQuerying(RateCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        if (!utils.isEmpty(criteria.getRate())) {
            whereCause.add("t.rate = :rate ");
            params.put("rate", criteria.getRate());
        }

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId ");
            params.put("selfId", criteria.getSelfId());
        }

        if (!utils.isEmpty(criteria.getArticleId())) {
            whereCause.add(" t.article.id = :articleId");
            params.put("articleId", criteria.getArticleId());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }


//    @Override
//    protected StringBuilder joinStringOnQuerying(RateCriteria criteria) {
//        StringBuilder joinBuilder = new StringBuilder();
//        if (hasText(criteria.getArticleName())) {
//            joinBuilder.append(" join _Article a on a = t.article ");
//        }
//        return joinBuilder;
//    }
}


