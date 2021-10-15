package uz.personal.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.Size;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ApiModel(value = "Article update request")
public class ArticleUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    Long id;

    @ApiModelProperty(required = true)
    String title;

    @ApiModelProperty(required = true)
    String text;

    @ApiModelProperty(required = true)
    List<LinkUpdateDto> linkUpdateDtoList;

    @ApiModelProperty(required = true)
    Boolean allowPublication;

    @ApiModelProperty(required = true)
    Boolean allowComment;

}
