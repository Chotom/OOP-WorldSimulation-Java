package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.util.Random;

public class Fox extends Animal{
    public Fox(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 7;
        this.strength = 3;
        this.name = "Fox";
    }

    @Override
    public boolean action() {
        boolean possibleMove[] = { false,false,false,false };
        boolean freePosition = false;
        int changeX = 0, changeY = 0;

        if ((posX - 1 >= 0) && ((world.getOrganism(posX - 1, posY) == null) || (world.getOrganism(posX - 1, posY).getStrength() < this.getStrength())))
        possibleMove[0] = freePosition = true;
        if ((posX + 1 < world.getSizeX()) && ((world.getOrganism(posX + 1, posY) == null) || (world.getOrganism(posX + 1, posY).getStrength() < this.getStrength())))
        possibleMove[1] = freePosition = true;
        if ((posY - 1 >= 0) && ((world.getOrganism(posX, posY - 1) == null) || (world.getOrganism(posX, posY - 1).getStrength() < this.getStrength())))
        possibleMove[2] = freePosition = true;
        if ((posY + 1 < world.getSizeY()) && ((world.getOrganism(posX, posY + 1) == null) || (world.getOrganism(posX, posY + 1).getStrength() < this.getStrength())))
        possibleMove[3] = freePosition = true;

        if (!freePosition) return false;

        while (true) {
            Random rand = new Random();
            int move = rand.nextInt(4);
            if (possibleMove[move]) {
                if (move == 0) changeX--;
                else if (move == 1) changeX++;
                else if (move == 2) changeY--;
                else if (move == 3) changeY++;
                break;
            }
            else continue;
        }

        Organism enemy = world.getOrganism(getPosX() + changeX, getPosY() + changeY);
        if (enemy != null) return enemy.collision(this, changeX, changeY);

        world.deletePosition(this);
        posX += changeX;
        posY += changeY;
        world.setPosition(this);

        return false;
    }

    public Organism reproduce(int posX, int posY) {
        return new Fox(posX, posY, world);
    }
}
