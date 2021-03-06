package com.benpinkas.bEngine;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.service.MessageManager;
import com.benpinkas.bEngine.shader.Shader;
import com.benpinkas.bEngine.shader.ShaderList;

import android.content.Context;

import static android.opengl.GLES20.*;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;


public class Brenderer implements GLSurfaceView.Renderer {

    private static float camXworld = 0.5f;
    private static float camYworld = 0.5f;
    private static final float[] projMatrix = new float[16];
    private static final float[] viewMatrix = new float[16];
    private static final float[] projMatrixInv = new float[16];

    private static final float[] farPointNdc = new float[4];
    private static final float[] farPointWorld = new float[4];
    private static final float[] farSizeWorld = new float[4];
    private static final float[] camTODOFIXME = new float[4];
    private static final float upX = 0.0f;
    private static final float upY = 1.0f;
    private static final float upZ = 0.0f;
    private Context context;
    private Shader shader;
    private static float camX = 0.0f;
    private static float lookX = camX;
    private static float camY = 0.0f;
    private static float lookY = camY;
    private static float camZ = 0.0f;
    private static float lookZ = -1.0f;

    private static int screenW;
    private static int screenH;

    private static final float NS_PER_SEC = 1000000000;
    private static float prev;
    private static final float MAX_FRAME_DELTA_SEC = 0.1f;
    private static float screenAspectRatio;

    public Brenderer(Context context) {
        super();
        this.context = context;
    }

    public static float getCamPosX() {
        return camXworld;
    }

    public static float getCamPosY() {
        return camYworld;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        glClearColor(1.0f, 1.0f, 1f, 0.0f);
        // TODO have a list that we go through
        new ShaderList(context);
        BtextureManager.loadAll(context);
        MessageManager.sendMessage("oscgl");
    }

    public static void calculateViewProjectionMatrix(BglObject obj) {

        float[] viewProjectMat = obj.getMvp();
        Matrix.setIdentityM(viewProjectMat, 0);

        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);

/*
        if (obj.glService.isBoundToCamera()) {

            if (camOffset == null) {
                // World coordinate wise the camera is initially in (0.5 0.5)
                camOffset = new PointF(0.5f - pos.x, 0.5f - pos.y);
                camXO = camXO - pos.x;
                camYO = camYO - pos.y;
            }
            moveCam(pos.x + camOffset.x, pos.y + camOffset.y);
        }
*/
        if (obj.getDisregardCam()) Matrix.setIdentityM(viewMatrix, 0);

