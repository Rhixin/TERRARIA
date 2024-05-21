package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Sprites.Bullets.Missile;

public class YearOneBoss extends Sprite {
    private World world;
    private Body b2body;

    private GameMode mode = GameMode.COMBAT_MODE;

    private final float cooldown = 10f;
    private float current_cooldown = 0;
    private float timeSinceLastAttack = 0f;
    private final float attackInterval = 0.2f;


    private static final Texture t = new Texture("RAW/item_box_hold.png");
    private static final float width = 100, height = 100;

    public float life;

    public YearOneBoss(World world, float WorldX, float WorldY){
        super(t);
        this.world = world;
        life = 200;
        defineBody(WorldX, WorldY);
    }

    private void defineBody(float WorldX, float WorldY) {
        setPosition(WorldX, WorldY);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(WorldX, WorldY);

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width, height);
        fdef.shape = shape;
        fdef.friction = 30f;

        b2body.setGravityScale(0f);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta) {

        if(mode == GameMode.DEAD_MODE) return;

        if(current_cooldown >= cooldown){
            if(mode == GameMode.COMBAT_MODE){
                mode = GameMode.VULNERABLE_MODE;
                b2body.setGravityScale(1f);
            } else {
                mode = GameMode.COMBAT_MODE;
                b2body.setGravityScale(0f);
                b2body.setTransform(new Vector2(520,520), 0);
            }


            current_cooldown = 0;
            return;
        }
        current_cooldown += delta;

        if(life == 0){
            world.destroyBody(b2body);
            mode = GameMode.DEAD_MODE;
        }
    }

    public Body getBody(){
        return b2body;
    }


    public void render(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public Missile attack(float delta){

        if(mode == GameMode.VULNERABLE_MODE){
            return null;
        }

        if(timeSinceLastAttack >= attackInterval){
            Missile m = new Missile(world, b2body.getPosition().x - width / 2, b2body.getPosition().y - height - 20);
            //new Missile(world, b2body.getPosition().x - width / 2, b2body.getPosition().y - height - 20);
            timeSinceLastAttack = 0f;
            return m;
        }

        timeSinceLastAttack += delta;
        return null;
    }

}
