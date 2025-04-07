package org.deha.be;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DehaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DehaBackendApplication.class, args);
        log.info("Swagger UI: http://localhost:8080/swagger-ui.html");
    }
}
