package Units.HoverTank.states;

import Units.HoverTank.HoverTank;
import com.jme3.math.Vector3f;

/**
 *
 * @author Jan
 */
public class TankState implements IHoverTankState {
    private HoverTank hoverTank;
    private final float height = 1;
    private final float damage = 25;
    private final boolean canMove = true;
    private final float speed = 5;
    
    public TankState(HoverTank hoverTank) {
        this.hoverTank = hoverTank;
    }

    @Override
    public float height() {
        return height;
    }
    
    @Override
    public float damage() {
        return damage;
    }
    
    @Override
    public boolean canMove() {
        return canMove;
    }
    
    @Override
    public float speed() {
        return speed;
    }

    @Override
    public void move(Vector3f clickPos3d) {
        hoverTank.hoverTank.setLocalTranslation(clickPos3d.getX(), height, clickPos3d.getZ());
    }
    
    @Override
    public void toHoverMode() {
        hoverTank.setState(hoverTank.hoverState);
    }
    
    @Override
    public void toGroundMode(){
        System.out.println("Already in Ground-Mode");
    }
}
