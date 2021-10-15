package uz.personal.repository.article.impl;

import org.springframework.stereotype.Repository;
import uz.personal.criteria.article.RateCriteria;
import uz.personal.domain.article._Rate;
import uz.personal.repository.GenericDao;
import uz.personal.repository.article.IRateRepository;

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

        if (!utils.isEmpty(criteria.getUserId())) {
            whereCause.add(" t.user.id = :userId");
            params.put("userId", criteria.getUserId());
        }
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

}


