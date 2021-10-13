package uz.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Scanner;

@SpringBootApplication
public class CoreApplication extends SpringBootServletInitializer {

//    @Value("${spring.liquibase.change-log}")
//    private String changeLogDir;
//
//    @Autowired
//    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);




        }
//
//    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(CoreApplication.class);
//    }
//
//    @Bean
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog(changeLogDir);
//        liquibase.setDataSource(dataSource);
//        return liquibase;
//    }
}
