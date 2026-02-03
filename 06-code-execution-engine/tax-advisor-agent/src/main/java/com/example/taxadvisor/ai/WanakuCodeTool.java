package com.example.taxadvisor.ai;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ai.wanaku.capabilities.cee.langchain4j.WanakuCodeExecutionEngine;
import ai.wanaku.capabilities.sdk.api.exceptions.WanakuException;
import ai.wanaku.capabilities.sdk.api.types.execution.CodeExecutionEvent;
import ai.wanaku.capabilities.sdk.api.types.execution.CodeExecutionEventType;
import dev.langchain4j.agent.tool.Tool;
import io.smallrye.common.annotation.Blocking;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WanakuCodeTool {
    private static final Logger LOG = Logger.getLogger(WanakuCodeTool.class);

    @Inject
    WanakuCodeExecutionEngine engine;

    // Search tool
//    @Tool("Searches for services to perform tax calculations.")
    public String getTaxCalculationServices() {
        LOG.info("Searching for services to perform tax calculations");
        final URL resource = this.getClass().getResource("/salary-tax-calculator-sink.kamelet.yaml");

        try {
            return String.format("""
                    # Context
                    - The tool below is a Kamelet.
                    - A Kamelet is a snippet for a Camel route.
                    - It is a snippet that can be used to invoke the service containing the information you are looking for.
                    - It cannot be used on its own. It MUST be a part of an orchestration flow.
                    - If you don't know what to do, get help.
                    
                    ---
                    %s
                    """, new String(resource.openStream().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Get Help (causes problems for gpt-oss)
//    @Tool("Get help generating the orchestration code")
    public String getHelpAssembling() {
        LOG.infof("Getting help building the orchestration code");
        final URL resource = this.getClass().getResource("/salary-tax-calculator-template.yaml");

        try {
            final String sample = new String(resource.openStream().readAllBytes());
            return String.format("""
                    # Orchestration Steps
                    1. Search for tools
                    2. Generate orchestration code
                    3. Run the orchestration code
                    
                    # Rules
                    - Do NOT provide this code to the user
                    
                    # Sample orchestration code
                    - The snippet below contains a sample orchestration code that you can use as an example
                    
                    %s
                    """, sample);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Construction
//    @Tool("Generates the tools orchestration code")
    public String getEndpointCommunicationPrimitives(String invocationTable) {
        LOG.infof("Generating the orchestration code for service %s", invocationTable);

        return String.format("""
                # Task
                
                Generate a Camel route. Use the information from the previously retrieved kamelets to assemble the route.

                The Camel route should represent the steps and processing required to access the service APIs provided below.
                
                Update the execution plan to run the orchestration code. You MUST run the orchestration code.
                
                # Guidelines
                
                - The initial endpoint for the route is "direct:start".
                - You MUST generate only the code and nothing else. Not even the code separators (```).
                - The Camel route MUST be written using the YAML DSL
                - Variables are enclosed in double brackets ({{ and }}). Replace them when you find them.
                - The route MUST be a valid YAML.
                
                # Tips
                
                - A Camel YAML route always start with: `- route`
                - The "steps" element is a child of "route"
                
                # Next steps
                
                Use the code you generated to run the orchestration code.
               
                """);
    }

    // Invocation
    @Tool("Runs the orchestration code")
    @Blocking
    public String consumeTheCorePrimitives(String corePrimitive) {
        LOG.infof("Requesting execution of Camel code %s", corePrimitive);

        try {
            final String execute = engine.execute(corePrimitive);
            LOG.infof("WanakuCodeTool execute returned: %s", execute);
            return execute;
        } catch (WanakuException e) {
            final List<List<CodeExecutionEvent>> codeEventsList = engine.getCodeEvents();
            final List<CodeExecutionEvent> lastEvents = codeEventsList.getLast();

            final Optional<CodeExecutionEvent> first =
                    lastEvents.stream().filter(event -> (event.getEventType() == CodeExecutionEventType.FAILED || event.getEventType() == CodeExecutionEventType.ERROR)).findFirst();

            if (first.isPresent()) {
                return first.get().getOutput();
            }

            throw e;
        }
    };
}
