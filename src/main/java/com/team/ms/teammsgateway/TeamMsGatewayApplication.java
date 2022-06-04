package com.team.ms.teammsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EntityScan("com.tms.common.*")
@ComponentScan({"com.tms.common.*", "com.team.ms.teammsgateway.*"})
@EnableJpaRepositories({"com.tms.common.*", "com.team.ms.teammsgateway.*"})
public class TeamMsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamMsGatewayApplication.class, args);
    }


}
