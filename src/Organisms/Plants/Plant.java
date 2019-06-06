package Organisms.Plants;

import Organisms.Organism;
import World.World;

import java.util.Random;

public abstract class Plant extends Organism {
    Plant(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 1;
        this.name = "Plant";
        this.isBorn = false;
    }


    @Override
    public boolean action() {
        Random rand = new Random();
        int canStart = rand.nextInt(10);
        if (canStart == 0) {

            boolean possiblePlant[] = tryMove(1, this.getPosX(), this.getPosY());
            boolean freePosition = false;

            //finding place to reproduce
            for(int i = 0; i < 4; i++) if(possiblePlant[i] == true) freePosition = true;

            if (!freePosition) return false;

            int plantX = 0, plantY = 0;
            while (true) {
                Random seed = new Random();
                int move = seed.nextInt(4);
                if (possiblePlant[move]) {
                    if (move == 0) plantX--;
                    else if (move == 1) plantX++;
                    else if (move == 2) plantY--;
                    else if (move == 3) plantY++;
                    break;
                }
                else continue;
            }

            //add plant to world

            world.addOrganism(this.reproduce((this.getPosX() + plantX), (this.getPosY() + plantY)));
            world.setInformation(2, world.getOrganism((this.getPosX() + plantX), (this.getPosY() + plantY)), null);
        }


        return false;
    }

}
