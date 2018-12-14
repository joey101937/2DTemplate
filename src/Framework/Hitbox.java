/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework;

import static Framework.Hitbox.Type.box;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author joey
 */
public class Hitbox {

    public static enum Type {
        box, circle
    };
    public GameObject2 host;
    public Game hostGame;
    public Type type = box;
    private  DCoordinate staticCenter = new DCoordinate(0, 0); //center point for if there is no host
    public double radius = 0.0; //radius if circle hitbox
    
    /**
     * topLeft, topRight, botLeft, botRight
     */
    public Coordinate[] vertices = null;            //topLeft, topRight, botLeft, botRight

    
    
    
    public DCoordinate getCenter(){
        if(host!=null)return staticCenter;
        else return host.location;
    }
    

    /**
     * Creates a circular hitbox at location given with radius given
     *
     * @param c location hitbox is centered on
     * @param radius radius of circle
     */
    public Hitbox(DCoordinate c, double radius) {
        staticCenter = c;
        this.radius = radius;
        this.type = Type.circle;
    }

    /**
     * creates a rectangle hitbox with given corner points topLeft, topRight,
     * botLeft, botRight
     */
    public Hitbox(Coordinate[] c) {
        if (c.length != 4) {
            System.out.println("ERROR: Bad argument for hitbox vertices- length should be 4");
        }
        for (Coordinate cor : c) {
            if (c == null) {
                System.out.println("WARNING CREATING HITBOX WITH NULL VERTEX");
            }
        }
       /*
        todo 
        set center to avg of point locations
        */
        vertices = c;
        type = Type.box;
    }

    /**
     * creates circular hitbox attached to gameObject
     *
     * @param go host unit
     * @param radius radius of circle
     */
    public Hitbox(GameObject2 go, double radius) {
        host = go;
        staticCenter = null;
        type = Type.circle;
        this.radius = radius;
    }

    /**
     * Creates a rectangle hitbox attached to game object NOTE VERTICES ARE NOW
     * OFFSETS NOT POINTS
     *
     * @param go host unit
     * @param verts offset locations of corners relative to go location. Ie:
     * (1,1) is location of go + 1 to the right and 1 up
     */
    public Hitbox(GameObject2 go, Coordinate[] verts) {
        this.vertices = verts;
        host = go;
        type = Type.box;
    }

    /**
     * coordinates for leftside line, used with box type
     */
    private double[] leftSide() {
        double[] output = {vertices[0].x, vertices[0].y, vertices[2].x, vertices[2].y};
        return output;
    }

    /**
     * coordinates for topside line used with box type
     */
    private double[] topSide() {
        double[] output = {vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y};
        return output;
    }

    /**
     * coordinates for right side line used with box type
     */
    private double[] rightSide() {
        double[] output = {vertices[1].x, vertices[1].y, vertices[3].x, vertices[3].y};
        return output;
    }

    /**
     * coordinates for bottom side line used with box type
     */
    private double[] botSide() {
        double[] output = {vertices[2].x, vertices[2].y, vertices[1].x, vertices[1].y};
        return output;
    }

    private boolean linesIntersect(double[] line1, double[] line2) {
        return Line2D.linesIntersect(line1[0], line1[1], line1[2], line1[3], line2[0], line2[1], line2[2], line2[3]);
    }

    /**
     * returns true if this hitbox touches the given hitbox
     * @param other hitbox to compare to
     * @return weather or not they overlap
     */
    public boolean intersects(Hitbox other) {
        if(this.type==Type.box && other.type==Type.box){
        if (linesIntersect(rightSide(), other.leftSide()) || linesIntersect(rightSide(), other.rightSide())
                || linesIntersect(rightSide(), other.topSide()) || linesIntersect(rightSide(), other.botSide())) {
            //our right side intersects one of their lines
            return true;
        }
        if (linesIntersect(leftSide(), other.leftSide()) || linesIntersect(leftSide(), other.rightSide())
                || linesIntersect(leftSide(), other.topSide()) || linesIntersect(leftSide(), other.botSide())) {
            //our left side intersects one of their lines
            return true;
        }
        if (linesIntersect(topSide(), other.leftSide()) || linesIntersect(topSide(), other.rightSide())
                || linesIntersect(topSide(), other.topSide()) || linesIntersect(topSide(), other.botSide())) {
            //our top side intersects one of their lines
            return true;
        }
        if (linesIntersect(botSide(), other.leftSide()) || linesIntersect(botSide(), other.rightSide())
                || linesIntersect(botSide(), other.topSide()) || linesIntersect(botSide(), other.botSide())) {
            //our bot side intersects one of their lines
            return true;
        }
        return false;
        }
        if (this.type == Type.box && other.type == Type.circle) {
            if (getCenter().distanceFrom(other.getCenter()) <= other.radius) {
                return true;
            }
            for (Coordinate c : vertices) {
                if (c.distanceFrom(other.getCenter()) <= other.radius) {
                    return true;
                }
            }
            return false;
        }
         if (this.type == Type.circle && other.type == Type.box) {
            if (getCenter().distanceFrom(other.getCenter()) <= radius) {
                return true;
            }
            for (Coordinate c : other.vertices) {
                if (c.distanceFrom(getCenter()) <= radius) {
                    return true;
                }
            }
            return false;
        }
        if(this.type==Type.circle && other.type == Type.circle){
            double distance = getCenter().distanceFrom(other.getCenter());
            if(distance < radius || distance < other.radius){
                return true;
            }
            return false;
        }
        System.out.println("error with intersecting, types are " + type + " " + other.type);
        return false;
    }
    
    public void render(Graphics2D g){
        if(this.type == Type.box){
            //render all sides
            g.drawLine((int)leftSide()[0], (int)leftSide()[1], (int)leftSide()[2], (int)leftSide()[3]);
            g.drawLine((int)rightSide()[0], (int)rightSide()[1], (int)rightSide()[2], (int)rightSide()[3]);
            g.drawLine((int)topSide()[0], (int)topSide()[1], (int)topSide()[2], (int)topSide()[3]);
            g.drawLine((int)botSide()[0], (int)botSide()[1], (int)botSide()[2], (int)botSide()[3]);
        }else if(type==Type.circle){
            g.drawOval((int)getCenter().x, (int)getCenter().y, (int)radius, (int)radius);
        }
    }

}