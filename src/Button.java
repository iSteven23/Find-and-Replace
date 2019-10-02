//Steven Sanchez
//COMP 585
//Due: October 9, 2018
//Project 2 - Find and Replace
//Purpose: TO create a find and replace application

import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static java.lang.System.out;

public class Button extends JButton {

    private log logger = new log();

    public JPanel createButtons() {
        String string = "info";
        String message = "Creating panels";
        logger.Log(string, message);

        JTextField textFieldFind, textFieldReplace, textFieldBrowse;
        JPanel checkboxFileType, checkBoxFind, gridbagPanel, abortClearPanel;
        JLabel findLabel, replaceLabel, fileTypeLabel, directoryLabel;
        JCheckBox matchCase, wholeWord, txt, cfg, java, html, css;
        JButton findButton, replaceButton, cancelButton, browseButton, clearButton;

        JProgressBar progressBar;
        progressBar = new JProgressBar();
        Dimension prefSize = progressBar.getPreferredSize();
        prefSize.width = 15;
        progressBar.setPreferredSize(prefSize);
        progressBar.setVisible(false);
        //progressBar.setIndeterminate(true);

        JFileChooser fileChooser = new JFileChooser();

        // CREATE TABLE******************************************************************
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Line #");
        model.addColumn("File Name");
        model.addColumn("String Line");


        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(1);

        table.setPreferredScrollableViewportSize(new Dimension(440,300));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        //******************************************************************************


        findLabel = new JLabel("Containing text: ");
        replaceLabel = new JLabel("Replace with: ");
        fileTypeLabel = new JLabel("Filter by: ");
        directoryLabel = new JLabel("Directory: ");

        findButton = new JButton("Find");
        //findButton.setEnabled(false);
        replaceButton = new JButton("Replace");
        //replaceButton.setEnabled(false);
        cancelButton = new JButton("Abort");
        cancelButton.setBackground(Color.RED);
        browseButton = new JButton("Browse");
        clearButton = new JButton("Clear");

        textFieldFind = new JTextField(40);
        textFieldReplace = new JTextField(40);
        textFieldBrowse = new JTextField(40);
        textFieldBrowse.setText("No Directory Chosen");
        textFieldBrowse.setEditable(false);

        matchCase = new JCheckBox("Ignore Case");
        wholeWord = new JCheckBox("Whole Word");
        txt = new JCheckBox(".txt");
        cfg = new JCheckBox(".cfg");
        java = new JCheckBox(".java");
        html = new JCheckBox(".html");
        css = new JCheckBox(".css");

        gridbagPanel = new JPanel( new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        //******************************************************************


        //******************************************************************



        // ROW 1 *********************************************************
        gc.weighty = 0.1;
        gc.weightx = 1;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gridbagPanel.add(findLabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(textFieldFind, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(findButton, gc);

        // ROW 2 ************************************************************

        checkBoxFind = new JPanel( new GridBagLayout());
        GridBagConstraints cbf = new GridBagConstraints();

        cbf.weighty = .1;
        cbf.weightx = .1;

        cbf.gridx = 0;
        cbf.gridy = 0;
        cbf.anchor = GridBagConstraints.NORTHWEST;
        checkBoxFind.add(matchCase,cbf);
        cbf.gridx = 1;
        cbf.gridy = 0;
        cbf.anchor = GridBagConstraints.NORTHWEST;
        checkBoxFind.add(wholeWord,cbf);

        gc.gridy = 1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(checkBoxFind, gc);

        //ROW 3****************************************************************
        gc.weighty = 0.1;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        gridbagPanel.add(replaceLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(textFieldReplace, gc);

        gc.gridx = 2;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(replaceButton, gc);

        //ROW 4 *************************************************************
        gc.gridy = 3;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gridbagPanel.add(directoryLabel, gc);

        gc.gridy = 3;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(textFieldBrowse, gc);

        gc.gridy = 3;
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gridbagPanel.add(browseButton, gc);

        // Row 5 **************************************************************

        checkboxFileType = new JPanel( new GridBagLayout());
        GridBagConstraints cft = new GridBagConstraints();

        gc.weighty = 0.5;
        gc.gridy = 4;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.NORTHEAST;
        gridbagPanel.add(fileTypeLabel,gc);

        cft.gridy = 0;
        cft.gridx = 1;
        cft.anchor = GridBagConstraints.LINE_START;
        checkboxFileType.add(txt,cft);

        cft.gridy = 0;
        cft.gridx = 2;
        cft.anchor = GridBagConstraints.WEST;
        checkboxFileType.add(cfg,cft);

        cft.gridy = 0;
        cft.gridx = 3;
        cft.anchor = GridBagConstraints.LINE_START;
        checkboxFileType.add(java,cft);

        cft.gridy = 0;
        cft.gridx = 4;
        cft.anchor = GridBagConstraints.LINE_START;
        checkboxFileType.add(html,cft);

        cft.gridy = 0;
        cft.gridx = 5;
        cft.anchor = GridBagConstraints.LINE_START;
        checkboxFileType.add(css,cft);

        gc.gridy = 4;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gridbagPanel.add(checkboxFileType, gc);



        //ROW 6 **************************************************************
        abortClearPanel = new JPanel( new GridBagLayout());
        GridBagConstraints acp = new GridBagConstraints();

        acp.gridy = 0;
        acp.gridx = 0;
        acp.anchor = GridBagConstraints.NORTH;
        abortClearPanel.add(cancelButton,acp);

        acp.gridy = 1;
        acp.gridx = 0;
        acp.anchor = GridBagConstraints.SOUTH;
        abortClearPanel.add(clearButton,acp);

        gc.gridy = 5;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.NORTHEAST;
        gridbagPanel.add(progressBar,gc);

        gc.gridy = 5;
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gridbagPanel.add(abortClearPanel,gc);

        gc.gridx = 1;
        gc.gridy = 5;
        gc.gridheight = 15;
        gc.gridwidth = 8;
        gc.weighty = 2.0;
        gc.weightx = 2.0;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gridbagPanel.add(new JScrollPane(table),gc);

        //Listener calls ****************************************************************************

        ActionListener actionListener = new Listener(findButton, replaceButton, browseButton, clearButton, textFieldFind,
                textFieldBrowse, model, fileChooser, progressBar, cancelButton, textFieldReplace, txt, cfg, java, html,
                css, wholeWord, matchCase);

        findButton.addActionListener(actionListener);
        replaceButton.addActionListener(actionListener);
        browseButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
            }
        });

        return gridbagPanel;
    }

}
