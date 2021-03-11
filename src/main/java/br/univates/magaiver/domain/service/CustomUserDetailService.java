/*
package br.univates.magaiver.domain.service;

import com.mvp.serverside.br.univates.magaiver.domain.model.User;
import com.mvp.serverside.br.univates.magaiver.domain.repository.UserRepository;
import br.univates.magaiver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.br.univates.magaiver.core.GrantedAuthority;
import org.springframework.security.br.univates.magaiver.core.authority.SimpleGrantedAuthority;
import org.springframework.security.br.univates.magaiver.core.userdetails.UserDetails;
import org.springframework.security.br.univates.magaiver.core.userdetails.UserDetailsService;
import org.springframework.security.br.univates.magaiver.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(" search for user in the database");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
        return new org.springframework.security.br.univates.magaiver.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    public UserDetails loadUserWithoutPermissions(String email) throws UsernameNotFoundException {
        log.info(" search for just user in the database");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
        return new org.springframework.security.br.univates.magaiver.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        log.info(" add authorities for the user");
        userRepository.getPermissions(user.getId())
                .forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
        return authorities;
    }
}
*/
