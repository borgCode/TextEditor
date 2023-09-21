import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDialog {

    JTextField findTextField = new JTextField();
    JTextArea textArea;

    public FindDialog(JFrame frame, JTextArea textArea) {
        this.textArea = textArea;

        JDialog findDialog = new JDialog(frame);
        findDialog.setType(Window.Type.UTILITY);
        findDialog.setTitle("Find");
        findDialog.setBounds(100, 100, 400, 200);

        findDialog.setLocation(900, 0);




        JLabel findLabel = new JLabel("Input search text", JLabel.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton findNextButton = new JButton("Find next");
        JButton replaceButton = new JButton("Replace");
        JButton cancelButton = new JButton("Cancel");
        bottomPanel.add(findNextButton);
        bottomPanel.add(replaceButton);
        bottomPanel.add(cancelButton);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JRadioButton upDirection = new JRadioButton("Up");
        upDirection.setSelected(true);
        JRadioButton downDirection = new JRadioButton("Down");
        JCheckBox wrapAround = new JCheckBox("Wrap around");
        wrapAround.setBorder(new EmptyBorder(20, 0, 0, 0));
        leftPanel.add(upDirection);
        leftPanel.add(downDirection);
        leftPanel.add(wrapAround);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        gc.weightx = 0.5;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = 1;
        contentPanel.add(leftPanel, gc);

        gc.anchor = GridBagConstraints.NORTH;
        gc.gridx = 1;
        gc.gridy = 0;
        contentPanel.add(findLabel, gc);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(3, 3, 3, 10);
        gc.gridx = 1;
        gc.gridy = 1;
        contentPanel.add(findTextField, gc);


        gc.anchor = GridBagConstraints.SOUTH;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 2;
        contentPanel.add(bottomPanel, gc);

        findDialog.getContentPane().add(contentPanel);

        findDialog.setVisible(true);


        JDialog replaceDialog = new JDialog(findDialog);
        replaceDialog.setType(Window.Type.POPUP);
        replaceDialog.setTitle("Replace");
        replaceDialog.setBounds(100, 100, 400, 150);
        replaceDialog.setLocation(1300, 0);
        replaceDialog.setVisible(false);

        JPanel replacePanel = new JPanel(new GridBagLayout());
        JTextField replaceField = new JTextField();
        JLabel replaceLabel = new JLabel("Replace with:");

        JButton replace = new JButton("Replace");
        JButton replaceAll = new JButton("Replace all");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.add(replace);
        buttonPanel.add(replaceAll);




        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;

        gc.gridy = 0;
        gc.gridx = 0;

        replacePanel.add(replaceLabel, gc);

        gc.weightx = 40;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 1;
        gc.gridy = 0;
        replacePanel.add(replaceField,gc);

        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0.5;
        gc.gridheight = 1;
        gc.gridx = 2;
        gc.gridy = 0;
        replacePanel.add(buttonPanel, gc);

        replaceDialog.getContentPane().add(replacePanel);



        Action findListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentPosition = textArea.getCaretPosition();
                String searchText = findTextField.getText();
                String textContent = textArea.getText();

                Pattern p = Pattern.compile(searchText);

                if (wrapAround.isSelected()) {
                    if (upDirection.isSelected()) {
                        Matcher m = p.matcher(textContent);
                        if (!m.find()) {
                            JOptionPane.showMessageDialog(findDialog, "\"" + searchText + "\" not found in the text", "Not found", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            int start = -1;
                            int end = 0;
                            boolean found = false;
                            if (textArea.getSelectedText() != null) {
                                currentPosition = textArea.getSelectionEnd() - 1;
                            }
                            while (m.find(start + 1)) {
                                if (m.end() <= currentPosition) {
                                    start = m.start();
                                    end = m.end();
                                    found = true;
                                } else {
                                    break;
                                }
                            }
                            if (!found) {
                                int start2 = -1;
                                int end2 = 0;
                                currentPosition = textContent.length();
                                while (m.find(start2 + 1)) {
                                    if (m.end() <= currentPosition) {
                                        start2 = m.start();
                                        end2 = m.end();
                                    } else {
                                        break;
                                    }
                                }
                                textArea.setCaretPosition(end2);
                                textArea.select(start2, end2);
                            } else {
                                textArea.setCaretPosition(end);
                                textArea.select(start, end);
                            }
                        }
                    } else {
                        Matcher m = p.matcher(textContent);
                        if (!m.find()) {
                            JOptionPane.showMessageDialog(findDialog, "\"" + searchText + "\" not found in the text", "Not found", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            m = p.matcher(textContent);
                            if (m.find(currentPosition)) {
                                textArea.setCaretPosition(m.end());
                                textArea.select(m.start(), m.end());
                            } else {
                                m = p.matcher(textContent);
                                if (m.find(0)) {
                                    textArea.setCaretPosition(m.end());
                                    textArea.select(m.start(), m.end());
                                }
                            }
                        }


                    }
                } else {
                    if (upDirection.isSelected()) {
                        Matcher m = p.matcher(textContent);
                        int start = -1;
                        int end = 0;
                        boolean found = false;
                        if (textArea.getSelectedText() != null) {
                            currentPosition = textArea.getSelectionEnd() - 1;
                        }
                        while (m.find(start + 1)) {
                            if (m.end() <= currentPosition) {
                                start = m.start();
                                end = m.end();
                                found = true;
                            } else {
                                break;
                            }
                        }
                        if (!found) {
                            JOptionPane.showMessageDialog(findDialog, "\"" + searchText + "\" not found in the text", "Not found", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            textArea.setCaretPosition(end);
                            textArea.select(start, end);
                        }
                    } else {
                        Matcher m = p.matcher(textContent);
                        if (m.find(currentPosition)) {
                            textArea.setCaretPosition(m.end());
                            textArea.select(m.start(), m.end());
                        } else {
                            JOptionPane.showMessageDialog(findDialog, "\"" + searchText + "\" not found in the text", "Not found", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        };

        findNextButton.addActionListener(findListener);

        replace.addActionListener(e -> {
            textArea.replaceSelection(replaceField.getText());
        });

        replaceAll.addActionListener(e -> {
            String pattern = findTextField.getText();
            String replaceText = replaceField.getText();
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(textArea.getText());

            int currentPosition = 0;
            while(m.find(currentPosition)) {
                textArea.select(m.start(),m.end());
                textArea.replaceSelection(replaceText);
                currentPosition = textArea.getCaretPosition();
            }
            textArea.setCaretPosition(0);
        });


        upDirection.addActionListener(e -> {
            if (downDirection.isSelected()) {
                downDirection.setSelected(false);
            }
        });
        downDirection.addActionListener(e -> {
            if (upDirection.isSelected()) {
                upDirection.setSelected(false);
            }
        });

        replaceButton.addActionListener(e -> {
            replaceDialog.setVisible(true);
        });

        cancelButton.addActionListener(e -> {
            findDialog.dispose();
        });


        }

    }
