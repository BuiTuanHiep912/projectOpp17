package seventeenth.group.gameobjects;

import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.effect.Animation;
import seventeenth.group.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

// test FinalBoss : GameWorld -> Hero x=2400 y=2218

public class FinalBoss extends Human {

    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation boulderback, boulderforward;
    private Animation shamanDead;

    private long startTimeForAttacked;
    
    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>(); 
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;
    
    public FinalBoss(float x, float y, GameWorld gameWorld) {
        super(x, y, 110, 150, 20, gameWorld);
        idleback = CacheDataLoader.getInstance().getAnimation("shamanIdle");
        idleforward = CacheDataLoader.getInstance().getAnimation("shamanIdle");
        idleback.flipAllImage();
        
        boulderback = CacheDataLoader.getInstance().getAnimation("shamanMagic_1");
        boulderforward = CacheDataLoader.getInstance().getAnimation("shamanMagic_1");
        boulderback.flipAllImage();

        shootingback = CacheDataLoader.getInstance().getAnimation("shamanMagic_2");
        shootingforward = CacheDataLoader.getInstance().getAnimation("shamanMagic_2");
        shootingback.flipAllImage();
        
        shamanDead = CacheDataLoader.getInstance().getAnimation("shamanDead");
        
        setTimeForNoBeHurt(1000000000);
        setDamage(10);
        
        attackType[0] = "NONE";
        attackType[1] = "boulderdrop";
        attackType[2] = "NONE";
        attackType[3] = "magicshot";
        
        timeAttack.put("NONE", new Long(2000));
        timeAttack.put("boulderdrop", new Long(1000));
        timeAttack.put("magicshot", new Long(850));
        
    }

    public void Update(){
        super.Update();
        if(getGameWorld().hero.getPosX() > getPosX())
            setDirection(RIGHT_DIR);
        else setDirection(LEFT_DIR);
        
        if(startTimeForAttacked == 0)
            startTimeForAttacked = System.currentTimeMillis();
        else if(System.currentTimeMillis() - startTimeForAttacked > 300){
            attack();
            startTimeForAttacked = System.currentTimeMillis();
        }
        
        if(!attackType[attackIndex].equals("NONE")){

        }
            else if(attackType[attackIndex].equals("magicshot")){
                setPosX(10);
            }
        else{
            // stop attack
            setSpeedX(0);
        }

    }
    
    @Override
    public void run() {
        
    }
/*
    @Override
    public void jump() {
        setSpeedY(-5);
    }

    @Override
    public void dick() {
    
    
    }

    @Override
    public void standUp() {
    
    
    }
*/
    @Override
    public void stopRun() {
    
    
    }

    @Override
    public void attack() {
    
        // only switch state attack
        
        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])){
            lastAttackTime = System.currentTimeMillis();
            
            attackIndex ++;
            if(attackIndex >= attackType.length) attackIndex = 0;
            if(attackType[attackIndex].equals("boulderdrop")){
                BoulderDrop boulder = new BoulderDrop(getGameWorld().hero.getPosX(), getGameWorld().hero.getPosY() - 400, getGameWorld());
                boulder.setSpeedY(6);
                boulder.setTeamType(2);
                getGameWorld().bulletManager.addObject(boulder);
                
            }
            if(attackType[attackIndex].equals("magicshot")){
                MagicShot magicshot = new MagicShot(this.getPosX(), this.getPosY(), this.getGameWorld());
            if (getDirection() == LEFT_DIR) {
                (magicshot).setSpeedX(-10.0F);
                (magicshot).setPosX((magicshot).getPosX() + 40.0F);
                if (this.getSpeedX() != 0.0F && this.getSpeedY() == 0.0F) {
                    (magicshot).setPosX((magicshot).getPosX() + 10.0F);
                    (magicshot).setPosY((magicshot).getPosY() + 5.0F);
                }
            }
            (magicshot).setTeamType(getTeamType());
            this.getGameWorld().bulletManager.addObject(magicshot);
            }
            
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;

        return rect;
        }

    @Override
    public void draw(Graphics2D g2) {
        if (getState()==ParticularObject.DEATH) {
            shamanDead.Update(System.nanoTime());
            shamanDead.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            return;
        }
        
        if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
        {
            System.out.println("Plash...");
        }
        else{
            
            if(attackType[attackIndex].equals("NONE")){
                if(getDirection() == RIGHT_DIR){
                    idleforward.Update(System.nanoTime());
                    idleforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                else{
                    idleback.Update(System.nanoTime());
                    idleback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
            }
            else if(attackType[attackIndex].equals("boulderdrop")){
                
                if(getDirection() == RIGHT_DIR){
                    boulderforward.Update(System.nanoTime());
                    boulderforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                else{
                    boulderback.Update(System.nanoTime());
                    boulderback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
            }
            else if(attackType[attackIndex].equals("magicshot")){
                if(getSpeedX() > 0){
                    shootingforward.Update(System.nanoTime());
                    shootingforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                }
                else{
                    shootingback.Update(System.nanoTime());
                    shootingback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                }
            }
        }
    //    drawBoundForCollisionWithEnemy(g2);
    }
    
}