package uz.personal.repository.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.repository.GenericDao;
import uz.personal.repository.article.IArticleRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository extends GenericDao<_Article, ArticleCriteria> implements IArticleRepository {

    protected final Log logger = LogFactory.getLog(getClass());

    protected final EntityManager entityManager;

    public ArticleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    protected void defineCriteriaOnQuerying(ArticleCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }
        if (!utils.isEmpty(criteria.getText())) {
            whereCause.add("lower(t.text) like :searchingWord");
            params.put("searchingWord", prepareLikeCause(criteria.getText()));
        }
        if (!utils.isEmpty(criteria.getAllowPublication())) {
            whereCause.add("t.allowPublication = :allowPublication");
            params.put("allowPublication", criteria.getAllowPublication());
        }
        if (!utils.isEmpty(criteria.getAllowPublication())) {
            whereCause.add("t.allowComment = :allowComment");
            params.put("allowPublication", criteria.getAllowComment());
        }
        if (!utils.isEmpty(criteria.getRate())) {
            whereCause.add("t.rate = :rate");
            params.put("rate", criteria.getRate());
        }
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }


}
