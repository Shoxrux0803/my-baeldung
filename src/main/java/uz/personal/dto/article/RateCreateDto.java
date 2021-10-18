package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "Rate create request")
public class RateCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Range(min = 0, max = 5, message = "Rate ")
    Long rate;

    @NotNull(message = "Article ")
    @NotEmpty(message = "Article ")
    Long articleId;

    @NotNull(message = "User ")
    @NotEmpty(message = "User ")
    Long userId;
}
