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
@ApiModel(value = "Link update request")
public class LinkUpdateDto extends GenericCrudDto {

    @ApiModelProperty
    @Range(min = 1, message = "Link id ")
    Long id;

    @ApiModelProperty
    String link;

    @ApiModelProperty
    Integer startIndex;

    @ApiModelProperty
    Integer endIndex;

    @ApiModelProperty
    Long articleId;
}

