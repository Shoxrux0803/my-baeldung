package uz.personal.service.article;

import org.springframework.http.ResponseEntity;
import uz.personal.criteria.article.PostCriteria;
import uz.personal.domain.article._Post;
import uz.personal.dto.article.PostCreateDto;
import uz.personal.dto.article.PostDto;
import uz.personal.dto.article.PostUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.IGenericCrudService;

public interface IPostService extends IGenericCrudService<_Post, PostDto, PostCreateDto, PostUpdateDto, Long, PostCriteria> {

    ResponseEntity<DataDto<Boolean>> deleteAll(Long id);

}
