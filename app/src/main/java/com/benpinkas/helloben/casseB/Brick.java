package com.benpinkas.helloben.casseB;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Brick extends Brectangle {



    private Brectangle[] particles = new Brectangle[4];

    public Brick(){
        super();
    }



    public Brick(float x, float y, float w, float h){
        super(x,y,w,h,0.1f,0.1f,0.1f);
    }

    public void initParticles(){
            for( int i=0; i < particles.length; i++  ){
            particles[i] = new Brectangle();
            particles[i].setSize(0.02f, 0.02f);
            particles[i].setColor(this);
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
        this.setCollide(false);
        MessageManager.sendMessage("explosion");
        //this.setVisible(false);

        explode();
    }

    public void init (){
        setCollide(true);
        setVisible(true);
        initParticles();
    }

    public void explode(){

        showParticles();

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

    }

}
