package GUI;

import Organisms.Organism;
import World.OrganismFactory;
import World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnimalChoose extends JFrame {
    World world;
    int X, Y;
    ArrayList<JRadioButton> radioButtons = new ArrayList<>();
    ButtonGroup group = new ButtonGroup();


    AnimalChoose(World world, int x, int y){
        this.world = world;
        this.X = x;
        this.Y = y;




        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int DEFAULT_WIDTH = 900;
        int DEFAULT_HEIGHT = 200;
        setLayout(new FlowLayout());
        setLocation(screenWidth / 2 - DEFAULT_WIDTH / 2, screenHeight / 2 - DEFAULT_HEIGHT / 2);
        setResizable( false );
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));


        JPanel listPanel = new JPanel();
        add(listPanel);

        OrganismFactory orgs = new OrganismFactory();
        String [] Organisms = {
                "Sheep","Antelope","Fox", "Turtle","Wolf","Dandelion",
                "DeadlyNightshade", "Grass", "Guarana", "SosnowskyHogweed"
        };

        for (String organism : Organisms) {
            JRadioButton radio = new JRadioButton(organism, true);
            radioButtons.add(radio);
            group.add(radio);
            listPanel.add(radio);
        }


        JButton AcceptButton;
        AcceptButton = new JButton("Accept");
        AcceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(world.getOrganism(x, y) == null) {
                    Organism org = orgs.getOrganismByName(showDialog(), x, y, world);
                    world.addOrganism(org);
                    System.out.println(showDialog());
                }
                repaint();
                dispose();
            }
        });

        JButton CancelButton;
        CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        add(AcceptButton);
        add(CancelButton);

        pack();
        setLocationRelativeTo( null );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public String showDialog() {

            for (JRadioButton radio : radioButtons) {
                if (radio.isSelected()) {
                    return radio.getText();
                }
            }
        return "";
    }
}
