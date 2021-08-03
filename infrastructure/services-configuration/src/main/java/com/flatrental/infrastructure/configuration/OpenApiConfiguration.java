package com.flatrental.infrastructure.configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConditionalOnWebApplication
@RequiredArgsConstructor
@Schema
public class OpenApiConfiguration {

    private static final String GROUP_PATHS_TEMPLATE = "/{0}/**";

    @Value("${server.servlet.context-path}") private final String serviceBasePath;
    @Value("${api-docs.title}") private final String title;
    @Value("${api-docs.description}") private final String description;
    @Value("${api-docs.version}") private final String version;
    @Value("${api-docs.termsOfServiceUrl}") private final String termsOfServiceUrl;
    @Value("${api-docs.license.name}") private final String licenseName;
    @Value("${api-docs.license.url}") private final String licenseUrl;
    @Value("${api-docs.contact.name}") private final String contactName;
    @Value("${api-docs.contact.email}") private final String contactEmail;
    @Value("${api-docs.contact.url}") private final String contactUrl;

    @Bean
    public GroupedOpenApi buildGroupedOpenApi() {
        return GroupedOpenApi.builder().group(serviceBasePath.substring(1))
                .pathsToMatch(getPathToMatchForGroupedApi())
                .build();
    }

    private String getPathToMatchForGroupedApi() {
        return MessageFormat.format(GROUP_PATHS_TEMPLATE, serviceBasePath);
    }

    @Bean
    public OpenAPI buildCustomOpenApi() {
        return new OpenAPI()
                .info(buildApiInfo());
    }

    private Info buildApiInfo() {
        return new Info()
                .title(title)
                .description(description)
                .version(version)
                .termsOfService(termsOfServiceUrl)
                .license(buildLicense())
                .contact(buildContact());
    }

    private License buildLicense() {
        return new License()
                .name(licenseName)
                .url(licenseUrl);
    }

    private Contact buildContact() {
        return new Contact()
                .name(contactName)
                .email(contactEmail)
                .url(contactUrl);
    }

}
