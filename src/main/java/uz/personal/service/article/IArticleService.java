package uz.personal.service.article;

import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.dto.article.ArticleCreateDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.article.ArticleUpdateDto;
import uz.personal.service.IGenericCrudService;

public interface IArticleService extends IGenericCrudService<_Article, ArticleDto, ArticleCreateDto, ArticleUpdateDto, Long, ArticleCriteria> {
}
