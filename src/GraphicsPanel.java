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
    private int[][] board;
    private int rows;
    private int cols;
    private int mines;

    public GraphicsPanel() {
        board = new int[9][9];
        rows = board.length;
        cols = board[0].length;
        mines = 10;
        initializeBoard();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 16;
        int y = 16;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                //find file to display here!
            }
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
                        for (int l = j-1; l < i+2; l++) {
                            if (((k>=0 && k<board.length) && (l>=0 && l<board[0].length)) && board[k][l] == 9) {
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        count--;
                    }
                    board[i][j] = count;
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}