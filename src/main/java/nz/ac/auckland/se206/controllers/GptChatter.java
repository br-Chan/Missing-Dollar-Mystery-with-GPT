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

/**
 * An abstract class that FXML controller subclasses can use to implement a conversation with
 * ChatGPT API.
 *
 * <p>A chat completion request is initialised with an input prompt, and conversation functonality
 * includes logic when sending a message, running the GPT and 2 methods setChatting and setThinking
 * to inform the user of what the GPT is doing (these 2 methods can be overriden to add to their
 * functionality).
 */
public abstract class GptChatter {

  @FXML protected TextArea txtaChat;
  @FXML protected TextField txtInput;
  @FXML protected Button btnSend;

  protected ChatCompletionRequest chatCompletionRequest;

  // The temperature and topP of GPT API used in the conversation. These can be edited in the
  // subclasses of GptChatter to alter its behaviour.
  protected double temperature = 0.2;
  protected double topP = 0.5;
  protected int maxTokens = 100;

  protected boolean isFirstTimeInit = true;
  protected String promptFilename;

  /**
   * This boolean is used for us to not use the large prompts for each suspect. When set to true, a
   * dummy chat message will be sent to the GPT model every time a system or user prompt is sent.
   */
  protected boolean disableNormalPrompt = false;

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
      ApiProxyConfig config = ApiProxyConfig.readConfig(); // Reads in the api config file
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(temperature)
              .setTopP(topP)
              .setMaxTokens(maxTokens); // Sets the settings for a general response

      setChatting(
          runGpt(
              new ChatMessage("system", getSystemPrompt()),
              setChattingNow)); // Starts the chat request

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
    if (disableNormalPrompt) {
      System.out.println(
          "Normal prompts have been disabled for this chat so that tokens are not wasted on long"
              + " prompts that aren't being tested. To enable this for a certain subclass of"
              + " GptChatter, remove the command setting disableNormalPrompt to true in its"
              + " constructor.");

      chatCompletionRequest.addMessage(new ChatMessage("user", "Make a short shrill shriek! Aa!"));
    } else {
      chatCompletionRequest.addMessage(msg);
    }

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
