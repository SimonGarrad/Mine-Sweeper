package MineSweeper;
import java.util.*;
import static java.lang.System.out;
public class mineSweeper{
    public static void main (String [] args){
        Scanner scan = new Scanner(System.in);
        Random random = new Random();
        int size = 0;
        int mines = 0;
        boolean go = true;
        boolean win = false;

        //runs game
        while (go){
            while(true){
                //difficulty settings
                out.println("Please choose a difficult setting: \neasy \nmedium \nhard\n");
                String difficulty = scan.nextLine();

                if (difficulty.equalsIgnoreCase("easy")){
                    size = 11;
                    mines = 10;
                    out.println("You have selected easy difficulty. Good luck!\n");
                    break;
                }else if(difficulty.equalsIgnoreCase("medium")){
                    size = 17;
                    mines = 40;
                    out.println("You have selected medium difficulty. Good luck!\n");
                    break;
                }else if(difficulty.equalsIgnoreCase("hard")){
                    size = 23;
                    mines = 99;
                    out.println("You have selected hard difficulty. Good luck,  you'll need it!\n");
                    break;
                }else{
                    out.println("\n*** Not a valid difficulty level.***\n");
                }
            }
            //sets the 2D array that will hold the mines
            String[][] mineSweeper = new String[size][size];
            //sets the 2D array that the player will interact with
            String[][] playerArray = new String[size][size];

            for (int i = 0; i < size; i++){
                out.println("");
                for (int j = 0; j < size; j++){
                    if (i == 0 && j != 0){
                        String num = Integer.toString(j);
                        if(j <10){
                            mineSweeper[i][j] = "[" + num + " ]";
                            playerArray[i][j] = "[" + num + " ]";
                        } else if (j>=10){
                            mineSweeper[i][j] = "[" + num + "]";
                            playerArray[i][j] = "[" + num + "]";
                        }
                    }else if (i != 0 && j != 0){
                        mineSweeper[i][j] = "[  ]";
                        playerArray[i][j] = "[  ]";
                    }
                    if (j == 0 && i != 0){
                        String num = Integer.toString(i);
                        if (i<10){
                            mineSweeper[i][j] = "[" + num + " ]\t";
                            playerArray[i][j] = "[" + num + " ]\t";
                        }else if (i>=10){
                            mineSweeper[i][j] = "[" + num + "]\t";
                            playerArray[i][j] = "[" + num + "]\t";
                        }
                    }
                    if (i == 0 && j == 0){
                        mineSweeper[i][j] = "   \t";
                        playerArray[i][j] = "   \t";
                    }
                    out.print(" ");
                    out.print(playerArray[i][j]);
                }
            }
            for (int k = 0; k < mines; k++) {
                int width, height;
                do {
                    width = random.nextInt(1, size);
                    height = random.nextInt(1, size);
                } while (mineSweeper[width][height].equals("[@ ]"));
                mineSweeper[width][height] = "[@ ]";
            }
            

            for(int i = 1; i < size-1; i++){
                for(int j = 1; j< size-1; j++){
                     if (mineSweeper[i][j].equals("[@ ]")){
                        numbers(mineSweeper, size);
                    }
                }
            }

            // // prints array showing mine placements. ******************************  for testing only!
            // out.println("\n");
            // printer(mineSweeper, size);
            
            long startTime = System.nanoTime();

            while (true){
                int row = 0;
                int column = 0;
                String action = "";

                while (true) {
                    try {
                        System.out.println("\nenter a row to check (1 to " + (size - 1) + "):");
                        row = Integer.parseInt(scan.nextLine());
                        if (row >= 1 && row < size) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a number between 1 and " + (size - 1) + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                while (true) {
                    try {
                        System.out.println("enter a column to check (1 to " + (size - 1) + "):");
                        column = Integer.parseInt(scan.nextLine());
                        if (column >= 1 && column < size) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a number between 1 and " + (size - 1) + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                while(true) {
                    out.println("place a flag 'f' or select that square 's'?");
                    action = scan.nextLine();
                    if(action.equals("f") || action.equals("s")){
                        break;
                    }else out.println("\nInvalid input\n");;
                }

                if (action.equals("f")){
                    playerArray[row][column] = "[f ]";
                    printer(playerArray, size);
                }else if (action.equals("s")){
                    turn(mineSweeper, playerArray, row, column, size, scan);
                    if (mineSweeper[row][column].equals("[@ ]")) {
                        win = false;
                        endGame(mineSweeper, playerArray, row, column, size, win);
                        long endTime = System.nanoTime();
                        out.println("\nGame Over!");
                        timer(startTime, endTime);
                        String again = "";
                        while (true){
                            out.println("Would you like to play again? y/n?");
                            again = scan.nextLine();
                            if(again.equals("y") || again.equals("n")){
                                break;
                            }else out.println("\nInvalid input\n");
                        }
                        if(again.equals("y")){
                            out.println("restarting");
                            break;
                        }else if (again.equals("n")){
                            go = false;
                            out.println("Thank you for playing! Have a nice day!");
                            break;
                        }
                    }
                    
                    printer(playerArray, size);
                }

                win = checkWin(mineSweeper, playerArray, size, mines);
                if (win){
                    endGame(mineSweeper, playerArray, row, column, size, win);
                    long endTime = System.nanoTime();
                    timer(startTime, endTime);
                    out.println("\nYou win!");
                    String again2 = "";
                    while (true){
                        out.println("Would you like to play again? y/n?");
                        again2 = scan.nextLine();
                        if(again2.equals("y") || again2.equals("n")){
                            break;
                        }else out.println("\nInvalid input\n");
                    }
                    if(again2.equals("y")){
                        out.println("restarting");
                        break;
                    }else if (again2.equals("n")){
                        go = false;
                        out.println("Thank you for playing! Have a nice day!");
                        break;
                    }
                }
            }
        }
        scan.close();
    }

    public static void printer(String[][] array, int size){
        for (int i = 0; i < size; i++){
            out.println("");
            for (int j = 0; j < size; j++){
                if(i==0 && j == (size)){
                    out.println("");
                }
                out.print(array[i][j]);
            }
        }
    }

    public static String[][] numbers(String[][] mines, int size) {
        int[][] increments = new int[size][size];
    
        for (int row = 1; row < size; row++) {
            for (int col = 1; col < size; col++) {
                if (mines[row][col].equals("[@ ]")) {
                    updateIncrements(increments, row, col, size);
                }
            }
        }
    
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (!mines[i][j].equals("[@ ]") && !mines[i][j].equals("[ ]")) {
                    if (increments[i][j] > 0) {
                        mines[i][j] = "[" + increments[i][j] + " ]";
                    }
                }
            }
        }
        return mines;
    }
    
