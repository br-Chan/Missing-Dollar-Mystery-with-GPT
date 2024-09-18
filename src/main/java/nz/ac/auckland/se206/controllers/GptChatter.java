package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

public abstract class GptChatter {

  @FXML protected TextArea txtaChat;
  @FXML protected TextField txtInput;
  @FXML protected Button btnSend;

  protected ChatCompletionRequest chatCompletionRequest;

  protected boolean isFirstTimeInit = true;

  protected String promptFilename;

  /**
   * Generates the system prompt.
   *
   * @return the system prompt string
   */
  protected abstract String getSystemPrompt();

  /**
   * Initialises the chat completion request and fetches the initial response to display to the user
   * in the chat bubble.
   */
  public void initialiseChatCompletionRequest(boolean setChattingNow) {
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);

      setChatting(runGpt(new ChatMessage("system", getSystemPrompt()), setChattingNow));

    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @param getReply whether the GPT model is to be asked to send a response
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  protected ChatMessage runGpt(ChatMessage msg, boolean getReply) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    if (getReply) {
      try {
        // Fetch ChatGPT's response.
        ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
        Choice result = chatCompletionResult.getChoices().iterator().next();
        chatCompletionRequest.addMessage(result.getChatMessage());

        // TODO: delete print statement before release.
        System.out.println(result.getChatMessage().getContent());

        return result.getChatMessage();
      } catch (ApiProxyException e) {
        e.printStackTrace();
        return null;
      }
    }

    return null;
  }

  /**
   * Calls the sendMessage function to send a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  protected void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    sendMessage();
  }

  /**
   * Sends a message to the GPT model and runs the GPT model.
   *
   * @throws ApiProxyException
   */
  protected void sendMessage() throws ApiProxyException {
    // Process user's input.
    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }
    txtInput.clear();

    setThinking();

    // Switch the chat bubble's state to chatting with ChatGPT's response to the user.
    ChatMessage userInput = new ChatMessage("user", message);
    Task<Void> backgroundTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            setChatting(runGpt(userInput, true));
            return null;
          }
        };
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
  }

  /**
   * Handles replacing the chat bubble's text with ellipses so that the user knows that the ChatGPT
   * API is processing their input.
   */
  protected void setThinking() {
    txtaChat.setText("...");
  }

  /**
   * Handles replacing the chat bubble's text with ChatGPT's response to the user.
   *
   * @param response the chat message to display to the user (if this is null, don't change the
   *     displayed text)
   */
  protected void setChatting(ChatMessage response) {
    if (response == null) {
      return;
    }
    txtaChat.setText(response.getContent());
  }
}
