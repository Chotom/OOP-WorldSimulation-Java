package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.util.Random;

public class Human extends Animal{
    int cooldown;

    public Human(int posX, int posY, World world) {
        super(posX, posY, world);
        this.initiative = 4;
        this.strength = 5;
        this.name = "Steve";
        this.canReproduce = true;
        this.cooldown = 0;
        world.setHuman(true);
    }

    /*Human::Human(int posX, int posY, World& world, int strength, int cooldown) : Animal(posX, posY, world, strength) {
        this.initiative = 4;
        this.symbol = 'H';
        this.cooldown = cooldown;
    }*/


    @Override
    public boolean action() {
        if (cooldown > 7) {
            this.setWalkBoost(true);
        }
        else if (cooldown > 5) {
            Random rand = new Random();
            int walkBoostChange = rand.nextInt(2);
            if (walkBoostChange ==  1) setWalkBoost(true);
        }

        int walkRange = 1;
        if (walkBoost) walkRange = 2;
        setWalkBoost(false);
        int changeX = 0;
        int changeY = 0;
        char zn = world.getZn();

        if(this.cooldown > 0) this.cooldown--;

        if(zn == 'r' && cooldown == 0){
            setSuperAbility();
            world.setInformation(9, this, null);
        }



        else {
            switch (zn) {
                case 'w':
                    changeX -= walkRange;
                    if ((changeX + this.getPosX()) < 0) changeX = 0;
                    break;
                case 's':
                    changeX += walkRange;
                    if ((changeX + this.getPosX()) >= world.getSizeX()) changeX = 0;
                    break;
                case 'a':
                    changeY -= walkRange;
                    if ((changeY + this.getPosY()) < 0) changeY = 0;
                    break;
                case 'd':
                    changeY += walkRange;
                    if ((changeY + this.getPosY()) >= world.getSizeY()) changeY = 0;
                    break;
            }
            if (changeX != 0 || changeY != 0) {
                Organism enemy = world.getOrganism(this.getPosX() + changeX, this.getPosY() + changeY);

                if (enemy != null)
                    return enemy.collision(this, changeX, changeY);

                world.deletePosition(this);
                posX += changeX;
                posY += changeY;
                world.setPosition(this);
            }
        }



        return false;
    }

    void setSuperAbility() {
        this.cooldown = 12;
    }

    public void setCooldown(int c) {
        this.cooldown = c;
    }

    @Override
    public String getToSave(){
        String flat = "";

        flat += getName();
        flat += ' ';
        flat += strength;
        flat += ' ';
        flat += posX;
        flat += ' ';
        flat += posY;
        flat += ' ';
        flat += cooldown;

        return flat;
    }

    public Organism reproduce(int posX, int posY) {
        return null;
    }

}
