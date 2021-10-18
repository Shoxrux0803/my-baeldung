package uz.personal.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.personal.domain.article._Link;
import uz.personal.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto extends GenericDto {

    String text;

    String title;

//    Boolean allowPublication;

    Boolean allowComment;

    Long rate;

    List<LinkDto> links;

    List<PostDto> postDtoList;

//    List<RateDto> rateDtoList;

    Long viewCount;

//    List<_User> user;

}
