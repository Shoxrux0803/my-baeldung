package uz.personal.criteria.rate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.criteria.GenericCriteria;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)

public class RateCriteria extends GenericCriteria {

    Integer rate;

    Long articleId;

    @Builder(builderMethodName = "childBuilder")
    public RateCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, Integer rate, Long articleId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.rate = rate;
        this.articleId = articleId;
    }
}
