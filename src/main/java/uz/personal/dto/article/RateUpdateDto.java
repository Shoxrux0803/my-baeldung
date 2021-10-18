package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import uz.personal.dto.GenericCrudDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ApiModel(value = "Rate update request")
public class RateUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Range(min = 1, message = "Rate Id ")
    Long id;

    @ApiModelProperty(required = true)
    @Range(min = 0, max = 5, message = "Rate ")
    Short rate;

    @ApiModelProperty(required = true)
    Long articleId;

    @ApiModelProperty(required = true)
    Long userId;

}
