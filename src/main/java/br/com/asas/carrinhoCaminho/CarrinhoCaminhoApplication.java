package br.com.asas.carrinhoCaminho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.asas.carrinhoCaminho.model")
@ComponentScan({"br.com.asas.carrinhoCaminho"})
@EnableJpaRepositories("br.com.asas.carrinhoCaminho.repository")
public class CarrinhoCaminhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrinhoCaminhoApplication.class, args);
    }
}
