package uz.personal.service.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.article.RateCriteria;
import uz.personal.domain.article._Article;
import uz.personal.domain.article._Rate;
import uz.personal.domain.auth._User;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.RateCreateDto;
import uz.personal.dto.article.RateDto;
import uz.personal.dto.article.RateUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.article.RateMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.article.IRateRepository;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.article.IRateService;
import uz.personal.utils.BaseUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service(value = "rateService")
public class RateService extends GenericCrudService<_Rate, RateDto, RateCreateDto, RateUpdateDto, RateCriteria, IRateRepository> implements IRateService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final RateMapper rateMapper;
    private final GenericMapper genericMapper;
    private final IArticleRepository articleRepository;
    private final IUserRepository userRepository;
    private final EntityManager entityManager;
    private final Validator validator;

    @Autowired
    public RateService(IRateRepository repository, BaseUtils utils, IErrorRepository errorRepository, RateMapper rateMapper, GenericMapper genericMapper, IArticleRepository articleRepository, IUserRepository userRepository, EntityManager entityManager, Validator validator) {
        super(repository, utils, errorRepository);
        this.rateMapper = rateMapper;
        this.genericMapper = genericMapper;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<DataDto<RateDto>> get(Long id) {
        _Rate rate = repository.find(RateCriteria.childBuilder().selfId(id).build());
        validate(rate, id);
        return new ResponseEntity<>(new DataDto<>(rateMapper.toDto(rate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<RateDto>>> getAll(RateCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(
                new DataDto<>(rateMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<GenericDto>> create(RateCreateDto dto) {

        Set<ConstraintViolation<RateCreateDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RateCreateDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(" ");
            }
            throw new ValidationException(sb.toString());
        }

        _Article article = articleRepository.find(dto.getArticleId());
        if (utils.isEmpty(article)) {
            logger.error(String.format("Article with id '%s' not found", dto.getArticleId()));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("Article", dto.getArticleId())));
        }

        _User user = userRepository.find(dto.getUserId());
        if (utils.isEmpty(user)) {
            logger.error(String.format("User with id '%s' not found", dto.getUserId()));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("User", dto.getUserId())));
        }

        _Rate rate = rateMapper.fromCreateDto(dto);
        baseValidation(rate);

        rate.setUser(user);
        rate.setArticle(article);
        repository.save(rate);

        ResponseEntity<DataDto<Double>> dataDtoResponseEntity = avgRate(rate.getArticle().getId());
        article.setRate(Objects.requireNonNull(dataDtoResponseEntity.getBody()).getData());
//        articleRepository.update(article);
        articleRepository.save(article);

        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(rate)), HttpStatus.CREATED);
    }


    @Transactional
    @Override
    public ResponseEntity<DataDto<RateDto>> update(@NotNull RateUpdateDto dto) {

        Set<ConstraintViolation<RateUpdateDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RateUpdateDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(" ");
            }
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_IS_NULL, utils.toErrorParams(sb)));
        }

        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Rate"));
        }
        _Rate rate = repository.find(dto.getId());
        validate(rate, dto.getId());

        rate = rateMapper.fromUpdateDto(dto, rate);
        baseValidation(rate);

        rate.setRate(rate.getRate());
        _Article article = rate.getArticle();
        rate.setArticle(article);
        repository.save(rate);

        ResponseEntity<DataDto<Double>> dataDtoResponseEntity = avgRate(rate.getArticle().getId());
        article.setRate(Objects.requireNonNull(dataDtoResponseEntity.getBody()).getData());
        articleRepository.save(article);

        return get(rate.getId());
    }

    @Transactional
    @Override
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _Rate rate = repository.find(id);
        validate(rate, id);
        repository.delete(rate);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(Long articleId) {
        List<_Rate> rateList = repository.findAll(RateCriteria.childBuilder().articleId(articleId).build());
        rateList.forEach(repository::delete);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Double>> avgRate(Long articleId) {
        try {
            Double rate = (double) Math.round((Double) entityManager.createQuery("SELECT (cast(sum(t.rate) as double)) / count(*) FROM _Rate t WHERE t.article = '" + articleId + "'").getSingleResult() * 10) / 10;
            return new ResponseEntity<>(new DataDto<>(rate), HttpStatus.OK);
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("Rate with this article id '%s' not found", articleId));
        }
    }

    @Override
    public void baseValidation(@NotNull _Rate entity) {
        if (utils.isEmpty(entity.getRate())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("rate", "Rate")));
        }
    }

    @Override
    public void validate(_Rate entity, Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Rate with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("Rate", id)));
        }
    }

}
