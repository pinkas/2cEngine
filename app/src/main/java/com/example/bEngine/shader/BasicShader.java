package com.example.bEngine.shader;


import static android.opengl.GLES20.*;

import com.example.bEngine.object.BglObject;
import com.example.helloben.R;
import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


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
	private static final int VERTEX_COUNT = 4;
	private static final int COORDS_PER_VERTEX = 3;
	private final int VERTEXSTRIDE = COORDS_PER_VERTEX * VERTEX_COUNT; // 4 bytes per vertex


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
	
        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, VERTEXSTRIDE, vertexBuffer );
        glEnableVertexAttribArray(mPositionHandle);

        glVertexAttribPointer( mTextureCoordinateHandle, 2, GL_FLOAT, false, VERTEXSTRIDE,
                               obj.glService.getTextCoordBuffer() );
        glEnableVertexAttribArray(mTextureCoordinateHandle);

        glActiveTexture(GL_TEXTURE0 );
        glBindTexture(GL_TEXTURE_2D, obj.glService.getTextureHandle() );

        glUniform1f( mAlphaHandle, obj.glService.getAlpha() );

        glUniform1i(mTextureUniformHandle, 0);
	    glUniformMatrix4fv( mMVPMatrixHandle, 1, false, mat, 0);
	}
	
}
