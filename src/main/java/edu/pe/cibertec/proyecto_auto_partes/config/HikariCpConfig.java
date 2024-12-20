package edu.pe.cibertec.proyecto_auto_partes.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariCpConfig {

    @Value("${DB_WORLD_URL}")
    private String dbAutoPartesUrl;
    @Value("${DB_WORLD_USER}")
    private String dbAutoPartesUser;
    @Value("${DB_WORLD_PASS}")
    private String dbAutoPartesPass;
    @Value("${DB_WORLD_DRIVER}")
    private String dbAutoPartesDriver;

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();

        /**
         * Configurar propiedad de conexion a BD
         */
        config.setJdbcUrl(dbAutoPartesUrl);
        config.setUsername(dbAutoPartesUser);
        config.setPassword(dbAutoPartesPass);
        config.setDriverClassName(dbAutoPartesDriver);

        System.out.println("DB_WORLD_URL: " + dbAutoPartesUrl);
        System.out.println("DB_WORLD_USER: " + dbAutoPartesUser);
        System.out.println("DB_WORLD_PASS: " + dbAutoPartesPass);
        System.out.println("DB_WORLD_DRIVER: " + dbAutoPartesDriver);

        /**
         * Configurar propiedades del pool de HikariCP
         * - MaximumPoolSize: Máximo # de conexiones del pool.
         * - MinimumIdle: Mínimo # de conexiones inactivas del pool.
         * - IdleTimeout: Tiempo máximo de espera para
         *      eliminar una conexión inactiva.
         * - ConnectionTimeout: Tiempo máximo de espera
         *
         *      para conectarse a la BD.
         */
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(30000);

        System.out.println("###### HikariCP initialized ######");
        return new HikariDataSource(config);

    }


}

