package uz.personal.dto.article;

import io.swagger.annotations.ApiModelProperty;
import uz.personal.dto.GenericCrudDto;
import uz.personal.dto.GenericDto;

import javax.validation.constraints.Size;

public class ArticleUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    @Size(min = 10, message = " min size %s")
    String text;

}
