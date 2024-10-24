package main;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame{
    private JTextArea textArea1;
    private JButton analyzeButton;
    private JLabel label;
    private JPanel mainPanel;

    public MainForm(){
        setSize(620, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(mainPanel);
        analyzeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(Main.test(textArea1.getText()));
            }
        });
    }
}
