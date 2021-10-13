package uz.personal.dto.article;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericCrudDto;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Link create request")
@FieldDefaults(level = PRIVATE)
public class LinkCreateDto extends GenericCrudDto {

    @NotNull
    String link;

    @NotNull
    Integer startIndex;

    @NotNull
    Integer endIndex;

    @NotNull
    Long articleId;

}
