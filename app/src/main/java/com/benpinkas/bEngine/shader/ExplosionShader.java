package com.benpinkas.bEngine.shader;

import static android.opengl.GLES20.*;
import android.content.Context;

import com.benpinkas.bEngine.object.BglObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class ExplosionShader extends TextureShader {

    private final int mTrajectoryHandle;
    private final int stride = 0;

    protected FloatBuffer vertexTrajBuffer;

    public ExplosionShader(Context context, int vertexCodeId, int fragmentCodeId) {
        super(context, vertexCodeId, fragmentCodeId);

        name="explosion";

        mTrajectoryHandle = glGetAttribLocation(mProgram, "trajectory");
        glEnableVertexAttribArray(mTrajectoryHandle);

        ByteBuffer bb = ByteBuffer.allocateDirect(BYTES_PER_FLOAT * 4000);
        bb.order (ByteOrder.nativeOrder());
        vertexTrajBuffer = bb.asFloatBuffer();
    }

    public void prepare(){
        super.prepare();
        vertexTrajBuffer.position(0);
    }

    @Override
    public void storeAttributes(BglObject o) {
        super.storeAttributes(o);

        float[] velocity = o.glService.getExtraVertexData();
        int squareIndex = 0;

        int cpt = 0;
        //(velocity.length/2)*2*3
        for (int i = 0; i<velocity.length*3; i++) {

            if (cpt > 5) {
                cpt = 0;
                squareIndex = squareIndex + 2;
            }

            vertexTrajBuffer.put(velocity[squareIndex]);
            vertexTrajBuffer.put(velocity[squareIndex + 1]);
            cpt++;
        }
    }

    @Override
    public void setAttributeBuffers() {
        super.setAttributeBuffers();
        vertexTrajBuffer.position(0);
    }

    @Override
    public void sendParametersToShader(BglObject obj, float[] mat) {
        super.sendParametersToShader(obj, mat);

        // Send trajetory
        glVertexAttribPointer(mTrajectoryHandle, 2, GL_FLOAT, false, stride,
                vertexTrajBuffer);
    }
}
