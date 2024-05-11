package com.mygdx.game.Items;

public abstract class Weapon extends Item{
    private float damage;
    private float hp;

    public Weapon(String name, String description, float damage, float hp){
        super(name, description);
        this.damage = damage;
        this.hp = hp;
    }

    public abstract void useItem();
}