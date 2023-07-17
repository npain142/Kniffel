package gui.game;


import connector.Connector;
import gui.Window;
import logic.Rules;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.Timer;

/**
 * Die Klasse ist neben dem GameBlock die wichtigste
 * grafische Komponente im Spiel. Sie beinhaltet die Animationen der Wuerfel
 * und das Auswaehlen einzelner Wuerfel.
 */
public class MainFrame extends JPanel implements MouseListener, MouseMotionListener, PropertyChangeListener {
    Dimension size = new Dimension((int) Window.getWindowDimensions().getWidth() * 3 / 4, ( int) Window.getWindowDimensions().getHeight());
    private static Connector connector;
    Random randomDice = new Random();
    ChoosePanel choosePanel;
    boolean drawingDice;
    private static Dice[] dice = new Dice[5];
    private final static ArrayList<Dice> diceList = new ArrayList<>();
    Path2D btnRoll;
    Path2D btnFinish;
    Animations animations;
    public MainFrame() {
        connector = Connector.instance();
        connector.addPropertyChangeListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setLayout(new BorderLayout());
        setPreferredSize((new Dimension(size)));
        setBackground(connector.getCurrentPlayer().getSecondaryTheme());
        choosePanel = new ChoosePanel();
        btnFinish = new Btn().btnFinish();
        btnRoll = new Btn().btnRoll();
        add(choosePanel, BorderLayout.SOUTH);
        configNewRound();
    }

