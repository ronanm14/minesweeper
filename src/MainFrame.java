import javax.swing.*;

public class MainFrame implements Runnable {
    private GraphicsPanel p;

    public MainFrame () {
        JFrame f = new JFrame("Minesweeper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(760,792);
        f.setLocation(500,500);

        p = new GraphicsPanel();
        f.add(p);

        f.setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            p.repaint();
        }
    }
}