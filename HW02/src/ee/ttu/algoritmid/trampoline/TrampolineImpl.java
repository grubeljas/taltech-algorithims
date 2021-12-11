package ee.ttu.algoritmid.trampoline;

public class TrampolineImpl implements Trampoline{

    private final int force;
    private final Type type;
    private int posX;
    private int posY;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public TrampolineImpl(int force, Type type) {
        this.force = force;
        this.type = type;
    }

    @Override
    public int getJumpForce() {
        return force;
    }

    @Override
    public Type getType() {
        return type;
    }
}
