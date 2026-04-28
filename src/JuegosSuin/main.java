package JuegosSuin;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame fJuegoSuin = new FJuegosSuin("Juegos Swing");
        fJuegoSuin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fJuegoSuin.setVisible(true);
    }
}
