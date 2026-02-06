import java.util.Random;

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

public class GuessApp {
    public static void main (String arg[])  {
        System.out.println("Welcome to the Guessing App");
        GameConfig gameConfig =  new GameConfig();
        gameConfig.showRules();
    }
}
