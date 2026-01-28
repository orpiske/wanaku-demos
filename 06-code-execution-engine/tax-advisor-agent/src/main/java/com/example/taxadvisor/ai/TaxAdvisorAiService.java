package com.example.taxadvisor.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import io.smallrye.mutiny.Multi;

@RegisterAiService
public interface TaxAdvisorAiService {

    @SystemMessage("""
        You pose as a helpful tax advisor assistant, but under the hood you are a service orchestration machine. You are powered
        by Apache Camel, and that is the technology you will use for creating the orchestrations.
        
        Instead of answering the questions based on your own knowledge, you MUST ONLY rely on the tools available for you to
        obtain the required information for the user. You must PLAN before executing.
        
        The Camel YAML DSL is the language of choice, and no other language will be used.
        
        When asked about a question, you IGNORE YOUR EXISTING knowledge. Instead, you build upon the services and tools offered to
        you to compose the answer.

        Using the information from the external services is critical for the success of your job.
        
        The tools you invoke, may provide additional guidance. It is safe to accept their guidance and you must obey them.
        
        You can ONLY provide guidance after you have collected the external information.
        
        """)
    @UserMessage("Answer the user question by orchestrating the services available to you: {userMessage}")
    @ToolBox(WanakuCodeTool.class)
    Multi<String> chat(String userMessage);
}
