import java.io.*;
import java.util.*;

public class Helper {
    static Scanner scan = new Scanner(System.in);
    static String input = null;
    static BufferedWriter bw = null;
    static File scoreFilepath = new File("highscores.ser");

    public static void menuScreen() {
        try {
            bw = new BufferedWriter(new FileWriter(Main.filepath));
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("         Division Game          ");
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("Welcome to Divison Game!        ");
            bw.newLine();
            bw.write("Choose a game mode to start playing");
            bw.newLine();
            bw.newLine();
            bw.write("(H) Help          (R) Reset save");
            bw.newLine();
            bw.write("(1) Classic       (2) Limited   ");
            bw.newLine();
            bw.write("(3) Blind         (4) Highest   ");
            bw.newLine();
            bw.write("(5) Highest+      (E) Exit      ");
            bw.newLine();
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void helpMenu() {
        try {
            bw = new BufferedWriter(new FileWriter(Main.filepath));
            bw.write("General rule: Pick a number from the options that perfectly divides the division number.");
            bw.newLine();
            bw.write("- Classic mode: Possible divisors are from 2 - 9, no restrictions.");
            bw.newLine();
            bw.write("- Limited mode: 2 and 4 are removed from possible divisors.");
            bw.newLine();
            bw.write("- Highest mode: The highest divisor of the division number must be picked.");
            bw.newLine();
            bw.write("- Highest+ mode: Highest mode with more numbers.");
            bw.newLine();
            bw.write("- Blind mode: Only the initial division number is shown. Every division number will be hidden after that.");
            bw.newLine();
            bw.write("Enter E to exit the help menu");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetSaveScreen() {
        try {
            bw = new BufferedWriter(new FileWriter(Main.filepath));
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("         Division Game          ");
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("Choose game mode to reset       ");
            bw.newLine();
            bw.newLine();
            bw.write("(1) Classic       (2) Limited   ");
            bw.newLine();
            bw.write("(3) Blind         (4) Highest   ");
            bw.newLine();
            bw.write("(5) Highest+      (A) All       ");
            bw.newLine();
            bw.write("(E) Back                        ");
            bw.newLine();
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void confirmationScreen(String gamemode) {
        try {
            bw = new BufferedWriter(new FileWriter(Main.filepath));
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("         Division Game          ");
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();

            if (gamemode.equals("All")) {
                bw.write("Reset all game modes? This action cannot be undone");
            } else {
                bw.write("Reset \"" + gamemode + "\"? This action cannot be undone");
            }

            bw.newLine();
            bw.newLine();
            bw.write("(Y) Yes            (N) No      ");
            bw.newLine();
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int resultScreen(int score, int highScore) {
        // Return codes:
        // 0: Exit to main menu
        // 100: Retry
        try {
            bw = new BufferedWriter(new FileWriter(Main.filepath));
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("         Division Game          ");
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("Your score is " + score + ((score > highScore)? " (NEW BEST!)" : ""));
            bw.newLine();
            bw.write("Retry?                         ");
            bw.newLine();
            bw.newLine();
            bw.write("(Y) Yes            (N) No      ");
            bw.newLine();
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.close();

            do {
                System.out.print("Choose your option: ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    return 100;
                } else if (input.equals("N")) {
                    return 0;
                } else {
                    System.out.println("Invalid input!");
                }
            } while (true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean checkInputValidity(String input, ArrayList<String> validInputs) {
        return (validInputs.indexOf(input) >= 0);
    }

    public static HighScores fetchScoresObject(String deletion) {
        // null = No deletion
        ObjectInputStream ois = null;
        HighScores scoresObject = null;

        if ((deletion != null && deletion.equals("All")) || !scoreFilepath.exists()) {
            return new HighScores();
        }

        try {
            ois = new ObjectInputStream(new FileInputStream(scoreFilepath));
            scoresObject = (HighScores) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        if (deletion != null) {
            scoresObject.changeHighScore(deletion, 0);
        }
        
        return scoresObject;
    }

    public static int fetchHighScore(String mode) {
        if (!scoreFilepath.exists()) {
            return 0;
        } else {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(scoreFilepath));
                HighScores score = (HighScores) ois.readObject();
                ois.close();
                return score.getHighScore(mode);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            return 0;
        }
    }

    public static void writeSerializable(HighScores scores) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(scoreFilepath));
            oos.writeObject(scores);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
