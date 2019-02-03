/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework;

import Framework.GraphicalAssets.Sequence;
import Framework.GraphicalAssets.Sprite;
import Framework.Stickers.Sticker;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import Framework.GraphicalAssets.Graphic;

/**
 * Parent class for all objects that appear in the gameworld
 * @author Joseph
 */
public class GameObject2 {
    protected Game hostGame;
    public String name= "Unnamed " + this.getClass().getName(); 
    public long tickNumber = 0; //used for debugging, counts number of times this has ticked
    public long renderNumber = 0; //used for debugging, counts number of times this has rendered
    public DCoordinate location = new DCoordinate(0,0); //location relative to the world
    public DCoordinate velocity = new DCoordinate(0,0); //added to location as a ratio of speed each tick
    public int innateRotation = 0; //0 = top of sprite is forwards, 90 is right of sprite is right, 180 is bottom of sprite is forwards etc
    protected double baseSpeed = 2; //total distance the object can move per tick
    private Graphic graphic; //visual representation of the object
    private double rotation = 0; //rotatoin in degrees (not radians)
    /**non-solid object will phase through other objects without triggering either object's onCollide method*/
    public boolean isSolid = false; //weather or not this object collides with other objects
    public boolean isInvisible = false; //invisible gameobjects are not rendered
    public double scale = 1; //size multiplier
    protected boolean isAlive = true; //weather or not the object has been destroyed
    public MovementType movementType = MovementType.SpeedRatio;
     /**
     * how the object behaves when traveling in two directions (up/down and
     * side/side) and it collides with something in one but not both of the
     * directions Disabled = stops moving Enabled = moves relative to portion of
     * velocity in unblocked direction
     */
    public boolean collisionSliding = true;
    public int plane = 0; //which 'layer' a unit is on. Units only collide with others in the same plane
    protected Hitbox hitbox ;
    protected ArrayList<Sticker> attachedStickers = new ArrayList<>();
    public final int ID;
    private static int IDLog = 0; //used to assign IDs
    public HashMap<PathingLayer.Type,Double> pathingModifiers = new HashMap<>(); //stores default speed modifiers for different terrain types
    public ArrayList<SubObject> subObjects = new ArrayList<>(); //stores all subobjects on this object
    /**this determines weather or not a gameobject will be able to move through other solid units, however this still triggers onCollide*/
    public boolean preventOverlap = true; 
    
    
    
    public void addSubObject(SubObject sub){
        sub.setHost(this);
    }
    
    
    public Game getHostGame(){
    return hostGame;
    }
    
    /**
     * @return speed this unit should be able to move at with the current terrain
     */
    public double getSpeed() {
        if (hostGame.pathingLayer == null) {
            return baseSpeed;
        }
        return baseSpeed * pathingModifiers.get(getCurrentTerrain());
    }

   public double getBaseSpeed(){
       return baseSpeed;
   }
   public void setBaseSpeed(double bs){
       baseSpeed = bs;
   }

   /**
    * Gets the current terrain this object is on; Terrain is determined by 
    * terrain type of pixel that this object is centered on
    * @return terrain type at the center of this object
    */
   public PathingLayer.Type getCurrentTerrain(){
       if(hostGame.pathingLayer==null){
           System.out.println("trying to get terrain type of null pathing layer -"+name);
           return null;
       }
       return hostGame.pathingLayer.getTypeAt(this.getPixelLocation());
   }
   /**
    * runs when the game containing this gameobject is issued a pause or unpause order
    * @param input true = pausing. false = unpausing
    */
    public void onGamePause(boolean input) {   
    }
    
    
    public static enum MovementType{
    RawVelocity, SpeedRatio, RotationBased;
    }

    
    /**
     * used to get integer location of object, used when rendering to screen
     * @return integer location
     */
    public Coordinate getPixelLocation() {
        return new Coordinate(location);
    }
    /**
     * @return The Rectangle object used as hitbox
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    
    public void setHitbox(Hitbox replacement){
        this.hitbox = replacement;
    }
    


    /**
     * returns the width of the visual. If no visual, return 0.
     * @return width of visual
     */
    public int getWidth() {
        try{
        return graphic.getCurrentImage().getWidth();
        }catch(NullPointerException npe){
            return 0;
        }
    }
    /**
     * returns the height of the visual gameobject. If no visual, return 0
     * @return height of visual
     */
    public int getHeight() {
        try {
            return graphic.getCurrentImage().getHeight();
        } catch (NullPointerException npe) {
            return 0;
        }
    }
    
