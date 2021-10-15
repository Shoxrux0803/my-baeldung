package uz.personal.dto.article;

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

      Long articleId;

      Long userId;

    // ArticleDto article;

    @Builder(builderMethodName = "childBuilder")
    public RateDto(Long id, Integer rate, Long articleId, Long userId) {
        super(id);
        this.rate = rate;
        this.articleId = articleId;
        this.userId = userId;
    }
}
