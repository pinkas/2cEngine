package com.benpinkas.bEngine.shader;

import static android.opengl.GLES20.*;

import com.benpinkas.bEngine.object.BglObject;

import android.content.Context;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class TextureShader extends Shader {

    public static final int COORDS_PER_TEXTURE_POINT = 3;

	protected final int mPositionHandle;
	protected final int mTextureCoordinateHandle;
	protected final int mMVPMatrixHandle;
	protected final int mTextureUniformHandle;
    protected final int mAlphaHandle;

    protected FloatBuffer vertexTextCoordBuffer;
    protected FloatBuffer vertexCoordBuffer;

    float[] vertexP = new float[COORDS_PER_VERTEX];


	public TextureShader(Context context, int vertexCodeId, int fragmentCodeId) {
		
		super(context, vertexCodeId, fragmentCodeId);

        name = "textureShader";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordinateHandle = glGetAttribLocation(mProgram, "a_texCoord");
        mAlphaHandle = glGetUniformLocation(mProgram,"alpha");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
        mTextureUniformHandle = glGetUniformLocation(mProgram, "u_texture");

        glEnableVertexAttribArray(mTextureCoordinateHandle);
        glEnableVertexAttribArray(mPositionHandle);

        glActiveTexture(GL_TEXTURE0);

        ByteBuffer bb = ByteBuffer.allocateDirect( BYTES_PER_FLOAT * 4000 );
        bb.order(ByteOrder.nativeOrder());
        vertexCoordBuffer = bb.asFloatBuffer();

        ByteBuffer bb2 = ByteBuffer.allocateDirect( BYTES_PER_FLOAT * 4000);
        bb2.order (ByteOrder.nativeOrder());
        vertexTextCoordBuffer = bb2.asFloatBuffer();
    }

    public void prepare(){
        vertexCoordBuffer.position(0);
        vertexTextCoordBuffer.position(0);
    }

    public void storeAttributes(BglObject o) {

        float[] modelMat = o.getModelMat();
        float[] verticesCoord = o.glService.getVerticesCoordRaw();
        int objVertexCount = verticesCoord.length/COORDS_PER_VERTEX;
        totalVertexCount = totalVertexCount + objVertexCount;

        if(o.isDirty()){
            for (int i = 0; i < objVertexCount; i++) {
                Matrix.multiplyMV(vertexP, 0, modelMat, 0, verticesCoord, i * COORDS_PER_VERTEX);
                o.glService.setVertexViaIndex(i, vertexP);
            }
            o.setDirty(false);
        }

        vertexCoordBuffer.put( o.glService.getVertexViaIndex() );
        vertexTextCoordBuffer.put(o.glService.getTextCoords());
    }

    public void setAttributeBuffers(){
        vertexCoordBuffer.position(0);
        vertexTextCoordBuffer.position(0);

        totalVertexCount = 0;
    }

	public void sendParametersToShader( BglObject obj, float[] mat) {
        // Send vertices coordinates
        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0,
                vertexCoordBuffer);

        // Send texture coordinates
        glVertexAttribPointer(mTextureCoordinateHandle, COORDS_PER_TEXTURE_POINT, GL_FLOAT,
                false, 0, vertexTextCoordBuffer);

        glBindTexture(GL_TEXTURE_2D, obj.glService.getTextureHandle());
//        glUniform1i(mTextureUniformHandle, obj.glService.getTextureHandle());

        // Send alpha
        glUniform1f(mAlphaHandle, obj.glService.getAlpha() );

        // send MVP
        glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mat, 0);
	}
}
