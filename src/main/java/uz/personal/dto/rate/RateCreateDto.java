package uz.personal.dto.rate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "_Rate create request")
public class RateCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    Integer rate;

    Long articleId;

}
