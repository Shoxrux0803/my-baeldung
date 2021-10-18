package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.personal.dto.GenericCrudDto;
import uz.personal.dto.GenericDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Article create request")
public class ArticleCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(min = 10, message = "Minimum size of article must be 10!")
    String text;

    @NotNull(message = "Title cannot be null!")
    @NotEmpty(message = "Title cannot be empty!")
    String title;
//    List<LinkCreateDto> links;

    @NotNull(message = "User id cannot be null!")
    @NotEmpty(message = "User id cannot be empty!")
    GenericDto user;

}
