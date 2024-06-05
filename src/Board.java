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

public class Board extends JPanel {
    private boolean[][] flagged;
    private boolean[][] clicked;
    private int[][] board;
    private boolean lose;
    private boolean win;
    private int rows;
    private int cols;
    private int mines;
    private int imgSize;

    public Board(int num) {
        setVars(num);
        flagged = new boolean[rows][cols];
        clicked = new boolean[rows][cols];
        board = new int[rows][cols];
        initializeBoard();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 38;
        int y = 88;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Image a = readBoardImage(i, j);
                g.drawImage(a, x, y, null);
                x += imgSize;
            }
            x = 38;
            y += imgSize;
        }
        y = 88;
    }

    public void setVars (int num) {
        if (num == 1) {
            rows = 9;
            cols = 9;
            mines = 10;
            imgSize = 76;
        } else if (num == 2) {
            rows = 16;
            cols = 16;
            mines = 40;
            imgSize = 38;
        } else {
            rows = 16;
            cols = 30;
            mines = 99;
            imgSize = 38;
        }
    }

    public BufferedImage readBoardImage(int r, int c) {
        try {
            BufferedImage image;
            if (imgSize != 76) {
                image = ImageIO.read(new File("images/"+board[r][c]+"b.png"));
            } else {
                image = ImageIO.read(new File("images/"+board[r][c]+".png"));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

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
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    int count = 0;
                    for (int k = i-1; k < i+2; k++) {
                        for (int l = j-1; l < j+2; l++) {
                            if (boardCheck(k, l) && board[k][l] == 9) {
                                count++;
                            }
                        }
                    }
                    board[i][j] = count;
                }
            }
        }
    }

    public boolean boardCheck(int r, int c) {
        return (r>=0 && r<rows) && (c>=0 && c<cols);
    }
}
