package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

/**
 * Standard interface to give the definitions needed in the Enum
 * @see KeyboardAction
 */
interface Action {

    Button getAssignedButton(Keyboard areaKeyboard);

    String getActionTitle();

}
