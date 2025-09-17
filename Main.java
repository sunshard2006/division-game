import java.util.*;

public class Main {
    public static String filepath = "Game.txt";
    public static HighScores score = Helper.fetchScoresObject(null);
    public static Scanner scan = new Scanner(System.in);
    public static void main(String args[]) {
        Classic classic = new Classic();
        Limited limited = new Limited();
        Blind blind = new Blind();
        Highest highest = new Highest();
        HighestPlus highestPlus = new HighestPlus();
        
        Helper.menuScreen();
        do {
            System.out.print("Choose your option: ");
            String input = scan.nextLine();

            switch (input) {
                case "H":
                    Helper.helpMenu();
                    do {
                        System.out.print("Choose your option: ");
                        input = scan.nextLine();
                        if (input.equals("E"))  {
                            Helper.menuScreen();
                            break;
                        } else {
                            System.out.println("Invalid input!");
                        }

                    } while (true);
                    break;

                case "R":
                    Helper.resetSaveScreen();
                    Map<String, String> optionMap = new HashMap<>();
                    optionMap.put("1", "Classic");
                    optionMap.put("2", "Limited");
                    optionMap.put("3", "Blind");
                    optionMap.put("4", "Highest");
                    optionMap.put("5", "Highest+");
                    optionMap.put("A", "All");

                    do {
                        System.out.print("Choose your option: ");
                        input = scan.nextLine();

                        if (input.equals("E")) {
                            Helper.menuScreen();
                            break;
                        } else if (optionMap.containsKey(input)) {
                            String deleteMode = optionMap.get(input);
                            Helper.confirmationScreen(deleteMode);

                            do {
                                System.out.print("Choose your option: ");
                                input = scan.nextLine();
                                if (input.equals("Y")) {
                                    score = Helper.fetchScoresObject(deleteMode);
                                    System.out.println("--------------------");
                                    System.out.println("Save deleted successfully!");
                                    System.out.println("--------------------");
                                    Helper.writeSerializable(score);
                                    Helper.resetSaveScreen();
                                    break;
                                } else if (input.equals("N")) {
                                    Helper.resetSaveScreen();
                                    break;
                                } else {
                                    System.out.println("Invalid input!");
                                }
                            } while (true);

                        } else {
                            System.out.println("Invalid input!");
                        }
                    } while (true);
                    break;
                
                case "1":
                    classic.play();
                    Helper.menuScreen();
                    break;
                case "2":
                    limited.play();
                    Helper.menuScreen();
                    break;
                case "3":
                    blind.play();
                    Helper.menuScreen();
                    break;
                case "4":
                    highest.play();
                    Helper.menuScreen();
                    break;
                case "5":
                    highestPlus.play();
                    Helper.menuScreen();
                    break;
                
                case "E":
                    System.out.println("Exit successfully!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        } while (true);
        
    }
}