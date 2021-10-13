package uz.personal.mapper.article;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.article._Link;
import uz.personal.dto.article.LinkCreateDto;
import uz.personal.dto.article.LinkDto;
import uz.personal.dto.article.LinkUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ArticleMapper.class})
@Component
public interface LinkMapper extends BaseMapper<_Link, LinkDto, LinkCreateDto, LinkUpdateDto> {

    @Mapping(source = "article.id", target = "articleId")
    LinkDto toDto(_Link link);

}