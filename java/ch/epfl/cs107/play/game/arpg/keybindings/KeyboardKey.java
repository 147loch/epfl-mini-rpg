package ch.epfl.cs107.play.game.arpg.keybindings;

public enum KeyboardKey implements Key {
    TAB(9),
    ENTER(10),
    SPACE(32),
    UP(38),
    DOWN(40),
    LEFT(37),
    RIGHT(39),
    A(65),
    B(66),
    C(67),
    D(68),
    E(69),
    F(70),
    G(71),
    H(72),
    I(73),
    J(74),
    K(75),
    L(76),
    M(77),
    N(78),
    O(79),
    P(80),
    Q(81),
    R(82),
    S(83),
    T(84),
    U(85),
    V(86),
    W(87),
    X(88),
    Y(89),
    Z(90);

    private int keyCode;

    KeyboardKey(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * A method to get a key from its id.
     * @param keyId The key Id
     * @return The corresponding enum entry
     */
    public static KeyboardKey fromKeyId(int keyId) {
        for (KeyboardKey key: values()) {
            if (key.getCode() == keyId) return key;
        }
        return null;
    }

    @Override
    public int getCode() {
        return this.keyCode;
    }
}
