import java.math.BigInteger;

public class Display {
    private BigInteger divisionNumber;
    private int score;
    private int bestScore;
    private String note;
    

    public Display(BigInteger divisionNumber, int score, int bestScore, String note) {
        this.divisionNumber = divisionNumber;
        this.score = score;
        this.bestScore = bestScore;
        this.note = note;
    }

    public BigInteger getDivisionNumber() {
        return divisionNumber;
    }
    public void setDivisionNumber(BigInteger divisionNumber) {
        this.divisionNumber = divisionNumber;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getBestScore() {
        return bestScore;
    }
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
