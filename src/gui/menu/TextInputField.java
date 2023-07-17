package gui.menu;

import connector.Connector;
import gui.Window;

import logic.Player;
import logic.Sound;
import logic.exceptions.PlayerMaxException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class TextInputField extends JTextField implements FocusListener, MouseListener, ActionListener {
    private final Connector connector;
    private boolean isEmpty, onError;
    private final String defaultText = "Username...";

    public TextInputField() {
        super(20);
        connector = Connector.instance();
        setEmpty(true);
        setOnError(false);
        setLayout(new BorderLayout());

        defaultState();
        addFocusListener(this);
        addMouseListener(this);
        addActionListener(this);
        JButton btnAdd = new JButton("+");

        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBackground(new Color(51, 204, 51));
        btnAdd.setFont(Window.getPrimaryFont(20));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addMouseListener(this);
        btnAdd.addActionListener(this);

        add(btnAdd, BorderLayout.EAST);
    }

    public void defaultState() {
        setFont(Window.getPrimaryFont(20));
        setText(defaultText);
        setForeground(Color.lightGray);
        setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
    }

    public void activeState() {
        setFont(Window.getPrimaryFont(20));
        setText("");
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Window.getPrimaryColor(), 2));
    }

    public void errorState() {
        setOnError(true);
        setBorder(BorderFactory.createLineBorder(Color.red, 1));
        setFont(Window.getPrimaryFont(12));
        setForeground(Color.red);
        setText("At least One Letter or not Default!");
    }

    public boolean isEmpty() {
        return (getText().isEmpty() || getText().equals(defaultText)) && isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isOnError() {
        return onError;
    }

    public void setOnError(boolean onError) {
        this.onError = onError;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (isEmpty()) {
            defaultState();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isEmpty() || isOnError()) {
            activeState();
            setOnError(false);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getText().equals(defaultText) && getText().length() >= 1) {
            try {
                Sound.playAdd();
                connector.addPlayer(new Player(getText(), new PlayerVisual()));
            } catch (PlayerMaxException exception) {
                JOptionPane.showMessageDialog(this, "Maximale Anzahl an Spielern erreicht!");
            }

            defaultState();
        } else {
            errorState();
        }

    }


}
