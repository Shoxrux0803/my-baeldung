package uz.personal.service.rate.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.rate.RateCriteria;
import uz.personal.domain.article._Article;
import uz.personal.domain.rate._Rate;
import uz.personal.dto.GenericDto;
import uz.personal.dto.rate.RateCreateDto;
import uz.personal.dto.rate.RateDto;
import uz.personal.dto.rate.RateUpdateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.rate.RateMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.rate.IRateRepository;
import uz.personal.repository.rate.impl.RateRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.rate.IRateService;
import uz.personal.utils.BaseUtils;

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


    @Autowired
    public RateService(IRateRepository repository, BaseUtils utils, IErrorRepository errorRepository, RateMapper rateMapper, GenericMapper genericMapper, IUserRepository userRepository, IUserRepository userRepository1, IArticleRepository articleRepository) {
        super(repository, utils, errorRepository);
        this.rateMapper = rateMapper;
        this.genericMapper = genericMapper;
//        this.rateRepository = rateRepository;
        this.articleRepository = articleRepository;
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
        if (dto.getArticleId() == null) {
            throw new RuntimeException("ArticleId siz umuman iloji yo'q!");
        }
        _Article article = articleRepository.find(dto.getArticleId());
        if (article == null)
            throw new RuntimeException("Article topilmadi.");
        _Rate rate = rateMapper.fromCreateDto(dto);
        baseValidation(rate);

        rate.setArticle(article);
        repository.save(rate);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(rate)), HttpStatus.CREATED);
    }


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

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        _Rate rate = repository.find(id);
        validate(rate, id);
        repository.delete(rate);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<DataDto<Double>> avgRate(@NotNull GenericDto dto) {
        RateCriteria rateCriteria = new RateCriteria();
        rateCriteria.setArticleId(dto.getId());
        List<_Rate> rate = repository.findAll(rateCriteria);

        _Article article = articleRepository.find(dto.getId());
        assert rate != null;
        Double rateArticle = (rate.stream().mapToLong(_Rate::getRate).sum()) * 1. / rate.size();
        article.setRate(rateArticle);
        return new ResponseEntity<>(new DataDto<>(article.getRate()), HttpStatus.OK);

    }

    @Override
    public void baseValidation(@NotNull _Rate entity) {
        if (utils.isEmpty(entity.getRate()))
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("rate", "Rate")));
    }

    @Override
    public void validate(@NotNull _Rate entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Rate with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_NOT_FOUND_ID, utils.toErrorParams("Player", id)));
        }
    }
}
