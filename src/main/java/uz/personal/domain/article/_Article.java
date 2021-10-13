package uz.personal.domain.article;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.personal.domain.Auditable;
import uz.personal.domain.auth._User;
import uz.personal.domain.rate._Rate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "article")
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    List<_Rate> rateList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    _User user;

    @Column
    Double rate;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    List<_Post> comments;

}