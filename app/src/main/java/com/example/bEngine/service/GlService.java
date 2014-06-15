package com.example.bEngine.service;

import android.graphics.PointF;

import com.example.bEngine.BtextureManager;
import com.example.bEngine.TextureCoordCalculator;
import com.example.bEngine.object.SpriteSheet;

import java.nio.FloatBuffer;

/**
 * Created by Ben on 14-Jun-14.
 */
public class GlService {

    private String shaderName = "basic";
    private boolean boundToCamera = false;
    private float alpha = 1.0f;

   	protected int [] textureHandle = new int [1];
    private FloatBuffer[] textCoordBuffer;
    
    protected PointF offsetCamera;
    private Gl textCoordService;


    public GlService(){
        textCoordBuffer = TextureCoordCalculator.calculate();
        textCoordService = new TextureTextCoord();
    }
    public GlService(String shaderName, boolean boundToCamera, float alpha) {
        textCoordService = new TextureTextCoord();
        //TODO merger cette class et TextureCoordCalculator??????????
        textCoordBuffer = TextureCoordCalculator.calculate();
        this.shaderName = shaderName;
        this.boundToCamera = boundToCamera;
        this.alpha = alpha;
    }

    public FloatBuffer getTextCoordBuffer(int 0){
        //TODO should i  (le textcoordbuffer) be stored in the object or the glService?
        // I'd say the object.
        //TODO The same for l'actual textCoord, see method below
        return  textCoordBuffer[0];
    }

    public void setTextCoordIndex(int i){
        textCoordBuffer[0].position(i * 12);
    }


    public void setTextureHandle(int[] res){
        //TODO it's for rectangle
        if (res == null) return;
        for (int i=0;i<res.length;i++){
            textureHandle[i] = BtextureManager.getInstance().findHandle(res[i]);
        }

    }

	public int[] getTextureHandle() {
		return textureHandle;
	}
    //increase size of textureHandle array
    public void resizeTextureHandle(int size){
        textureHandle = new int[size];
    }

    //TODO avoir une reference vers un FloatBuffer YADA (non pas le tableau de floatBuffer)
    //TODO et une methode qui fait pointer ce FloatBuffer sur le bon indice du tableau
    //TODO textCoordBuffer. La classe AnimatedSprite fais juste changer ce sur quoi YADA pointe
    //le basic shader fait juste un "GetYada"

    public void recalculateTextCoord(float x,float y, float w, float h, SpriteSheet[] ss){
        textCoordBuffer = TextureCoordCalculator.calculate(x,y,w,h,ss);
    }

    public String getShaderName() {
        return shaderName;
    }
    public void setShaderName(String shaderName) {
        this.shaderName = shaderName;
    }

    public boolean isBoundToCamera() {
        return boundToCamera;
    }
    public void setBoundToCamera(boolean bound) { //TODO ugly
        if (!bound){
            offsetCamera = null;
        }
        boundToCamera = bound ;
    }

    public float getAlpha() {
        return alpha;
    }
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public  void setOffsetCamera( PointF offset ){
        offsetCamera = offset;
    }
    public PointF getCameraOffset(){
        return offsetCamera;
    }
}
