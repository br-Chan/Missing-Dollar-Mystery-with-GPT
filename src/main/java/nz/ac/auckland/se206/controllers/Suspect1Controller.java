package nz.ac.auckland.se206.controllers;

/** Controller class for the suspect1 view. Sets suspect1's data. */
public class Suspect1Controller extends AbstractSuspectController {
  /** Initialises variables for suspect one, Louie. */
  public Suspect1Controller() {
    suspectId = "suspect1";
    suspectName = "Louie";
    promptFilename = "suspect1.txt";

    temperature = 0.6;
    topP = 0.6;

    /*
     * Uncomment the following if you want to disable this suspect's normal prompt and make them a
     * dumb, chattering animal.
     */
    disableNormalPrompt = true;
  }
}
