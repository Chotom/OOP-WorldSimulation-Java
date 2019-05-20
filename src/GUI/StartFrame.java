package GUI;
import World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {
    public int X = 10, Y = 20;
    private JSpinner xSizeSpinner;
    private JSpinner ySizeSpinner;

    public StartFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int DEFAULT_WIDTH = 400;
        int DEFAULT_HEIGHT = 100;
        setLayout(new FlowLayout());
        setLocation(screenWidth / 2 - DEFAULT_WIDTH / 2, screenHeight / 2 - DEFAULT_HEIGHT / 2);
        setResizable( false );
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));


        SpinnerModel xSpinner = new SpinnerNumberModel(10, 2, 18, 1);
        SpinnerModel ySpinner = new SpinnerNumberModel(20, 2, 50, 1);
        xSizeSpinner =  addLabeledSpinner(this,"Give size Y and X:", xSpinner);
        ySizeSpinner =  addLabeledSpinner(this,"", ySpinner);

        add(xSizeSpinner);
        add(ySizeSpinner);

        JButton AcceptButton;
        AcceptButton = new JButton("Accept");
        AcceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                X = (int)xSizeSpinner.getValue();
                Y = (int)ySizeSpinner.getValue();
                Start();

                dispose();
            }
        });

        add(AcceptButton);

        pack();
        setLocationRelativeTo( null );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) {
        JLabel l = new JLabel(label);
        c.add(l);

        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);

        return spinner;
    }

    void Start() {
        World world = new World(X, Y);
        new GameFrame(world, X, Y);
    }

}