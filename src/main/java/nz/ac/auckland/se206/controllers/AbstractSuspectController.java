package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

public abstract class AbstractSuspectController {

  @FXML private TextArea txtaChat;
  @FXML private TextField txtInput;
  @FXML private Button btnSend;

  @FXML private ImageView chatBubbleImage;

  protected ChatCompletionRequest chatCompletionRequest;

  protected boolean isFirstTimeInit = true;
  protected static GameStateContext context;

  protected String suspectId;
  protected String suspectName;
  protected String promptFilename;

  public AbstractSuspectController() {
    context = new GameStateContext(this);
  }

  /**
   * Initializes the suspect view. If it's the first time initialization, it will provide
   * instructions via text-to-speech.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialise is being run from the abstract class!");
    if (isFirstTimeInit) {
      TextToSpeech.speak("Chat with " + suspectName);
      isFirstTimeInit = false;
    }
    initialiseChatCompletionRequest();
  }

  /**
   * Handles the key pressed event, sending a message if the user presses ENTER.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException {
    if (event.getCode().toString().equals("ENTER")) {
      sendMessage();
    }
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {}

  /**
   * Generates the system prompt based on the suspect's data.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("suspectName", suspectName);
    return PromptEngineering.getPrompt(promptFilename, dataMap);
  }

  /**
   * Initialises the chat completion request and fetches the initial response to display to the user
   * in the chat bubble.
   */
  public void initialiseChatCompletionRequest() {
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);

      setChatting(runGpt(new ChatMessage("system", getSystemPrompt())));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
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

  /**
   * Calls the sendMessage function to send a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    sendMessage();
  }

  /**
   * Sends a message to the GPT model and runs the GPT model.
   *
   * @throws ApiProxyException
   */
  private void sendMessage() throws ApiProxyException {
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
            setChatting(runGpt(userInput));
            return null;
          }
        };
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
  }

  /**
   * Handles switching the chat bubble image to a thought bubble and replacing its text with
   * ellipses so that the user knows that the ChatGPT API is processing their input.
   */
  private void setThinking() {
    chatBubbleImage.setImage(
        new Image(App.class.getResource("/images/thoughtbubblepixel.png").toString()));
    txtaChat.setText("...");
  }

  /**
   * Handles switching the chat bubble image to a speech bubble and replacing its text with
   * ChatGPT's response to the user.
   *
   * @param response the chat message to display to the user
   */
  private void setChatting(ChatMessage response) {
    chatBubbleImage.setImage(
        new Image(App.class.getResource("/images/chatbubblepixel.png").toString()));
    txtaChat.setText(response.getContent());
  }
}
