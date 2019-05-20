package Organisms.Plants;

import Organisms.Organism;
import World.World;

public class Dandelion extends Plant{
    public Dandelion(int posX, int posY, World world) {
        super(posX, posY, world);
        this.name = "Dandelion";
    }

    @Override
    public boolean action() {
        for (int i = 0; i < 3; i++) super.action();
        return false;
    }

    public Organism reproduce(int posX, int posY) {
        return new Dandelion(posX, posY, world);
    }
}
