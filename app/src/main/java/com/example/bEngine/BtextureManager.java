package com.example.bEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by Ben on 1/16/14.
 */
public class BtextureManager {

    private static Map<Integer, int[]> textureHashMap = new HashMap<Integer, int[]>();

    public static void fillTextureHashTable( Integer textureId ){
        int[] handle = new int[1];
        textureHashMap.put( textureId, handle );
    }

    public static int findHandle( int textureId ){
        // TODO findHandle seems to get called even for rectangle
        // So we avoid a crash by returning zero when the textureId is not valid
        if (textureHashMap.get(textureId) == null ){
            return 0;
        }
        return textureHashMap.get(textureId)[0];
    }

    public static void loadAll( Context context ){

        // iterate through the whole hashMap
        for ( int textureId : textureHashMap.keySet() ){
            load( textureId, context );
        }
    }

    public static void load( int textureId, Context context ){
        //create a bitmap, from image to pixel data, has to be done whenever we reload the
        //texture since we "recycle" the bitmap at the end
/*
        Enumeration<Btexture> t = Collections.enumeration( loaded );
        while( t.hasMoreElements() ){
            Btexture text = t.nextElement();
            if ( textureId == text.getTextureId() ){
                return;
            }
        }
*/

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), textureId, options );

        glGenTextures( 1, textureHashMap.get(textureId), 0 );

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        glBindTexture(GL_TEXTURE_2D, textureHashMap.get(textureId)[0] );
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        bitmap.recycle();

    }

}
