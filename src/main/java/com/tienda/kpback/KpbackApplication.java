package com.tienda.kpback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque de la aplicación Spring Boot.
 */
@SpringBootApplication
public final class KpbackApplication {

    private KpbackApplication() {
    }

    /**
     * Punto de entrada de la aplicación.
     * @param args argumentos de línea de comandos
     */
    public static void main(final String[] args) {
        SpringApplication.run(KpbackApplication.class, args);
    }
}
