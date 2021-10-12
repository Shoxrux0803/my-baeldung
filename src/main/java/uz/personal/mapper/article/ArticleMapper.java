package uz.personal.mapper.article;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.article._Article;
import uz.personal.dto.article.ArticleCreateDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.article.ArticleUpdateDto;
import uz.personal.mapper.BaseMapper;
import uz.personal.mapper.auth.RoleMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RoleMapper.class})
@Component
public interface ArticleMapper extends BaseMapper<_Article, ArticleDto, ArticleCreateDto, ArticleUpdateDto> {

    @Override
    ArticleDto toDto(_Article entity);


}
