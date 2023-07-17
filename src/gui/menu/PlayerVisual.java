package gui.menu;

import connector.Connector;
import gui.Window;
import logic.Player;
import logic.Sound;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *Ein PlayerVisual repraesentiert einen angelegten Spieler
 * im StartMenue. Über dieses Playervisual kann man auch
 * einen Spieler wieder entfernen.
 */
public class PlayerVisual extends JPanel implements ActionListener {
    Connector connector;
    Player player;
    JButton delete;
    JLabel playername, label;
    public PlayerVisual() {
        connector = Connector.instance();
        setBackground(AvatarChooser.getCurrentColor());
        setLayout(new BorderLayout(20, 20));

        Container c = new Container();
        c.setLayout(new BorderLayout());

        delete = new JButton("X");
        delete.setBackground(Color.RED);
        delete.setFocusPainted(false);
        delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        delete.setFont(Window.getPrimaryFont(10));
        delete.addActionListener(this);

        label = new JLabel("Player");
        label.setFont(Window.getPrimaryFont(15));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(label, BorderLayout.CENTER);
        c.add(delete, BorderLayout.EAST);
        add(c, BorderLayout.NORTH);

    }
    public void setPlayer(Player player) {
        this.player = player;
        playername = new JLabel(player.getName());
        playername.setFont(Window.getPrimaryFont(15));
        playername.setForeground(Color.WHITE);
        playername.setHorizontalAlignment(SwingConstants.CENTER);
        add(playername, BorderLayout.SOUTH);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.playError();
        connector.removePlayer(player);

    }
}
