package com.example.taxadvisor.ai;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ai.wanaku.capabilities.cee.langchain4j.WanakuCodeExecutionEngine;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import java.util.Map;
import java.util.function.Supplier;

// For auth (not working)
@ApplicationScoped
public class WanakuToolsProvider implements Supplier<ToolProvider> {
    private McpTransport transport;
    private McpClient mcpClient;
    private ToolProvider toolProvider;

    @Inject
    WanakuCodeExecutionEngine engine;

    @Override
    public ToolProvider get() {
        if(toolProvider == null) {
            transport = new StreamableHttpMcpTransport.Builder()
                    .url("http://localhost:8080/public/mcp")
                    .customHeaders(headerProvider())
                    .logRequests(true)
                    .logResponses(true)
                    .build();
            mcpClient = new DefaultMcpClient.Builder()
                    .transport(transport)
                    .build();
            toolProvider = McpToolProvider.builder()
                    .mcpClients(mcpClient)
                    .build();
        }

        return toolProvider;
    }

    private Map<String, String> headerProvider() {
        return Map.of();
//        return Map.of("Authorization", engine.getClient().getServiceAuthenticator().toHeaderValue());
    }
}
