import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

public class GraphicsPanel extends JPanel implements MouseListener {
    private boolean[][] flagged;
    private boolean[][] clicked;
    private int[][] board;
    private boolean lose;
    private boolean win;
    private int rows;
    private int cols;
    private int mines;
    private int imgSize;

    public GraphicsPanel(int num) {
        setVars(num);
        flagged = new boolean[rows][cols];
        clicked = new boolean[rows][cols];
        board = new int[rows][cols];
        initializeBoard();
        this.addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 38;
        int y = 88;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!clicked[i][j]) {
                    Image a = readImage("blank");
                    if (flagged[i][j]) {
                        a = readImage("flag");
                    }
                    g.drawImage(a, x, y, null);
                } else {
                    Image a = readBoardImage(i, j);
                    g.drawImage(a, x, y, null);
                }
                x += imgSize;
            }
            x = 38;
            y += imgSize;
        }

        if (win) {
            g.setColor(Color.magenta);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 72));
            g.drawString("you win :)", 240, 60);
        }

        if (lose) {
            g.setColor(Color.red);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 72));
            g.drawString("you lose :(", 240, 60);
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

    public BufferedImage readImage(String name) {
        try {
            BufferedImage image;
            if (imgSize != 76) {
                image = ImageIO.read(new File("images/"+name+"b.png"));
            } else {
                image = ImageIO.read(new File("images/"+name+".png"));
            }
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

    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        int r = ((int) click.getY() - 88)/imgSize;
        int c = ((int) click.getX() - 38)/imgSize;
        if (boardCheck(r, c)) {
            if (e.getButton() == 1) {
                clicked[r][c] = true;
                if (board[r][c] == 0) {
                    clearZeros(r, c);
                } else if (board[r][c] == 9) {
                    lose = true;
                    MainFrame.end();
                }
            }
            if (e.getButton() == 3) {
                flagged[r][c] = !flagged[r][c];
            }
        }
        winCheck();
    }

    public void clearZeros(int r, int c) {
        ArrayList<Integer> zeroListR = new ArrayList<>();
        ArrayList<Integer> zeroListC = new ArrayList<>();
        zeroListR.add(r);
        zeroListC.add(c);
        for (int n = 0; n < zeroListR.size(); n++) {
            int i = zeroListR.get(n);
            int j = zeroListC.get(n);
            for (int k = i-1; k < i+2; k++) {
                for (int l = j-1; l < j+2; l++) {
                    if (boardCheck(k, l) && !clicked[k][l]) {
                        clicked[k][l] = true;
                        if (board[k][l] == 0) {
                            zeroListR.add(k);
                            zeroListC.add(l);
                        }
                    }
                }
            }
        }
    }

    public boolean boardCheck(int r, int c) {
        return (r>=0 && r<rows) && (c>=0 && c<cols);
    }

    public void winCheck() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!clicked[i][j] && (board[i][j] != 9 || !flagged[i][j])) {
                    return;
                }
            }
        }
        win = true;
        MainFrame.end();
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

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}