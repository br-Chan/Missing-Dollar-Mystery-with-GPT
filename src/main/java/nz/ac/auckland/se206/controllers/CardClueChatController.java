package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.prompts.PromptEngineering;

/**
 * Controller class for the card clue chat FXML, containing the investigation chat with the GPT API.
 */
public class CardClueChatController extends GptChatter {

  /** Initialises instance of card clue chat with temperature and topP for prompts. */
  public CardClueChatController() {
    promptFilename = "cardClueChat.txt";

    temperature = 0.1;
    topP = 0.1;
  }

  /**
   * Initializes the card clue chat. If it's the first time initialization, it will display the
   * mini-tutorial message in the chat.
   */
  @FXML
  public void initialize() {
    // checks if first time initing
    if (isFirstTimeInit) {
      setChatting(
          new ChatMessage(
              "assistant",
              "This clue can be Investigated! Type actions to Investigate the card. Examples:"
                  + " \"look at profile picture\", \"turn card around\", etc."));
      isFirstTimeInit = false;
    }

    // Initialise the chat completion request without the GPT API creating a response.
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
  protected ChatMessage runGpt(ChatMessage msg, boolean getReply) throws ApiProxyException {
    ChatMessage newMsg;
    if (msg.getRole().equals("user")) {
      // Add the cleanlines information for the GPT.
      newMsg = appendPrompt(msg);
      System.out.println(newMsg.getContent());
    } else {
      newMsg = msg;
    }

    return super.runGpt(newMsg, getReply);
  }

  /**
   * Adds to the back of the chat message the cleanliness information for the 4 main sections of the
   * card: profile picture, top details, bottom details and back side.
   *
   * @param message the chat message to append the information to
   * @return the updated chat message
   */
  private ChatMessage appendPrompt(ChatMessage message) {
    String messageContent = message.getContent();
    messageContent =
        messageContent
            // If the debris on card has been cleaned
            + " [Profile picture is "
            + (GlobalVariables.isCardProfilePicClean() ? "clean" : "covered in debris")
            + "]"
            // If the dirt on card has been cleaned
            + " [Bottom details are "
            + (GlobalVariables.isCardDetailsBottomClean() ? "clean" : "covered in dirt")
            + "]"
            // If the pencil marks on card has been cleaned
            + " [Top details are "
            + (GlobalVariables.isCardDetailsTopClean() ? "clean" : "covered by pencil marks")
            + "]"
            // Back side is always clean
            + " [Back side is clean]";

    return new ChatMessage(message.getRole(), messageContent);
  }

  @Override
  protected void setThinking() {
    txtaChat.setText("investigating...");
  }

  @Override
  protected void setChatting(ChatMessage response) {
    super.setChatting(response);

    // Mark the card clue as having been interacted with.
    GlobalVariables.handleInteraction("cardClue");
  }
}
