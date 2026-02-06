import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

class GameConfig {
    private final int Min = 1;
    private final int Max = 100;
    private final int Max_Attempts = 7;
    private final int Max_Hints = 3;
    int targetNumber;

    public GameConfig() {
        Random random = new Random();
        this.targetNumber = random.nextInt(Max - Min + 1) + Min;
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
        System.out.println("You have " + Max_Attempts + " attempts");
        System.out.println("Hints will be provided after wrong guesses.\n");
    }
}

class GuessValidator {
    public static String validateGuess(int guess, int target) {
        if (guess == target) return "CORRECT";
        return (guess < target) ? "LOW" : "HIGH";
    }
}

class HintService {
    public static String generateHint(int target, int hintCount) {
        if (hintCount == 1)
            return (target % 2 == 0) ? "Hint: Number is EVEN" : "Hint: Number is ODD";
        else if (hintCount == 2)
            return (target > 50) ? "Hint: Number is greater than 50" : "Hint: Number is 50 or less";
        return "No more hints available";
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class ValidationService {
    public static int validateInput(String input) throws InvalidInputException {
        try {
            int value = Integer.parseInt(input);
            if (value < 1 || value > 100)
                throw new InvalidInputException("Number must be between 1 and 100");
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input. Please enter numbers only.");
        }
    }
}

class StorageService {
    public static void saveResult(String player, int attempts, boolean win) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("game_result.txt", true))) {

            writer.write("Player: " + player +
                    ", Attempts: " + attempts +
                    ", Result: " + (win ? "WIN" : "LOSE"));
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Unable to save game result.");
        }
    }
}

class GameController {
    public static boolean restartGame(Scanner scanner) {
        System.out.print("Do you want to play again? (Yes/No): ");
        return scanner.nextLine().equalsIgnoreCase("yes");
    }
}

public class GuessApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean restart;

        System.out.println("Welcome to the Guessing App");

        do {
            System.out.print("Enter Player Name: ");
            String player = scanner.nextLine();

            GameConfig config = new GameConfig();
            config.showRules();

            int attempts = 0;
            int hintsUsed = 0;
            boolean win = false;

            while (attempts < config.getMaxAttemps()) {
                try {
                    System.out.print("Enter your guess: ");
                    int guess = ValidationService.validateInput(scanner.nextLine());
                    attempts++;

                    String result = GuessValidator.validateGuess(
                            guess, config.getTargetNumber());

                    if (!"CORRECT".equals(result) &&
                            hintsUsed < config.getMaxHints()) {
                        hintsUsed++;
                        System.out.println(
                                HintService.generateHint(
                                        config.getTargetNumber(), hintsUsed)
                        );
                    }

                    System.out.println(result);

                    if ("CORRECT".equals(result)) {
                        win = true;
                        System.out.println("🎉 You won in " + attempts + " attempts!");
                        break;
                    }

                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }

            StorageService.saveResult(player, attempts, win);
            restart = GameController.restartGame(scanner);

        } while (restart);

        System.out.println("Thanks for playing!");
    }
}
