package uz.personal.domain.article;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.domain.Auditable;
import uz.personal.domain.auth._User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class _Post extends Auditable {

    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    _User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    _Article article;
}
