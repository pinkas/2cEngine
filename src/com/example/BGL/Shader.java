package com.example.BGL;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;

public class Shader {
	
	protected final int mProgram;
	
	public Shader( Context context, int vertexCodeId, int fragmentCodeId ) {
		String vertexShaderCode = TextResourceReader.readTextFileFromResource(context, vertexCodeId );
		String fragmentShaderCode = TextResourceReader.readTextFileFromResource(context, fragmentCodeId );
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
        	System.out.println( "compilation success!");
        return shader;
    }
    
    //TODO should that really be here ?
	public void loadTexture( Bitmap bitmap, int [] textureHandle ) {
        glGenTextures(1, textureHandle, 0);
        glBindTexture(GL_TEXTURE_2D, textureHandle[0]);
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        bitmap.recycle();
	}

	public int get_program () {
		return mProgram;
	}

	public void setuniform(BglObject obj, float[] mat) {
		// TODO Auto-generated method stub
	}
	
}
