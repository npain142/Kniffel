package gui.game;

import connector.Connector;
import gui.Settings;
import gui.Window;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Die MenuBar erscheint im Spiel ueber dem
 * GameBlock. Es implementiert Runnable, um
 * die Uhrzeit zu animieren.
 */
public class MenuBar extends JPanel implements PropertyChangeListener, Runnable {

    Connector connector;
    Thread thread;
    JLabel timeLabel, nameLabel;
    JButton settings;
    LocalTime lct;
    DateTimeFormatter formatter;
    public MenuBar() {
        connector = Connector.instance();
        connector.addPropertyChangeListener(this);
        timeLabel = new JLabel();
        thread = new Thread(this);
        thread.start();
        setLayout(new GridLayout(2, 2));
        nameLabel = new JLabel("Spieler: " + connector.getCurrentPlayer().getName());
        settings = new JButton("Settings");
        settings.addActionListener(e->{
            Sound.playKeySwish();
            new Settings();
        });
        setBackground(Window.getPrimaryColor());
        setPreferredSize(new Dimension(250, 75));
        add(nameLabel);
        add(timeLabel);
        add(settings);
    }

    public DateTimeFormatter getTimeString(boolean[] b) {
        b[0] = !b[0];
        if (b[0])
            return DateTimeFormatter.ofPattern("HH:mm");
        return DateTimeFormatter.ofPattern("HH mm");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("TABLE_TURN")) {
            setBackground(connector.getCurrentPlayer().getTheme());
            nameLabel.setText(connector.getCurrentPlayer().getName());
            repaint();
        }
    }

    @Override
    public void run() {
        String time;
        boolean[] b = new boolean[1];
        while (!thread.isInterrupted()) {
            formatter = getTimeString(b);
            lct = LocalTime.now();
            time = lct.format(formatter);
            timeLabel.setText(time);
            repaint();
            revalidate();
            try {
                Thread.sleep(750);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
