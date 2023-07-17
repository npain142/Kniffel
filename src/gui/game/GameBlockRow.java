package gui.game;

import gui.Window;
import logic.Block;
import logic.Player;
import connector.Connector;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GameBlockRow ist die Grafische Darstellung einer Zeile
 * im Spielblock. Jede Zeile kann individuell initalisiert werden.
 *
 */
class GameBlockRow extends JPanel implements MouseListener, MouseMotionListener, PropertyChangeListener {
        Connector connector;
        Block currentBlock;
        Block.DiceVariation currentDiceVar;
        JPanel result;
        JLabel title;
        JLabel textResult = new JLabel("");
        public String type;
        public GameBlockRow(String type, Player player) {
            connector = Connector.instance();
            connector.addPropertyChangeListener(this);
            setType(type);
            if (player != null)
                config(player);

            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            setBackground(Color.WHITE);


            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.HORIZONTAL;

            title = new JLabel(type);
            title.setFont(Window.getPrimaryFont(15));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            title.setPreferredSize(new Dimension(50, 50));
            add(title, c);


            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            result = new JPanel();
            result.setLayout(new BorderLayout());
            result.setPreferredSize(new Dimension(50, 50));
            result.setBackground(new Color(0, 0, 0, 0));
            result.setCursor(new Cursor(Cursor.HAND_CURSOR));
            result.addMouseListener(this);
            textResult.setHorizontalAlignment(SwingConstants.CENTER);
            result.add(textResult, BorderLayout.CENTER);
            add(result, c);

            if (
                    getType().equals("Gesamt") || getType().equals("Bonus") || getType().equals("<html><span>Gesamt<br>Oberer Teil</span></html>") ||
                            getType().equals("<html><span>Gesamt<br>Unterer Teil</span></html>") || getType().equals("Endsumme")
            ) {
                removeListeners();
            }
        }

        public void removeListeners() {

            this.result.removeMouseListener(this);
            this.result.removeMouseMotionListener(this);

        }

        public void setCurrentBlock(Block currentBlock) {
            this.currentBlock = currentBlock;
        }

        public void config(Player player) {
            setCurrentBlock(player.getPlayBlock());
            for (Block.DiceVariation d : currentBlock.getBlockTotal().keySet()) {
                if (d.toString().equals(this.getType())) {
                    currentDiceVar = d;
                    if (currentBlock.getBlockTotal().get(d)) {
                        setText(String.valueOf(d.getPoints()));
                    } else {
                        setText("");
                    }
                }
            }
            repaint();
        }

        public void configFinalResult(Player player) {

            switch (this.getType()) {
                case "Gesamt" -> setText(String.valueOf(player.getPlayBlock().getSummeA()));
                case "Bonus" -> setText(String.valueOf(player.getPlayBlock().getBonus()));
                case "<html><span>Gesamt<br>Oberer Teil</span></html>" ->
                        setText(String.valueOf(player.getPlayBlock().getSummeABonus()));
                case "<html><span>Gesamt<br>Unterer Teil</span></html>" ->
                        setText(String.valueOf(player.getPlayBlock().getSummeB()));
                case "Endsumme" -> setText(String.valueOf(player.getPlayBlock().getSummeGes()));
            }
            repaint();
        }


        public void setPoints(int points) {
            connector.writePlayerPoints(points, getType());
            textResult.setText(String.valueOf(points));

            repaint();
        }

        public void setText(String s){
            textResult.setText(s);

        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public JPanel getResultPanel() {
            return result;
        }


        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("TABLE_TURN")) {
                config((Player) evt.getNewValue());
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            if (!currentBlock.getBlockTotal().get(currentDiceVar) && connector.getCurrentPlayer().getCurrentPlay().isReadyToWritePoints()) {
                Sound.playAdd();
                setPoints(connector.getCurrentPlayer().getCurrentPlay().previewPoints(getType()));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!currentBlock.getBlockTotal().get(currentDiceVar) && connector.getCurrentPlayer().getCurrentPlay().isReadyToWritePoints()) {
                Sound.playTap();
                int prev = connector.getCurrentPlayer().getCurrentPlay().previewPoints(getType());
                setText(String.valueOf(prev));

                repaint();
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!currentBlock.getBlockTotal().get(currentDiceVar) && connector.getCurrentPlayer().getCurrentPlay().isReadyToWritePoints()) {
                setText("");
                repaint();
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }



















