package com.benpinkas.bEngine.shader;

import android.content.Context;
import android.opengl.Matrix;

import static android.opengl.GLES20.*;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import static android.opengl.GLES20.glVertexAttribPointer;

public class RectangleShader extends Shader {

    private static int mPositionHandle = -1;
    private static int mMVPMatrixHandle = - 1;
    private static int mColorHandle = -1;
    private static int mAlphaHandle = -1;

    private FloatBuffer vertexCoordBuffer;

    float[] vertexP = new float[COORDS_PER_VERTEX];


    public RectangleShader( Context context ){
        super(context, R.raw.rectangle_vertex, R.raw.rectangle_fragment);

        name = "rect";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
        mColorHandle = glGetUniformLocation(mProgram, "vColor");
        mAlphaHandle = glGetUniformLocation(mProgram,"alpha");

        glEnableVertexAttribArray(mPositionHandle);

        ByteBuffer bb = ByteBuffer.allocateDirect( BYTES_PER_FLOAT * 200 );
        bb.order(ByteOrder.nativeOrder());
        vertexCoordBuffer = bb.asFloatBuffer();
    }

    public void storeAttributes(BglObject o) {

        float[] modelMat = o.getModelMat();
        float[] verticesCoord = o.glService.getVerticesCoordRaw();
        int objVertexCount = verticesCoord.length / COORDS_PER_VERTEX ;
        totalVertexCount = totalVertexCount + objVertexCount;

        if(o.isDirty()){
            for (int i = 0; i < objVertexCount; i++) {
                Matrix.multiplyMV(vertexP, 0, modelMat, 0, verticesCoord, i * COORDS_PER_VERTEX);
                o.glService.setVertexViaIndex(i, vertexP);
            }
            o.setDirty(false);
        }

        vertexCoordBuffer.put(o.glService.getVertexViaIndex());
    }

    public void setAttributeBuffers() {
        vertexCoordBuffer.position(0);
        totalVertexCount = 0;
    }

    public void prepare(){
        vertexCoordBuffer.position(0);
    //    glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID[0]);

    //    glEnableVertexAttribArray(mPositionHandle);
    //    glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT,
    //            false, 0, 0);

    //    glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void sendParametersToShader( BglObject obj, float[] mat) {

        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0,
            vertexCoordBuffer);

        glUniform4fv(mColorHandle, 1, obj.getColor(), 0);

        glUniform1f(mAlphaHandle, obj.glService.getAlpha() );

        glUniformMatrix4fv( mMVPMatrixHandle, 1, false, mat, 0);
    }

}
