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

    private volatile static BtextureManager instance;

    private Map<Integer, int[]> textureHashMap;

    private BtextureManager(){
        this.textureHashMap = new HashMap<Integer, int[]>();
    }

    /* Singleton magic */
    /* TODO study the following lines more seriously */
    public static BtextureManager getInstance(){
        if (instance == null){
            synchronized (BtextureManager.class){
                if (instance == null){
                    instance = new BtextureManager();
                }
            }
        }
        return instance;
    }

    public void fillTextureHashTable( Integer textureId ){
        int[] handle = new int[1];
        textureHashMap.put( textureId, handle );
    }

    public int findHandle( int textureId ){
        return textureHashMap.get(textureId)[0];
    }

    public void addToFactory( int textureId ){
        //TODO remove from the table when it's loaded
    }

    public void loadAll( Context context ){

        // iterate through the whole hashMap
        for ( int textureId : textureHashMap.keySet() ){
            System.out.println( "======================> BtextureManager" + textureId );
            load( textureId, context );
        }
/*
        Enumeration<int> t = Collections.enumeration( textureHashMap );
        while( t.hasMoreElements() ){
            int textureId = t.nextElement();
            load( textureId );
        }
*/
    }

    public void load( int textureId, Context context ){
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
        System.out.println( "loadALL!!!!!" + textureHashMap.get(textureId)[0] );
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    //    bitmap.recycle();

    }

//    public static void flush( int textureHandle ?? ){
//    }

}
