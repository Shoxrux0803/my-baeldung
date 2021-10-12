package uz.personal.dto.article;


import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.dto.GenericDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LinkDto extends GenericDto {

    String link;

    Integer startIndex;

    Integer endIndex;

    Long articleId;
//    List<_Article> article;

    @Builder(builderMethodName = "childBuilder")
    public LinkDto(Long id, String link, Integer startIndex, Integer endIndex,Long articleId) {
        super(id);
        this.link = link;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.articleId=articleId;
//        this.article = article;
    }


}
