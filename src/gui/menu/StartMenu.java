package gui.menu;

import connector.Connector;
import gui.Settings;
import gui.Window;
import logic.Player;
import logic.Sound;


import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Das Startmenue ist das erste was am Anfang angezeigt wird.
 * Über das startmenue kann man das naechste Spiel konfigurieren.
 */

public class StartMenu extends JPanel implements PropertyChangeListener {

    Connector connector;
    JPanel northPanel, centerPanel, southPanel;
    JLabel headline;
    JButton menuBtn;

    public StartMenu() {
        connector = Connector.instance();
        setLayout(new GridLayout(3, 1));
        initContainer();
    }

    public void initContainer() {
        Connector connector = Connector.instance();
        connector.addPropertyChangeListener(this);
        GridBagConstraints c = new GridBagConstraints();
        northPanel = new JPanel(new GridBagLayout());
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 1;
        c.weighty = 1;
        c.ipady = 10;
        c.insets = new Insets(20, 0, 0, 0);
        menuBtn = new JButton("Settings");
        menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuBtn.setFocusPainted(false);
        menuBtn.setBackground(Window.getPrimaryColor());
        menuBtn.setForeground(Color.WHITE);
        menuBtn.addActionListener(e ->{
            Sound.playKeySwish();
            new Settings();
        } );
        northPanel.add(menuBtn, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipady = 0;
        c.gridx = 1;
        headline = new JLabel("Kniffel");
        headline.setFont(Window.getPrimaryFont(100));
        headline.setForeground(Color.BLACK);
        northPanel.add(headline, c);
        c.gridx = 2;
        northPanel.add(Box.createHorizontalStrut(menuBtn.getPreferredSize().width), c);
        add(northPanel);


        centerPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        AvatarChooser ac = new AvatarChooser();
        centerPanel.add(ac, c);
        c.gridx  = 1;

        centerPanel.add(new TextInputField(), c);

        c.gridx  = 2;
        centerPanel.add(Box.createHorizontalStrut(ac.getPreferredSize().width), c);
        add(centerPanel);

        southPanel = new JPanel(new GridLayout(2, 7, 10, 10));

        add(southPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("NEW_PLAYER")) {
            updatePlayerVisuals();
        } else if (evt.getPropertyName().equals("PLAYER_REMOVED")) {
            updatePlayerVisuals();
        }
    }

    public void updatePlayerVisuals() {

        southPanel.removeAll();
        for (Player player : connector.getAllPlayers()) {
            southPanel.add(player.getVisual());
        }

        if (connector.getAllPlayers().size() > 0) {
            southPanel.add(new StartButton());
        }

        for (int i = 0; i < 13 - connector.getAllPlayers().size(); i++) {
            southPanel.add(new JPanel());
        }
        revalidate();

        requestFocusInWindow();
    }



}
