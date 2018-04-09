package ga.myparser.backend.domain;

import ga.myparser.backend.domain.emums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class User {

    private int id;
    private String login;
    private String pass;

    @Enumerated(EnumType.STRING)
    private Role role;

}
