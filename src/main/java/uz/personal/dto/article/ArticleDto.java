package uz.personal.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.personal.domain.article._Link;
import uz.personal.domain.auth._User;
import uz.personal.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto extends GenericDto {

    String text;

    boolean allowPublication;

    List<_Link> roles;

    List<_User> user;

}
