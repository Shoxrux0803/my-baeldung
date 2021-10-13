package uz.personal.domain.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;
import uz.personal.domain.Auditable;
import uz.personal.domain.article._Article;
import uz.personal.domain.article._Post;
import uz.personal.enums.UserType;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class _User extends Auditable {

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String middleName;

    @Enumerated(EnumType.STRING)
    UserType userType;

    @Column(unique = true)
    String username;

    @Column
    String password;

    @Column(unique = true)
    String email;

    @Column(unique = true)
    String phone;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    boolean locked;

    @Column(name = "is_system_admin", columnDefinition = "boolean default false")
    boolean systemAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<_Article> article;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<_Post> commentList;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    List<_Role> roles;

    public String getShortName() {
        return String.format("%s %s",
                StringUtils.isEmpty(lastName) ? "" : lastName,
                StringUtils.isEmpty(firstName) ? "" : firstName.substring(0, 1));
    }
}
