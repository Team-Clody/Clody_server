package com.clody.infra.models.reply;

import static com.azure.ai.openai.assistants.models.MessageRole.USER;

import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantThread;
import com.azure.ai.openai.assistants.models.AssistantThreadCreationOptions;
import com.azure.ai.openai.assistants.models.CreateRunOptions;
import com.azure.ai.openai.assistants.models.MessageContent;
import com.azure.ai.openai.assistants.models.MessageTextContent;
import com.azure.ai.openai.assistants.models.PageableList;
import com.azure.ai.openai.assistants.models.RunStatus;
import com.azure.ai.openai.assistants.models.ThreadMessage;
import com.azure.ai.openai.assistants.models.ThreadMessageOptions;
import com.azure.ai.openai.assistants.models.ThreadRun;
import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.event.ReplyMessagePublisher;
import com.clody.domain.reply.service.RodyProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RodyProcessorImpl implements RodyProcessor {

  private final AssistantsClient client;
  private final Assistant assistant;
  private final ReplyMessagePublisher replyMessagePublisher;

  public void createReply(DequeuedMessage messageContent) {
    log.info("Processing message: {}", messageContent);

    AssistantThread thread = createAssistantThread();
    sendMessageToThread(thread.getId(), messageContent.content());

    List<String> result;

    try {
      result = getGptResponse(thread.getId());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    publishResponseEvent(messageContent.replyId(), result, messageContent.version());
  }

  private AssistantThread createAssistantThread() {
    return client.createThread(new AssistantThreadCreationOptions());
  }

  private void sendMessageToThread(String threadId, String messageContent) {
    client.createMessage(threadId, new ThreadMessageOptions(USER, messageContent));
  }

  private List<String> getGptResponse(String threadId) throws InterruptedException {
    ThreadRun run = client.createRun(threadId, new CreateRunOptions(assistant.getId()));

    do {
      run = client.getRun(run.getThreadId(), run.getId());
      Thread.sleep(500);
    } while (run.getStatus() == RunStatus.QUEUED || run.getStatus() == RunStatus.IN_PROGRESS);

    return extractMessagesFromResponse(client.listMessages(run.getThreadId()));
  }

  private List<String> extractMessagesFromResponse(PageableList<ThreadMessage> messages) {
    List<String> result = new ArrayList<>();
    ThreadMessage threadMessage = messages.getData().getFirst();
    for (MessageContent messageContent : threadMessage.getContent()) {
      log.info("Message content: {}", messageContent);
      MessageTextContent messageTextContent = (MessageTextContent) messageContent;
      result.add(messageTextContent.getText().getValue());
    }
    return result;
  }

  private void publishResponseEvent(Long replyId, List<String> result, Integer version) {
    String responseMessage = String.join(" ", result);
    log.info("Publishing response message: {}", responseMessage);
    Message resultMessage = Message.of(replyId, responseMessage, version);
    replyMessagePublisher.publishMessage(resultMessage);
  }

}
