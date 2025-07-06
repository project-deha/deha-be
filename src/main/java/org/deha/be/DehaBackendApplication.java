package org.deha.be;

import lombok.extern.slf4j.Slf4j;
import org.deha.be.service.mcp.PredictedEarthquakeMCPService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DehaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DehaBackendApplication.class, args);
        log.info("Swagger UI: http://localhost:8080/swagger-ui.html");
    }

    @Bean
    public ToolCallbackProvider predictedEarthquakeTools(PredictedEarthquakeMCPService predictedEarthquakeMCPService) {
        return MethodToolCallbackProvider.builder().toolObjects(predictedEarthquakeMCPService).build();
    }
}
