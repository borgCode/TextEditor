import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FindDialog {

    public FindDialog(JFrame frame, JTextArea textArea) {
        JDialog findDialog = new JDialog(frame);
        findDialog.setType(Window.Type.UTILITY);
        findDialog.setTitle("Find");
        findDialog.setBounds(100, 100, 420, 200);

        findDialog.setLocationRelativeTo(frame);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JLabel findLabel = new JLabel("Input search text", JLabel.CENTER);
        findLabel.setBorder(new EmptyBorder(3, 3, 8, 3));



        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton findNextButton = new JButton("Find next");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(findNextButton);
        buttonPanel.add(cancelButton);

        JTextField textField = new JTextField();
        contentPanel.add(findLabel, BorderLayout.NORTH);
        contentPanel.add(textField, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        findDialog.getContentPane().add(contentPanel);
        findDialog.setVisible(true);

    }
}
