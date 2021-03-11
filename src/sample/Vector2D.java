package sample;

public class Vector2D {
    // Variables
    private double x;
    private double y;

    // Constructors
    /**
     * Constructs a two-dimensional vector
     *
     * @param x x component of vector
     * @param y y component of vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Add
    /**
     * Adds two vectors together
     *
     * @param v Second vector in operation
     * @return vector result of vector addition
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }
    /**
     * Subtracts second vector from the first
     *
     * @param v Second vector in operation
     * @return vector result of vector subtraction
     */
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }
    /**
     * Multiplies vector by a scalar
     *
     * @param scalar scalar to multiply inputted vector by
     * @return scalar result of vector multiplication
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    /**
     * Multiplies a vector by -1 (a.k.a. flips it, etc.)
     *
     * @return vector result of operation
     */
    public Vector2D opposite() {
        return multiply(-1);
    }
    /**
     * Solves for the length of a vector
     *
     * @return scalar result of operation
     */
    public double len() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    /**
     * Solves for the squared length of a vector
     * Allows for a dot product optimization
     *
     * @return scalar result of operation
     */
    public double squaredLen() {
       return x * x + y * y;
    }
    /**
     * Normalizes a vector (finds its unit vector)
     *
     * @return the unit vector
     */
    public Vector2D norm() {
        double len = len();

        if (len > 0) {
            return new Vector2D(x / len, y / len);
        } else {
            return new Vector2D(0, 0);
        }
    }
    /**
     * Finds a normal vector to the vector
     *
     * @return a normal vector
     */
    public Vector2D normal() {
        return new Vector2D(-getY(), getX());
    }
    /**
     * Scales a vector up to an inputted length
     *
     * @param newLen new length of the vector
     *
     * @return vector result of operation
     */
    public Vector2D relen(double newLen) {
        return norm().multiply(newLen);
    }
    /**
     * Gets the dot product of two vectors
     *
     * @param other the other vector
     *
     * @return dot product of the vectors (a scalar)
     */
    public double dot(Vector2D other) {
        return x * other.getX() + y * other.getY();
    }
    /**
     * Performs a vector projection
     *
     * @param other vector to project onto
     *
     * @return projection of the vector onto the other
     */
    public Vector2D projectOnto(Vector2D other) {
        double squaredLen = other.squaredLen();

        if (squaredLen == 0) {
            return new Vector2D(0, 0);
        }

        return other.multiply(dot(other) / other.dot(other));
    }

    /**
     * Gets the squared distance between vectors
     * (Not taking the square root is an optimization, as square root
     * functions tend to be expensive)
     *
     * @param otherLen the other vector
     *
     * @return squared distance between the vectors
     */
    public double distanceSquared(Vector2D otherLen) {
        return subtract(otherLen).squaredLen();
    }

    // toString()
    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }

    // Getters
    /**
     * @return x-component of vector
     */
    public double getX() {
        return x;
    }
    /**
     * @return y-component of vector
     */
    public double getY() {
        return y;
    }

    // Setters
    /**
     * @param x new x-component of vector
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * @param y new y-component of vector
     */
    public void setY(double y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object other){
        if( other instanceof Vector2D) {
            return ((Vector2D)other).getX() == this.x && ((Vector2D)other).getY() == this.y;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (int)(this.x *100) * 101 + (int)(this.y * 100) * 179;
    }
}
