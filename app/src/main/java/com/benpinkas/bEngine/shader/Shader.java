package com.benpinkas.bEngine.shader;

import android.content.Context;
import static android.opengl.GLES20.*;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.TextResourceReader;

public abstract class Shader {

    public static final int BYTES_PER_FLOAT = 4;
    public static final int COORDS_PER_VERTEX = 4;

	protected final int mProgram;
    protected String name;


    protected int totalVertexCount;

    public static float verticesCoord[] = {
        // Triangle 1
        -1, -1, 0, 1, // bottom left
        -1, 1, 0, 1,  // top left
        1, -1, 0, 1,  // bottom right
        // Triangle 2
        1, -1, 0, 1,  // bottom right
        1, 1, 0, 1,    // top right
        -1, 1, 0, 1 // top left
    };


	public Shader( Context context, int vertexCodeId, int fragmentCodeId ) {
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
        	System.out.println( "  ===>shader compilation failed" );
        }
        else
        	System.out.println( "  ===>compilation success!!");
        return shader;
    }

    public int getVertexCount(){
        return totalVertexCount;
    }

    public String getName(){
        return name;
    }

	public int get_program () {
		return mProgram;
	}
    public void prepare(){};

	public abstract void sendParametersToShader(BglObject obj, float[] mat);
    public abstract void setAttributeBuffers();
    public abstract void storeAttributes(BglObject o);
}
