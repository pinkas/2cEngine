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
	private final int mTextureUniformHandle;
    private final int mAlphaHandle;
	private static final int COORDS_PER_VERTEX = 3;

	public BasicShader( Context context ){
		
		super(context, R.raw.basic_vertex, R.raw.basic_fragment);

        name = "basic";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordinateHandle = glGetAttribLocation(mProgram, "a_texCoord");
        mAlphaHandle = glGetUniformLocation(mProgram,"alpha");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
        mTextureUniformHandle = glGetUniformLocation(mProgram, "u_texture");
    }
	
	public  void sendParametersToShader( BglObject obj, float[] mat) {

        // Send vertices coordinates
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID[0] );
        glEnableVertexAttribArray(mPositionHandle);
        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Send texture coordinates
        glEnableVertexAttribArray(mTextureCoordinateHandle);
        glVertexAttribPointer(mTextureCoordinateHandle, COORDS_PER_VERTEX, GL_FLOAT, false, 0,
                obj.glService.getTextCoordBuffer());
        glActiveTexture(GL_TEXTURE0 );
        glBindTexture(GL_TEXTURE_2D, obj.glService.getTextureHandle() );
        glUniform1i(mTextureUniformHandle, 0);

        // Send alpha
        glUniform1f(mAlphaHandle, obj.glService.getAlpha() );

        // send MVP
	    glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mat, 0);
	}
	
}
