package uz.personal.service.article.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.domain.article._Article;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.ArticleCreateDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.article.ArticleUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.enums.State;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.article.ArticleMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.article.IArticleService;
import uz.personal.utils.BaseUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service(value = "articleService")
public class ArticleService extends GenericCrudService<_Article, ArticleDto, ArticleCreateDto, ArticleUpdateDto, ArticleCriteria, IArticleRepository> implements IArticleService {

    private final Log logger = LogFactory.getLog(getClass());
    private final IUserRepository userRepository;
    private final GenericMapper genericMapper;
    private final ArticleMapper articleMapper;
    private final ObjectMapper objectMapper;
    private final LinkService linkService;
    private final RateService rateService;
    private final PostService postService;

    @Autowired
    public ArticleService(IArticleRepository repository, BaseUtils utils, IErrorRepository errorRepository, IUserRepository userRepository, GenericMapper genericMapper, ArticleMapper articleMapper, ObjectMapper objectMapper, LinkService linkService, RateService rateService, PostService postService) {
        super(repository, utils, errorRepository);
        this.userRepository = userRepository;
        this.genericMapper = genericMapper;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
        this.linkService = linkService;
        this.rateService = rateService;
        this.postService = postService;
    }


    @Override
//    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ARTICLE_READ)")
    public ResponseEntity<DataDto<ArticleDto>> get(Long id) {
        _Article article = repository.find(ArticleCriteria.childBuilder().selfId(id).build());
        validate(article, id);
        return new ResponseEntity<>(new DataDto<>(articleMapper.toDto(article)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ARTICLE_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull ArticleCreateDto dto) {

//        parentId nima uchun kerak

        _Article article = articleMapper.fromCreateDto(dto);
        article.setState(State.NEW);
        baseValidation(article);
        article.setUser(userRepository.findById(article.getUser().getId()));
        repository.save(article);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(article)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
//    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ARTICLE_UPDATE)")
    public ResponseEntity<DataDto<ArticleDto>> update(@NotNull ArticleUpdateDto dto) {

        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Article"));
        }

        _Article article = repository.find(dto.getId());
        validate(article, dto.getId());

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            if (dto.getLinkUpdateDtoList()!=null){
                dto.getLinkUpdateDtoList().forEach(linkService::update);
            }
            objectMapper.updateValue(article, dto);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }


//        article = articleMapper.fromUpdateDto(dto, article);
//        baseValidation(article);

        repository.update(article);

        repository.save(article);


        return get(dto.getId());
    }

    @Override
    @Transactional
//    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ARTICLE_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _Article article = repository.find(id);
        article.setState(State.DELETED);
        validate(article, id);

        linkService.deleteAllByArticleId(id);
        postService.deleteAllByArticleId(id);
        rateService.deleteAllByArticleId(id);

        repository.save(article);// article bazaadan ochirish kerakmi
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.personal.enums.Permissions).ARTICLE_READ)")
    public ResponseEntity<DataDto<List<ArticleDto>>> getAll(ArticleCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(articleMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    public void baseValidation(@NotNull _Article entity) {
        if (utils.isEmpty(entity.getUser())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("User", "Article")));
        }
        if (utils.isEmpty(entity.getText())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("Text", "Article")));
        }
    }

    @Override
    public void validate(@NotNull _Article entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Article with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("Article", id)));
        }
    }
}
