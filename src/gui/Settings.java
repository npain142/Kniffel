/**
 * Im gui-package sind alle Klassen zur grafischen Darstellung
 * enthalten
 */
package gui;

import connector.Connector;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Settings extends JDialog {
    Connector connector;

    public Settings() {
        connector = Connector.instance();
        setLayout(new GridLayout(2, 1));
        setTitle("Settings");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            setBackground(connector.getCurrentPlayer().getTheme());
        } catch (Exception e) {
            setBackground(Window.getPrimaryColor());
        }
        setLocationRelativeTo(null);

        JSlider slider = new JSlider(0, 30, (int) Sound.getVolume() + 30);
        slider.addChangeListener(e -> {
            Sound.setVolume(slider.getValue() - 30);
            slider.setValue((int) Sound.getVolume() + 30);
        });
        slider.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btn = new JButton("REGELN");
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            File file = new File("assets\\kniffel_rules.pdf");
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JLabel l = new JLabel("VOLUME");

        add(l);
        add(slider);
        add(btn);
        setVisible(true);
    }
}
