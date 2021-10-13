package uz.personal.domain.article;

import lombok.*;
import uz.personal.domain.Auditable;
import uz.personal.domain.auth._User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "article")
public class _Article extends Auditable {

    @Column(columnDefinition = "text")
    String text;

    // maqolani ko`rishga ruxsat berish
    @Column(columnDefinition = "boolean default false")
    Boolean allowPublication;

    // commentga ruxsat
    @Column(columnDefinition = "boolean default false")
    Boolean allowComment;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    List<_Link> links;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    _User user;

    @Column
    Long rate;

}