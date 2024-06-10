import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MainFrame m = new MainFrame(1);
    }
}

        /*
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("What type of game would you like to play?");
        System.out.print("Beginner (1), Intermediate (2), or Expert (3)? ");
        int num = s.nextInt();
        if (num < 1 || num > 3) {
            num = 1;
        }
        //to fix code, just set num to always equal 1
        MainFrame m = new MainFrame(num);
        */