    /**
     * gets current visual representation of this object
     * could be Sprite class if not animated or Sequence class if animated
     * @return current visual representation object
     */
    public Graphic getGraphic(){
        return graphic;
    }
    
     /**
     * Sets current visual representation of this object
     * could be Sprite class if not animated or Sequence class if animated
     * @param g new graphic object
     */
    public void setGraphic(Graphic g){
        graphic = g;
    }

    /**
     * Directly sets the rotation of this object to a given degree.
     * Does not check collision.
     * @param degree sets direction to this exact degree
     */
    public void setRotation(double degree){
        rotation = degree;
    }
    
    /**
     * @return Rotation of this object in degrees.
     */
    public double getRotation(){
        return rotation;
    }
    
    /**
     * rotates the object to a given degree, checks for collision at destination
     * @param degrees new degree of location.
     */
    public void rotateTo(double degrees) {
        double degreeToRotate = degrees-rotation;
        rotate(degreeToRotate);
    }

    /**
     * rotates the object from the current rotation to the current location +
     * given degrees. Checks collision at destination.
     * @param degrees amount to rotate 
     */
    public void rotate(double degrees) {
        if(isSolid && preventOverlap && getHitbox()!=null){
            //if solid first check collisions
            for(GameObject2 other : hostGame.getAllObjects()){
                if(plane==other.plane && other.isSolid && other.getHitbox()!=null && getHitbox().intersectsIfRotated(other.getHitbox(), degrees)){
                     return; 
                }
            }
        }
        rotation += degrees;
        for(SubObject sub : subObjects){
            sub.onHostRotate(degrees);
        }
        if(getHitbox()!=null){
            getHitbox().rotate(degrees);
        }
    }
    
    /**
     * Rotates this object so that its front (determined by innate rotation) is
     * angled towards other's location
     * @param other object whos location we will look at
     */
    public void lookAt(GameObject2 other) {
        rotateTo(DCoordinate.angleFrom(location, other.location) - innateRotation);
    }
    /**
     * Rotates this object so that its front (determined by innate rotation) is
     * angled towards given location
     * @param destination location to look at
     */
    public void lookAt(DCoordinate destination){
         rotateTo(DCoordinate.angleFrom(location, destination) - innateRotation);
    }
        /**
     * Rotates this object so that its front (determined by innate rotation) is
     * angled towards given location
     * @param destination location to look at
     */
    public void lookAt(Coordinate destination){
         rotateTo(DCoordinate.angleFrom(getPixelLocation(), destination) - innateRotation);
    }
    
    
    /**
     * Draws the object on screen in the game world
     * @param g Graphics2D object to draw with
     */
    public void render(Graphics2D g){
        renderNumber++;
        if((!isOnScreen() && !Main.overviewMode())||isInvisible)return;
        Coordinate pixelLocation = getPixelLocation();
        AffineTransform old = g.getTransform();
        if(getGraphic()!=null && getGraphic().getScale()!=scale){
            getGraphic().scaleTo(scale);
        }
        while(rotation > 360){rotation-=360;}  //constrain rotation size
        while(rotation < -360){rotation+=360;}
        g.rotate(Math.toRadians(rotation),getPixelLocation().x,getPixelLocation().y);
        if(isAnimated()){
            Sequence sequence = (Sequence)getGraphic();
            if(sequence == null){
                System.out.println("Warning trying to render null sequence object " +name);
                return;
            }
            if(sequence.getCurrentFrame()!=null){
                sequence.startAnimating();
                BufferedImage toRender = sequence.getCurrentFrame();
                g.drawImage(toRender, pixelLocation.x-toRender.getWidth()/2 , pixelLocation.y-toRender.getHeight()/2,null); //draws frmae centered on pixelLocation
                if(sequence.currentFrameIndex == sequence.frames.length-1) this.onAnimationCycle();
            }else{
                System.out.println("Warning: null frame in sequence of " + name);
            }
        }else{
            Sprite sprite = (Sprite)getGraphic();
            if(sprite!=null){                
                g.drawImage(sprite.getImage(), pixelLocation.x-sprite.getImage().getWidth()/2, pixelLocation.y-sprite.getImage().getHeight()/2, null); //draws sprite centered on pixelLocation
            }else{
                System.out.println("Warning: unanimated game object sprite is null " + name);
            }
        }
        if (Main.debugMode) {
            g.setColor(Color.red);
            g.drawRect((int) location.x - 15, (int) location.y - 15, 30, 30);
            g.drawString(name, (int) location.x - getWidth() / 2, (int) location.y - getHeight() / 2);
            g.rotate(Math.toRadians(innateRotation));
            g.drawLine((int) location.x, (int) location.y, (int) location.x, (int) location.y - 80);
            g.rotate(-Math.toRadians(innateRotation));
        }
        g.setTransform(old); //reset rotation for next item to render
        if(Main.debugMode && getHitbox()!=null)getHitbox().render(g); //render hitbox without graphics rotation
    }
    
    

