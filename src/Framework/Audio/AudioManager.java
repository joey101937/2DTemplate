/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.Audio;

import Framework.Game;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Stores all sounds linked to host game
 * @author Joseph
 */
public class AudioManager {

    public final Game hostGame;
    private final CopyOnWriteArrayList<SoundEffect> storage = new CopyOnWriteArrayList<>();

    public AudioManager(Game g) {
        hostGame = g;
    }

    /**
     * all sound effects associated with this manager
     * @return all sound effects associated with this manager
     */
    public ArrayList<SoundEffect> getAllSounds() {
        ArrayList<SoundEffect> output = new ArrayList<>();
        for (SoundEffect se : storage) {
            output.add(se);
        }
        return output;
    }

    /**
     * adds sound to this manager
     * @param se sound to add
     */
    protected void addSound(SoundEffect se) {
        storage.add(se);
    }
    /**
     * removes sound from this manager
     * @param se sound to remove
     */
    protected void removeSound(SoundEffect se) {
        storage.remove(se);
    }

    /**
     * updates sounds, checking if their linked game is unpaused or not
     */
    public void updateGamePause() {
        for (SoundEffect se : storage) {
            se.onGamePaused(hostGame.isPaused());
        }
    }

}
