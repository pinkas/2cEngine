package com.benpinkas.bEngine.shader;

import static android.opengl.GLES20.*;

import android.content.Context;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.R;


public class WobblyShader extends Shader {

	private final int mPositionHandle;
	private final int mTextureCoordinateHandle;
	private final int mMVPMatrixHandle;
//	private final int mTextureUniformHandle;
	private final int mTimeHandle;
	private static final int VERTEX_COUNT = 4;
	private static final int COORDS_PER_VERTEX = 3;
	private final int VERTEXSTRIDE = COORDS_PER_VERTEX * VERTEX_COUNT; // 4 bytes per vertex
    
    private float test;

		
	public WobblyShader( Context context ) {
		
		super(context, R.raw.basic_vertex, R.raw.wobbly_fragment);

        name = "wobbly";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordinateHandle = glGetAttribLocation(mProgram, "a_texCoord");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
    //    mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_texture");	
        mTimeHandle = glGetUniformLocation(mProgram, "time");
        
        test = 0;
	}
	
	public void sendParametersToShader ( BglObject obj, float[] mat)
    {
//        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, VERTEXSTRIDE,
//                vertexCoordBuffer);
        glEnableVertexAttribArray(mPositionHandle);
        
//        glVertexAttribPointer(mTextureCoordinateHandle, 2, GL_FLOAT, false,  VERTEXSTRIDE,
//                vertexCoordBuffer);
        glEnableVertexAttribArray(mTextureCoordinateHandle); 
        
  //      glActiveTexture( GLES20.GL_TEXTURE0 );
        glBindTexture(GL_TEXTURE_2D,  obj.glService.getTextureHandle() );
  //      glUniform1i(mTextureUniformHandle, 0);
        
	    glUniformMatrix4fv( mMVPMatrixHandle, 1, false, mat, 0);
	    test = test%3 + 0.05f;
	    glUniform1f(mTimeHandle, test);
	}

    public void storeAttributes(BglObject o){

    }

    @Override
    public void setAttributeBuffers() {}

}
