package Organisms;

import World.World;

import java.util.Random;

public abstract class Organism {

    protected int posX, posY, strength, initiative, boost, canEscape;
    protected boolean canDef, canPoison, canReproduce, isBorn;
    protected String name;
    protected World world;

    public Organism() {}

    public Organism(int posX, int posY, World world) {
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        this.initiative = 0;
        this.strength = 0;
        this.boost = 0;
        this.name = "Organism";
        this.canEscape = 0;
        this.canDef = false;
        this.canPoison = false;
        this.canReproduce = false;
        this.isBorn = true;
    }

    public Organism(int posX, int posY, World world, int strength) {
        this(posX, posY, world);
        this.strength = strength;
        this.canReproduce = true;
    }

    public abstract boolean action();
    public abstract Organism reproduce(int posX, int posY);

    //return false - attacker alive
    public boolean collision(Organism org, int changeX, int changeY) {

        //both alive nothing change
        if (this.getDefence() && (org.getStrength() < 5)) {
            world.setInformation(6, org, this);
            return false;
        }

        //defender die and give boost to attacker
	else if (this.getBoost()) {
            world.setInformation(7, org, this);
            org.setStrength(org.getStrength() + this.boost);

            world.deletePosition(org);
            org.posX += changeX;
            org.posY += changeY;
            world.setPosition(org);

            world.deleteOrganism(this);
            return false;
        }

        //both die
	else if (this.getPoison()) {
            world.setInformation(0, this, org);
            world.setInformation(0, org, this);

            world.deleteOrganism(this);
            return true;
        }

        //defender die or escape
	else if (this.getStrength() <= org.getStrength()) {
	        Random rand = new Random();
            int escape = rand.nextInt(2);
            if (escape * this.canEscape == 1) {
                //check any possible way to escape
                if (randomiseEscape(this, this.getPosX(), this.getPosY())) {
                    world.setInformation(5, org, this);

                    world.deletePosition(org);
                    org.posX += changeX;
                    org.posY += changeY;
                    world.setPosition(org);
                    return false;
                }
                //defender die
			else {
                    world.setInformation(0, org, this);

                    world.deletePosition(org);
                    org.posX += changeX;
                    org.posY += changeY;
                    world.setPosition(org);
                    world.deleteOrganism(this);
                    return false;
                }
            }
            //defender die
		else {
                world.setInformation(0, org, this);

                world.deletePosition(org);
                org.posX += changeX;
                org.posY += changeY;
                world.setPosition(org);

                world.deleteOrganism(this);
            }
            return false;
        }
        //attacker die
	else {
            world.setInformation(0, this, org);
            return true;
        }
    }
    protected boolean[] tryMove(int walkRange, int posX, int posY){
        boolean possibleMove[] = {false,false,false,false};

        if ((posX - walkRange >= 0) && (world.getOrganism(posX - walkRange, posY) == null)) possibleMove[0]  = true;
        if ((posX + walkRange < world.getSizeX()) && (world.getOrganism(posX + walkRange, posY) == null)) possibleMove[1] = true;
        if ((posY - walkRange >= 0) && (world.getOrganism(posX, posY - walkRange) == null)) possibleMove[2]  = true;
        if ((posY + walkRange < world.getSizeY()) && (world.getOrganism(posX, posY + walkRange) == null)) possibleMove[3]  = true;

        return possibleMove;
    }

    boolean randomiseEscape(Organism org, int posX, int posY) {
        boolean possibleMove[] = tryMove(1, posX, posY);
        boolean freePosition = false;
        //check all possible ways to go
        for(int i = 0; i < 4; i++) if(possibleMove[i] == true) freePosition = true;

        if (!freePosition) return false;

        //find proper way
        while (true) {
            Random rand = new Random();
            int changeX = 0, changeY = 0;
            int move = rand.nextInt(4);
            if (possibleMove[move]) {
                if (move == 0) changeX--;
                else if (move == 1) changeX++;
                else if (move == 2) changeY--;
                else if (move == 3) changeY++;

                world.deletePosition(this);
                this.posX += changeX;
                this.posY += changeY;
                world.setPosition(this);

                return true;
            }
            else continue;
        }
    }
    public void setBorn(boolean change){
        this.isBorn = change;
    }

    public void setStrength(int s) {
        this.strength = s;
    }
    public void setReproduce(boolean change) {
        this.canReproduce = change;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int getStrength() {
        return strength;
    }
    public int getInitiative() {
        return initiative;
    }
    public String getName(){
        return this.name;
    }

    public boolean getBorn(){
        return this.isBorn;
    }
    boolean getPoison() {
        return this.canPoison;
    }
    public boolean getReproduce() {
        return canReproduce;
    }
    boolean getBoost() {
        if (this.boost == 0) return false;
	    else return true;
    }

    boolean getDefence() {
        return this.canDef;
    }

    public String getToSave(){
        String flat = "";

        flat += getName();
        flat += ' ';
        flat += strength;
        flat += ' ';
        flat += posX;
        flat += ' ';
        flat += posY;

        return flat;
    }
}
