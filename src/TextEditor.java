import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
        JMenuItem fi1 = new JMenuItem("New                   Ctrl+N");
        JMenuItem fi2 = new JMenuItem("Open                 Ctrl+O");
        JMenuItem fi3 = new JMenuItem("Save                   Ctrl+S");
        JMenuItem fi4 = new JMenuItem("Save as              Ctrl+Shift+S");
        JMenuItem fi5 = new JMenuItem("Print                  Ctrl+P");
        JMenuItem fi6 = new JMenuItem("Close");

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
        JMenuItem ed1 = new JMenuItem("Undo             Ctrl+Z");
        JMenuItem ed2 = new JMenuItem("Cut                Ctrl+X");
        JMenuItem ed3 = new JMenuItem("Copy             Ctrl+C");
        JMenuItem ed4 = new JMenuItem("Paste             Ctrl+V");
        JMenuItem ed5 = new JMenuItem("Delete                Del");
        JMenuItem ed6 = new JMenuItem("Find               Ctrl+F");
        JMenuItem ed7 = new JMenuItem("Replace         Ctrl+H");
        JMenuItem ed8 = new JMenuItem("Select All      Ctrl+A");

        edit.add(ed1);
        edit.addSeparator();
        edit.add(ed2);
        edit.add(ed3);
        edit.add(ed4);
        edit.add(ed5);
        edit.addSeparator();
        edit.add(ed6);
        edit.add(ed7);
        edit.addSeparator();
        edit.add(ed8);


        JMenu options = new JMenu("Options");
        JCheckBoxMenuItem darkMode = new JCheckBoxMenuItem("Dark Mode");
        JCheckBoxMenuItem readOnly = new JCheckBoxMenuItem("Read only");
        options.add(darkMode);
        options.add(readOnly);

        JComboBox<String> fontList = new JComboBox<>(fonts);




        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(options);
        menuBar.add(fontList);


        textArea.setLineWrap(true);

        frame.add(sp, BorderLayout.CENTER);
        frame.setTitle(fileTitle);

        frame.setJMenuBar(menuBar);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);


        frame.setVisible(true);


        Action newListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextEditor te = new TextEditor();
            }
        };

        fi1.addActionListener(newListener);
        addKeyBind(fi1,KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK, "newKeyBind", newListener);
        Action openListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(fi2);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
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
        };

        fi2.addActionListener(openListener);
        addKeyBind(fi2, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, "openKeyBind", openListener);

        Action saveListener = new AbstractAction() {
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
        };

        fi3.addActionListener(saveListener);
        addKeyBind(fi3, KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK,"saveKeyBind", saveListener);



        Action saveAsListener = new AbstractAction() {
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
        };

        fi4.addActionListener(saveAsListener);
        addKeyBind(fi4, KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK, "saveAsKeyBind", saveAsListener);

        //print listener


        fi6.addActionListener(e -> {
            frame.dispose();
        });


        ed2.addActionListener(e -> {
            textArea.cut();
        });
        ed3.addActionListener(e -> {
            textArea.copy();
        });
        ed4.addActionListener(e -> {
            textArea.paste();
        });
        ed5.addActionListener(e -> {
            textArea.replaceSelection("");
        });

        Action findListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindDialog fd = new FindDialog(frame, textArea);
            }
        };
        ed6.addActionListener(findListener);
        addKeyBind(ed6, KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK, "findKeyBind", findListener);

        ed7.addActionListener(e -> {
            //Replace Method
        });
        ed8.addActionListener(e -> {
            textArea.selectAll();
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

        readOnly.addActionListener(e -> {
            if (readOnly.isSelected()) {
                textArea.setEditable(false);
            } else {
                textArea.setEditable(true);
            }
        });

        fontList.addActionListener(e -> {
            String selectedFamilyName = (String) fontList.getSelectedItem();
            Font selectedFont = new Font(selectedFamilyName, Font.PLAIN, 12);
            textArea.setFont(selectedFont);
            textArea.repaint();
        });




    }

    public static void addKeyBind(JComponent comp, int keyCode, int mask, String id, Action action) {
        InputMap newIm = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap newAm = comp.getActionMap();
        newIm.put(KeyStroke.getKeyStroke(keyCode, mask, false), id);
        newAm.put(id, action);
    }



}
