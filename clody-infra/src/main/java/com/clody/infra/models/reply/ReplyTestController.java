package com.clody.infra.models.reply;

import static com.azure.ai.openai.assistants.models.MessageRole.USER;

import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantThread;
import com.azure.ai.openai.assistants.models.AssistantThreadCreationOptions;
import com.azure.ai.openai.assistants.models.CreateRunOptions;
import com.azure.ai.openai.assistants.models.MessageContent;
import com.azure.ai.openai.assistants.models.MessageRole;
import com.azure.ai.openai.assistants.models.MessageTextContent;
import com.azure.ai.openai.assistants.models.PageableList;
import com.azure.ai.openai.assistants.models.RunStatus;
import com.azure.ai.openai.assistants.models.ThreadMessage;
import com.azure.ai.openai.assistants.models.ThreadMessageOptions;
import com.azure.ai.openai.assistants.models.ThreadRun;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyTestController {

  private final AssistantsClient client;

  private final Assistant assistant;

  @PostMapping("/send")
  public ResponseEntity<?> sendMessage(
      @RequestBody String diaryList
  ) throws InterruptedException {

    AssistantThread thread = client.createThread(new AssistantThreadCreationOptions());
    String threadId = thread.getId();

    client.createMessage(threadId, new ThreadMessageOptions(USER, diaryList));

    ThreadRun run = client.createRun(threadId, new CreateRunOptions(assistant.getId()));

    do {
      run = client.getRun(run.getThreadId(), run.getId());
      Thread.sleep(1000);
    } while (run.getStatus() == RunStatus.QUEUED || run.getStatus() == RunStatus.IN_PROGRESS);

    PageableList<ThreadMessage> messages = client.listMessages(run.getThreadId());
    List<ThreadMessage> data = messages.getData();
    List<String> result = new ArrayList<>();
    for(int i =0 ; i < data.size(); i++){
      ThreadMessage dataMessage = data.get(i);
      MessageRole role = dataMessage.getRole();
      for(MessageContent messageContent : dataMessage.getContent()){
        MessageTextContent messageTextContent = (MessageTextContent) messageContent;
        result.add(messageTextContent.getText().getValue());
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
