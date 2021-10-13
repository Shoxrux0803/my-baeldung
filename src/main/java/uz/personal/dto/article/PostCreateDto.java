package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Post create request")
@FieldDefaults(level = PRIVATE)
public class PostCreateDto extends GenericCrudDto {

    @NotEmpty
    String content;

    @NotEmpty
    Long userId;

    @NotEmpty
    Long articleId;

}
