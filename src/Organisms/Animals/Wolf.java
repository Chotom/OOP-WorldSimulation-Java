package Organisms.Animals;

import Organisms.Organism;
import World.World;

public class Wolf extends Animal{
    public Wolf(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 5;
        this.strength = 8;
        this.name = "Wolf";
    }

    public Organism reproduce(int posX, int posY) {
        return new Wolf(posX, posY, world);
    }
}
