package Organisms.Plants;

import Organisms.Organism;
import World.World;

public class Grass extends Plant {
    public Grass(int posX, int posY, World world) {
        super(posX, posY, world);
        this.name = "Grass";
    }


    public Organism reproduce(int posX, int posY) {
        return new Grass(posX, posY, world);
    }
}
