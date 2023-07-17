package gui.menu;

import connector.Connector;
import gui.Window;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends JButton implements ActionListener {
    Connector connector;
    public StartButton() {
        connector = Connector.instance();
        setText("Start");
        setFont(Window.getPrimaryFont(20));
        setForeground(Color.WHITE);
        setBackground(new Color(51, 204, 51));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.playTap();
        connector.startGame();
    }
}
