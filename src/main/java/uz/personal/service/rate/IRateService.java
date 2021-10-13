package uz.personal.service.rate;

import org.springframework.http.ResponseEntity;
import uz.personal.criteria.auth.PermissionCriteria;
import uz.personal.criteria.rate.RateCriteria;
import uz.personal.domain.auth._Permission;
import uz.personal.domain.rate._Rate;
import uz.personal.dto.GenericDto;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.dto.rate.RateCreateDto;
import uz.personal.dto.rate.RateDto;
import uz.personal.dto.rate.RateUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.IGenericCrudService;

public interface IRateService extends IGenericCrudService<_Rate, RateDto, RateCreateDto, RateUpdateDto, Long, RateCriteria> {
    ResponseEntity<DataDto<Double>> avgRate(GenericDto dto);
}
