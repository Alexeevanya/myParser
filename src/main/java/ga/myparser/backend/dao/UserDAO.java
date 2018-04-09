package ga.myparser.backend.dao;

import ga.myparser.backend.domain.User;

public interface UserDAO {
    User findByLogin(String login);
}
