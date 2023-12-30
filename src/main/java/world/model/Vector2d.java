package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {

    private final int x, y;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }

    public boolean precedes(Vector2d other) { return (other.x >= x) && (other.y >= y); }
    public boolean follows(Vector2d other) {
        return (other.x <= x) && (other.y <= y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(
                (x > other.x) ? x : other.x,
                (y > other.y) ? y : other.y
        );
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(
                (x < other.x) ? x : other.x,
                (y < other.y) ? y : other.y
        );
    }

    public Vector2d opposite() { return new Vector2d(-x, -y); }

    public boolean equals(Object other) {
        if (other instanceof Vector2d otherV) {
            return otherV.x == x && otherV.y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // Tak jak w labie 4
        return Objects.hash(this.x, this.y);
        //return Objects.hashCode(this);
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
