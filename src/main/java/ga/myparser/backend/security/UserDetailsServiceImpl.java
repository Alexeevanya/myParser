package ga.myparser.backend.security;

import ga.myparser.backend.dao.UserDAO;
import ga.myparser.backend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userDAO.findByLogin(login);
        if(user != null){
            String roleName = user.getRole().name();

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            List<SimpleGrantedAuthority> authority = Collections.singletonList(simpleGrantedAuthority);

            return new MyUserDetails(login, user.getPass(), authority);
        } else {
            throw new  UsernameNotFoundException(login + "not found");
        }
    }
}
