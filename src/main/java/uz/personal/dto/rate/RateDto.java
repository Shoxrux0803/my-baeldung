package uz.personal.dto.rate;

import lombok.*;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.auth.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateDto extends GenericDto {

      Integer rate;


    // ArticleDto article;

      @Builder(builderMethodName = "childBuilder")
    public RateDto(Long id, Integer rate, ArticleDto article) {
        super(id);
        this.rate = rate;
       // this.article = article;
    }
}
