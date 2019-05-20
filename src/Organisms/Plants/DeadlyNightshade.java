package Organisms.Plants;

import Organisms.Organism;
import World.World;

public class DeadlyNightshade extends Plant{
    public DeadlyNightshade(int posX, int posY, World world) {
        super(posX, posY, world);
        this.name = "DeadlyNightshade";
        this.strength = 99;
        this.canPoison = true;
    }


    public Organism reproduce(int posX, int posY) {
        return new DeadlyNightshade(posX, posY, world);
    }
}
