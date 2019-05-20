package World;
import GUI.BoardGame;
import Organisms.Animals.*;
import Organisms.Organism;
import Organisms.Plants.*;


import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class World {
    private int sizeX, sizeY, turn;
    boolean humanAlive = false;
    char zn = ' ';
    private Organism [][] map;
    private LinkedList<Organism> initiativeList = new LinkedList<>();
    private String information;


    public World(int sizeY, int sizeX) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.turn = 0;
        this.information = "";
        map = new Organism[sizeX][sizeY];

        //adding organisms to world
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                map[i][j] = null;
            }
        }

        for (int i = 0; i < sizeY; i++) {

            for (int j = 0; j < sizeX; j++) {
                Random rand = new Random();
                int character = rand.nextInt(40);

                if (character == 0) addOrganism(new Sheep(j, i, this));

			else if (character == 1) addOrganism(new Wolf(j, i, this));

			else if (character == 2) addOrganism(new Grass(j, i, this));

                if (character == 3 && humanAlive == false) {

                    addOrganism(new Human(j, i, this));
                    humanAlive = true;
                }

                else if (character == 4) addOrganism(new Dandelion(j, i, this));

			else if (character == 5) addOrganism(new Fox(j, i, this));

			else if (character == 6) addOrganism(new Turtle(j, i, this));

			else if (character == 7) addOrganism(new Guarana(j, i, this));

			else if (character == 8) addOrganism(new DeadlyNightshade(j, i, this));

			else if (character == 9) addOrganism(new SosnowskyHogweed(j, i, this));


			else if (character == 10) addOrganism(new Antelope(j, i, this));
            }
        }

    }


        /*
        addOrganism(new Wolf(0, 1, this));
        addOrganism(new Turtle(1, 0, this));
        addOrganism(new Guarana(9, 9, this));
        addOrganism(new DeadlyNightshade(8, 8, this));
        addOrganism(new Fox(8, 9, this));
        addOrganism(new Human(0, 0, this));
        addOrganism(new Antelope(1, 5,this));
        addOrganism(new Grass(5, 5,this));
        addOrganism(new Dandelion(7, 7,this));

        addOrganism(new Antelope(1, 5, *this));
        addOrganism(new Guarana(2, 2, *this));
        addOrganism(new Dandelion(4, 4, *this));
        addOrganism(new Human(1, 1, *this));
        addOrganism(new SosnowskyHogweed(4, 5, *this));
        addOrganism(new Wolf(3, 3, *this));
        addOrganism(new Fox(2, 3, *this));
        */


     public void setNextTurn(BoardGame boardPanel, char zn) {
        //do action of every organism on the list
        //ListIterator<Organism> it = initiativeList.listIterator();



        setInformation(-1, null, null);

        this.turn++;

        for (int i = 0; i < initiativeList.size(); i++) {
            //when organism is new born now he become adult
            if (!initiativeList.get(i).getReproduce()) {
                //System.out.println(initiativeList.get(i).getName());

                if (initiativeList.get(i).getBorn()) {
                    initiativeList.get(i).setBorn(false);
                    setInformation(2, initiativeList.get(i), null);
                } else if (!(initiativeList.get(i) instanceof Plant)) setInformation(4, initiativeList.get(i), null);
            }

            //get zn to walk as human
            else if (initiativeList.get(i) instanceof Human) {
                this.zn = zn;
                //setInformations(8, (it), nullptr);

                //cout << information;

                /*public void keyTyped(KeyEvent event) {
                    if(event.getKeyCode() == KeyEvent.VK_UP){

                    }

                }

                while ((zn != 'w') && (zn != 's') && (zn != 'a') && (zn != 'd') && (zn != 'r')) {
                    zn = _getch();
                }*/

                if (initiativeList.get(i).action()) {
                    deletePosition(initiativeList.get(i));
                    initiativeList.remove(initiativeList.get(i));
                    i--;
                }
            }

            //when its true organism die
            else if (initiativeList.get(i).action()) {
                deletePosition(initiativeList.get(i));
                initiativeList.remove(initiativeList.get(i));
                i--;
            }
            boardPanel.repaint();
        }

        //all organism get ready to copulate in next turn
        for (int i = 0; i < initiativeList.size(); i++) {
            if (!initiativeList.get(i).getReproduce()) {
                initiativeList.get(i).setReproduce(true);
            }
        }
        setInformation(11, null, null);
    }

    public void saveGame(File file){

        try(PrintWriter out = new PrintWriter(file.getAbsolutePath())){

            out.println(this.getSizeX());
            out.println(this.getSizeY());
            for(Organism org : initiativeList){
                out.println(org.getToSave());
            }

            out.println("E");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }


    public void loadGame(File file){

        int worldSizeX, worldSizeY;
        boolean isHex;

        try {
            Scanner sc = new Scanner(file);
            this.clear();

            int X = sc.nextInt();
            int Y = sc.nextInt();

            while(sc.hasNextLine()){
                OrganismFactory organismFactory = new OrganismFactory();
                String s = sc.next();

                if(s.equals("E"))break;

                int strength = sc.nextInt();
                int x = sc.nextInt();
                int y = sc.nextInt();
                if(s.equals("Steve")){
                    int cooldown = sc.nextInt();
                    Human human = new Human(x, y, this);
                    human.setStrength(strength);
                    human.setCooldown(cooldown);
                    addOrganism(human);
                }
                else {
                    Organism org = organismFactory.getOrganismByName(s, x, y, this);
                    org.setStrength(strength);
                    addOrganism(org);
                }
            }
        }
        catch(NoSuchElementException e){
            System.out.println("Corrupted save file");
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void clear(){
        initiativeList.clear();
        humanAlive = false;
        map = new Organism[sizeX][sizeY];
    }


    public void setPosition(Organism organism) {
        this.map[organism.getPosX()][organism.getPosY()] = organism;
    }
    public void deletePosition(Organism organism) {
        this.map[organism.getPosX()][organism.getPosY()] = null;
    }

    public void addOrganism(Organism organism) {
        this.map[organism.getPosX()][organism.getPosY()] = organism;

        this.initiativeList.add(initiativeList.size(), organism);

        for (int i = 0; i < initiativeList.size(); i++) {
            if (initiativeList.get(i).getInitiative() < organism.getInitiative()) {
                initiativeList.add(i, organism);
                initiativeList.remove(initiativeList.size() -1);
                break;
            }
        }
    }

    public void deleteOrganism(Organism organism) {
        // if oragnism still exist on map deleting his position
        if(organism instanceof Human) {
            setHuman(false);
        }
        if(this.getOrganism(organism.getPosX(), organism.getPosY()) == organism)
        this.deletePosition(organism);

        initiativeList.remove(initiativeList.indexOf(organism));
    }

    public void setInformation(int i, Organism org, Organism enemy) {
        switch (i) {
            //0 is for organism killed an enemy
            case 0:
                this.information += org.getName();
                this.information+= "(" + org.getPosX() + ", " + org.getPosY() + ") has killed "
                    + enemy.getName() + " (" + enemy.getPosX() + ", " + enemy.getPosY() + ")" + "\n";
                break;
            //1 is for being born
            case 1:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") was born" + "\n";
                break;
            //2 is for become adult
            case 2:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " +org.getPosY() + ") has grown" + "\n";
                break;
            //telling that organism already made his turn
            case 4:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") already made his action" + "\n";
                break;
            //telling that oragnism has esacped
            case 5:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") trying to kill "
                    + enemy.getName() + " (" + enemy.getPosX() + ", " + enemy.getPosY() + ")" + "\n";
                this.information += enemy.getName();
                this.information += "(" + enemy.getPosX() + ", " + enemy.getPosY() + ") has escaped " + "\n";
                break;
            //telling that organism deflected attack
            case 6:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") trying to kill "
                    + enemy.getName() + " (" + enemy.getPosX() + ", " + enemy.getPosY() + ")" + "\n";
                this.information += enemy.getName();
                this.information += "(" + enemy.getPosX() + ", " + enemy.getPosY() + ") has deflected attack " + "\n";
                break;
            //telling about boost
            case 7:
                this.information += enemy.getName();
                this.information += "(" + enemy.getPosX() + ", " + enemy.getPosY() + ") give boost to "
                    + org.getName() + " (" + org.getPosX() + ", " + org.getPosY() + ")" + "\n";
                break;
            //telling about keys to choose
            case 8:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") its human turn" + "\n";
                break;
            case 9:
                this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ") acctivated antelope speed" + "\n";
                break;
            //telling whose turn now
            case 10: this.information += org.getName();
                this.information += "(" + org.getPosX() + ", " + org.getPosY() + ")" + "\n";
                break;
            case 11: this.information += "Turn ended \n";
                break;
            case 12: this.information += "cant safe game in 0 turn \n";
                break;
            default:
                this.information = "";
                break;
        }
    }

    //gets
    public Organism getOrganism(int x, int y) {
        return this.map[x][y];
    }

    public int getSizeX() {
        return this.sizeX;
    }
    public int getSizeY() {
        return this.sizeY;
    }

    public String getInformation(){
         return this.information;
    }

    public char getZn() {
        return this.zn;
    }

    public void setHuman(boolean b) {
        humanAlive = b;
    }

    public boolean isHumanAlive() {
        return humanAlive;
    }
}
