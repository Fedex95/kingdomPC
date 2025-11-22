package com.tienda.kpback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public final class KpbackApplication {

    private KpbackApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(KpbackApplication.class, args);
    }
}
