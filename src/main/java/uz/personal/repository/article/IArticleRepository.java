package uz.personal.repository.article;

import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.repository.IGenericCrudRepository;

public interface IArticleRepository extends IGenericCrudRepository<_Article, ArticleCriteria> {

}
