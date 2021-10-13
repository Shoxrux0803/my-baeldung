package uz.personal.domain.auth;

import lombok.*;
import org.springframework.util.StringUtils;
import uz.personal.domain.Auditable;
import uz.personal.domain.article._Article;
import uz.personal.enums.UserType;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_users")
public class _User extends Auditable {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String middleName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    private boolean locked;

    @Column(name = "is_system_admin", columnDefinition = "boolean default false")
    private boolean systemAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<_Article> article;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<_Role> roles;

    public String getShortName() {
        return String.format("%s %s",
                StringUtils.isEmpty(lastName) ? "" : lastName,
                StringUtils.isEmpty(firstName) ? "" : firstName.substring(0, 1));
    }
}
