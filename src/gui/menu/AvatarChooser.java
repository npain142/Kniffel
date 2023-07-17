/**
 * Im gui.menu-package sind alle Klassen zur grafischen
 * Darstellung vom Menue enthalten.
 */
package gui.menu;

import gui.Window;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Der Avatarchooser ist auf dem Startmenue zu sehen.
 * Mit ihm kann man sich einen individuellen Avatar (Farbe)
 * für seinen Spieler festlegen. Diese Farbe bestimmt dann das Spiel-
 * interface.
 */
public class AvatarChooser extends JPanel implements MouseListener {
    private static Color currentColor;
    JPanel colorChooser;
    JLabel title;
    public AvatarChooser() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 150));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        title = new JLabel("Avatar");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Window.getPrimaryFont(20));
        title.setForeground(Color.BLACK);


        colorChooser = new JPanel();
        colorChooser.addMouseListener(this);
        colorChooser.setBackground(Window.getPrimaryColor());
        colorChooser.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(title, BorderLayout.NORTH);
        add(colorChooser, BorderLayout.CENTER);
        add(Box.createHorizontalStrut(25), BorderLayout.EAST);
        add(Box.createHorizontalStrut(25), BorderLayout.WEST);
        add(Box.createVerticalStrut(title.getFont().getSize()), BorderLayout.SOUTH);

        setCurrentColor(colorChooser.getBackground());
    }

    public static Color getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(Color color) {
        currentColor = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Sound.playKeySwish();
        setCurrentColor(JColorChooser.showDialog(this, "Farbauswahl", colorChooser.getBackground()));

        colorChooser.setBackground(getCurrentColor());
        if (getCurrentColor() == null) {
            colorChooser.setBackground(Window.getPrimaryColor());
            setCurrentColor(Window.getPrimaryColor());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