    /**
     * maintains hitboxes, runs after default render.
     * Override to use custom hitboxes.
     * by default, this sets up a rectangular hitbox and maintains it based on current sprite
     * if the hitbox is set to be circular, maintains circle radius to be equal to width/2
     */
    public void updateHitbox() {
        //if no hitbox, create the default box hitbox
        if (getHitbox() == null && getWidth()>0 && renderNumber>0) {
            int width = getWidth();
            int height = getHeight();
            Coordinate[] verts = new Coordinate[4];
            verts[0] = new Coordinate(-width / 2, -height / 2);
            verts[1] = new Coordinate(width / 2, -height / 2);
            verts[2] = new Coordinate(-width / 2, height / 2);
            verts[3] = new Coordinate(width / 2, height / 2);
            setHitbox(new Hitbox(this, verts));
            return;
        }
        //maintain the default hitbox
        if (getHitbox() != null && getHitbox().type == Hitbox.Type.box) {
            int width = getWidth();
            int height = getHeight();
            Coordinate[] verts = new Coordinate[4];
            verts[0] = new Coordinate(-width / 2, -height / 2);
            verts[1] = new Coordinate(width / 2, -height / 2);
            verts[2] = new Coordinate(-width / 2, height / 2);
            verts[3] = new Coordinate(width / 2, height / 2);
            getHitbox().setVertices(verts);
        }else if(getHitbox() != null && getHitbox().type == Hitbox.Type.circle){
            //maintain default circle hitbox
            getHitbox().radius = getWidth()/2;
        }
        
    }

    /**
     * this method runs every "tick" similar to update() in unity; Reccomended you
     * start your overridden tick method with super() so that updateLocation
     * method runs and tickNumber continues counting
     */
    public void tick(){
        updateLocation();
        tickNumber++;
    }
    
