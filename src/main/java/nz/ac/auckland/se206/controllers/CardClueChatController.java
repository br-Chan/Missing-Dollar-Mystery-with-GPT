package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public class CardClueChatController extends GptChatter {

  public CardClueChatController() {
    promptFilename = "cardClueChat.txt";
  }

  /**
   * Initializes the suspect view. If it's the first time initialization, it will provide
   * instructions via text-to-speech.
   */
  @FXML
  public void initialize() {
    // checks if first time initing
    if (isFirstTimeInit) {
      setChatting(
          new ChatMessage(
              "assistant",
              "Card can now be investigated! Type actions to investigate the card. Examples:"
                  + " \"look at profile picture\", \"turn card around\", etc."));
      isFirstTimeInit = false;
    }
    // calls chat request
    initialiseChatCompletionRequest(false);
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

  @Override
  protected String getSystemPrompt() {
    Map<String, String> dataMap = new HashMap<>();
    return PromptEngineering.getPrompt(promptFilename, dataMap);
  }

  @Override
  protected void setThinking() {
    super.setThinking();
  }

  @Override
  protected void setChatting(ChatMessage response) {
    super.setChatting(response);

    // Mark the card clue as having been interacted with.
    GlobalVariables.handleInteraction("cardClue");
  }
}
