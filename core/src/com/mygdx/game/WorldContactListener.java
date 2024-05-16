package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.Drop;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.WeaponObject;
import com.mygdx.game.Sprites.YearOneBoss;

public class WorldContactListener implements ContactListener {
    GameWorld world;
    public WorldContactListener(GameWorld world){
        this.world = world;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null) return;

        if(PlayerAndDrop(fixtureA, fixtureB)){
            Player player = (Player) fixtureA.getUserData();
            Drop drop = (Drop) fixtureB.getUserData();

            player.getDrop(drop);
        }

        if(PlayerAndDrop(fixtureB, fixtureA)){
            Player player = (Player) fixtureB.getUserData();
            Drop drop = (Drop) fixtureA.getUserData();

            player.getDrop(drop);
        }

        if(fixtureA.getUserData() instanceof WeaponObject && fixtureB.getUserData() instanceof YearOneBoss){
            YearOneBoss boss = (YearOneBoss) fixtureB.getUserData();
            boss.life -= 20;
            System.out.println("Lifee: " + boss.life);
        }

        if(fixtureB.getUserData() instanceof WeaponObject && fixtureA.getUserData() instanceof YearOneBoss){
            YearOneBoss boss = (YearOneBoss) fixtureA.getUserData();
            boss.life -= 1;
            System.out.println("Lifee: " + boss.life);
        }



    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private boolean PlayerAndDrop(Fixture a, Fixture b){
        return (a.getUserData() instanceof Player && b.getUserData()
        instanceof Drop);
    }
}
