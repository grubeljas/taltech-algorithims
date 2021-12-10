package ee.ttu.algoritmid.trampoline.implementation;

import ee.ttu.algoritmid.trampoline.Trampoline;

public class TrampolineTile implements Trampoline {

    private final int jumpForce;
    private final Type type;

    public TrampolineTile(int jumpForce, Type type) {
        this.jumpForce = jumpForce;
        this.type = type;
    }

    @Override
    public int getJumpForce() {
        return jumpForce;
    }

    @Override
    public Type getType() {
        return type;
    }
}
