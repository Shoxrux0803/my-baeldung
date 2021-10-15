package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "Rate create request")
public class RateCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(max = 5, message = "Rate must be between 0 and 5")
    Integer rate;

    @NotBlank(message = "Article id cannot be null!")
//    @NotEmpty(message = "Article id cannot be empty!")
    Long articleId;

    @NotBlank(message = "User id cannot be null!")
//    @NotEmpty(message = "User id cannot be empty!")
    Long userId;
}
