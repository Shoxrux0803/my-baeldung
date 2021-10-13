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

//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Sonni kiriting:\t");
//        long n = scanner.nextInt();
//        long m = n / 2 + 1;
//        for (long i = 0; i < n * 2 - 1; i++) {
//            if (m >= i) {
//                for (long j = 0; j <= i; j++) {
//                    System.out.print(" *");
//                }
//            } else {
//                for (long j = 0; j < 2 * n - 1 - i; j++) {
//                    System.out.print(" *");
//                }
//            }
//            System.out.println();
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
