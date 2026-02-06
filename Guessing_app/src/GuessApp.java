import java.util.Random;
import java.util.Scanner;
class GameConfig {
    private final int Min = 1;
    private final int Max = 100;
    private final int Max_Attempts = 7;
    private final int Max_Hints = 3;
    int targetNumber;
    public GameConfig () {
        Random random  = new Random();
        this.targetNumber =  random.nextInt(Max-Min + 1)+ Min;

    }
    public int getTargetNumber() {
        return targetNumber;
    }
    public int getMaxAttemps() {
        return Max_Attempts;
    }

    public int getMaxHints() {
        return Max_Hints;
    }

    public void showRules() {
        System.out.println("Guess a number between " + Min + " and " + Max);
        System.out.println("You have "+ Max_Attempts + " attempts");
        System.out.println("Hints will be provided after wrong guesses.\n");
    }

}
class GuessValidator {
    public static String validateGuess(int guess, int target) {
        if (guess == target) {
            return "CORRECT";

        } else if (guess < target) {
            return "Low";
        }
        return "High";
    }

}
class HintService {
    public static String generateHint(int target, int hintCount) {
        if(hintCount == 1) {
            return (target % 2 == 0) ? "Hint: Number is EVEN" : "Hint: Number is ODD";
        } else if(hintCount == 2) {
            return (target > 50) ? "Hint: Number is greater than 50" : "Hint: Number is 50 or less";
        }
        return "No more hints available";
    }
}

public class GuessApp {
    public static void main (String arg[])  {
    System.out.println("Welcome to the Guessing App");
    GameConfig config = new GameConfig();
    config.showRules();
     Scanner scanner = new Scanner(System.in);
     int attempts = 0;
     while (attempts < config.getMaxAttemps()) {
         System.out.println("Enter your guess");
         int guess = scanner.nextInt();
         attempts++;
         String result = GuessValidator.validateGuess(guess, config.getTargetNumber());
         System.out.println(result);
         if("CORRECT".equals(result)) {
             break;
         }
     }
    }
}
