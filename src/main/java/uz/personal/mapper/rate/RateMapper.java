package uz.personal.mapper.rate;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.auth._Permission;
import uz.personal.domain.rate._Rate;
import uz.personal.dto.auth.PermissionCreateDto;
import uz.personal.dto.auth.PermissionDto;
import uz.personal.dto.auth.PermissionUpdateDto;
import uz.personal.dto.rate.RateCreateDto;
import uz.personal.dto.rate.RateDto;
import uz.personal.dto.rate.RateUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface RateMapper extends BaseMapper<_Rate, RateDto, RateCreateDto, RateUpdateDto> {
}
