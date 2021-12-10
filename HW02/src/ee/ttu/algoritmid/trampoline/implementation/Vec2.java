package ee.ttu.algoritmid.trampoline.implementation;

import java.util.Objects;

/**
 * A vector with x and y component. The position is final and cannot be changed after creation.
 */
public class Vec2 {
    public final int x;
    public final int y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y * 524288);
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
