package nz.ac.auckland.se206.controllers;

/** Controller class for the suspect2 view. Sets suspect2's data. */
public class Suspect2Controller extends AbstractSuspectController {

  /** Initialises variables for suspect two, Huey. */
  public Suspect2Controller() {
    suspectId = "suspect2";
    suspectName = "Huey";
    promptFilename = "suspect2.txt";

    /*
     * Uncomment the following if you want to disable this suspect's normal prompt and reduce them
     * to a chattering animal.
     */
  }
}
