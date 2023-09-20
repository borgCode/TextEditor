import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindDialog {

    Highlighter hl = new DefaultHighlighter();
    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
    JTextField textField = new JTextField();
    JTextArea textArea;
    JLabel searchResult = new JLabel("");

    public FindDialog(JFrame frame, JTextArea textArea) {
        this.textArea = textArea;

        JDialog findDialog = new JDialog(frame);
        findDialog.setType(Window.Type.UTILITY);
        findDialog.setTitle("Find");
        findDialog.setBounds(100, 100, 420, 200);

        findDialog.setLocationRelativeTo(frame);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JLabel findLabel = new JLabel("Input search text", JLabel.CENTER);
        findLabel.setBorder(new EmptyBorder(3, 3, 8, 3));

        textArea.setHighlighter(hl);


        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton findNextButton = new JButton("Find next");
        JButton cancelButton = new JButton("Cancel");
        bottomPanel.add(findNextButton);
        bottomPanel.add(cancelButton);
        searchResult.setBorder(new EmptyBorder(0, 40, 0, 0));
        bottomPanel.add(searchResult);

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


        contentPanel.add(findLabel, BorderLayout.NORTH);
        contentPanel.add(textField, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        contentPanel.add(leftPanel, BorderLayout.WEST);
        findDialog.getContentPane().add(contentPanel);
        findDialog.setVisible(true);

        findNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hl.removeAllHighlights();
                int currentPosition = textArea.getCaretPosition();
                String searchText = textField.getText();
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
                        System.out.println(currentPosition);
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

//        public void search () {
//            hl.removeAllHighlights();
//            String s = textField.getText();
//            if (s.length() < 0) {
//                searchResult.setText("0 results found");
//                return;
//            }
//            String content = textArea.getText();
//            int index = content.indexOf(s, 0);
//            if (index >= 0) {
//                try {
//                    int end = index + s.length();
//                    hl.addHighlight(index, end, painter);
//                    textArea.setCaretPosition(end);
//
//                } catch (BadLocationException e) {
//                    throw new RuntimeException(e);
//                }
//            }

        }

    }
