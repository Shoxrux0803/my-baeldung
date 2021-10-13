package uz.personal.repository.article;

import uz.personal.criteria.article.PostCriteria;
import uz.personal.domain.article._Post;
import uz.personal.repository.IGenericCrudRepository;

public interface IPostRepository extends IGenericCrudRepository<_Post, PostCriteria> {
}
