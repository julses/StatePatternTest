package Units;

import com.jme3.math.Vector3f;

/**
 *
 * @author Jan
 */
public abstract class Unit {
    public static int id; 
    
    public Unit() {
        id++;
    }    
    public static int getId() {
        return id;
    }
    
    public abstract void move(Vector3f clickPos3d);
    public abstract void toGroundMode();
    public abstract void toFlyMode();
}
