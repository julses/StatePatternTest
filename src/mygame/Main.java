package mygame;

import Units.HoverTank.HoverTank;
import Units.Unit;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

/**
 * State pattern test
 * @author jan
 */
public class Main extends SimpleApplication {
    private static int playFieldsX;
    private static int playFieldsZ;
    private Node tank;
    public Node units;
    public Material matSelected;
    public Material matUnselected;
    public Material matPlaceArea;
    public Material matMovableArea;
    public Material matHover;
    public Material floor_mat;
    //Verschiebevektor für Figuren, damit Sie wieder aufs Grid passen
    private static Vector3f unitPositionCorrectionVector;
    private static float playFieldSize;
    
    public static void main(String[] args) {
        playFieldSize = 5;
        playFieldsX = 25;
        playFieldsZ = 40;
        unitPositionCorrectionVector = new Vector3f((playFieldSize*0.5f), 0f, (playFieldSize*(-0.5f)));
        Main app = new Main();

        app.start();
    }
    private Node floorNode;

    @Override
    public void simpleInitApp() {
        initMat();
        PlayField playField = new PlayField(this, playFieldsX, playFieldsZ, playFieldSize);
        floorNode = playField.getFloorNode();
        Unit hoverTank = new HoverTank(this);
        createCamera();
        createLight();
        Controls controls = new Controls(this, hoverTank);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Initialize Material
     */
    private void initMat() {
        matSelected = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matSelected.setColor("Color", ColorRGBA.Red);
        matUnselected = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matUnselected.setTexture("ColorMap", assetManager.loadTexture("Textures/gras_texture.jpg"));
        matPlaceArea = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matPlaceArea.setColor("Color", ColorRGBA.Brown);
        matMovableArea = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matMovableArea.setColor("Color", ColorRGBA.DarkGray);
        matHover = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matHover.setTexture("ColorMap", assetManager.loadTexture("Textures/floorQuadMouseHover2.png"));
        matHover.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //TextureKey keyFloor = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
        TextureKey keyFloor = new TextureKey("Textures/gras_texture.jpg");
        keyFloor.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(keyFloor);
        tex3.setWrap(Texture.WrapMode.Repeat);
        //floor_mat.setColor("Color", ColorRGBA.White);
        floor_mat.setTexture("ColorMap", tex3);
    }

    private void createCamera() {
        flyCam.setEnabled(false);
        if(null != tank) {
            ChaseCamera chaseCam = new ChaseCamera(cam, tank, inputManager);
            chaseCam.setSmoothMotion(true);
            chaseCam.setMaxDistance(100000);
            chaseCam.setMinVerticalRotation(-FastMath.PI / 2);
            viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        } else {
            flyCam.setEnabled(false);
            flyCam.setMoveSpeed(10f);
            
            cam.setLocation(new Vector3f(-8.210456f, 10.598184f, -11.870819f));
            cam.setRotation(new Quaternion(0.16486596f, 0.360926f, -0.06600052f, 0.9155302f));
            
            //cam.setLocation(new Vector3f(-12.5f, 18.5f, playFieldsZ / 2));
            //cam.setRotation(new Quaternion(0.296f, 0.64054734f, -0.296f, 0.6433284f));
            // You must add a light to make the model visible
            DirectionalLight sun = new DirectionalLight();
            sun.setDirection(new Vector3f(0.85f, -1f, 1.0f));
            rootNode.addLight(sun);
        }
    }

    private void createLight() {
        Vector3f lightDir = new Vector3f(-0.8719428f, -0.46824604f, 0.14304268f);
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(new ColorRGBA(1.0f, 0.92f, 0.75f, 1f));
        dl.setDirection(lightDir);

        Vector3f lightDir2 = new Vector3f(0.70518064f, 0.5902297f, -0.39287305f);
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setColor(new ColorRGBA(0.7f, 0.85f, 1.0f, 1f));
        dl2.setDirection(lightDir2);

        rootNode.addLight(dl);
        rootNode.addLight(dl2);
    }
    
    public static Vector3f getUnitPositionCorrectionVector() {
        return unitPositionCorrectionVector;
    }
    
}