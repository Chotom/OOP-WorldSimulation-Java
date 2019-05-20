package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.util.Random;

public class Turtle extends Animal{
    public Turtle(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 1;
        this.strength = 2;
        this.name = "Turtle";
        this.canDef = true;
    }
    /*Wolf(int posX, int posY, World& world, int strength) : Animal(posX, posY, world, strength) {
        this.initiative = 5;
        this.symbol = 'W';
    }*/
    @Override
    public boolean action() {
        Random rand = new Random();
        int canMove = rand.nextInt(4);
        if (canMove == 0) return super.action();
	else return false;
    }

    public Organism reproduce(int posX, int posY) {
        return new Turtle(posX, posY, world);
    }
}
