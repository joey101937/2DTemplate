/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework;

import static Framework.Game.visHandler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import GameObjects.*;

/**
 *
 * @author Joseph
 */
public class Input implements KeyListener{
    //FIELDS
    public Game hostGame;
    public Input(Game x){
        hostGame = x;
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 'W':
                System.out.println("pressed W");
                break;
            case 'D':
                Game.testObject.location.x += 25;
                Game.testObject.setRotation(DCoordinate.angleFrom(Game.testObject.location, new DCoordinate(200, 200)));
                visHandler.addLine(Game.testObject.getPixelLocation(), new Coordinate(200, 200));
                break;
            case 'A':
                 Game.testObject.location.x -= 25;
                Game.testObject.setRotation(DCoordinate.angleFrom(Game.testObject.location, new DCoordinate(200, 200)));
                visHandler.addLine(Game.testObject.getPixelLocation(), new Coordinate(200, 200));
                break;
            case 'U':
                Camera.yVel=Camera.camSpeed;
                break;
            case 'J':
                Camera.yVel=-Camera.camSpeed;
                break;
            case 'H':
                Camera.xVel = Camera.camSpeed;
                break;
            case 'K':
                Camera.xVel = -Camera.camSpeed;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 'U':
                Camera.yVel = 0;
                break;
            case 'J':
                Camera.yVel = 0;
                break;
            case 'H':
                Camera.xVel = 0;
                break;
            case 'K':
                Camera.xVel = 0;
                break;
        }
    }

}
