package com.atom.personnel;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//localhost:8080/register

@SpringBootApplication
public class PersonnelApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PersonnelApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run();
    }

}

/*
If you need to package the project as war and run in Tomcat,
please use the following code

@SpringBootApplication
public class PersonnelApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HomeworkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
}
 */