package GUI;

import World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GameFrame extends JFrame{
    int X, Y;
    char zn = ' ';
    public JTextArea events;
    World world;
    BoardGame boardPanel;

    public GameFrame(World world, int X, int Y) {
        super("Tomasz Czochański");
        this.world = world;
        this.X = X;
        this.Y = Y;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int DEFAULT_WIDTH = 30*Y + 100;
        int DEFAULT_HEIGHT = 30*X + 300;

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 , screenHeight/2 - DEFAULT_HEIGHT/2);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setResizable( false );

        //prepare Board
        boardPanel = new BoardGame(X, Y, world);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(boardPanel, gbc);


        //prepare Control Section
        JPanel controlPanel = createControlPanel();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(controlPanel, gbc);



        // Prepare events section
        events = new JTextArea(9, 40);
        events.setEditable(false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(events);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Events"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(scroll, gbc);

        if(world.isHumanAlive()) keyListener();


        pack();
        setLocationRelativeTo( null );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createControlPanel() {

        final JPanel controlPanel = new JPanel();
        JButton nextButton, saveButton, loadButton;

        nextButton = new JButton("Next Turn");
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");

        controlPanel.add(nextButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTurn();

                //boardPanel.repaint();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();

                    world.saveGame(selectedFile);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();

                    try {

                        Scanner sc = new Scanner(selectedFile);
                        Y = sc.nextInt();
                        X = sc.nextInt();

                        World w = new World(X, Y);
                        world = w;
                        w.loadGame(selectedFile);
                        new GameFrame(w, X, Y);
                        dispose();
                    }
                    catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    repaint();
                }
            }
        });

        controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Controls"));

        return controlPanel;
    }

    private void keyListener(){
        if(true) {
            System.out.println(world.isHumanAlive());
            events.getInputMap().put(KeyStroke.getKeyStroke("UP"), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(world.isHumanAlive()){
                        zn = 'a';
                        newTurn();
                    }
                }
            });
            events.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(world.isHumanAlive()) {
                        zn = 'd';
                        newTurn();
                    }
                }
            });
            events.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(world.isHumanAlive()) {
                        zn = 'w';
                        newTurn();
                    }
                }
            });
            events.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(world.isHumanAlive()) {
                        zn = 's';
                        newTurn();
                    }
                }
            });
            //events.getActionMap().put("pressedUP", );
            events.getInputMap().put(KeyStroke.getKeyStroke("R"), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(world.isHumanAlive()) {
                        zn = 'r';
                        newTurn();
                    }
                }
            });
        }
    }

    private void newTurn(){
        world.setNextTurn(boardPanel, zn);
        events.setText(world.getInformation());
        events.grabFocus();
    }
}