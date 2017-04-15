package daggerok;

import config.AuthorizationServerConfig;
import daggerok.account.Account;
import daggerok.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(AuthorizationServerConfig.class)
public class AuthorizationServiceApplication {

    @Bean
    CommandLineRunner init(AccountRepository accountRepository) {

        if (accountRepository.count() > 0) {
            return args -> log.info("{} users exists", accountRepository.count());
        }

        return args -> accountRepository.save(
                new Account()
                        .setActive(true)
                        .setUsername("test")
                        .setPassword("test"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

    /*

    open rest client:
        POST http://localhost:9999/uaa/oauth/token
        Headers:
            Authentication: Basic aHRtbDpwd2Q= (html:pwd)
            Accept: application/json
        Request Parameters:
            username=test
            password=test
            client=html
            grant_type=password
            secret=pwd
            scope=openid

    or
        http --auth html:pwd --form post :9999/uaa/oauth/token username=test password=test client=html grant_type=password secret=pwd scope=openid

    {
        "access_token":"98ecfa5e-80c0-4b9a-856e-fd74db9b2ad6",
        "token_type":"bearer",
        "expires_in":42510,
        "scope":"openid"
    }

    */
}
