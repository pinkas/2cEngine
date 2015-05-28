package com.benpinkas.bEngine.shader;

import android.content.Context;

import com.benpinkas.R;

public class ShaderList {

	public static Shader textureShader;
    public static Shader wobblyShader;
	public static Shader rectangleShader;
    public static Shader explosionShader;

	public ShaderList( Context context ){

        textureShader = new TextureShader(context, R.raw.basic_vertex, R.raw.basic_fragment);
        explosionShader = new ExplosionShader(context, R.raw.explosion_vertex, R.raw.basic_fragment);
		wobblyShader = new WobblyShader(context);
        rectangleShader = new RectangleShader(context);
	}

    public static Shader getProg( String sName ){
         if (sName ==  "textureShader" ){
            return textureShader;
         }
         else if ( sName == "rect" ){
            return rectangleShader;
         } else if (sName == "explosion"){
             return explosionShader;
         }
        else
            return textureShader;
    }

}
