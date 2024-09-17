package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

public abstract class AbstractSuspectController extends GptChatter {

  @FXML private ImageView chatBubbleImage;

  protected boolean isFirstTimeInit = true;
  protected static GameStateContext context;

  protected String suspectId;
  protected String suspectName;

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
  @Override
  protected String getSystemPrompt() {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("suspectName", suspectName);
    return PromptEngineering.getPrompt(promptFilename, dataMap);
  }

  /**
   * Handles switching the chat bubble image to a thought bubble and replacing its text with
   * ellipses so that the user knows that the ChatGPT API is processing their input.
   */
  @Override
  protected void setThinking() {
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
  @Override
  protected void setChatting(ChatMessage response) {
    chatBubbleImage.setImage(
        new Image(App.class.getResource("/images/chatbubblepixel.png").toString()));
    txtaChat.setText(response.getContent());
  }
}
