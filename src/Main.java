//Steven Sanchez
//COMP 585
//Due: October 9, 2018
//Project 2 - Find and Replace
//Purpose: TO create a find and replace application

import javax.swing.SwingUtilities;

public class Main {
    public static void main (String[] args) {
        //runs the task inside Swing event dispatch thread.
        //avoids errors
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new Frame();
            }
        });

    }
}