    public void configNewRound() {
        diceList.clear();
        dice = new Dice[5];
        for (int i = 0; i < dice.length; i++) {
            Dice d = new Dice(randomDice.nextInt(1, 7));
            dice[i] = d;
            diceList.add(d);
        }
        choosePanel.configNewRound();
        animations = new Animations(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawButtonRoll(g2d);
        drawDice(g2d);
        drawButtonFinish(g2d);
    }

    public void drawButtonRoll(Graphics2D g2d) {
        if (connector.getCurrentPlayer().getCurrentPlay().isReadyToRoll() && !connector.getCurrentPlayer().getCurrentPlay().isReadyToPass() && diceList.size() > 0) {
            g2d.setColor(Color.GREEN);
            g2d.fill(btnRoll);
            g2d.setColor(Color.BLACK);
            g2d.setFont(Window.getPrimaryFont(15));
            g2d.drawString("Roll", (int) (btnRoll.getBounds().getX() + btnRoll.getBounds().getWidth() / 2) - 15, (int) (btnRoll.getBounds().getY() + btnRoll.getBounds().getHeight() / 2) + 7);
        }

    }

    public void drawButtonFinish(Graphics2D g2d) {
        if (connector.getCurrentPlayer().getCurrentPlay().isReadyToPass()) {
            g2d.setColor(Color.red);
            g2d.fill(btnFinish);
            g2d.setColor(Color.WHITE);
            g2d.setFont(Window.getPrimaryFont(15));
            g2d.drawString("FINISH >", (int) (btnFinish.getBounds().getX() + btnFinish.getBounds().getWidth() / 2) - 30, (int) (btnFinish.getBounds().getY() + btnFinish.getBounds().getHeight() / 2) + 7);
        }
    }

    public void drawDice(Graphics2D g2d) {
        if (getDiceList().size() > 0 && connector.getCurrentPlayer().getCurrentPlay().getThrowsLeft() != 3) {
            try {
                for (Dice dice : diceList) {
                    drawingDice = true;
                    g2d.setColor(Color.white);
                    g2d.fill(dice);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(dice);
                    for (Area eye : dice.getVisualEyes()) {
                        g2d.fill(eye);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            drawingDice = false;
        }
    }

    public static ArrayList<Dice> getDiceList() {
        return diceList;
    }

    public void addDice(Dice dice) {
        diceList.add(dice);
        repaint();
    }

    public static  Dice[] getDice() {
        return dice;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (btnRoll.contains(e.getX(), e.getY()) && !Animations.isIsAnimatingThrow() && connector.getCurrentPlayer().getCurrentPlay().isReadyToRoll()) {

            Sound.playRoll();
            connector.getCurrentPlayer().throwDice();
            animations.animateThrow();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    for (Dice d : getDiceList()) {
                        d.throwDiceForAnimation();
                    }
                    if (!Animations.isIsAnimatingThrow()) {
                        timer.cancel();
                        repaint();
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 10);
        }
        if (!Animations.isIsAnimatingThrow() && !connector.getCurrentPlayer().getCurrentPlay().isReadyToPass() && connector.getCurrentPlayer().getCurrentPlay().getThrowsLeft() != 3) {
            Dice toRemove = null;
            for (Dice dice : diceList) {
                if (dice.contains(e.getX(), e.getY())) {
                    Sound.playChoose();
                    toRemove = dice;
                    choosePanel.addChoosedDice(dice);
                }
            }
            diceList.remove(toRemove);
        }
        if (diceList.size() == 0) {
            connector.getCurrentPlayer().getCurrentPlay().setReadyToWritePoints(true);
        }
        if (btnFinish.contains(e.getX(), e.getY()) && connector.getCurrentPlayer().getCurrentPlay().isReadyToPass()) {
            Sound.playKeyType();
            connector.turnTable();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
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
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean readyToRoll = connector.getCurrentPlayer().getCurrentPlay().isReadyToRoll();
        boolean readyToPass = connector.getCurrentPlayer().getCurrentPlay().isReadyToPass();
        if ((btnRoll.contains(e.getX(), e.getY()) && readyToRoll) || (btnFinish.contains(e.getX(), e.getY()) && readyToPass)) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            return;
        }
        for (Dice d : getDiceList()) {
            if (d.contains(e.getX(), e.getY()) && readyToRoll && connector.getCurrentPlayer().getCurrentPlay().getThrowsLeft() != 3) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                return;
            }
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("WRITE_POINTS")) {
            repaint();
        } else if (evt.getPropertyName().equals("TABLE_TURN")) {
            setBackground(connector.getCurrentPlayer().getSecondaryTheme());
            configNewRound();
        }
    }

    /**
     * Das ChoosePanel ist die untere Haelfte des Mainframes.
     * Es zeigt die ausgewaehlten Würfel.
     */
    class ChoosePanel extends JPanel  {
        ArrayList<Dice> choosed = new ArrayList<>();
        private DiceHolder dh = null;
        public ChoosePanel() {
            setLayout(new FlowLayout());
            setPreferredSize(new Dimension((int) Window.getWindowDimensions().getWidth() * 3/4, 150));
            setBackground(connector.getCurrentPlayer().getTheme());
        }
        public void configNewRound() {
            choosed.clear();
            if (dh != null) {
                dh.configNewRound();
            }
            setBackground(connector.getCurrentPlayer().getTheme());
            removeAll();
            repaint();
            revalidate();
        }
        public void removeDiceHolder(DiceHolder dh) {
            choosed.remove(dh.originalDice);
            connector.getCurrentPlayer().getCurrentPlay().removeChoosedDice(dh.newDice.getNumOfEyes());
            remove(dh);
            revalidate();
            repaint();
        }
        public void addChoosedDice(Dice dice) {
            dh = new DiceHolder(dice);
            choosed.add(dice);
            add(dh);
            connector.getCurrentPlayer().getCurrentPlay().chooseDice(dice.getNumOfEyes());
            sort();
            revalidate();
        }
        public void sort() {
            ArrayList<DiceHolder> dice = new ArrayList<>();
            Component[] comps = getComponents();

            for (Component d : comps) {
                DiceHolder holder = (DiceHolder)d;
                dice.add(holder);
            }
            removeAll();
            for (int i = 1; i <= 6; i++) {
                for (DiceHolder d : dice) {
                    if (d.originalDice.getNumOfEyes() == i) {
                        add(d);
                    }
                }
            }

        }

        /**
         * DiceHolder ist nur eine Komponente des ChoosePanels.
         */
        public class DiceHolder extends JPanel implements MouseListener {
            Dice originalDice;
            Dice newDice;
            public DiceHolder(Dice originalDice) {
                addMouseListener(this);
                setPreferredSize(new Dimension(100, 100));
                this.originalDice = originalDice;
                this.newDice = new Dice(originalDice.getNumOfEyes());
                setBackground(Color.WHITE);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void configNewRound() {
                removeDiceHolder(this);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.BLACK);
                g2d.draw(newDice);
                for (Area eye : newDice.getVisualEyes()) {
                    g2d.fill(eye);
                }

            }
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (newDice.contains(e.getX(), e.getY()) && !connector.getCurrentPlayer().getCurrentPlay().isNoThrowsLeft() && !connector.getCurrentPlayer().getCurrentPlay().isReadyToPass()) {
                    Sound.playError();
                    addDice(originalDice);
                    removeDiceHolder(this);
                    repaint();
                }
                if (choosed.size() < 5) {
                    connector.getCurrentPlayer().getCurrentPlay().setReadyToWritePoints(false);
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
    }

    class Btn extends Path2D.Double{

        int xHalf = (int) (size.getWidth() / 2) - 25 - Window.getInset("HORIZONTAL");
        int yHalf = (int) (size.getHeight() /2) - 25 - Window.getInset("VERTICAL") - (int) choosePanel.getPreferredSize().getHeight()/2;
        int xCorner = (int) (size.getWidth() * 12/15) - 25 - Window.getInset("HORIZONTAL");
        int yCorner = (int) (size.getHeight() * 13/15) - 25 - Window.getInset("VERTICAL") - (int) choosePanel.getPreferredSize().getHeight();
        public Path2D btnRoll() {
            Path2D p2d = new Path2D.Double();
            p2d.moveTo(xHalf,yHalf);
            p2d.lineTo(xHalf+ 50, yHalf );
            p2d.lineTo(xHalf+ 50, yHalf +50);
            p2d.lineTo(xHalf, yHalf +50);
            p2d.lineTo(xHalf, yHalf );
            p2d.closePath();
            return p2d;
        }

        public Path2D btnFinish() {
            Path2D p2d = new Path2D.Double();
            p2d.moveTo(xCorner, yCorner);
            p2d.lineTo(xCorner + 100, yCorner);
            p2d.lineTo(xCorner + 100, yCorner + 50);
            p2d.lineTo(xCorner, yCorner + 50);
            p2d.lineTo(xCorner, yCorner);
            p2d.closePath();
            return p2d;
        }

    }
}
