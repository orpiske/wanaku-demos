package com.example.taxadvisor.ai;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import ai.wanaku.capabilities.cee.langchain4j.WanakuCodeExecutionEngine;
import ai.wanaku.capabilities.sdk.common.config.DefaultServiceConfig;
import ai.wanaku.capabilities.sdk.common.config.ServiceConfig;
import ai.wanaku.capabilities.sdk.common.serializer.JacksonSerializer;
import ai.wanaku.capabilities.sdk.security.TokenEndpoint;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WanakuCodeExecutionProvider {
    private static final Logger LOG = Logger.getLogger(WanakuCodeExecutionProvider.class);

    @Produces
    public WanakuCodeExecutionEngine createWanakuCodeExecutionEngine() {
        LOG.info("Creating WanakuCodeExecutionEngine");
        final String value = ConfigProvider.getConfig().getConfigValue("wanaku.client.secret").getValue();

        final ServiceConfig serviceConfig = DefaultServiceConfig.Builder.newBuilder()
                .baseUrl("http://localhost:8080")
                .serializer(new JacksonSerializer())
                .clientId("wanaku-service")
                .tokenEndpoint(TokenEndpoint.autoResolve("http://localhost:8080", null))
                .secret(value)
                .build();

        final WanakuCodeExecutionEngine engine = WanakuCodeExecutionEngine.builder()
                .serviceConfig(serviceConfig)
                .taskTimeout(90)
                .engineType("camel")
                .language("yaml")
                .build();

        return engine;
    }
}
