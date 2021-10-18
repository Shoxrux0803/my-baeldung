package uz.personal.service.article;

import org.springframework.http.ResponseEntity;
import uz.personal.criteria.article.RateCriteria;
import uz.personal.domain.Auditable;
import uz.personal.domain.article._Article;
import uz.personal.domain.article._Rate;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.RateCreateDto;
import uz.personal.dto.article.RateDto;
import uz.personal.dto.article.RateUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.IGenericCrudService;

public interface IRateService extends IGenericCrudService<_Rate, RateDto, RateCreateDto, RateUpdateDto, Long, RateCriteria> {
    ResponseEntity<DataDto<Double>> avgRate(Long articleId);

    ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(Long articleId);
}
