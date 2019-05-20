package Organisms.Animals;

import Organisms.Organism;
import World.World;

public class Sheep extends Animal{
    public Sheep(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 4;
        this.strength = 4;
        this.name = "Sheep";
    }
    /*Wolf(int posX, int posY, World& world, int strength) : Animal(posX, posY, world, strength) {
        this.initiative = 5;
        this.symbol = 'W';
    }*/

    public Organism reproduce(int posX, int posY) {
        return new Sheep(posX, posY, world);
    }
}
