package uz.personal.dto.article;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PostDto extends GenericDto {

    String content;

    Long userId;

    Long articleId;

    @Builder(builderMethodName = "childBuilder")
    public PostDto(Long id, String content, Long userId, Long articleId) {
        super(id);
        this.content = content;
        this.userId = userId;
        this.articleId = articleId;
    }

}
