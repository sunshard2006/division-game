import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class HighScores implements Serializable {
    private Map<String, Integer> highscores = new HashMap<>();

    public HighScores() {

    }

    public HighScores(HashMap<String, Integer> highscores) {
        this.highscores = highscores;
    }

    public Map<String, Integer> getScoresMap() {
        return highscores;
    }

    public void setScoresMap(Map<String, Integer> scoreMap) {
        highscores = scoreMap;
    }

    public void changeHighScore(String mode, int highScore) {
        highscores.put(mode, highScore);
    }

    public int getHighScore(String mode) {
        return (highscores.containsKey(mode))? highscores.get(mode) : 0;
    }
}
