import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

public class GraphicsPanel extends JPanel implements MouseListener {
    private boolean[][] clicked;
    private int[][] board;
    private int rows;
    private int cols;
    private int mines;

    public GraphicsPanel() {
        clicked = new boolean[9][9];
        board = new int[9][9];
        rows = board.length;
        cols = board[0].length;
        mines = 10;
        initializeBoard();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 38;
        int y = 38;
        /*
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                Image a = readBoardImage(i, j);
                g.drawImage(a, x, y, null);
                x += 76;
            }
            x = 38;
            y += 76;
        }
        y = 38;
        */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (!clicked[i][j]) {
                    Image a = readImage("blank");
                    g.drawImage(a, x, y, null);
                    x += 76;
                }
            }
            x = 38;
            y += 76;
        }
    }

    //-1 = 0 adjacent mines
    //1-8 = 1-8 adjacent mines
    //9 = mine
    public void initializeBoard() {
        int rows = board.length;
        int cols = board[0].length;
        while (mines > 0) {
            int rRow = (int) (Math.random() * rows);
            int rCol = (int) (Math.random() * cols);
            if (board[rRow][rCol] == 0) {
                board[rRow][rCol] = 9;
                mines--;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] == 0) {
                    int count = 0;
                    for (int k = i-1; k < i+2; k++) {
                        for (int l = j-1; l < j+2; l++) {
                            if (((k>=0 && k<rows) && (l>=0 && l<cols)) && board[k][l] == 9) {
                                count++;
                            }
                        }
                    }
                    board[i][j] = count;
                }
                clicked[i][j] = false;
            }
        }
    }

    public BufferedImage readImage(String name) {
        try {
            BufferedImage image;
            image = ImageIO.read(new File("images/"+name+".png"));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public BufferedImage readBoardImage(int r, int c) {
        try {
            BufferedImage image;
            image = ImageIO.read(new File("images/"+board[r][c]+".png"));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}