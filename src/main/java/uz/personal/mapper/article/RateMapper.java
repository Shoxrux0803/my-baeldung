package uz.personal.mapper.article;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.article._Post;
import uz.personal.domain.article._Rate;
import uz.personal.dto.article.PostDto;
import uz.personal.dto.article.RateCreateDto;
import uz.personal.dto.article.RateDto;
import uz.personal.dto.article.RateUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ArticleMapper.class})
@Component
public interface RateMapper extends BaseMapper<_Rate, RateDto, RateCreateDto, RateUpdateDto> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "user.id", target = "userId")
    RateDto toDto(_Rate entity);
}
