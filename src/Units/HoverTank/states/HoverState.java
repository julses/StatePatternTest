package Units.HoverTank.states;

import Units.HoverTank.HoverTank;
import com.jme3.math.Vector3f;

/**
 *
 * @author Jan
 */
public class HoverState implements IHoverTankState {
    private HoverTank hoverTank;
    private final float height = 5;
    private final float damage = 10;
    private final boolean canMove = true;
    private final float speed = 25;
    
    
    public HoverState(HoverTank hoverTank) {
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
    public void toHoverMode(){
        System.out.println("Already in Fly-Mode");
    }
    
    @Override
    public void toGroundMode() {
        hoverTank.setState(hoverTank.tankState);
    }
}
