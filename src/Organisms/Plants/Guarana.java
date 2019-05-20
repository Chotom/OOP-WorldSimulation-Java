package Organisms.Plants;

import Organisms.Organism;
import World.World;

public class Guarana extends Plant{
    public Guarana(int posX, int posY, World world) {
        super(posX, posY, world);
        this.name = "Guarana";
        this.boost = 3;
    }


    public Organism reproduce(int posX, int posY) {
        return new Guarana(posX, posY, world);
    }
}