    /**
     * This method runs every tick and controls object positioning regarding:
     * note: called as part of default tick; if you want to override tick then
     * you will need to call super.tick() or this once directly. (not both)
     * -Adjusts object position based on velocity
     * -Constrains object to world when necessary
     * -Detects collisions
     * -updates hitbox
     */
    public void updateLocation() {
      DCoordinate newLocation = location.copy();
        switch (movementType) {
            case SpeedRatio:
                double delta = 0.0;
                double totalVelocity = Math.abs(velocity.x) + Math.abs(velocity.y);
                if (totalVelocity != 0) {
                    delta = (getSpeed()) / totalVelocity;
                }
                newLocation.x += velocity.x * delta;
                newLocation.y += velocity.y * delta;
                break;
            case RotationBased:
                double deltaR = 0.0;
                DCoordinate vel = velocity.copy();
                vel = DCoordinate.adjustForRotation(vel, rotation);
                double totalVelocityR = Math.abs(vel.x) + Math.abs(vel.y);
                if (totalVelocityR != 0) {
                    deltaR = (getSpeed()) / totalVelocityR;
                }
                newLocation.x += vel.x * deltaR;
                newLocation.y += vel.y * deltaR;
                break;
            case RawVelocity:
                newLocation.add(velocity);
                break;
            default :
                throw new RuntimeException("Movement Type undefined for object: " + this);
        }
        //COLLISION
        if (isSolid && getHitbox()!=null) {
            DCoordinate toMove = new DCoordinate((newLocation.x-location.x),(newLocation.y-location.y));
            for (GameObject2 other : hostGame.handler.getAllObjects()) {
                if (!other.isSolid || other.getHitbox()==null || other==this || other.plane!=plane) {
                    continue;
                }
                if (getHitbox().intersects(other.getHitbox())) {
                    //if we are already on top of another unit, just keep going to not get stuck
                    onCollide(other);
                    continue;
                }
                if (preventOverlap && other.preventOverlap && getHitbox().intersectsIfMoved(other.getHitbox(), new Coordinate((int) Math.ceil(toMove.x), (int) Math.ceil(toMove.y)))) {
                    boolean xClear = !getHitbox().intersectsIfMoved(other.getHitbox(), new Coordinate((int) Math.ceil(toMove.x), 0));
                    boolean yClear = !getHitbox().intersectsIfMoved(other.getHitbox(), new Coordinate(0, (int) Math.ceil(toMove.y)));
                    boolean bothClear = !getHitbox().intersectsIfMoved(other.getHitbox(), new Coordinate((int) Math.ceil(toMove.x), (int) Math.ceil(toMove.y)));
                    if (xClear && !yClear && collisionSliding) {
                        //if only moving in x direction would result in no collision
                        newLocation.y = location.y;
                        onCollide(other);
                    } else if (yClear && !xClear && collisionSliding) {
                        //if only moving in y direction would result in no collision
                        newLocation.x = location.x;
                        onCollide(other);
                    } else {
                        //this movement would result in a collision in either and both directions
                        //if we would collide with a unit, stop moving and run onCollide
                        //prevents units from stacking on top of eachother
                        newLocation = location.copy();
                        onCollide(other);
                        continue;
                    }
                }
                for (SubObject sub : other.subObjects) {
                    if(!sub.isSolid || other.hitbox==null)continue;
                    if (getHitbox().intersects(sub.getHitbox())) {
                        //if we are already on top of another unit, just keep going to not get stuck
                        onCollide(sub);
                        continue; 
                    }
                    if (preventOverlap && other.preventOverlap && getHitbox().intersectsIfMoved(sub.getHitbox(), new Coordinate((int) Math.ceil(velocity.x), (int) Math.ceil(velocity.y)))) {
                        //if we would collide with a unit, stop moving and run onCollide
                        //prevents units from stacking on top of eachother controlled with preventOverlap condtion
                        newLocation = location.copy();
                        onCollide(sub);
                        continue;
                    }
                }
            }
        }
        
        //pathing layer now
        
        if (hostGame.pathingLayer != null) {
            if (pathingModifiers.get(hostGame.pathingLayer.getTypeAt(new Coordinate(newLocation))) < .05 && collisionSliding) {
                //pathing at new location is blocked. (speed multiplier < .05)
                //check directions to see which are blocked so we can possibly slide
                boolean xClear = pathingModifiers.get(hostGame.pathingLayer.getTypeAt(new Coordinate((int) Math.ceil(newLocation.x), (int) Math.ceil(location.y)))) > .05;
                boolean yClear = pathingModifiers.get(hostGame.pathingLayer.getTypeAt(new Coordinate((int) Math.ceil(location.x), (int) Math.ceil(newLocation.y)))) > .05;
                if (!xClear) {
                    newLocation.x = location.x;
                }
                if (!yClear) {
                    newLocation.y = location.y;
                }
            }
        }
        if(hostGame.pathingLayer==null || pathingModifiers.get(hostGame.pathingLayer.getTypeAt(new Coordinate(newLocation))) > .05){
             location = newLocation;
        }
        updateHitbox();
        constrainToWorld();
    }

    
    /**
     * runs whevenever an object would run out of bounds
     * by default, prevents the object from moving outside the world by 
     * resetting the location to a legal, in-bounds location
     */
    public void constrainToWorld(){
        if(location.x < hostGame.worldBorder) location.x=hostGame.worldBorder;
        if(location.y < hostGame.worldBorder) location.y=hostGame.worldBorder;
        if(location.x > hostGame.getWorldWidth() - hostGame.worldBorder) location.x = hostGame.getWorldWidth()- hostGame.worldBorder;
        if(location.y > hostGame.getWorldHeight() - hostGame.worldBorder) location.y = hostGame.getWorldHeight()- hostGame.worldBorder;
    }
    /**
     * Creates new GameObject2 at location
     * @param c location
     */
    public GameObject2(Coordinate c){
      init(new DCoordinate(c));
      ID = IDLog++;
    }
     /**
     * Creates new GameObject2 at exact location
     * @param dc location
     */
    public GameObject2(DCoordinate dc){
        init(dc);
        ID = IDLog++;
    }
     /**
     * Creates new GameObject2 at location
     * @param x location x-coordinate
     * @param y location y-coordinate
     */
    public GameObject2(int x, int y){
      init(new DCoordinate(x,y));
      ID = IDLog++; 
    }
    /**
     * sets initial values common for all gameObjects
     * @param dc spawn point
     */
    private final void init(DCoordinate dc){
        location = dc;
        //set up default pathing modifiers. Move normal on ground, reduced speed in water, not at all in impass
        pathingModifiers.put(PathingLayer.Type.ground, 1.0);
        pathingModifiers.put(PathingLayer.Type.hostile, 1.0);
        pathingModifiers.put(PathingLayer.Type.water, .33);
        pathingModifiers.put(PathingLayer.Type.impass, 0.0);
    }
    /**
     * removes object from game, functionally
     */
    public final void destroy() {
        isAlive = false;
        onDestroy();
        if (!(this instanceof SubObject)) {
            hostGame.removeObject(this);

        }else{
            SubObject me = (SubObject)this;
            me.setHost(null);
        }
        this.detatchAllStickers();
        if(hitbox!=null)hitbox.host=null;
        graphic.destroy();
        graphic = null;
    }

