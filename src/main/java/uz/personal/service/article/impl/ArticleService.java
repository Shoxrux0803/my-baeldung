package uz.personal.service.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.ArticleCreateDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.article.ArticleUpdateDto;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.article.ArticleMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.article.IArticleService;
import uz.personal.utils.BaseUtils;

import java.util.List;

public class ArticleService extends GenericCrudService<_Article, ArticleDto, ArticleCreateDto, ArticleUpdateDto, ArticleCriteria, IArticleRepository> implements IArticleService {

    private final Log logger = LogFactory.getLog(getClass());
    private final GenericMapper genericMapper;
    private final ArticleMapper articleMapper;

    public ArticleService(IArticleRepository repository, BaseUtils utils, IErrorRepository errorRepository, GenericMapper genericMapper, ArticleMapper articleMapper) {
        super(repository, utils, errorRepository);
        this.genericMapper = genericMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<ArticleDto>> get(Long id) {
        _Article article = repository.find(ArticleCriteria.childBuilder().selfId(id).build());
        validate(article, id);
        return new ResponseEntity<>(new DataDto<>(articleMapper.toDto(article)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<GenericDto>> create(ArticleCreateDto dto) {
        return super.create(dto);
    }

    @Override
    public ResponseEntity<DataDto<ArticleDto>> update(ArticleUpdateDto dto) {
        return super.update(dto);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        return super.delete(id);
    }

    @Override
    public ResponseEntity<DataDto<List<ArticleDto>>> getAll(ArticleCriteria criteria) {
        return null;
    }
}
