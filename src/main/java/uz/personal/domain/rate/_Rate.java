package uz.personal.domain.rate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.domain.Auditable;
import uz.personal.domain.article._Article;
import uz.personal.domain.auth._User;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "rate")
@FieldDefaults(level = PRIVATE)
public class _Rate extends Auditable {

  //  @Size(min = 1 , max = 5)
    Integer rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    _Article article;



}
