package com.benpinkas.bEngine.service;

import android.graphics.PointF;

import com.benpinkas.bEngine.BtextureManager;
import com.benpinkas.bEngine.TextureCoordCalculator;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.shader.Shader;

public class GlService {

    private static final int VERTEX_PER_TRIANGLE = 3;
    private static final int TRIANGLE_PER_RECTANGLE = 2;
    private static final int FLOAT_PER_RECTANGLE = Shader.COORDS_PER_VERTEX * TRIANGLE_PER_RECTANGLE
            * VERTEX_PER_TRIANGLE;

    private float[] vertexCoords;
    private float[] verticesCoordRaw;
    private int[] textureHandle = new int[1];
    private int textureHandleIndex;
    private float [][][] textCoords = new float[1][1][1];
    private float[] extraVertexData;

    protected PointF offsetCamera;

    private int spriteFrame;

    private String shaderName = "textureShader";

    private boolean boundToCamera = false;

    private float alpha = 1.0f;


    public GlService() {
        textCoords[0][0] = TextureCoordCalculator.calculate(1,1);
        textureHandle[0] = -1;
        calcVertexCoord(1, 1);
    }

    public GlService(String shaderName, boolean boundToCamera, float alpha) {
        calcVertexCoord(1,1);
        textCoords[0][0] = TextureCoordCalculator.calculate(1,1);
        this.shaderName = shaderName;
        this.boundToCamera = boundToCamera;
        this.alpha = alpha;
    }

    public void calcVertexCoord(int Nx, int Ny){

        int floatPerRow = Nx * FLOAT_PER_RECTANGLE;
        verticesCoordRaw = new float[Nx*Ny*FLOAT_PER_RECTANGLE];
        vertexCoords = new float[verticesCoordRaw.length];
        for (int row=0; row<Ny; row++) {
            for (int col=0; col<Nx; col++) {

                float left = -1 + (2.0f / Nx) * col;
                float right = -1 + (2.0f / Nx) * (col+1);
                float top = 1 - (2.0f/Ny) * row;
                float bottom = 1 - (2.0f/Ny) * (row+1);

                int offset = col*FLOAT_PER_RECTANGLE + row*floatPerRow;

                //bottom left
                verticesCoordRaw[0 + offset] = left;
                verticesCoordRaw[1 + offset] = bottom;
                verticesCoordRaw[2 + offset] = 0;
                verticesCoordRaw[3 + offset] = 1;
                //top left
                verticesCoordRaw[4 + offset] = left;
                verticesCoordRaw[5 + offset] = top;
                verticesCoordRaw[6 + offset] = 0;
                verticesCoordRaw[7 + offset] = 1;
                //bottom right
                verticesCoordRaw[8 + offset] = right;
                verticesCoordRaw[9 + offset] = bottom;
                verticesCoordRaw[10 + offset] = 0;
                verticesCoordRaw[11 + offset] = 1;

                //bottom right
                verticesCoordRaw[12 + offset] = verticesCoordRaw[8 + offset];
                verticesCoordRaw[13 + offset] = verticesCoordRaw[9 + offset];
                verticesCoordRaw[14 + offset] = verticesCoordRaw[10 + offset];
                verticesCoordRaw[15 + offset] = verticesCoordRaw[11 + offset];
                //top right
                verticesCoordRaw[16 + offset] = right;
                verticesCoordRaw[17 + offset] = top;
                verticesCoordRaw[18 + offset] = 0;
                verticesCoordRaw[19 + offset] = 1;
                //top left
                verticesCoordRaw[20 + offset] = verticesCoordRaw[4 + offset ];
                verticesCoordRaw[21 + offset] = verticesCoordRaw[5 + offset];
                verticesCoordRaw[22 + offset] = verticesCoordRaw[6 + offset];
                verticesCoordRaw[23 + offset] = verticesCoordRaw[7 + offset];
            }
        }
    }

    public void setExtraVertexData(float[] extraVertexData){
        this.extraVertexData = extraVertexData;
    }

    public float[] getExtraVertexData(){
        return extraVertexData;
    }

    public void tesselate(int Nx, int Ny){
        textCoords[0][0] = TextureCoordCalculator.calculate(Nx,Ny);
        calcVertexCoord(Nx,Ny);
    }

    public float[] getVerticesCoordRaw(){
        return verticesCoordRaw;
    }

    public float[] getTextCoords() {
        return textCoords[textureHandleIndex][spriteFrame];
    }

    public void setVertexViaIndex(int vertexIndex, float[] val){
        for (int i=0; i < Shader.COORDS_PER_VERTEX; i++) {
            vertexCoords[vertexIndex*(Shader.COORDS_PER_VERTEX) + i] = val[i];
        }
    }

    public float[] getVertexViaIndex(){
        return vertexCoords;
    }

    public void setTextCoordPos(int i) {
        spriteFrame = i;
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

    public void recalculateTextCoord(SpriteSheet[] ss) {
        textCoords = TextureCoordCalculator.calculate(ss);
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
