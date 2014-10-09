package com.benpinkas.bEngine.shader;

import android.content.Context;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public abstract class Shader {

    private final int BYTES_PER_FLOAT = 4;

	protected final int mProgram;
    protected String name;

    protected static FloatBuffer vertexBuffer;
    protected static int[] vertexBufferID = new int[1];

    protected static float objCoords[] = {
            -1, -1, 0,    // top left
            -1, 1, 0,   // bottom left
            1, -1, 0,   // bottom right
            1,  1, 0 }; // top right

	
	public Shader( Context context, int vertexCodeId, int fragmentCodeId ) {

        ByteBuffer bb = ByteBuffer.allocateDirect( objCoords.length * 4);
        bb.order( ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);

        glGenBuffers(1, vertexBufferID, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID[0]);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity() * BYTES_PER_FLOAT,
                vertexBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vertexBuffer.limit(0);
        vertexBuffer = null;

        String vertexShaderCode = TextResourceReader.readTextFileFromResource(context, vertexCodeId);
		String fragmentShaderCode = TextResourceReader.readTextFileFromResource(context, fragmentCodeId);
        int vertexShader = loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
        // create a GL program, add the shaders to it, and link.
        mProgram = glCreateProgram();             
        glAttachShader(mProgram, vertexShader);   
        glAttachShader(mProgram, fragmentShader); 
        glLinkProgram(mProgram);
	}
	
    public static int loadShader(int type, String shaderCode){
        int shader = glCreateShader(type);
        // add the source code to the shader and compile it
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        // check if compilation was successful
        final int[] compileStatus = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
        	// compilation failed
        	glDeleteShader(shader);
        	System.out.println( "======>shader compilation failed" );
        }
        else
        	System.out.println( "compilation success!!!!!!!!!!!!!!!!!!!!!!");
        return shader;
    }

	public int get_program () {
		return mProgram;
	}
    public void prepare(){};

	public abstract void sendParametersToShader(BglObject obj, float[] mat);

	
}
