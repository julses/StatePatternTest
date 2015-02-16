package mygame;

import Units.Unit;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 *
 * @author Jan
 */
public class Controls {
    private final Spatial floorNode;
    private final Camera cam;
    private final InputManager inputManager;
    
    public Controls(final Main main, final Unit hoverTank) {
        this.floorNode = main.getRootNode().getChild("floorNode");
        if(null == floorNode) System.out.println("Floor Node not found!");
        this.cam = main.getCamera();
        this.inputManager=main.getInputManager();
        
        inputManager.addMapping("LeftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("CameraUp", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("FlyMode", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("GroundMode", new KeyTrigger(KeyInput.KEY_G));

        
        ActionListener acl = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("LeftClick") && !isPressed) {
                CollisionResults resultsFloor = new CollisionResults();
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 0f);
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
                Ray ray = new Ray(click3d, dir);
                floorNode.collideWith(ray, resultsFloor);
                if (resultsFloor.size() > 0) {
                    Geometry target = resultsFloor.getClosestCollision().getGeometry();
                    Vector3f clickPos3d = new Vector3f(resultsFloor.getClosestCollision().getContactPoint()); //der genaue Klickpunkt in der Scene
                    System.out.println("Move to: " + clickPos3d.toString());
                    hoverTank.move(clickPos3d);
                    //System.out.println("Selection: " + target.getName() + "\nPosition: " + target.getLocalTranslation());
                    //target.setMaterial(main.matSelected);
                }
            } else if(name.equals("GroundMode") && !isPressed) {
                hoverTank.toGroundMode();
            } else if(name.equals("FlyMode") && !isPressed) {
                hoverTank.toFlyMode();
            }       
        }
    };
        
    inputManager.addListener(acl, "LeftClick", "CameraUp", "FlyMode", "GroundMode");
    }
    
}
