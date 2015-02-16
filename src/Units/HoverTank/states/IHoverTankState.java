/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.HoverTank.states;

import com.jme3.math.Vector3f;

/**
 *
 * @author Jan
 */
public interface IHoverTankState {
    
    float height();
    
    boolean canMove();

    float damage();

    float speed();
    
    public void move(Vector3f clickPos3d);

    public void toGroundMode();

    public void toHoverMode();
    
}
