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
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public abstract class AbstractSuspectController extends GptChatter {

  @FXML private ImageView chatBubbleImage;

  protected String suspectId;
  protected String suspectName;

  /**
   * Initializes the suspect view. If it's the first time initialization, it will provide
   * instructions via text-to-speech.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialising AbstractSuspectController...");
    if (isFirstTimeInit) {
      isFirstTimeInit = false;
    }
    initialiseChatCompletionRequest(true);
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
    super.setThinking();
  }

  /**
   * Handles switching the chat bubble image to a speech bubble and replacing its text with
   * ChatGPT's response to the user. If the suspect is being chatted to for the first time, the
   * suspect is marked as been having interacted with.
   *
   * @param response the chat message to display to the user
   */
  @Override
  protected void setChatting(ChatMessage response) {
    chatBubbleImage.setImage(
        new Image(App.class.getResource("/images/chatbubblepixel.png").toString()));
    super.setChatting(response);

    // Mark the suspect as having been interacted with.
    GlobalVariables.handleInteraction(suspectId);
  }
}
