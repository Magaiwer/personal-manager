package br.univates.magaiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(value = JwtConfiguration.class)
public class ServerSideApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSideApplication.class, args);
    }

}
