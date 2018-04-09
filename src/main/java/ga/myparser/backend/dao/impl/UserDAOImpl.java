package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.UserDAO;
import ga.myparser.backend.domain.User;
import ga.myparser.backend.domain.emums.Role;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Override
    public User findByLogin(String login) {
        if(login.equals("Admin")){
            User user = new User();
            user.setId(1);
            user.setLogin("login");
            user.setPass("$2a$10$fM5KzZA4uBhmbwEsLXC8tu5UZeRjt45W.mBJ3IWq2htGGabQyPKDy");
            user.setRole(Role.ROLE_ADMIN);
            return user;
        } else {
            return null;
        }
    }
}
