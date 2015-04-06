package com.benpinkas.bEngine.shader;


import static android.opengl.GLES20.*;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.R;

import android.content.Context;


/*TODO
*
*  this is a shader that uses texture and everything that should deal
*  with bglobject but at least a bglsprite level.
*
*
*
* */

public class BasicShader extends Shader {

	private final int mPositionHandle;
	private final int mTextureCoordinateHandle;
	private final int mMVPMatrixHandle;
//	private final int mTextureUniformHandle;
    private final int mAlphaHandle;
    private final int currentTextureHandle = 0;
	private static final int COORDS_PER_VERTEX = 3;

	public BasicShader( Context context ){
		
		super(context, R.raw.basic_vertex, R.raw.basic_fragment);

        name = "basic";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordinateHandle = glGetAttribLocation(mProgram, "a_texCoord");
        mAlphaHandle = glGetUniformLocation(mProgram,"alpha");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
//        mTextureUniformHandle = glGetUniformLocation(mProgram, "u_texture");

        glEnableVertexAttribArray(mTextureCoordinateHandle);
        glEnableVertexAttribArray(mPositionHandle);

        glActiveTexture(GL_TEXTURE0 );
    }
	
	public  void sendParametersToShader( BglObject obj, float[] mat) {

        // Send vertices coordinates
        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0,
                obj.glService.getVertexBuffer());

        // Send texture coordinates
        glVertexAttribPointer(mTextureCoordinateHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0,
                obj.glService.getTextCoordBuffer());
        int nextTextureHandle = obj.glService.getTextureHandle();
        if (currentTextureHandle != nextTextureHandle) {
            glBindTexture(GL_TEXTURE_2D, nextTextureHandle);
        }
     //   glUniform1i(mTextureUniformHandle, 0);

        // Send alpha
        glUniform1f(mAlphaHandle, obj.glService.getAlpha() );

        // send MVP
	    glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mat, 0);
	}
	
}
