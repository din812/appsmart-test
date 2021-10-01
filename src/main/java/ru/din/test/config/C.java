package ru.din.test.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "ru.din.test")
@EntityScan(basePackages = "ru.din.test")
@EnableJpaRepositories(basePackages = "ru.din.test")
@Slf4j
public class C {
    private static final Logger logger = LoggerFactory.getLogger(C.class);

    @Value("${db.driver}")
    public String DB_DRIVER;
    @Value("${db.url}")
    public String DB_URL;
    @Value("${db.login}")
    public String DB_LOGIN;
    @Value("${db.password}")
    public String DB_PASSWORD;
    @Value("${db.pool.size}")
    public int DB_POOL_SIZE;
    @Value("${db.min.idle}")
    public int DB_MIN_IDLE;


    @Bean
    @Order(1)
    DataSource dataSource(C config) {
        logger.info("init DataSource start");
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(config.DB_DRIVER);
        hikariDataSource.setJdbcUrl(config.DB_URL);
        hikariDataSource.setUsername(config.DB_LOGIN);
        hikariDataSource.setPassword(config.DB_PASSWORD);
        hikariDataSource.setMaximumPoolSize(config.DB_POOL_SIZE);
        hikariDataSource.setMinimumIdle(config.DB_MIN_IDLE);
        hikariDataSource.setAutoCommit(false);
        hikariDataSource.setConnectionTestQuery("select 1");
        hikariDataSource.setConnectionInitSql("SET timezone TO 'Europe/Moscow'");
        logger.info("init DataSource done");
        return hikariDataSource;
    }

    @SneakyThrows
    @PostConstruct
    public void write() {

        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                if (field.getType() == String.class) {
                    Object v = field.get(this);
                    if (v != null) {
                        String v2 = (String) v;
                        String v3 = new String(v2.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        field.set(this,v3);
                    }
                }
            }
        }
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotation = field.getDeclaredAnnotation(Value.class);
                String name = annotation.value();
                name = name.substring("${".length());
                name = name.substring(0, name.length() - "}".length());
                Object value = field.get(this);
                if ("NULL".equals(value)) {
                    field.set(this, value = null);
                }
                names.add(name);
                values.add(String.valueOf(value));
            }
        }

        int sizeColumnName = names.stream().mapToInt(String::length).max().orElse(0) + 1;
        int sizeColumnValue = values.stream().mapToInt(String::length).max().orElse(0) + 1;

        String format = "\n" + "%-" + sizeColumnName + "s" + " " + "%-" + sizeColumnValue + "s" + "";

        StringBuilder stringBuilder = new StringBuilder("PROPERTIES")
                .append(String.format(format, "name", "value"));

        int n = names.size();

        for (int i = 0; i < n; i++) {
            String name = names.get(i);
            String value = values.get(i);
            stringBuilder.append(String.format(format, name, value));
        }

        log.info(stringBuilder.toString());
    }
}
