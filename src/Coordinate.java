public class Coordinate {
    private int x,y;

    /**
     * Make a new Coordinate instance with the given x and y coordinates
     * @param x horizontal coordinate of a cartesian coordinate system
     * @param y vertical coordinate of a cartesian coordinate system
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Check an object for equivalence with this Coordinate instance
     * @param o The object to be checked for equivalence
     * @return True if o was equivalent and false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Coordinate)) return false;
        Coordinate other = (Coordinate)o;
        if(other.x != this.x) return false;
        if(other.y != this.y) return false;
        return true;
    }

    /**
     * Create a unique hash code using x and y coordinates.
     * This technique was taken from Matthew Szudzik's Wolfram Alpha 2006 paper.
     * @return a unique hash code
     */
    @Override
    public int hashCode(){
        if (x>y) return (x*x)+x+y;
        else return (y*y)+x;
    }

    /**
     * our string representation
     */
    @Override
    public String toString(){
        return x + "," + y;
    }

    //just a heads up these were doubles before I'm not sure how putting them as ints changes things
    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }
}