    private static void updateIncrements(int[][] increments, int row, int col, int size) {
        for (int i = Math.max(row - 1, 1); i <= Math.min(row + 1, size - 1); i++) {
            for (int j = Math.max(col - 1, 1); j <= Math.min(col + 1, size - 1); j++) {
                increments[i][j]++;
            }
        }
    }

    private static void empty(String[][] mines, String[][] player, int row, int col, int size) {
        if (row < 1 || row >= size || col < 1 || col >= size) {
            return;
        }
    
        if (!mines[row][col].equals("[@ ]") && !mines[row][col].equals("[  ]")) {
            String num = "|" + mines[row][col].substring(1, 2) + " |";
            player[row][col] = num;
            return;
        }
    
        if (!mines[row][col].equals("[  ]") || player[row][col].equals("    ")) {
            return;
        }
    
        player[row][col] = "    ";
    
        empty(mines, player, row - 1, col, size);
        empty(mines, player, row + 1, col, size);
        empty(mines, player, row, col - 1, size);
        empty(mines, player, row, col + 1, size);
        empty(mines, player, row - 1, col - 1, size);
        empty(mines, player, row - 1, col + 1, size);
        empty(mines, player, row + 1, col - 1, size);
        empty(mines, player, row + 1, col + 1, size);
    }
    

    public static void endGame(String[][] mineSweeper, String[][] playerArray, int row, int col, int size, boolean win){
        out.println("\n");
        for(int i = 1; i < size; i++){
            for (int j = 1; j < size; j++){
                if(mineSweeper[i][j].equals("[@ ]")){
                    playerArray[i][j] = "[@ ]";
                }
            }
        }
        if (!win){
            playerArray[row][col] = "BOOM!";
            printer(playerArray, size);
        }else {
            printer(playerArray, size);
        }
    }
    

    public static void turn(String[][] mineSweeper, String[][] player, int row, int col, int size, Scanner scan) {
        if (player[row][col].equals("[f ]")) {
            while (true) {
                System.out.println("This square is flagged, are you sure? y/n?");
                String deflag = scan.nextLine();
                if (deflag.equals("y")) {
                    empty(mineSweeper, player, row, col, size);
                    break;
                } else if (deflag.equals("n")) {
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
        } else {
            empty(mineSweeper, player, row, col, size);
        }
    }
    
    

    public static boolean checkWin(String[][] mineSweeper, String[][] player, int size, int mines) {
        int correctFlags = 0;
        int nonMineCellsRevealed = 0;
        int totalNonMineCells = ((size - 1) * (size - 1)) - mines;
    
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (mineSweeper[i][j].equals("[@ ]")) {
                    if (player[i][j].equals("[f ]")) {
                        correctFlags++;
                    }
                } else {
                    // Check if the cell is a non-mine cell and it's revealed (not equal to "[  ]")
                    if (!player[i][j].equals("[  ]") && !player[i][j].equals("[f ]")) {
                        nonMineCellsRevealed++;
                    }
                }
            }
        }
    
        // Win condition: all non-mine cells are revealed and all mines are correctly flagged
        return (nonMineCellsRevealed == totalNonMineCells) && (correctFlags == mines);
    }
    

    public static void timer(long startTime, long endTime){
        long elapsedTime = endTime - startTime;

        // Optionally convert to seconds for more human-readable format
        double elapsedTimeInSecond = elapsedTime / 1_000_000_000;
        int mins = (int)elapsedTimeInSecond/60;
        int seconds = (int)elapsedTimeInSecond %60;

        System.out.println("\nYour time was: " + mins + " mins and " + seconds + " seconds");
    }
}
