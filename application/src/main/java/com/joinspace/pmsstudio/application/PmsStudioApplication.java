package com.joinspace.pmsstudio.application;

import com.joinspace.pmsstudio.application.config.ApplicationProperties;
import com.joinspace.pmsstudio.application.util.baseutil.DefaultProfileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;

import static com.joinspace.pmsstudio.application.util.constant.AppConstants.SPRING_PROFILE_CLOUD;
import static com.joinspace.pmsstudio.application.util.constant.AppConstants.SPRING_PROFILE_DEVELOPMENT;
import static com.joinspace.pmsstudio.application.util.constant.AppConstants.SPRING_PROFILE_PRODUCTION;

@SpringBootApplication
@ComponentScan({"com.joinspace.pmsstudio"})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EnableJpaRepositories({"com.joinspace.pmsstudio.repository"})
@EntityScan({"com.joinspace.pmsstudio.domain"})
@Slf4j
public class PmsStudioApplication {

    private final Environment env;

    public PmsStudioApplication(Environment env) {
        this.env = env;
    }

    /**
     * Initializes one.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                    "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }


    public static void main(String[] args) {
           // SpringApplication.run(PmsStudioApplication.class, args);
        SpringApplication app = new SpringApplication(PmsStudioApplication.class);

        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                hostAddress,
                env.getProperty("server.port"),
                env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        log.info("\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
    }
}
