import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor {

    JFrame frame = new JFrame();
    JMenuBar menuBar = new JMenuBar();
    JTextArea textArea = new JTextArea();
    JScrollPane sp = new JScrollPane(textArea);
    JFileChooser fc = new JFileChooser();
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String fileTitle = "";
    File filePath;

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

        JComboBox<String> fontList = new JComboBox<>(fonts);


        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(options);
        menuBar.add(fontList);


        textArea.setLineWrap(true);

        frame.add(sp);


        frame.setTitle(fileTitle);

        frame.setJMenuBar(menuBar);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);


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
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fc.getSelectedFile() + ".txt"))) {
                        textArea.read(reader, textArea);
                        filePath = fc.getSelectedFile();
                        fileTitle = fc.getSelectedFile().getName().replaceFirst("[.][^.]+$", "");
                        frame.setTitle(fileTitle);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        fi3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fileTitle.equals("")) {
                    System.out.println(filePath);
                    try (BufferedWriter buffer = new BufferedWriter(new FileWriter(filePath + ".txt"))) {
                        buffer.write(textArea.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    int returnVal = fc.showSaveDialog(fi3);
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
                    try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fc.getSelectedFile() + ".txt"))) {
                        buffer.write(textArea.getText());
                        filePath = fc.getSelectedFile();
                        fileTitle = fc.getSelectedFile().getName().replaceFirst("[.][^.]+$", "");
                        frame.setTitle(fileTitle);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
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
                try (BufferedWriter buffer = new BufferedWriter(new FileWriter(fc.getSelectedFile() + ".txt"))) {
                    buffer.write(textArea.getText());
                    filePath = fc.getSelectedFile();
                    fileTitle = fc.getSelectedFile().getName().replaceFirst("[.][^.]+$", "");
                    frame.setTitle(fileTitle);

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

        fontList.addActionListener(e -> {
            String selectedFamilyName = (String) fontList.getSelectedItem();
            Font selectedFont = new Font(selectedFamilyName, Font.PLAIN, 12);
            textArea.setFont(selectedFont);
            textArea.repaint();
        });


    }


}
