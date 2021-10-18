package uz.personal.mapper.article;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.personal.domain.article._Post;
import uz.personal.dto.article.PostCreateDto;
import uz.personal.dto.article.PostDto;
import uz.personal.dto.article.PostUpdateDto;
import uz.personal.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ArticleMapper.class})
@Component
public interface PostMapper extends BaseMapper<_Post, PostDto, PostCreateDto, PostUpdateDto> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "user.id", target = "userId")
    PostDto toDto(_Post entity);

}
