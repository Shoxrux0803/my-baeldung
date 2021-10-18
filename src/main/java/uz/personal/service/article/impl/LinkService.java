package uz.personal.service.article.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.article.LinkCriteria;
import uz.personal.domain.article._Article;
import uz.personal.domain.article._Link;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.LinkCreateDto;
import uz.personal.dto.article.LinkDto;
import uz.personal.dto.article.LinkUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.article.LinkMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.article.ILinkRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.article.ILinkService;
import uz.personal.utils.BaseUtils;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LinkService extends GenericCrudService<_Link, LinkDto, LinkCreateDto, LinkUpdateDto, LinkCriteria, ILinkRepository> implements ILinkService {

    private final Log logger = LogFactory.getLog(getClass());
    private final GenericMapper genericMapper;
    private final LinkMapper linkMapper;
    private final IArticleRepository articleRepository;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public LinkService(ILinkRepository repository, BaseUtils utils, IErrorRepository errorRepository, GenericMapper genericMapper, LinkMapper linkMapper, IArticleRepository articleRepository, ObjectMapper objectMapper, Validator validator) {
        super(repository, utils, errorRepository);
        this.genericMapper = genericMapper;
        this.linkMapper = linkMapper;
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<DataDto<LinkDto>> get(final Long id) {
        _Link link = repository.find(LinkCriteria.childBuilder().selfId(id).build());
        validate(link, id);
        LinkDto linkDto = linkMapper.toDto(link);
        return new ResponseEntity<>(new DataDto<>(linkDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<LinkDto>>> getAll(final LinkCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(linkMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<GenericDto>> create(final LinkCreateDto dto) {

        Set<ConstraintViolation<LinkCreateDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<LinkCreateDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(" ");
            }
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_IS_NULL, utils.toErrorParams(sb)));
        }

        if (dto.getStartIndex() > dto.getEndIndex()) {
            throw new ValidationException("The endIndex cannot be greater from startIndex!");
        }

        _Article article = articleRepository.find(dto.getArticleId());
        if (utils.isEmpty(article)){
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("Article", dto.getArticleId())));
        }
        _Link link = linkMapper.fromCreateDto(dto);

        link.setArticle(article);
        baseValidation(link);

        repository.save(link);

        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(link)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<LinkDto>> update(final LinkUpdateDto dto) {

        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Rate"));
        }

        _Link link = repository.find(dto.getId());

        validate(link, dto.getId());

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            objectMapper.updateValue(link, dto);
        } catch (JsonMappingException e) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_IS_NOT_UPDATED, utils.toErrorParams("Link")));
        }

        repository.update(link);
        repository.save(link);

        return get(dto.getId());

    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        _Link link = repository.find(id);
        validate(link, id);
        repository.delete(link);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(Long articleId) {
        List<_Link> linkList = repository.findAll(LinkCriteria.childBuilder().articleId(articleId).build());

        linkList.forEach(repository::delete);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public void baseValidation(@NotNull _Link entity) {
        if (utils.isEmpty(entity.getArticle())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("Article", "Link")));
        }
        if (utils.isEmpty(entity.getLink())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("link text", "Link")));
        }
    }

    @Override
    public void validate(@NotNull _Link entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Link with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("Link", id)));
        }
    }

}
