package uz.personal.repository.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.repository.GenericDao;
import uz.personal.repository.IAbstractRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository extends GenericDao<_Article, ArticleCriteria> implements IAbstractRepository {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    protected void defineCriteriaOnQuerying(ArticleCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }
        if (!utils.isEmpty(criteria.getAllowPublication())) {
            whereCause.add("lower(t.text) like '%text%'");
            params.put("text", criteria.getText());
        }
        if (!utils.isEmpty(criteria.getAllowPublication())) {
            whereCause.add("t.allowPublication = :allowPublication");
            params.put("allowPublication", criteria.getAllowPublication());
        }
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }



}
