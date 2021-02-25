package sample;

public class Vector2D {
    // Variables
    private double x;
    private double y;

    // Constructors
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Add
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }
    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D opposite() {
        return multiply(-1);
    }
    public double len() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    public Vector2D norm() {
        double len = len();

        if (len > 0) {
            return new Vector2D(x / len, y / len);
        }
        else {
            return new Vector2D(0, 0);
        }
    }

    // Getters
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}
