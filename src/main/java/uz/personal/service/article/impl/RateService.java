package uz.personal.service.article.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.article.LinkCriteria;
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

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @Autowired
    public RateService(IRateRepository repository, BaseUtils utils, IErrorRepository errorRepository, RateMapper rateMapper, GenericMapper genericMapper, IArticleRepository articleRepository, IUserRepository userRepository) {
        super(repository, utils, errorRepository);
        this.rateMapper = rateMapper;
        this.genericMapper = genericMapper;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
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
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull RateCreateDto dto) {

        // todo validatsiya jarayonida null ga tekshirmayapti ???

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

        avgRate(rate.getArticle().getId(), article);
//        rate.setArticle(article);
        repository.save(rate);

        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(rate)), HttpStatus.CREATED);
    }


    @Transactional
    @Override
    public ResponseEntity<DataDto<RateDto>> update(@NotNull RateUpdateDto dto) {
        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Rate"));
        }
        _Rate rate = repository.find(dto.getId());
        validate(rate, dto.getId());

        rate = rateMapper.fromUpdateDto(dto, rate);
        baseValidation(rate);

        rate.setRate(rate.getRate());
        rate.setArticle(rate.getArticle());
        repository.save(rate);

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


    @Override
    public ResponseEntity<DataDto<Double>> avgRate(@NotNull Long dto, final _Article article) {
        RateCriteria rateCriteria = new RateCriteria(); // todo criteria ni final qilib olsam boladimi
        rateCriteria.setArticleId(dto);
        List<_Rate> rate = repository.findAll(rateCriteria);

        assert rate != null;
        Double rateArticle = (rate.stream().mapToDouble(_Rate::getRate).sum()) / rate.size();
        article.setRate(rateArticle);
        articleRepository.update(article);

        return new ResponseEntity<>(new DataDto<>(article.getRate()), HttpStatus.OK);

    }

    @Transactional
    @Override
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(Long articleId) {
        List<_Rate> rateList = repository.findAll(RateCriteria.childBuilder().articleId(articleId).build());

        rateList.forEach(repository::delete);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
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

//    @Override
//    public Double avgRate(Long article) {
//        try {
//            return (_User) entityManager.createQuery("SELECT t FROM _User t WHERE t.id = '" + id + "' ORDER BY t.id DESC ").getSingleResult();
//        } catch (NoResultException ex) {
//            logger.error(ex.getMessage());
//            throw new RuntimeException(String.format("User with id '%s' not found", id));
//        }
//    }

}
