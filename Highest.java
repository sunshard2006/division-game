import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Highest implements Gamemodes {
    private static String filePath = Main.filepath;
    private static Scanner scan = new Scanner(System.in);
    private static String input;

    public Highest() {

    }

    public static void printInfo(Display display, String filePath) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("         Division Game          ");
            bw.newLine();
            bw.write("--------------------------------");
            bw.newLine();
            bw.write("Mode: Highest");
            bw.newLine();
            bw.write("Best score: " + display.getBestScore());
            bw.newLine();
            bw.write("Score: " + display.getScore());
            bw.newLine();
            bw.write("Number: " + display.getDivisionNumber());
            bw.newLine();
            bw.newLine();
            bw.write("(E) Exit              (H) Help   ");
            bw.newLine();
            bw.write("(2) ÷2                (3) ÷3     ");
            bw.newLine();
            bw.write("(4) ÷4                (5) ÷5     ");
            bw.newLine();
            bw.write("(6) ÷6                (7) ÷7     ");
            bw.newLine();
            bw.write("(8) ÷8                (9) ÷9     ");
            bw.newLine();
            bw.write("(10) ÷10                         ");
            bw.newLine();
            bw.write(display.getNote());
            bw.newLine();
            bw.write("--------------------------------");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger generateNumber(int score) {
        int numOfMul = score;
        BigInteger divisionNumber = BigInteger.valueOf((long)(Math.random() * 8) + 2);
        BigInteger multiplier;

        while (numOfMul-- > 0) {
            multiplier = BigInteger.valueOf((long)(Math.random() * 8) + 2);
            divisionNumber = divisionNumber.multiply(multiplier);
        }

        return divisionNumber;
    }

    public static int fetchHighestDivisor(BigInteger divisionNumber, int lowerBound, int upperBound) {
        int highestDivisor = 0;

        for (int i = lowerBound; i <= upperBound; i++) {
            highestDivisor = (divisionNumber.remainder(BigInteger.valueOf(i)).compareTo(BigInteger.ZERO) == 0)? i : highestDivisor;
        }

        return highestDivisor;
    }

    public static int mainGameplay(Display display) {
        /*
        Return codes:
        -100: Exited
        0: Round failed by indivisible number
        1: Round failed by non-highest divisor
        100: Round succeeded
        */

        BigInteger[] divideAndRemainderArr;
        BigInteger divider = null;
        boolean showHelpMenu = false;

        while (display.getDivisionNumber().compareTo(BigInteger.ONE) != 0) {
            if (!showHelpMenu) printInfo(display, Main.filepath);
            display.setNote("");
            System.out.print("Choose your option: ");
            input = scan.nextLine();

            if (!Helper.checkInputValidity(input, new ArrayList<>(Arrays.asList("E", "H", "2", "3", "4", "5", "6", "7", "8", "9", "10"))) || (showHelpMenu && !input.equals("E"))) {
                System.out.println("Invalid input!");
                display.setNote("Invalid input!");
                continue;
            } else if (input.equals("E")) {
                if (showHelpMenu) {
                    showHelpMenu = false;
                    continue;
                } else {
                    return -100;
                }
            } else if (input.equals("H")) {
                Helper.helpMenu();
                showHelpMenu = true;
                continue;
            } else {
                divider = BigInteger.valueOf(Integer.parseInt(input));
            }

            divideAndRemainderArr = display.getDivisionNumber().divideAndRemainder(divider);

            if (divideAndRemainderArr[1].compareTo(BigInteger.ZERO) == 0) {
                // Change lower/upper bounds for future highest game modes
                if (!input.equals(String.valueOf(fetchHighestDivisor(display.getDivisionNumber(), 2, 10)))) {
                    return 1;
                }
                display.setDivisionNumber(divideAndRemainderArr[0]);
            } else {
                return 0;
            }
        }

        printInfo(display, filePath);
        return 100;
    }

    public void play() {
        Display display = new Display(new BigInteger("0"), 0, Helper.fetchHighScore("Highest"), "");
        int returnCode;
        int initialBestScore;
        boolean exit = false;

        do {
            initialBestScore = Main.score.getHighScore("Highest");
            do {
                display.setDivisionNumber(generateNumber(display.getScore()));

                returnCode = mainGameplay(display);

                if (returnCode != 100) {
                    continue;
                } else {
                    System.out.println("--------------------");
                    System.out.println("Great job!");
                    System.out.println("--------------------");
                    display.setNote("Great job! Next round!");
                    printInfo(display, filePath);
                    display.setNote("");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    display.setScore(display.getScore() + 1);
                    if (display.getScore() > display.getBestScore()) {
                        display.setBestScore(display.getScore());
                    }
                }

            } while (returnCode == 100);

            if (returnCode == -100) {
                System.out.println("--------------------");
                System.out.println("Exited successfully!");
                System.out.println("--------------------");
                display.setNote("Exited successfully!");
                printInfo(display, filePath);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                display.setNote("");

            } else if (returnCode == 0 || returnCode == 1) {
                System.out.println("--------------------");
                System.out.println("Round failed!");
                System.out.println("Final score: " + display.getScore());
                System.out.println("--------------------");

                if (returnCode == 0) {
                    display.setNote("This number is not divisible by " + input + ", better luck next time!\n" +
                                    "Highest divisor: " + fetchHighestDivisor(display.getDivisionNumber(), 2, 10));
                } else if (returnCode == 1) {
                    display.setNote("This divisor is not the highest, better luck next time!\n" + 
                    "Highest divisor: " + fetchHighestDivisor(display.getDivisionNumber(), 2, 10));
                }
                printInfo(display, filePath);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

            }
            
            if (display.getScore() > initialBestScore) {
                Main.score.changeHighScore("Highest", display.getScore());
                Helper.writeSerializable(Main.score);
            }

            int resultReturnCode = Helper.resultScreen(display.getScore(), initialBestScore);

            if (resultReturnCode == 0) exit = true;

            display.setNote("");
            display.setScore(0);

        } while (!exit);
    }
}
