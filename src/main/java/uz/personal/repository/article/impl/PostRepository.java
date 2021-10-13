package uz.personal.repository.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import uz.personal.criteria.article.PostCriteria;
import uz.personal.domain.article._Post;
import uz.personal.repository.GenericDao;
import uz.personal.repository.article.IPostRepository;

import java.util.List;
import java.util.Map;

@Repository
public class PostRepository extends GenericDao<_Post, PostCriteria> implements IPostRepository {
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void defineCriteriaOnQuerying(PostCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getSelfId())) {
            whereCause.add("t.id = :selfId");
            params.put("selfId", criteria.getSelfId());
        }

        if (!utils.isEmpty(criteria.getContent())) {
            whereCause.add("t.content like :content");
            params.put("content", prepareLikeCause(criteria.getContent()));
        }

        if (!utils.isEmpty(criteria.getArticleId())) {
            whereCause.add("t.article.id = :articleId");
            params.put("articleId", criteria.getArticleId());
        }

        if (!utils.isEmpty(criteria.getUserId())) {
            whereCause.add("t.user.id = :userId");
            params.put("userId", criteria.getUserId());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }
}
