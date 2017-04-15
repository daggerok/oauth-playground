package daggerok.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("user '%S' wasn't found.", username)));
    }
}
