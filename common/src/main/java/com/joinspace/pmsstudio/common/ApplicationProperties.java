package com.joinspace.pmsstudio.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import static com.joinspace.pmsstudio.common.util.constant.AppConstants.LOG_HEADER;

/**
 * Properties specific to One.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter @Setter @ToString
@Slf4j
public class ApplicationProperties {

    private final ApplicationProperties.Mail mail = new ApplicationProperties.Mail();
    private final ApplicationProperties.Swagger swagger = new ApplicationProperties.Swagger();
    private final ApplicationProperties.Metrics metrics = new ApplicationProperties.Metrics();
    private final ApplicationProperties.Logging logging = new ApplicationProperties.Logging();
    private final ApplicationProperties.Security security = new ApplicationProperties.Security();
    private final ApplicationProperties.Cache cache = new ApplicationProperties.Cache();

    private final CorsConfiguration cors = new CorsConfiguration();


    @Getter @Setter @ToString
    public static class Logging {
        @Getter @Setter @ToString
        public static class Logstash {
            private boolean enabled = false;
            private String host = "localhost";
            private int port = 5000;
            private int queueSize = 512;
        }
    }

    @Getter @Setter @ToString
    public static class Metrics {

        private final ApplicationProperties.Metrics.Jmx jmx = new ApplicationProperties.Metrics.Jmx();
        private final ApplicationProperties.Metrics.Logs logs = new ApplicationProperties.Metrics.Logs();

        @Getter @Setter @ToString
        public static class Logs {
            private boolean enabled = false;
            private long reportFrequency = 60L;

        }

        @Getter @Setter @ToString
        public static class Jmx {
            private boolean enabled = true;

        }
    }

    @Getter @Setter @ToString
    public static class Swagger {
        private String title = "Application API";
        private String description = "API documentation";
        private String version = "0.0.1";
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
        private String defaultIncludePattern;
        private String host;
        private String[] protocols;
        private boolean useDefaultResponseMessages;

        public Swagger() {
            this.defaultIncludePattern = "/api/.*";
            this.protocols = new String[0];
            this.useDefaultResponseMessages = true;
        }
    }

    @Getter @Setter @ToString
    public static class Mail {
        private boolean enabled = false;
        private String from = "";
        private String baseUrl = "";

    }

    @Getter @Setter @ToString
    public static class Security {
        private final ApplicationProperties.Security.ClientAuthorization clientAuthorization = new ApplicationProperties.Security.ClientAuthorization();
        private final ApplicationProperties.Security.Authentication authentication = new ApplicationProperties.Security.Authentication();
        private final ApplicationProperties.Security.RememberMe rememberMe = new ApplicationProperties.Security.RememberMe();

        @Getter @Setter @ToString
        public static class RememberMe {
            @NotNull
            private String key;
        }

        @Getter @Setter @ToString
        public static class Authentication {
            private final ApplicationProperties.Security.Authentication.Jwt jwt = new ApplicationProperties.Security.Authentication.Jwt();

            @Getter @Setter @ToString
            public static class Jwt {
                private String secret;
                private long tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe;

                public Jwt() {
                    this.tokenValidityInSeconds = 1800L;
                    this.tokenValidityInSecondsForRememberMe = 2592000L;
                }

            }
        }

        @Getter @Setter @ToString
        public static class ClientAuthorization {
            private String accessTokenUri;
            private String tokenServiceId;
            private String clientId;
            private String clientSecret;
        }

        @Getter @Setter @ToString
        public static class Ehcache {
            private int timeToLiveSeconds = 3600;
            private long maxEntries = 100L;

        }
    }

    @Getter @Setter @ToString
    public static class Cache {
        private final ApplicationProperties.Cache.Hazelcast hazelcast = new ApplicationProperties.Cache.Hazelcast();
        private final ApplicationProperties.Cache.Ehcache ehcache = new ApplicationProperties.Cache.Ehcache();

        @Getter @Setter @ToString
        public static class Ehcache {
            private int timeToLiveSeconds = 3600;
            private long maxEntries = 100L;
        }

        @Getter @Setter @ToString
        public static class Hazelcast {
            private int timeToLiveSeconds = 3600;
            private int backupCount = 1;
            private final ApplicationProperties.Cache.Hazelcast.ManagementCenter managementCenter = new ApplicationProperties.Cache.Hazelcast.ManagementCenter();

            @Getter @Setter @ToString
            public static class ManagementCenter {
                private boolean enabled = false;
                private int updateInterval = 3;
                private String url = "";
            }
        }
    }

    @PostConstruct
    private void postConstruct() {
        log.info("\n" +
                LOG_HEADER + "\n" +
                "				ApplicationProperties:\n" +
                LOG_HEADER + "\n" +
                this + "\n" +
                LOG_HEADER + "\n" +
                LOG_HEADER + "\n");
    }


    }
