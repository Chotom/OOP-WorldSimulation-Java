package Organisms.Plants;

import Organisms.Organism;
import World.World;

public class SosnowskyHogweed extends Plant{
    public SosnowskyHogweed(int posX, int posY, World world) {
        super(posX, posY, world);
        this.name = "SosnowskyHogweed";
        this.strength = 10;
        this.canPoison = true;
    }

    @Override
    public boolean action() {
        if ((this.getPosX() - 1 >= 0) && ((world.getOrganism(this.getPosX() - 1, this.getPosY())) != null)) {
            Organism org = world.getOrganism(this.getPosX() - 1, this.getPosY());
            if (!(org instanceof Plant)) {
                world.setInformation(0, this, org);
                world.deleteOrganism(org);
            }
        }


        if ((this.getPosX() + 1 < world.getSizeX()) && ((world.getOrganism(this.getPosX() + 1, this.getPosY())) != null)) {
            Organism org = world.getOrganism(this.getPosX() + 1, this.getPosY());
            if (!(org instanceof Plant)) {
                world.setInformation(0, this, org);
                world.deleteOrganism(org);
            }
        }

        if ((this.getPosY() - 1 >= 0) && ((world.getOrganism(this.getPosX(), this.getPosY() - 1)) != null)) {
            Organism org = world.getOrganism(this.getPosX(), this.getPosY() - 1);
            if (!(org instanceof Plant)){
                world.setInformation(0, this, org);
                world.deleteOrganism(org);
            }
        }

        if ((this.getPosY() + 1 < world.getSizeY()) && ((world.getOrganism(this.getPosX(), this.getPosY() + 1)) != null)) {
            Organism org = world.getOrganism(this.getPosX(), this.getPosY() + 1);
            if (!(org instanceof Plant)) {
                world.setInformation(0, this, org);
                world.deleteOrganism(org);
            }
        }

        return super.action();
    }


    public Organism reproduce(int posX, int posY) {
        return new SosnowskyHogweed(posX, posY, world);
    }
}
