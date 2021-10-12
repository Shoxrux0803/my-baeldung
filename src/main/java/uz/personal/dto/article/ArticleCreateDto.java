package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.personal.dto.GenericCrudDto;
import uz.personal.dto.GenericDto;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Article create request")
public class ArticleCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(min = 10, message = " min size %s")
    String text;

//    List<LinkCreateDto> links;

    GenericDto user;

}
