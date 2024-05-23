import javax.swing.*;

public class MainFrame implements Runnable {
    private GraphicsPanel p;
    public static boolean endGame = false;

    public MainFrame () {
        JFrame f = new JFrame("Minesweeper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(760,792);
        f.setLocation(400,100);

        p = new GraphicsPanel();
        f.add(p);

        f.setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    public static void lose() {
        endGame = true;
    }

    public void run() {
        while (!endGame) {
            p.repaint();
        }
        p.repaint();
    }
}