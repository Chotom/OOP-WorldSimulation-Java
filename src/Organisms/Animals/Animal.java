package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.util.Random;

public abstract class Animal extends Organism {
    protected boolean walkBoost;

    Animal(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 1;
        this.strength = 1;
        this.walkBoost = false;
    }

    /*Animal(int posX, int posY, World world, int strength) : Organism(posX, posY, world, strength) {
        this.initiative = 1;
        this.walkBoost = false;
    }*/

    @Override
    public boolean action() {
        //randomise walking
        int walkRange = 1;
        if (walkBoost) walkRange = 2;
        int changeX = 0;
        int changeY = 0;
        Random rand = new Random();
        int motion = rand.nextInt(4);

        switch (motion) {
            case 0:
                changeX-=walkRange;
                if ((changeX + this.getPosX()) < 0) changeX *= -1;
                break;
            case 1:
                changeX+=walkRange;
                if ((changeX + this.getPosX()) >= world.getSizeX()) changeX *= -1;
                break;
            case 2:
                changeY-=walkRange;
                if ((changeY + this.getPosY()) < 0) changeY *= -1;
                break;
            case 3:
                changeY+=walkRange;
                if ((changeY + this.getPosY()) >= world.getSizeY()) changeY *= -1;
                break;
        }

        Organism enemy = world.getOrganism(this.getPosX() + changeX, this.getPosY() + changeY);
        if (enemy != null) return enemy.collision(this, changeX, changeY);

        world.deletePosition(this);
        this.posX += changeX;
        this.posY += changeY;
        world.setPosition(this);

        return false;
    }

    @Override
    public boolean collision(Organism org, int changeX, int changeY) {
        //Animal copulate when find type
        if (org instanceof Animal) {
            if (this.getName() == org.getName() && (this.getReproduce()) && (org.getReproduce())) {
                boolean possibleBorn[] = tryMove(1, org.getPosX(), org.getPosY());
                boolean freePosition = false;

                //finding place to born
                for(int i = 0; i < 4; i++) if(possibleBorn[i] == true) freePosition = true;

                if (!freePosition) return false;

                int bornX = 0, bornY = 0;
                while (true) {
                    Random rand = new Random();
                    int move = rand.nextInt(4);
                    if (possibleBorn[move]) {
                        if (move == 0) bornX--;
                        else if (move == 1) bornX++;
                        else if (move == 2) bornY--;
                        else if (move == 3) bornY++;
                        break;
                    }
                    else continue;
                }
                //add baby to world and end adults turns
                world.addOrganism(org.reproduce((org.getPosX() + bornX), (org.getPosY() + bornY)));
                world.setInformation(1, world.getOrganism(org.getPosX() + bornX, org.getPosY() + bornY), null);
                this.setReproduce(false);
                org.setReproduce(false);
                return false;
            }
            //Adult cant copulate with child
		    else if (this.getName() == org.getName() && !org.getBorn() && !this.getBorn()) return false;
            //Adult cant copulate twice in one turn
		    else if (this.getName() == org.getName()) return false;
        }
        return super.collision(org, changeX, changeY);
    }



    void setWalkBoost(boolean change) {
        this.walkBoost = change;
    }

    boolean getWalkBoost() {
        return walkBoost;
    }
}
