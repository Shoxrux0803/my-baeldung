package uz.personal.domain.article;

import lombok.*;
import uz.personal.domain.Auditable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "_link")
public class _Link extends Auditable {

    @Column
    String link;

    Integer startIndex;

    Integer endIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    _Article article;

}
