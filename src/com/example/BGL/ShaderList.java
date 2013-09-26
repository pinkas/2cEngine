package com.example.BGL;

import android.content.Context;

public class ShaderList {

	public Shader[] effects;
	
	public ShaderList( Context context ){
        effects = new Shader[2];
		effects[0] = new BasicShader(context);
		effects[1] = new WobblyShader(context);
	}

	
}
