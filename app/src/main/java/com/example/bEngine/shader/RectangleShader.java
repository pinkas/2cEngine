package com.example.bEngine.shader;

import android.content.Context;
import static android.opengl.GLES20.*;

import com.example.bEngine.object.BglObject;
import com.example.helloben.R;

import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Ben on 10/9/13.
 */
public class RectangleShader extends Shader {

    private static int mPositionHandle = -1;
    private static int mMVPMatrixHandle = - 1;
    private static int mColorHandle = -1;
    private static final int COORDS_PER_VERTEX = 3;

    public RectangleShader( Context context ){

        super(context, R.raw.rectangle_vertex, R.raw.rectangle_fragment);

        name = "rect";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
        mColorHandle = glGetUniformLocation(mProgram, "vColor");
    }

    public void prepare(){
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID[0]);

        glEnableVertexAttribArray(mPositionHandle);
        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT,
                false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void sendParametersToShader( BglObject obj, float[] mat) {
        glUniform4fv(mColorHandle, 1, obj.getColor(), 0);

        glUniformMatrix4fv( mMVPMatrixHandle, 1, false, mat, 0);
    }

}
