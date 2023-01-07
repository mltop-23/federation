package sevices;


import com.example.federation.domain.User;


import com.example.federation.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails= userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Not Found User")
        );

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(userDetails.getRoles().toString())));
    }
}