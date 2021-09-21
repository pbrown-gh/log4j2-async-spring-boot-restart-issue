package org.pbrowngh.discoveryserver;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(DiscoveryServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
