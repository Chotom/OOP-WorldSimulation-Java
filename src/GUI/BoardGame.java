package GUI;

import World.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class BoardGame extends JPanel {
    private World world;
    private int X, Y;
    private int currentX, currentY;

    BoardGame(int x, int y, World world) {
        super(new GridLayout(x, y));
        this.X = x;
        this.Y = y;
        this.world = world;
        this.setPreferredSize(new Dimension( 30*Y + 15, 30*X + 20 ) );
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Board"));

        addMouseListener(new MouseAdapter() {
            public void mousePressed (MouseEvent e){
                // save coord x,y when mouse is pressed
                currentX = e.getX();
                currentY = e.getY();
                int tileX = (currentX - 5)/30;
                int tileY = (currentY - 15)/30;
                if(world.getOrganism(tileX, tileY) == null) {
                    new AnimalChoose(world, tileX, tileY);
                }

               // System.out.println( tileX + " " + tileY);
                repaint();
            }
        });
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < this.Y; i++) {
            for(int j = 0; j < this.X; j++) {
                if (world.getOrganism(i, j) != null) {
                    Image background = null;
                    try {
                        background = ImageIO.read(new File("D:\\Documents\\Repo\\Java\\intelij-workspace\\WorldSimOOP-swing\\src\\Organisms\\Pictures\\"
                                    + world.getOrganism(i, j).getName() + ".png")).getScaledInstance(30, 30, Image.SCALE_DEFAULT);

                    } catch (IOException e) {
                        System.err.println("File doesnt exist");
                        e.printStackTrace();
                    }
                    g2d.drawImage(background, 5+30 * i, 15+j * 30, null);
                }
                Shape rectangle2D = new Rectangle2D.Double(30*i + 5,15+j * 30, 30, 30);
                g2d.draw(rectangle2D);
            }
        }
    }
}
