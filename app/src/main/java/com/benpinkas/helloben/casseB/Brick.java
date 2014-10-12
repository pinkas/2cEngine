package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Brick extends BglSprite {

    private Brectangle[] particles = new Brectangle[4];
    private boolean hasBonus;
    private int remainingHp;
    private int totalHp;

    public Brick(int totalHp){
        super(0,0,0.1f,0.1f, new int[] {R.drawable.brick} );
        this.totalHp = totalHp;
        remainingHp = totalHp;
    }

    public Brick(float x, float y, float w, float h){
        super(x,y,w,h, new int[] {R.drawable.brick} );
    }

    public void initParticles(){
            for( int i=0; i < particles.length; i++  ){
            particles[i] = new Brectangle();
            particles[i].setSize(0.02f, 0.02f);
//            particles[i].setColor(this);
            particles[i].setPos(this);
            particles[i].setVisible(false);
            particles[i].setCollide(false);
        }
    }

    public Brectangle getParticles(int i){
        return particles[i];
    }

    public void moveParticles(int i, float dx, float dy){
        float posX = particles[i].getPosX();
        float posY = particles[i].getPosY();

        particles[i].setPos(posX+dx*30f/1000f, posY+dy*30f/1000f);
        particles[i].setAngleZ(particles[i].getAngleZ()+50f*dx);
    }
    public void showParticles(){
        for ( Brectangle b :particles)
            b.setVisible(true);
    }

    public void setParticlesColor(float r, float g, float b, float a){
        for (Brectangle rect : particles){
            rect.setColor(r,g,b,a);
        }
    }



    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        remainingHp --;
        if (remainingHp == 0) {
            MessageManager.sendMessage("explosion");
            this.setCollide(false);
            explode();
        }
    }

    public void init (){
        setCollide(true);
        setVisible(true);
        initParticles();
    }

    public void explode(){
        setVisible(false);
//        showParticles();
/*
        new Btimer( 1,  new Callable() {
            @Override
            public Boolean call() {

                float speed = 0.15f;
                moveParticles(0, speed, -speed);
                moveParticles(1, -speed, -speed);
                moveParticles(2, -speed, speed);
                moveParticles(3, speed, speed);

                color[3] = color[3] - 0.04f;
                color[2] = color[2] - 0.04f;
                color[1] = color[1] - 0.04f;
                color[0] = color[0] - 0.04f;

                setParticlesColor(color[0], color[1], color[2], color[3]);
                if (color[3] < 0) {
                    color[3] = 0;
                    initParticles();
                    return false;
                }
                else
                    return true;
            }
        } );
*/
    }

}
