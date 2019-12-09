package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

interface Action {

    Button getAssignedButton(Keyboard areaKeyboard);

    String getActionTitle();

}
