//Steven Sanchez
//COMP 585
//Due: October 9, 2018
//Project 2 - Find and Replace
//Purpose: TO create a find and replace application

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    //variables
    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 600;

    Button button = new Button();
    private JMenuBar menuBar;
    private JMenuItem menuItem;
    private log logger = new log();

    public Frame(){

        super("Find and Replace");

        String string = "info";
        String message = "Creating frame";
        logger.Log(string, message);

        buildMenu();

        JPanel panel = (button.createButtons());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        setJMenuBar(menuBar);
        setResizable(false);
        setVisible(true);

        menuItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

    }
    private void buildMenu(){
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menuItem = new JMenuItem("Exit");

        menu.add(menuItem);
        menuBar.add(menu);

    }
}
