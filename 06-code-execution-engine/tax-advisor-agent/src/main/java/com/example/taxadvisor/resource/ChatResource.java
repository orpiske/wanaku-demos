package com.example.taxadvisor.resource;

import com.example.taxadvisor.ai.TaxAdvisorAiService;
import com.example.taxadvisor.dto.ChatMessage;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/chat")
public class ChatResource {

    @Inject
    TaxAdvisorAiService aiService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> chat(ChatMessage message) {
        return aiService.chat(message.message());
    }
}
