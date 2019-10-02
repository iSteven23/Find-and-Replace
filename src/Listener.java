//Steven Sanchez
//COMP 585
//Due: October 9, 2018
//Project 2 - Find and Replace
//Purpose: TO create a find and replace application

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listener implements ActionListener {

    private JButton find;
    private JButton replace;
    private JButton browse;
    private JButton cancel;
    private JButton clearButton;
    private JTextField textFieldFind;
    private JTextField textFieldBrowse;
    private JTextField textFieldReplace;
    private DefaultTableModel model;
    private JFileChooser fileChooser;
    private JCheckBox txt;
    private JCheckBox cfg;
    private JCheckBox java;
    private JCheckBox html;
    private JCheckBox css;
    private JCheckBox wholeWord;
    private JCheckBox matchCase;
    private JProgressBar progressBar;
    private findProgress taskInfinite;
    private replaceProgress replaceTask;

    private log logger = new log();


    public Listener (JButton find, JButton replace, JButton browse, JButton clearButton,
                     JTextField textFieldFind, JTextField textFieldBrowse, DefaultTableModel model,
                     JFileChooser fileChooser, JProgressBar progressBar, JButton cancel, JTextField textFieldReplace,
                     JCheckBox txt, JCheckBox cfg, JCheckBox java, JCheckBox html, JCheckBox css, JCheckBox wholeWord,
                     JCheckBox matchCase){

        this.find = find;
        this.replace = replace;
        this.browse = browse;
        this.textFieldFind = textFieldFind;
        this.textFieldBrowse = textFieldBrowse;
        this.textFieldReplace = textFieldReplace;
        this.model = model;
        this.fileChooser = fileChooser;
        this.progressBar = progressBar;
        this.cancel = cancel;
        this.txt = txt;
        this.cfg = cfg;
        this.java = java;
        this.html = html;
        this.css = css;
        this.clearButton = clearButton;
        this.wholeWord = wholeWord;
        this.matchCase = matchCase;


    }
    public void actionPerformed(ActionEvent e){
        String string = "info";
        String message = "started actionPerformed Action Event";
        logger.Log(string, message);


        //FIND BUTTON PRESSED********************************************************************
        if(e.getSource() == find)
        {
            string = "info";
            message = "Find button pressed";
            logger.Log(string, message);

            taskInfinite = new findProgress();
            taskInfinite.execute();

        }
        //REPLACE BUTTON PRESSED****************************************************************
        else if(e.getSource() == replace)
        {
            string = "info";
            message = "Replace button pressed";
            logger.Log(string, message);

            replaceTask = new replaceProgress();
            replaceTask.execute();


        }
        //BROWSE BUTTON PRESSED***************************************************************
        else if (e.getSource() == browse)
        {
            string = "info";
            message = "Browse button pressed";
            logger.Log(string, message);

            fileChooser.setDialogTitle("Choose a directory ");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                textFieldBrowse.setText(""+fileChooser.getSelectedFile().getAbsolutePath());
                //replace.setEnabled(true);
                //findButton.setEnabled(true);
            }
            else{
                textFieldBrowse.setText("No Directory Chosen");
                //replaceButton.setEnabled(false);
                //findButton.setEnabled(false);
            }
        }

        textFieldFind.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    taskInfinite = new findProgress();
                    taskInfinite.execute();
                }
            }
        });
        textFieldReplace.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    replaceTask = new replaceProgress();
                    replaceTask.execute();
                }
            }
        });

    }

    class replaceProgress extends SwingWorker<Void, Void>
    {


        @Override
        public Void doInBackground() {
            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            find.setEnabled(false);
            replace.setEnabled(false);
            browse.setEnabled(false);
            clearButton.setEnabled(false);
            try{replaceLogic();}catch(Exception e){}
            return null;

        }
        @Override
        public void done() {
            taskCompleted();
        }

    }

    class findProgress extends SwingWorker<Void, Void>
    {
        @Override
        public Void doInBackground(){

            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            find.setEnabled(false);
            replace.setEnabled(false);
            browse.setEnabled(false);
            findLogic();


            return null;
        }
        @Override
        public void done()
        {
            taskCompleted();
        }


    }


    public void findLogic()
    {
        String string = "info";
        String message = "findLogic() called";
        logger.Log(string, message);

        String word = textFieldFind.getText();
        String directory = textFieldBrowse.getText();

        // Searches by Directory only; Needs specific file types checked off *******************************************
        if (!word.equals("") && !textFieldBrowse.getText().equals("No Directory Chosen")
                && !directory.endsWith(".txt")
                && !directory.endsWith(".cfg")
                && !directory.endsWith(".java")
                && !directory.endsWith(".html")
                && !directory.endsWith(".css"))
        {
            string = "info";
            message = "Searching by directory only. Needs specific file types checked";
            logger.Log(string, message);

            String[] stringLine = new String[1000000];
            String[] fileName = new String[1000000];

            String dir = textFieldBrowse.getText();
            String text = textFieldFind.getText();
            File folder = new File(dir);

            if(txt.isSelected()){
                File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".txt");
                    }
                });
                printTable(listOfFiles, fileName, stringLine, text);

            }
            if(cfg.isSelected()){
                File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".cfg");
                    }
                });
                printTable(listOfFiles, fileName, stringLine, text);

            }
            if(java.isSelected()){
                File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".java");
                    }
                });
                printTable(listOfFiles, fileName, stringLine, text);

            }
            if(html.isSelected()){
                File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".html");
                    }
                });
                printTable(listOfFiles, fileName, stringLine, text);

            }
            if(css.isSelected()){
                File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".css");
                    }
                });
                printTable(listOfFiles, fileName, stringLine, text);

            }

        }
        //Searches by file selected only ***************************************************************************
        else if(!word.equals("") && !textFieldBrowse.getText().equals("No Directory Chosen")
            && (directory.endsWith(".txt")
            || directory.endsWith(".cfg")
            || directory.endsWith(".java")
            || directory.endsWith(".html")
            || directory.endsWith(".css"))){

            string = "info";
            message = "Searching by file selected only";
            logger.Log(string, message);

            int lineNumber = 1;
            String fileName = textFieldBrowse.getText();
            String lineFromFile;
            String[] stringLine = new String[1000000];
            //String regex = ".*\\b" +textFieldFind.getText()+"\\b.";
            int i = 0;
            File myFile = new File(fileName);
            Scanner scanner = new Scanner(System.in);
            try {
                scanner = new Scanner(myFile);
            } catch (Exception ex) {
                //
            }
            while (scanner.hasNextLine())
            {
                lineFromFile = scanner.nextLine();

                Pattern p = Pattern.compile(textFieldFind.getText(), Pattern.CASE_INSENSITIVE);
                Matcher matcher = Pattern.compile("\\b"+textFieldFind.getText()+"\\b").matcher(lineFromFile);

                if(wholeWord.isSelected() || matchCase.isSelected()){
                    if(wholeWord.isSelected()) {
                        if (matcher.find()) {
                            //System.out.println("it worked");
                            stringLine[i] = lineFromFile;
                            model.addRow(new Object[]{lineNumber, myFile.getName(), stringLine[i]});
                            if (cancel.getModel().isPressed())
                                break;
                        }
                    }
                    if(matchCase.isSelected())
                    {
                        Matcher m = p.matcher(lineFromFile);
                        if (m.find()) {
                            //System.out.println("it worked");
                            stringLine[i] = lineFromFile;
                            model.addRow(new Object[]{lineNumber, myFile.getName(), stringLine[i]});
                            if (cancel.getModel().isPressed())
                                break;
                        }
                    }
                }
                else if (lineFromFile.contains(textFieldFind.getText())) {
                    //System.out.println("test");
                    stringLine[i] = lineFromFile;
                    model.addRow(new Object[]{lineNumber, myFile.getName(), stringLine[i]});
                    if (cancel.getModel().isPressed())
                        break;
                }
                lineNumber++;
                i++;
            }
        }
        // Either directory is empty or word to find text box is empty *****************************************
        else {
            string = "warn";
            message = "Must select a directory and/or enter a word to find";
            logger.Log(string, message);

            if(word.equals(""))
            {
                JOptionPane.showMessageDialog(null,
                        "Must enter a word to find",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        "Must enter a directory",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void replaceLogic(){

        String string = "info";
        String message = "called replaceLogic()";
        logger.Log(string, message);

        System.out.println("Replace pressed");
        int dialogButton = JOptionPane.YES_NO_OPTION;

        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to replace all?",
                "Warning",
                dialogButton);
        if(dialogResult == 0){
            string = "warn";
            message = "the user selected 'yes' in dialog box to continue to replace all";
            logger.Log(string, message);
            //For testing
            //System.out.println("Yes option");

            String findWord = textFieldFind.getText();
            String word = textFieldReplace.getText();
            String directory = textFieldBrowse.getText();

            //REPLACE BY SPECIFIC FILE SELECTED *****************************************************************
            if (!word.equals("") && !textFieldBrowse.getText().equals("No Directory Chosen")
                    && (directory.endsWith(".txt")
                    || directory.endsWith(".cfg")
                    || directory.endsWith(".java")
                    || directory.endsWith(".html")
                    || directory.endsWith(".css")))
            {

                String dir = textFieldBrowse.getText();
                String search = textFieldFind.getText();
                String replace = textFieldReplace.getText();
                String[] stringLine = new String[1000000];
                String lineFromFile;
                int lineNumber = 1;
                int i = 0;

                File log = new File(dir);

                writeToFileLogic(log,search,replace);

                String fileName = log.getName();
                Scanner scanner = new Scanner(System.in);
                try {
                    scanner = new Scanner(log);
                } catch (Exception ex) {
                    //
                }
                while(scanner.hasNextLine())
                {
                    lineFromFile = scanner.nextLine();
                    if (lineFromFile.contains(replace)) {
                        stringLine[i] = lineFromFile;
                        model.addRow(new Object[]{lineNumber, fileName, stringLine[i]});
                        if (cancel.getModel().isPressed())
                            break;
                    }
                    lineNumber++;
                    i++;
                }


            }
            //REPLACE BY DIRECTORY AND FILE TYPE *********************************************************************
            else if(!word.equals("") && !textFieldBrowse.getText().equals("No Directory Chosen")
                    && !directory.endsWith(".txt")
                    && !directory.endsWith(".cfg")
                    && !directory.endsWith(".java")
                    && !directory.endsWith(".html")
                    && !directory.endsWith(".css"))
            {
                String[] stringLine = new String[1000000];
                String[] fileName = new String[1000000];

                String dir = textFieldBrowse.getText();
                String text = textFieldFind.getText();
                String replaceText = textFieldReplace.getText();
                File folder = new File(dir);

                if(txt.isSelected()) {
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".txt");
                        }

                    });
                    printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);
                    //try{printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);}catch(Exception e){}
                }
                if(cfg.isSelected()){
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".cfg");
                        }
                    });
                    //
                    printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);

                }
                if(java.isSelected()){
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".java");
                        }
                    });
                    //
                    printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);

                }
                if(html.isSelected()){
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".html");
                        }
                    });
                    //
                    printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);

                }
                if(css.isSelected()){
                    File[] listOfFiles = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".css");
                        }
                    });
                    //
                    printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);
                    //try{printReplaceTable(listOfFiles, fileName, stringLine, text, replaceText);}catch(Exception e){}

                }


            }
            else
            {
                if(word.equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "Must enter a word to replace",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "Must enter a directory",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        else
        {
            string = "info";
            message = "the user selected 'no' in dialog box to not replace all";
            logger.Log(string, message);
            //for testing
            //System.out.println("No option");
        }


    }
    public void taskCompleted()
    {
        String string = "info";
        String message = "task is completed";
        logger.Log(string, message);

        progressBar.setVisible(false);
        progressBar.setIndeterminate(false);
        find.setEnabled(true);
        replace.setEnabled(true);
        browse.setEnabled(true);
        clearButton.setEnabled(true);

        if(cancel.getModel().isPressed())
            JOptionPane.showMessageDialog(null, "Task canceled");
        else if (textFieldReplace.getText().equals("") || textFieldBrowse.getText().equals("")|| textFieldBrowse.getText().equals("No Directory Chosen"))
        {
            //JOptionPane.showMessageDialog(null, "Error");
        }
        else
            JOptionPane.showMessageDialog(null, "Task completed");

    }
    public void printTable(File[] listOfFiles, String[] fileName, String[] stringLine, String text)
    {
        String string = "info";
        String message = "Printing table";
        logger.Log(string, message);

        for (int i = 0; i < listOfFiles.length; i++)
        {

            int lineNumber = 1;
            fileName[i] = listOfFiles[i].getName();
            Scanner scanner = new Scanner(System.in);
            try {
                scanner = new Scanner(listOfFiles[i]);
            } catch (Exception ex) {
                //
            }
            while (scanner.hasNextLine())
            {
                String lineFromFile = scanner.nextLine();
                Matcher matcher = Pattern.compile("\\b"+text+"\\b").matcher(lineFromFile);
                Pattern p = Pattern.compile(textFieldFind.getText(), Pattern.CASE_INSENSITIVE);


                if(wholeWord.isSelected() || matchCase.isSelected()){

                    if(wholeWord.isSelected()) {
                        if (matcher.find()) {
                            //System.out.println("it worked");
                            stringLine[i] = lineFromFile;
                            model.addRow(new Object[]{lineNumber, fileName[i], stringLine[i]});
                            if (cancel.getModel().isPressed())
                                break;

                        }

                    }

                    if(matchCase.isSelected())
                    {
                        Matcher m = p.matcher(lineFromFile);
                        if (m.find()) {
                            //System.out.println("it worked");
                            stringLine[i] = lineFromFile;
                            model.addRow(new Object[]{lineNumber, fileName[i], stringLine[i]});
                            if (cancel.getModel().isPressed()) {
                                break;
                            }
                        }
                    }

                }
                else if (lineFromFile.contains(text)) {
                    //System.out.println("sdfas");
                    stringLine[i] = lineFromFile;
                    model.addRow(new Object[]{lineNumber, fileName[i], stringLine[i]});
                    if (cancel.getModel().isPressed())
                        break;
                }
                lineNumber++;
            }
            scanner.close();
        }
    }
    public void printReplaceTable(File[] listOfFiles, String[] fileName, String[] stringLine, String text, String replaceText)
    {
        String string = "info";
        String message = "printing replace table";
        logger.Log(string, message);

        for(int i = 0; i < listOfFiles.length; i++)
        {
            int lineNumber = 1;
            fileName[i] = listOfFiles[i].getPath();
            String theFileName = listOfFiles[i].getName();
            System.out.println(fileName[i]);

            File log = new File(fileName[i]);

            writeToFileLogic(log,text,replaceText);


            Scanner scanner = new Scanner(System.in);
            try {
                scanner = new Scanner(listOfFiles[i]);
            } catch (Exception ex) {
                //
            }
            while(scanner.hasNextLine())
            {
                final String fileLine = scanner.nextLine();

                if(fileLine.contains(replaceText))
                {
                    stringLine[i] = fileLine;
                    model.addRow(new Object[]{lineNumber, theFileName, stringLine[i]});
                    if (cancel.getModel().isPressed())
                        break;
                }
                lineNumber++;

            }

        }

    }
    public void writeToFileLogic(File log, String text, String replaceText)
    {
        String string = "info";
        String message = "doing write to file logic";
        logger.Log(string, message);
        try{
            FileReader fr = new FileReader(log);
            String s;
            String totalStr = "";
            BufferedReader br = new BufferedReader(fr) ;

            while ((s = br.readLine()) != null) {
                totalStr = totalStr + s + System.getProperty("line.separator");
                //totalStr += "\n";
            }
            totalStr = totalStr.replace(text, replaceText);
            FileWriter fw = new FileWriter(log);
            fw.write(totalStr);
            fw.close();
        }catch(Exception e){}
    }

}
