package fun.diviner.aidiviner.diviner.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonField;
import com.openai.core.http.StreamResponse;
import com.openai.credential.BearerTokenCredential;
import com.openai.models.*;

/**
 * @author Coaixy
 * @createTime 2025-01-23
 * @packageName fun.diviner.diviner.util
 **/

public class AIRequestUtil {
    public static ReasoningResponse getAIResponse(String userPrompt, AIModel model) {
        StringBuilder content = new StringBuilder();
        StringBuilder reasoning_content = new StringBuilder();
        OpenAIOkHttpClient.Builder clientBuilder = OpenAIOkHttpClient.builder();
        clientBuilder
                .baseUrl(model.getBaseApi())
                .credential(BearerTokenCredential.create(
                        model.getKey()
                ));
        OpenAIClient client = clientBuilder.build();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addMessage(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(
                        ChatCompletionUserMessageParam.builder()
                                .content(ChatCompletionUserMessageParam.Content.ofTextContent(userPrompt))
                                .build()))
                .model(model.getModelName())
                .build();
        StreamResponse<ChatCompletionChunk> chatCompletion = client.chat().completions().createStreaming(params);
        chatCompletion.stream().forEach(chunk -> {
            ChatCompletionChunk.Choice choice = chunk.choices().get(0);
            JsonField<ChatCompletionChunk.Choice.Delta> delta = choice._delta();
            String tempContent = "";
            if (delta.asObject().isPresent()){
                tempContent = delta.asObject().get().get("content").toString();
            }else{
                tempContent = choice.delta().content().get();
            }
            if (tempContent != null) content.append(tempContent);
            if (model == AIModel.DEEPSEEK_REPONSER){
                String tempReasoningContent = delta.asObject().get().get("reasoning_content").toString();
                if (tempReasoningContent != null) reasoning_content.append(tempReasoningContent);
            }
        });

        return new ReasoningResponse(
                formatStr(content.toString()),
                formatStr(reasoning_content.toString())
        );
    }

    private static String formatStr(String str) {
        return str.replaceAll("null", "").replaceAll("\\*", "").replaceAll("#", "");
    }
}