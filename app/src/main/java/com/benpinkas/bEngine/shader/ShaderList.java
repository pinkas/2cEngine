package com.benpinkas.bEngine.shader;

import android.content.Context;

public class ShaderList {

	public static Shader basicShader;
    public static Shader wobblyShader;
	public static Shader rectangleShader;

	public ShaderList( Context context ){

        basicShader = new BasicShader(context);
		wobblyShader = new WobblyShader(context);
        rectangleShader = new RectangleShader(context);
	}

    public static Shader getProg( String sName ){
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
