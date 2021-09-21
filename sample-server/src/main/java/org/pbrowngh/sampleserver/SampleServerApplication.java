package org.pbrowngh.sampleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableDiscoveryClient
public class SampleServerApplication {

    private static ConfigurableApplicationContext configurableApplicationContext;

    public static void main(final String[] args) {
        try {
            configurableApplicationContext = SpringApplication.run(SampleServerApplication.class,
                    args);
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PreDestroy
    public void clearContext() {
        configurableApplicationContext.close();
    }
}
