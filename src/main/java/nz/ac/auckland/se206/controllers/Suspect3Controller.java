package nz.ac.auckland.se206.controllers;

/** Controller class for the suspect3 view. Sets suspect3's data. */
public class Suspect3Controller extends AbstractSuspectController {

  /** Initialises variables for suspect three, Dewey. */
  public Suspect3Controller() {
    suspectId = "suspect3";
    suspectName = "Dewey";
    promptFilename = "suspect3.txt";

    /*
     * Uncomment the following if you want to disable this suspect's normal prompt and make them a
     * dumb, chattering animal.
     */
    disableNormalPrompt = true;
  }
}