    /**
     * method that runs when this object is destroyed, to be used for gameplay
     * Note this does not handle technical engine removal of object from game
     */
    public void onDestroy(){
        
    }
    /**
     * runs whenvever the current animation sequence renders the last frame in animation
     */
    public void onAnimationCycle(){}
    
    /**
     * @return weather or not this object is considered alive 
     */
    public boolean isAlive(){
        return isAlive;
    }
    /**
     * Weather or not this object is using an animated sequence or static sprite
     * if graphic is null, return false;
     * @return Weather or not this object is using an animated sequence or static sprite
     */
    public boolean isAnimated(){
        if(graphic==null)return false;
        return graphic.isAnimated();
    }
    
    /**
     * Runs each tick this object's hitbox is touching another object's hitbox
     * @param other the object whose hitbox we are touching
     */
    public void onCollide(GameObject2 other){
    }
    
    /**
     * If this onbject's hitbox is intersected by the camera's field of view
     * @return if the object is visable by the camera
     */
    public boolean isOnScreen(){
        Rectangle sightBox = new Rectangle((int)(this.location.x-getWidth()/2),(int)this.location.y-(getHeight()/2),getWidth()+1,getHeight()+1);
        return sightBox.intersects(hostGame.camera.getFieldOfView());
    }
    
    @Override
    public String toString(){
        return this.name + " in game " + hostGame;
    }
 
    
    public ArrayList<Sticker> getAttachedStickers(){
        return attachedStickers;
    }
    
    public void detatchSticker(Sticker s){
        if(attachedStickers.contains(s)){
            attachedStickers.remove(s);
            s.attachTo(null);
        }
    }
    //detaches all stickers which have been attached to this object
    public void detatchAllStickers() {
        for (Sticker s : attachedStickers) {
            s.attachTo(null);
        }
        attachedStickers.clear();
    }


}
