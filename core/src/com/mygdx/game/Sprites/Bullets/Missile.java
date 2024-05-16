package com.mygdx.game.Sprites.Bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Projectile;

public class Missile extends Projectile {

    public Missile(World world, float WorldX, float WorldY){
        damage = 20f;
        width = 20;
        height = 20;
        speed = 5;
        defineBody(world, WorldX, WorldY);
    }


}
