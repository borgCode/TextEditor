import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor {

    JFrame frame = new JFrame();
    JMenuBar menuBar = new JMenuBar();
    JTextArea textArea = new JTextArea();
    JScrollPane sp = new JScrollPane(textArea);
    JFileChooser fc = new JFileChooser();

    public TextEditor() {

        JMenu file = new JMenu("File");
        JMenuItem fi1 = new JMenuItem("New");
        JMenuItem fi2 = new JMenuItem("Open");
        JMenuItem fi3 = new JMenuItem("Save");
        JMenuItem fi4 = new JMenuItem("Save as");
        JMenuItem fi5 = new JMenuItem("Print");
        JMenuItem fi6 = new JMenuItem("Exit");
        file.add(fi1);
        file.addSeparator();
        file.add(fi2);
        file.addSeparator();
        file.add(fi3);
        file.add(fi4);
        file.addSeparator();
        file.add(fi5);
        file.addSeparator();
        file.add(fi6);


        JMenu edit = new JMenu("Edit");

        JMenu options = new JMenu("Options");
        JCheckBoxMenuItem darkMode = new JCheckBoxMenuItem("Dark Mode");
        options.add(darkMode);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(options);


        textArea.setLineWrap(true);

        frame.add(sp);


        frame.setJMenuBar(menuBar);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);


        fi1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextEditor te = new TextEditor();
            }
        });

        fi2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(fi2);
            }
        });

        file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(fi3);

            }
        });


        fi4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(fi4);
                File fileName = new File(fc.getSelectedFile().toString() + ".txt");
                if (fileName.exists()) {
                    int n = JOptionPane.showConfirmDialog(null,
                            "The file name already exists, do you wish to overwrite it?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (n != JOptionPane.YES_OPTION) {
                        return;
                    }

                }

                try(BufferedWriter buffer = new BufferedWriter(new FileWriter(fc.getSelectedFile()+".txt"))) {
                    buffer.write(textArea.getText());
                    frame.setTitle(fc.getSelectedFile().getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });






        darkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (darkMode.isSelected()) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            FlatAnimatedLafChange.showSnapshot();
                            FlatDarculaLaf.setup();
                            FlatLaf.updateUI();
                            FlatAnimatedLafChange.hideSnapshotWithAnimation();
                        }
                    });
                } else {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            FlatAnimatedLafChange.showSnapshot();
                            FlatIntelliJLaf.setup();
                            FlatLaf.updateUI();
                            FlatAnimatedLafChange.hideSnapshotWithAnimation();
                        }
                    });
                }
            }
        });


    }


}
