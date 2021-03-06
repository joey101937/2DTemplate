/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleGames.Galiga;

import SampleGames.Galiga.Enemies.*;
import Framework.Audio.SoundEffect;
import Framework.DCoordinate;
import Framework.Game;
import Framework.SpriteManager;
import Framework.Window;
import java.io.File;

/**
 *
 * @author Joseph
 */
public class GaligaGame {
    
    public static PlayerShip player;
    public static Game mainGame; 
    public static SoundEffect deathSound = new SoundEffect(new File("Assets/Sounds/blast1.au"));
    public static SoundEffect pewSound = new SoundEffect(new File("Assets/Sounds/pew.au"));
    public static GaligaUI UI = new GaligaUI();
    
    public static void main(String[] args) {
        mainGame = new Game(SpriteManager.spaceBG);
        Window.initialize(mainGame);
        //Main.debugMode=true;
        DCoordinate spawnPoint = new DCoordinate(mainGame.getWorldWidth()/2,mainGame.getWorldHeight()-150);
        player = new PlayerShip(spawnPoint);
        mainGame.addObject(player);
        mainGame.setInputHandler(new GaligaInput());
        mainGame.addIndependentEffect(UI);
        mainGame.addIndependentEffect(new GameDriver());
        
    }
   
}
