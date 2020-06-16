package org.nerdcore.phylactory;

import org.nerdcore.phylactory.controller.MainViewController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PhylactoryApplication {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(PhylactoryApplication.class);
        builder.web(WebApplicationType.NONE);
        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
        MainViewController.initView();

//        new SpringApplicationBuilder(PhylactoryApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
        //SpringApplication.run(PhylactoryApplication.class, args);;
    }

}
