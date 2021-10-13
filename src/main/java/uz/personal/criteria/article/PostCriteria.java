package uz.personal.criteria.article;


import lombok.*;
import uz.personal.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCriteria extends GenericCriteria {
    String content;

    Long userId;

    Long articleId;

    @Builder(builderMethodName = "childBuilder")
    public PostCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String content, Long userId, Long articleId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.content = content;
        this.userId = userId;
        this.articleId = articleId;
    }

}
