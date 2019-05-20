package World;

import Organisms.Animals.*;
import Organisms.Organism;
import Organisms.Plants.Dandelion;
import Organisms.Plants.DeadlyNightshade;
import Organisms.Plants.Grass;
import Organisms.Plants.Guarana;

public class OrganismFactory {
    public Organism getOrganismByName(String name, int posX, int posY, World world){
        if(name == null){
            return null;
        }
        if(name.equalsIgnoreCase("Antelope")){
            return new Antelope(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Fox")){
            return new Fox(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Sheep")){
            return new Sheep(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Turtle")){
            return new Turtle(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Wolf")){
            return new Wolf(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Dandelion")){
            return new Dandelion(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("DeadlyNightshade")){
            return new DeadlyNightshade(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Grass")){
            return new Grass(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("Guarana")){
            return new Guarana(posX, posY, world);

        }
        else if(name.equalsIgnoreCase("SosnowskyHogweed")){
            return new Antelope(posX, posY, world);

        }

        return null;
    }
}
