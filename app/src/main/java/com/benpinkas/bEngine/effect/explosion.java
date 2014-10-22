package com.benpinkas.bEngine.effect;

import android.graphics.PointF;
import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.service.MessageManager;
import java.util.concurrent.Callable;


public class Explosion {

    private BglSprite[] particle;
    private int tesselX;
    private int tesselY;
    private float particleW;
    private float particleH;
    private Explosion explosionReference = this;

    public Explosion( BglSprite exploser, int tesselX, int tesselY){

        this.tesselX = tesselX;
        this.tesselY = tesselY;

        this.particleW = exploser.getSizeW() / tesselX;
        this.particleH = exploser.getSizeH() / tesselY;

        float exploserX = exploser.getPosX();
        float exploserY = exploser.getPosY();

        particle = new BglSprite[tesselX*tesselY];

        int index = 0;
        for (int i=0; i < tesselY; i++){
            for (int j=0; j < tesselX; j++){
                float posx = exploserX + j*particleW;
                float posy = exploserY + i*particleH;
                particle[index] = new BglSprite( posx, posy, particleW, particleH,
                        exploser.getRes());
                particle[index].glService.recalculateTextCoord(j, i, tesselX, tesselY);
                particle[index].setCollide(false);
                index++;
            }
        }
    }

    public void init(BglObject o){
        float exploserX = o.getPosX();
        float exploserY = o.getPosY();

        particleW = o.getSizeW() / tesselX;
        particleH = o.getSizeH() / tesselY;

        int index = 0;
        for (int i=0; i < tesselY; i++){
            for (int j=0; j < tesselX; j++){
                BglObject p = particle[index];
                p.setPos(exploserX + j * particleW, exploserY + i * particleH);
                p.setSize(particleW, particleH);
                p.setVel((p.getPosX(0.5f) - o.getPosX(0.5f))/20f,
                        (p.getPosY(0.5f) - o.getPosY(0.5f))/20f);
                p.setVisible(true);
                p.glService.setAlpha(1.0f);
                p.setAngleZ(0);
                index++;
            }
        }
    }

    public void boooom(){
        new Btimer(1, new Callable() {
            @Override
            public Boolean call() {
                float alpha = 0;
                for (BglSprite p :particle){
                    PointF vel = p.getVel();
                    p.setPos(p.getPosX() + vel.x, p.getPosY() + vel.y);
                    alpha = p.glService.getAlpha();
                    p.glService.setAlpha( alpha - 0.05f );
                    p.setAngleZ(p.getAngleZ() + vel.x*2000);
                }

                if ( alpha < 0.0f ){
                    for (BglSprite p :particle) {
                        p.setVel(0,0);
                        p.setVisible(false);
                    }
                    MessageManager.sendMessage("explosion_end", explosionReference);
                    return false;
                }
                return true;
            }
        } );
    }

    public void setVisible(boolean visible){
        for (BglSprite p :particle){
            p.setVisible(visible);
        }
    }

    public BglSprite[] getParticle() {
        return particle;
    }

}
