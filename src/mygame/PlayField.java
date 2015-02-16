package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author Jan
 */
public class PlayField {

    private Main main;
    private float playFieldsX;
    private float playFieldsZ;
    private float playFieldSize;
    private final AssetManager assetManager;
    private final Node rootNode;
    private Node floorNode;

    public Node getFloorNode() {
        return floorNode;
    }
    private static final Quaternion pitch90;
    static {
        pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI / 2, new Vector3f(-1, 0, 0));
    }

    
    public PlayField(Main main, float playFieldsX, float playFieldsZ, float playFieldSize) {
        this.main = main;
        this.playFieldsX = playFieldsX;
        this.playFieldsZ = playFieldsZ;
        this.playFieldSize = playFieldSize;
        this.assetManager = main.getAssetManager();
        this.rootNode = main.getRootNode();
        createPlayField();
    }
    
    /**
     * Creates the play fields
     */
    private void createPlayField() {
        floorNode = new Node("floorNode");
        rootNode.attachChild(floorNode);
        float playFieldOffsetX = 0;
        float playFieldOffsetZ = 0;
        for (float x = 0; x < playFieldsX; x++) {
            for (float z = 0; z < playFieldsZ; z++) {
                floorNode.attachChild(makeQuad(new Vector3f(playFieldOffsetX, 0, playFieldOffsetZ)));
                playFieldOffsetZ+=playFieldSize;
            }
            playFieldOffsetZ=0;
            playFieldOffsetX+=playFieldSize;
        }
        attachGrid(ColorRGBA.LightGray);
    }
    
    /**
     * Attaches a grid to the play fields
     * @param color
     * @return 
     */
    private Geometry attachGrid(ColorRGBA color) {
        Geometry g = new Geometry("wireframe grid", new Grid((int) playFieldsZ+1, (int) playFieldsX+1, 1));
        g.scale(playFieldSize);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(playFieldsX*playFieldSize / 2, .015f, (playFieldsZ*playFieldSize / 2) - playFieldSize);
        rootNode.attachChild(g);
        return g;
    }
    
    private Geometry makeQuad(Vector3f vector3f) {
        Quad b = new Quad(playFieldSize, playFieldSize);
        //Box b = new Box(1, 0.01f, 1);
        Geometry geom = new Geometry("QuadHover", b);
        geom.setMaterial(main.matUnselected);
        geom.setLocalTranslation(new Vector3f(vector3f));
        /* Apply the rotation to the object */
        geom.setLocalRotation(pitch90);
        return geom;
    }
}
