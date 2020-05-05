public class Coordinate {
    int x, y;
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){ return x; }
    public int getY(){ return y; }
    /**
     * our string representation
     */
    @Override
    public String toString(){
        return x + "," + y;
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
}
