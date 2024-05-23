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
    private boolean[][] flagged;
    private boolean[][] clicked;
    private int[][] board;
    private boolean lose;
    private int rows;
    private int cols;
    private int mines;

    public GraphicsPanel() {
        flagged = new boolean[9][9];
        clicked = new boolean[9][9];
        board = new int[9][9];
        rows = board.length;
        cols = board[0].length;
        mines = 10;
        initializeBoard();
        this.addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 38;
        int y = 38;

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

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (!clicked[i][j]) {
                    Image a = readImage("blank");
                    if (flagged[i][j]) {
                        a = readImage("flag");
                    }
                    g.drawImage(a, x, y, null);
                }
                x += 76;
            }
            x = 38;
            y += 76;
        }

        if (lose) {
            int num = (int) (Math.random()*6)+1;
            Image q = readImage("lose"+num);
            g.drawImage(q, 100, 100, null);
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

    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        int r = ((int) click.getY() - 38)/76;
        int c = ((int) click.getX() - 38)/76;
        if (boardCheck(r, c)) {
            if (e.getButton() == 1) {
                clicked[r][c] = true;
                if (board[r][c] == 0) {
                    clearZeros(r, c);
                } else if (board[r][c] == 9) {
                    lose = true;
                    MainFrame.lose();
                }
            }
            if (e.getButton() == 3) {
                flagged[r][c] = !flagged[r][c];
            }
        }
    }

    public void clearZeros(int r, int c) {
        //coords are represented by a table
        //to get coords, set row to num/9 and col to num%9
        ArrayList<Integer> zeroList = new ArrayList<>();
        zeroList.add(r*9 + c);
        for (int n = 0; n < zeroList.size(); n++) {
            int i = zeroList.get(n)/9;
            int j = zeroList.get(n)%9;
            for (int k = i-1; k < i+2; k++) {
                for (int l = j-1; l < j+2; l++) {
                    if (boardCheck(k, l) && (!clicked[k][l] /*&& board[k][l] == 0*/)) {
                        clicked[k][l] = true;
                        if (board[k][l] == 0) {
                            zeroList.add(k * 9 + l);
                        }
                    }
                }
            }
        }
        /*
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if ()
            }
        }

        /*
        ArrayList<Integer> zeroList2 = new ArrayList<>();
        boolean newZero = true;
        while (newZero) {
            newZero = false;
            for (Integer n : zeroList) {
                int i = n/9;
                int j = n%9;
                for (int k = i-1; k < i+2; k++) {
                    for (int l = j-1; l < j+2; l++) {
                        if (boardCheck(k, l) && (!clicked[k][l] && board[k][l] == 0)) {
                            clicked[k][l] = true;
                            zeroList2.add(k*9 + l);
                            newZero = true;
                        }
                    }
                }
            }
            zeroList = new ArrayList<>();
            for (Integer z : zeroList2) {
                zeroList.add(z);
            }
        }
        */
    }

    public boolean boardCheck(int r, int c) {
        return (r>=0 && r<rows) && (c>=0 && c<cols);
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}