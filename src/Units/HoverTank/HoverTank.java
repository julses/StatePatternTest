package Units.HoverTank;

import Units.HoverTank.states.HoverState;
import Units.HoverTank.states.IHoverTankState;
import Units.HoverTank.states.TankState;
import Units.Unit;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.LodControl;
import mygame.BloomUI;
import mygame.Main;

/**
 *
 * @author Jan
 */
public class HoverTank extends Unit{
    private final AssetManager assetManager;
    public Node hoverTank;
    private float flyHeight = 5f;
    private float groundHeight = 1f;
    private final InputManager inputManager;
    private final ViewPort viewPort;
    private final Node rootNode;
    private final Vector3f unitPositionCorrectionVector;
    public IHoverTankState state;
    public final TankState tankState;
    public final HoverState hoverState;
    
    public HoverTank(Main main) {
        //Initialize States
        tankState = new TankState(this);
        hoverState = new HoverState(this);
        //Reference Local Objects
        this.unitPositionCorrectionVector = Main.getUnitPositionCorrectionVector();
        this.assetManager = main.getAssetManager();
        this.inputManager = main.getInputManager();
        this.viewPort = main.getViewPort();
        this.rootNode =main.getRootNode();
        //Create the Tank
        createTank();
    }
    
    private void createTank() {
        hoverTank = (Node) assetManager.loadModel("Models/HoverTank/Tank2.mesh.xml");

        Geometry tankGeom = (Geometry) hoverTank.getChild(0);
        LodControl control = new LodControl();
        tankGeom.addControl(control);
        rootNode.attachChild(hoverTank);
        
        hoverTank.scale(0.4f); //Größe definieren
        //Positionierung
        Vector3f position = new Vector3f(0, 1, 0);
        position.addLocal(unitPositionCorrectionVector);
        hoverTank.setLocalTranslation(position);
        state=tankState;
        
        /*Tests for Blur Settings---------------------------------------*/
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
        bf.setBloomIntensity(2.0f);
        bf.setExposurePower(1.3f);
        fpp.addFilter(bf);
        BloomUI bui = new BloomUI(inputManager, bf);
        viewPort.addProcessor(fpp);
        /*--------------------------------------------------------------*/
    }
    
    public IHoverTankState getState() {
        return state;
    }

    public void setState(IHoverTankState state) {
        this.state = state;
        applyState();
        state.move(hoverTank.getLocalTranslation());
    }
    
    public void applyState() {
        System.out.println("Stats: speed=" +state.speed() +" : damage=" +state.damage() +" : canMove=" +state.canMove());
    }
    
    @Override
    public void toFlyMode(){
        state.toHoverMode();
    }
    
    @Override
    public void toGroundMode() {
        state.toGroundMode();
    }

    @Override
    public void move(Vector3f clickPos3d) {
        state.move(clickPos3d);
    }
}
