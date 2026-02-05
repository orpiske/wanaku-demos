# Tax Advisor Agent Demo

A demonstration of an AI-powered tax advisor that uses [Wanaku](https://github.com/wanaku-ai/wanaku) and Apache Camel for dynamic service orchestration. Instead of relying on pre-trained knowledge, the agent generates and executes Camel routes on-the-fly to retrieve tax information from external services.

## Overview

This agent showcases the Code Execution Engine capability of Wanaku. When asked tax-related questions, it:

1. Searches for available Kamelets (Camel integration snippets)
2. Generates a Camel YAML DSL route based on the available services
3. Submits the route to Wanaku for execution via the Camel Code Execution Engine
4. Returns the computed results to the user

## Technology Stack

- **Quarkus 3.27** - Java framework
- **LangChain4j** - LLM integration (OpenAI/OpenRouter or Ollama)
- **Wanaku SDK** - Code execution engine integration
- **Apache Camel** - Service orchestration via YAML DSL
- **H2** - In-memory database for tax rate configuration

## Prerequisites

- Java 21+
- Maven 3.8+
- A running [Wanaku](https://github.com/wanaku-ai/wanaku) instance ([setup guide](https://github.com/wanaku-ai/wanaku/blob/main/docs/usage.md))
- [Camel Code Execution Engine](https://github.com/wanaku-ai/camel-code-execution-engine) registered with Wanaku
- An LLM API key (OpenRouter, OpenAI, or local Ollama)

## Setup

### 1. Configure the Camel Code Execution Engine

Clone and build the [Camel Code Execution Engine](https://github.com/wanaku-ai/camel-code-execution-engine):

```bash
git clone https://github.com/wanaku-ai/camel-code-execution-engine
cd camel-code-execution-engine
```

Copy the Kamelet from this project to the engine's classpath:

```bash
cp salary-tax-calculator-sink.kamelet.yaml src/main/resources/kamelets/
```

Build and run the engine, then register it with Wanaku.

### 2. Configure the Agent

Edit `src/main/resources/application.properties`:

```properties
# Set your OpenRouter API key
quarkus.langchain4j.openai.api-key=your-api-key-here

# Or use Ollama (uncomment and configure)
# quarkus.langchain4j.ollama.base-url=http://localhost:11434
# quarkus.langchain4j.ollama.chat-model.model-id=llama3
```

Set the Wanaku client secret:

```bash
export WANAKU_CLIENT_SECRET=your-secret
```

### 3. Build and Run

```bash
mvn quarkus:dev
```

The agent starts on port **8081** by default.

## Usage

### Web Interface

Open http://localhost:8081 in your browser to access the chat interface.

### API

Send a POST request to the chat endpoint:

```bash
curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What is the income tax for a Czech resident with a salary of 100000?"}'
```

### Example Questions

- "What is the income tax for a Czech resident with a salary of 100000?"
- "Calculate the tax for a US resident earning 75000"
- "How much tax would someone in the UK pay on a 50000 salary?"

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/chat` | POST | Chat with the tax advisor agent |
| `/api/admin/tax-rates` | GET/POST | Manage tax rate configurations |
| `/api/tax/salary/calculate` | POST | Direct tax calculation (used by Camel routes) |
| `/openapi` | GET | OpenAPI specification |
| `/q/swagger-ui` | GET | Swagger UI |

## How It Works

```
User Question
     │
     ▼
┌─────────────────┐
│  Tax Advisor    │
│  AI Service     │
└────────┬────────┘
         │ 1. Search for Kamelets
         ▼
┌─────────────────┐
│  WanakuCodeTool │──────────────────────────┐
└────────┬────────┘                          │
         │ 2. Generate Camel Route           │
         │ 3. Execute via Wanaku             │
         ▼                                   │
┌─────────────────┐    ┌─────────────────┐   │
│     Wanaku      │───▶│  Camel Code     │   │
│     Router      │    │  Exec Engine    │   │
└─────────────────┘    └────────┬────────┘   │
                                │            │
                                ▼            │
                       ┌─────────────────┐   │
                       │  Tax Advisor    │◀──┘
                       │  REST API       │
                       └─────────────────┘
```

