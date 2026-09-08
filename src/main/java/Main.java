import ai.koog.agents.core.agent.AIAgent;
import ai.koog.prompt.executor.clients.google.GoogleModels;
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor;

import static ai.koog.prompt.executor.clients.google.GoogleClientFactory.googleClient;

void main() {
    buildBasicAgent();
}

private void buildBasicAgent() {
    String apiKey = System.getenv("GEMINI_API_KEY");
    if (apiKey == null) {
        throw new RuntimeException("Api key is not set.");
    }

    AIAgent<String, String> agent = AIAgent.builder()
            .promptExecutor(new MultiLLMPromptExecutor(googleClient(apiKey)))
            .llmModel(GoogleModels.Gemini3_1FlashLite)
            .build();

    String result = agent.run("Hello! How can you help me?");
    IO.println(result);
}

