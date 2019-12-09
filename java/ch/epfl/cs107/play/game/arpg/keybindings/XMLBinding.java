package ch.epfl.cs107.play.game.arpg.keybindings;

public class XMLBinding {

    private String name;
    private int keyId;

    /**
     * A pair of two properties to store the data from the XML keybinds
     * @param name Action and Keybind name
     * @param keyId The Keyboard key ID correpsonding to the action
     * @see KeyboardAction
     */
    XMLBinding(String name, int keyId) {
        this.name = name;
        this.keyId = keyId;
    }

    /**
     * @return The action/keybind name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The key id linked
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * A way to have the linked action rapidly from the data held in the XML
     * @return The keyboard action linked
     */
    public KeyboardAction toAction() {
        return KeyboardAction.valueOf(this.name);
    }

}
