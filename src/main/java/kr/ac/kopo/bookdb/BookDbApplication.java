package kr.ac.kopo.bookdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookDbApplication.class, args);
    }

}
