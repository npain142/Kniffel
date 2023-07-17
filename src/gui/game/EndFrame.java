package gui.game;

import connector.Connector;
import gui.Window;
import logic.Player;
import logic.Sound;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Diese Klasse erscheint nachdem das Spiel fertig ist und
 * zeigt alle Spielbloecke.
 */
public class EndFrame extends JPanel {
    ArrayList<Player> players;
    Player currenPlayer;
    Connector connector;
    FinishBlock currentFinBlock;
    Controller currentController;
    Player winner;
    int index = 0;
    public EndFrame(ArrayList<Player> players) {
        this.players = players;
        connector = Connector.instance();
        setSize(Window.getWindowDimensions());
        setLayout(new GridLayout());
        nextPlayer();
    }
    public void nextPlayer() {
        if (index == players.size()) {
            Sound.playError();
            new WinnerFrame(players);
        }
        if (index == players.size() - 1) {
            Sound.playKeyType();
            removeAll();
            currentFinBlock = new FinishBlock(players.get(index++));
            currentController = new Controller();
            currentController.ending();
            add(currentFinBlock);
            add(currentController);
            revalidate();
        }
        if (index < players.size()) {
            Sound.playKeyType();
            removeAll();
            currentFinBlock = new FinishBlock(players.get(index++));
            currentController = new Controller();
            add(currentFinBlock);
            add(currentController);
            revalidate();
        }
    }
    /**
     * Der Finishblock ist die grafische Darstellung des Spielblocks
     * am Ende eines Spiels. Außerdem bietet er eine Funktion zum Speichern
     * der Blocks als .txt-Datei.
     */
    class FinishBlock extends JPanel{

        GameBlockA gbA;
        GameBlockB gbB;
        Player player;
        public FinishBlock(Player player) {
            currenPlayer = player;
            setPlayer(player);
            setLayout(new GridLayout(0,2));
            gbA = new GameBlockA(player);
            gbB = new GameBlockB(player);

            for (Component c : gbA.getComponents()) {
                if (c instanceof GameBlockRow gr) {
                    gr.getResultPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    gr.removeListeners();
                    gr.configFinalResult(player);
                }

            }
            for (Component c : gbB.getComponents()) {
                if (c instanceof GameBlockRow gr) {
                    gr.getResultPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    gr.removeListeners();
                    gr.configFinalResult(player);
                }
            }

            add(gbA);
            add(gbB);
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        /**
         * Speichert den aktuellen Spielblock als .txt-Datei in einem
         * con Nutzer definierten Ordner.
         */
        public void saveFile() {
            String str, str2, tmp, tmp2;
            StringBuilder stringbuilder = new StringBuilder("SpielBlock von " + player.getName() + " - gespielt am " + LocalDate.now() + "\n\n");
            File file = new File("assets\\block_template.txt");
            String filename = file.getPath();
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
                while ((str = reader.readLine()) != null) {
                    str2 = "";
                    for (int i = 0; i < str.length(); i++) {
                            tmp = str.toUpperCase();

                            switch (tmp.charAt(i)) {
                                case '1' :
                                case '2' :
                                case '3' :
                                case '4' :
                                case '5' :
                                case '6' :
                                    str2 = "" + player.getPlayBlock().getPointsOf(""+tmp.charAt(i));

                            }
                            tmp2 = tmp.replaceAll("\\s+","");
                         if (tmp2.length() > i + 11) {
                             switch (tmp2.substring(i, i + 11)) {
                                 case "DREIERPASCH":
                                 case "VIERERPASCH":
                                     str2 = "" + player.getPlayBlock().getPointsOf(tmp2.substring(i, i +11));
                             }
                         }
                        if (tmp.length() > i + 14) {
                            switch (tmp.substring(i, i + 14)) {
                                case "KLEINE STRASSE":
                                case "GROSSE STRASSE":
                                    str2 = "" + player.getPlayBlock().getPointsOf(tmp.substring(i, i +14));
                            }
                        }
                        if (tmp.length() > i + 6) {
                            if (tmp.startsWith("CHANCE", i)) {
                                str2 = "" + player.getPlayBlock().getPointsOf(tmp.substring(i, i + 6));
                            }
                        }
                        if (tmp.length() > i + 10) {
                            if (tmp.startsWith("FULL-HOUSE", i)) {
                                str2 = "" + player.getPlayBlock().getPointsOf(tmp.substring(i, i + 10));
                            }
                        }

                        if (tmp.length() > i + 5) {
                            if (tmp.startsWith("BONUS", i)) {
                                str2 = "" + player.getPlayBlock().getBonus();
                            } else if (tmp.startsWith("PUNKTE", i)) {
                                str2 = "" + player.getPlayBlock().getSummeA();
                            } else if (tmp.startsWith("GESAMT", i)) {
                                str2 = "" + player.getPlayBlock().getSummeGes();
                            }
                        }
                        if (tmp.length() > i + 7) {
                            if (tmp.startsWith("KNIFFEL", i)) {
                                str2 = "" + player.getPlayBlock().getPointsOf(tmp.substring(i, i + 7));
                            } else if (tmp.startsWith("GESAMT A", i)) {
                                str2 = "" + player.getPlayBlock().getSummeABonus();
                            } else if (tmp.startsWith("GESAMT B", i)) {
                                str2 = "" + player.getPlayBlock().getSummeB();
                            }
                        }

                    }
                    stringbuilder.append("   ").append(str).append(str2).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String f = "Kniffel_" + LocalDate.now() + "_" + player.getName() + ".txt";
            JFileChooser jf = new JFileChooser();
            jf.setSelectedFile(new File(f));
            jf.showSaveDialog(null);
            File writerFile = new File(jf.getCurrentDirectory() + "\\" +jf.getSelectedFile().getName());
            try (PrintWriter writer = new PrintWriter(writerFile)) {
                for (int i= 0; i < stringbuilder.length(); i++) {
                    writer.print(stringbuilder.charAt(i));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Controller beschreibt die die "kontroll-einheit"
     * auf dem Endscreen, um Spieler zu wechseln oder
     * eine Datei zu speichern.
     */
    class Controller extends JPanel {
        JButton btnNext;
        JButton btnSave;
        public Controller() {
            setLayout(new GridLayout());
            btnNext = new JButton("Nächster");
            btnNext.addActionListener(e -> nextPlayer());
            btnNext.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnSave = new JButton("Speichern");
            btnSave.addActionListener(e ->
            {
                Sound.playTap();
                currentFinBlock.saveFile();
            });
            btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnNext.setPreferredSize(new Dimension(100, 50));
            btnSave.setPreferredSize(new Dimension(100, 50));
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel1.add(new JLabel("PLAYER: " + currenPlayer.getName()));
            panel2.add(btnSave);
            panel3.add(btnNext);

            add(panel1);
            add(panel2);
            add(panel3);

        }
        public void ending() {
            btnNext.setText("Beenden");
            revalidate();
        }
    }

    public class WinnerFrame {
        public WinnerFrame(ArrayList<Player> player) {
            Player winner = getWinner(player);
            JOptionPane.showMessageDialog(null, "WINNER: " + winner.getName());
            connector.startApplication();
        }

        public Player getWinner(ArrayList<Player> player) {
            Player winner = player.get(0);
            for (int i = 1; i < player.size(); i++) {
                if (player.get(i).getPlayBlock().getSummeGes() > winner.getPlayBlock().getSummeGes()) {
                    winner = player.get(i);
                }
            }
            return winner;
        }

    }
}
