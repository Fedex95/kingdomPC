package com.tienda.kpback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

/**
 * Clase principal para iniciar la aplicación Spring Boot.
 */
@OpenAPIDefinition(
    info = @Info(
        title = "Kingdom PC API",
        version = "1.0",
        description = "API para la aplicación Kingdom PC"
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
@SpringBootApplication
public final class KpbackApplication {

    /**
     * Constructor privado para evitar instanciación.
     */
    private KpbackApplication() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Método principal de la aplicación.
     *
     * @param args argumentos de la aplicación.
     */
    public static void main(final String[] args) {
        SpringApplication.run(KpbackApplication.class, args);
    }
}
