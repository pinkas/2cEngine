package com.example.bEngine.shader;

import android.content.Context;

public class ShaderList {

	public Shader basicShader;
    public Shader wobblyShader;
	public Shader rectangleShader;

	public ShaderList( Context context ){

        this.basicShader = new BasicShader(context);
		this.wobblyShader = new WobblyShader(context);
        this.rectangleShader = new RectangleShader(context);
	}

    public Shader getProg( String sName ){
         if (sName ==  "basic" ){
            return basicShader;
         }
         else if ( sName == "rect" ){
            return rectangleShader;
         }
        else
            return basicShader;
    }

}