        Matrix.multiplyMM(viewProjectMat, 0, projMatrix, 0, viewMatrix, 0);
    }

    public static void calculateModelMatrix(BglObject obj) {

        float[] modelMat = obj.getModelMat();
        Matrix.setIdentityM(modelMat, 0);

        boolean perspective_scorll = false;

        float objX = obj.getPosX();
        float objY = obj.getPosY();
        float sizeW = obj.getSizeW();
        float sizeH = obj.getSizeH();

        float xGl = (objX + sizeW / 2f) * 2f - 1;
        float yGl = 1 - (objY + sizeH / 2f) * 2f;
        float z = obj.getZ();

     //   if (z != 0) perspective_scorll = true;
        fromWorldToGlFar(xGl, yGl, z, farPointWorld);
        fromWorldToGlFar(sizeW, sizeH, 0, farSizeWorld);

        float div = 1 - z;
        if (perspective_scorll) {
            farSizeWorld[0] = farSizeWorld[0] / div;
            farSizeWorld[1] = farSizeWorld[1] / div;
        }

        Matrix.translateM(modelMat, 0, farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        Matrix.rotateM(modelMat, 0, obj.getAngleX(), 1, 0, 0);
        Matrix.rotateM(modelMat, 0, obj.getAngleY(), 0, 1, 0);
        Matrix.rotateM(modelMat, 0, obj.getAngleZ(), 0, 0, 1);
        Matrix.scaleM(modelMat, 0, farSizeWorld[0], farSizeWorld[1], farSizeWorld[2]);
    }

    public static void moveCam(float x, float y) {
        camXworld = x;
        camYworld = y;
        x = x * 2 - 1;
        y = 1 - y * 2;
        fromWorldToGlFar(x, y, 0f, camTODOFIXME);
        camX = camTODOFIXME[0];
        camY = camTODOFIXME[1];
        lookX = camX;
        lookY = camY;
        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

    public static float[] getProjMatInv(){
        return projMatrixInv;
    }

    public static int getScreenW() {
        return screenW;
    }

    public static int getScreenH() {
        return screenH;
    }

    public static float getScreenAspectRatio(){
        return screenAspectRatio;
    }

    public static float getCamXworld() {
        return camXworld;
    }

    public static float getCamYworld() {
        return camYworld;
    }

    public static float getCamY() {
        return camY;
    }

    public static float getCamX() {
        return camX;
    }

    public static void fromWorldToGlFar(float x, float y, float z, float[] pointInput) {

        farPointNdc[0] = x;
        farPointNdc[1] = y;
        farPointNdc[2] = z;
        farPointNdc[3] = 1;

        pointInput[0] = pointInput[1] = pointInput[2] = pointInput[3] = 0;

        Matrix.multiplyMV(pointInput, 0, projMatrixInv, 0, farPointNdc, 0);
        pointInput[0] /= pointInput[3];
        pointInput[1] /= pointInput[3];
        pointInput[2] /= pointInput[3];
    }

    @Override
    public void onDrawFrame( GL10 unused ) {
        if ( prev == 0 ) {
            prev = System.nanoTime();
            return;
        }
        float now = System.nanoTime();
        float dt = ( now - prev ) / NS_PER_SEC;
   //     System.out.println(now-prev);
        prev = now;

        if ( dt > MAX_FRAME_DELTA_SEC ) {
            dt = MAX_FRAME_DELTA_SEC;
        }
        //InputStatus.updateObjectsTouchStates(screenW, screenH);
        //TODO update les "updateOnlyMembers" cf update method in SceneManager
        SceneManager.updateTimers( dt );
        Scene focusScene = SceneManager.getInputFocus();

        MessageManager.deliverMessages();

        UpdateableManager.update(dt);

        glClear(GL_COLOR_BUFFER_BIT);

		// 1 - Scene enumeration
        for ( Scene scene : SceneManager.getScenes() )
        {
            scene.update( dt );
            if ( scene.getVisible() )
            {
                if ( scene.dirty ) {
                    scene.fillMembers();
                    scene.dirty = false;
                }

                // Objects that don't get rendered (  like a group of other objects )
                ArrayList<Bobject> updateOnlyObj = scene.getUpdateOnly();
                for ( Bobject o : updateOnlyObj ) {
                    o.update( dt );
                }
                // 2 - Objects enumeration
                BglObject[] members = scene.getMembers();

                int i = 0;
                boolean draw = false;

                for ( BglObject obj : members )
                {
                    i++;

                    if ( obj == null ) {
                        continue;
                    }
                    if (scene == focusScene) {
                        InputStatus.updateObjecTouchState(obj, screenW, screenH);
                    }

                    obj.update( dt );

                    // have one if just for visible, dirty and collide
                    if ( obj.visible )
                    {
                        if ( obj.isDirty() )
                        {
                            if(obj.collide)
                            {
                                ArrayList<BglObject> collider = obj.collisionService.getCollider();
                                for( BglObject obj2 : collider )
                                {
                                    if ( obj2 == null || !obj2.visible || !obj2.collide || obj == obj2 )
                                        continue;
                                    if ( obj.collisionService.collide( obj, obj2 ) ) {
                                        obj.collisionService.fixPos( obj, obj2 );
                                        break;
                                    }
                                }
                            }

                            calculateModelMatrix( obj );
                            calculateViewProjectionMatrix( obj );
                        }

                        Shader shaderProg =  ShaderList.getProg( obj.glService.getShaderName() );
                        if (shader != shaderProg){
                            shader = shaderProg;
                            glUseProgram( shader.get_program() );
                            shader.prepare();
                        }
                        shader.storeAttributes( obj );

                        if ( i == members.length ) {
                            draw = true;
                        } else {
                            BglObject nextVisible = members[i];
                            int nextVisibleIndex = i;
                            while ( !nextVisible.isVisible() ) {
                                nextVisibleIndex ++;
                                if ( nextVisibleIndex >= members.length )
                                    break;
                                nextVisible = members[nextVisibleIndex];
                            }

                            if ( nextVisible.glService.getTextureHandle() != obj.glService.getTextureHandle() ) {
                                draw = true;
                            } else if ( members[i].getColor() != obj.getColor() ) {
                                draw = true;
                            } else if ( nextVisible.glService.getShaderName() != obj.glService.getShaderName() ) {
                                draw = true;
                            }
                        }

                        if ( draw ) {
                            int vertexCount = shader.getVertexCount();
                            shader.setAttributeBuffers();
                            shader.sendParametersToShader( obj, obj.getMvp() );
                            glDrawArrays( GL_TRIANGLES, 0, vertexCount );
                            draw = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        screenW = width;
        screenH = height;
        screenAspectRatio = (float) screenW / (float) screenH;

        MatrixHelper.perspectiveM(projMatrix, 45, (float) screenW / (float) screenH, 1f, 200f);
        Matrix.invertM(projMatrixInv, 0, projMatrix, 0);
    }

}
