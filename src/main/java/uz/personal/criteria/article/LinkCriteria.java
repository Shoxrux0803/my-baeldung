package uz.personal.criteria.article;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.criteria.GenericCriteria;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LinkCriteria extends GenericCriteria {

    String link;

    Integer startIndex;

    Integer endIndex;

    Long articleId;

    @Builder(builderMethodName = "childBuilder")
    public LinkCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String link, Integer startIndex, Integer endIndex, Long articleId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.link = link;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.articleId = articleId;
    }
}
