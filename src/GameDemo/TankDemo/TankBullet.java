/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameDemo.TankDemo;

import Framework.Coordinate;
import Framework.DCoordinate;
import Framework.GameObject2;
import Framework.Hitbox;
import Framework.UtilityObjects.Projectile;
import Framework.GraphicalAssets.Sequence;
import Framework.SpriteManager;
import Framework.Stickers.OnceThroughSticker;
import GameDemo.SandboxDemo.Creature;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joseph
 */
public class TankBullet extends Projectile {
        public GameObject2 shooter; //the object that launched this projectile
    
    
    public TankBullet(DCoordinate start, DCoordinate end) {
        super(start, end);
        this.setGraphic(new Sequence(new BufferedImage[]{SpriteManager.bullet}));
        baseSpeed = 30;
        setScale(.25);
        this.setHitbox(new Hitbox(this, 0)); //sets this to se a circular hitbox. updateHitbox() method manages radius for us so we set it to 0 by default
        maxRange = 750;
    }


    //when this runs into a creature, deal damage to it then destroy this projectile
    @Override
    public void onCollide(GameObject2 other){
        if(other==shooter)return; //dont collde with the gameobject that launched this projectile
        if(other instanceof Creature) {
            Creature c = (Creature) other;
            c.takeDamage(50);
            OnceThroughSticker impactExplosion = new OnceThroughSticker(getHostGame(), SpriteManager.explosionSequence, getPixelLocation());
            impactExplosion.scaleTo(2);
        }
        destroy();
    }

    @Override
    public void onTimeOut() {
        OnceThroughSticker s = new OnceThroughSticker(getHostGame(), SpriteManager.explosionSequence, this.getPixelLocation());
        s.scaleTo(2);
    }

    /**
     * bullets just destroy when they go out of bounds
     */
    @Override
    public void constrainToWorld() {
        if (location.x < getHostGame().worldBorder) {
            onTimeOut();
            destroy();
        }
        if (location.y < getHostGame().worldBorder) {
            onTimeOut();
            destroy();
        }
        if (location.x > getHostGame().getWorldWidth() - getHostGame().worldBorder) {
            onTimeOut();
            destroy();
        }
        if (location.y > getHostGame().getWorldHeight() - getHostGame().worldBorder) {
            onTimeOut();
            destroy();
        }
    }
}
