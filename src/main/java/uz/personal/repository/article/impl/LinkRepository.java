package uz.personal.repository.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.article.LinkCriteria;
import uz.personal.domain.article._Link;
import uz.personal.repository.GenericDao;
import uz.personal.repository.article.ILinkRepository;

import java.util.List;
import java.util.Map;

@Repository
public class LinkRepository extends GenericDao<_Link, LinkCriteria> implements ILinkRepository {

    protected final Log logger = LogFactory.getLog(getClass());

//    @Autowired
//    protected EntityManager entityManager;

    @Override
    protected void defineCriteriaOnQuerying(LinkCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }

        if (!utils.isEmpty(criteria.getLink())) {
            whereCause.add("t.link = : link");
            params.put("link", criteria.getLink());
        }

        if (!utils.isEmpty(criteria.getArticleId())) {
            whereCause.add("t.article = : id");
            params.put("id", criteria.getArticleId());
        }


        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }
}
