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

    private final int mPositionHandle;
    private final int mMVPMatrixHandle;
    private  final int mColorHandle;
    private static final int VERTEX_COUNT = 4;
    private static final int COORDS_PER_VERTEX = 3;
    private final int VERTEXSTRIDE = COORDS_PER_VERTEX * VERTEX_COUNT; // 4 bytes per vertex

    public RectangleShader( Context context ){

        super(context, R.raw.rectangle_vertex, R.raw.rectangle_fragment);

        name = "rect";
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
        mColorHandle = glGetUniformLocation(mProgram, "vColor");
    }
    public void sendParametersToShader( BglObject obj, float[] mat) {

        glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GL_FLOAT, false, VERTEXSTRIDE, vertexBuffer);
        glEnableVertexAttribArray(mPositionHandle);

        glUniform4fv(mColorHandle, 1, obj.getColor(), 0);
        glUniformMatrix4fv( mMVPMatrixHandle, 1, false, mat, 0);
    }

}
