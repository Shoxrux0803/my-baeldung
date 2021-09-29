package uz.personal.domain.article;

import lombok.*;
import uz.personal.domain.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "link")
public class _Link extends Auditable {

    @Column
    String link;

    Integer startIndex;

    Integer endIndex;

    @ManyToOne
    _Article article;

}
