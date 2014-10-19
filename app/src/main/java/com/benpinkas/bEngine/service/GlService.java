package com.benpinkas.bEngine.service;

import android.graphics.PointF;

import com.benpinkas.bEngine.BtextureManager;
import com.benpinkas.bEngine.TextureCoordCalculator;
import com.benpinkas.bEngine.object.SpriteSheet;

import java.nio.FloatBuffer;

/**
 * Created by Ben on 14-Jun-14.
 */
public class GlService {

    private String shaderName = "basic";
    private boolean boundToCamera = false;
    private float alpha = 1.0f;

    protected int[] textureHandle = new int[1];
    protected int textureHandleIndex;

    private FloatBuffer[] textCoordBuffer;

    protected PointF offsetCamera;


    public GlService() {
        textCoordBuffer = TextureCoordCalculator.calculate();
    }

    public GlService(String shaderName, boolean boundToCamera, float alpha) {
        //TODO merger cette class et TextureCoordCalculator??????????
        textCoordBuffer = TextureCoordCalculator.calculate();
        this.shaderName = shaderName;
        this.boundToCamera = boundToCamera;
        this.alpha = alpha;
    }

    public FloatBuffer getTextCoordBuffer() {
        //TODO should i  (le textcoordbuffer) be stored in the object or the glService?
        // I'd say the object.
        //TODO The same for l'actual textCoord, see method below
        return textCoordBuffer[textureHandleIndex];
    }

    public void setTextCoordPos(int i) {
        textCoordBuffer[textureHandleIndex].position(i * 12);
    }

    //TODO rename this function maybe?
    public void setTextureHandle(int[] res) {
        //TODO the following if is for rectangle, yes is ugly
        if (res == null) return;

        for (int i = 0; i < res.length; i++) {
            textureHandle[i] = BtextureManager.findHandle(res[i]);
        }

    }

    public void setTextureHandleIndex(int textHandleIndex){
        textureHandleIndex = textHandleIndex;
    }

    public int getTextureHandle() {
        return textureHandle[textureHandleIndex];
    }

    //increase size of textureHandle array
    public void resizeTextureHandle(int size) {
        textureHandle = new int[size];
    }

    public void recalculateTextCoord(float x, float y, float w, float h, SpriteSheet[] ss) {
        textCoordBuffer = TextureCoordCalculator.calculate(x, y, w, h, ss);
    }

    public void recalculateTextCoord( int x, int y, int tesselX, int tesselY ) {
        textCoordBuffer = TextureCoordCalculator.calculate(x, y, tesselX, tesselY);
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
        if (!bound) {
            offsetCamera = null;
        }
        boundToCamera = bound;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setOffsetCamera(PointF offset) {
        offsetCamera = offset;
    }

    public PointF getCameraOffset() {
        return offsetCamera;
    }
}
