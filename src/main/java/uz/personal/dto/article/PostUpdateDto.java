package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ApiModel(value = "Post update request")
public class PostUpdateDto extends GenericCrudDto {

    @ApiModelProperty
    Long id;

//    @ApiModelProperty
    Long articleId;

//    @ApiModelProperty
    Long userId;

    @ApiModelProperty
    String content;
}
