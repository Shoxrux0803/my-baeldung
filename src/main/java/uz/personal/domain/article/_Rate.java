package uz.personal.domain.article;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.domain.Auditable;
import uz.personal.domain.auth._User;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_rate")
@FieldDefaults(level = PRIVATE)
public class _Rate extends Auditable {

    Integer rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    _Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    _User user;

}
