package uz.personal.criteria.article;

import lombok.*;
import uz.personal.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCriteria extends GenericCriteria {

    String text;

    Boolean allowPublication;

    Boolean allowComment;

    Long rate;

    @Builder(builderMethodName = "childBuilder")
    public ArticleCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String text, Boolean allowPublication, Long rate) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.text = text;
        this.allowPublication = allowPublication;
        this.rate= rate;
    }
}
