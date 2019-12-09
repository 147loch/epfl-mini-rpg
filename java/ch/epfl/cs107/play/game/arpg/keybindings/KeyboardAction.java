package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

// TODO remove the overrides in enum definitions so that they use XML storage

public enum KeyboardAction implements Action {
    MOVE_UP {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.UP);
        }

        @Override
        public String getActionTitle() {
            return "Move Up";
        }
    },
    MOVE_RIGHT {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.RIGHT);
        }

        @Override
        public String getActionTitle() {
            return "Move Right";
        }
    },
    MOVE_DOWN {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.DOWN);
        }

        @Override
        public String getActionTitle() {
            return "Move Down";
        }
    },
    MOVE_LEFT {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.LEFT);
        }

        @Override
        public String getActionTitle() {
            return "Move Left";
        }
    },
    CYCLE_INVENTORY {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.TAB);
        }

        @Override
        public String getActionTitle() {
            return "Cycle Inventory";
        }
    },
    USE_CURRENT_ITEM {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.SPACE);
        }

        @Override
        public String getActionTitle() {
            return "Use";
        }
    },
    VIEW_INTERACTION {
        @Override
        public Button getAssignedButton(Keyboard areaKeyboard) {
            return areaKeyboard.get(Keyboard.E);
        }

        @Override
        public String getActionTitle() {
            return "Interact";
        }
    };

    private int actionKey;

    KeyboardAction() {
        // TODO Read value from XML
        actionKey = -1;
    }

    /*
     * TODO this method should be used when we will have a proper settings panel to set keybindings,
     *  otherwise we should just use the standard XML-defined keybind
     */
    public void setActionKey(int key) {
        actionKey = key;
        // TODO Set settings storage and sync with XML
    }

    @Override
    public Button getAssignedButton(Keyboard areaKeyboard) {
        return areaKeyboard.get(actionKey);
    }
}
