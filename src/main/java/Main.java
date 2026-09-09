import ai.koog.agents.core.agent.AIAgent;
import ai.koog.agents.core.agent.config.AIAgentConfig;
import ai.koog.prompt.Prompt;
import ai.koog.prompt.executor.clients.google.GoogleModels;
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor;
import ai.koog.prompt.params.LLMParams;

import static ai.koog.prompt.executor.clients.google.GoogleClientFactory.googleClient;

private static final String GEMINI_API_KEY_ENV = "GEMINI_API_KEY";
private static final String API_KEY_NOT_SET_MESSAGE = "The API key is not set.";

void main() {
    buildAssistantAgent();
}

private void buildBasicAgent() {
    String apiKey = getApiKey(GEMINI_API_KEY_ENV);

    AIAgent<String, String> agent = AIAgent.builder()
            .promptExecutor(new MultiLLMPromptExecutor(googleClient(apiKey)))
            .llmModel(GoogleModels.Gemini3_1FlashLite)
            .build();

    String result = agent.run("Hello! How can you help me?");
    IO.println(result);
}

private void buildAssistantAgent() {
    var apiKey = getApiKey(GEMINI_API_KEY_ENV);
    var prompt = Prompt.builder("assistant")
            .system("You are a helpful assistant.")
            .build()
            .withParams(new LLMParams(
                    0.7,
                    null,
                    1,
                    null,
                    null,
                    LLMParams.ToolChoice.Auto.INSTANCE,
                    null,
                    null
            ));

    AIAgentConfig agentConfig = AIAgentConfig.builder()
            .model(GoogleModels.Gemini3_1FlashLite)
            .prompt(prompt)
            .maxAgentIterations(10)
            .build();

    AIAgent<String, String> agent = AIAgent.builder()
            .promptExecutor(new MultiLLMPromptExecutor(googleClient(apiKey)))
            .agentConfig(agentConfig)
            .build();

    String result = agent.run("Greet user. His name is Milo.");
    IO.println(result);
}

private String getApiKey(String envName) {
    String apiKey = System.getenv(envName);
    if (apiKey == null) {
        throw new RuntimeException(API_KEY_NOT_SET_MESSAGE);
    }
    return apiKey;
}

