package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.util.Random;

public class Antelope  extends Animal{
    public Antelope(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 4;
        this.strength = 4;
        this.canEscape = 1;
        this.name = "Antelope";
    }
    /*Wolf(int posX, int posY, World& world, int strength) : Animal(posX, posY, world, strength) {
        this.initiative = 5;
        this.symbol = 'W';
    }*/
    @Override
    public boolean action() {
        Random rand = new Random();
        int walkBoostChange = rand.nextInt(2);
        if (walkBoostChange == 1) setWalkBoost(true);
        else setWalkBoost(false);

        return super.action();
    }

    public Organism reproduce(int posX, int posY) {
        return new Antelope(posX, posY, world);
    }
}
