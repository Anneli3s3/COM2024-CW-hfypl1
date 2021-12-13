package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore{

    /* Array list for scores */
    private ArrayList<Score> scores;

    /* File to store the high scores */
    private static final String HIGHSCOREFILE = "HighScore.dat";

    ObjectOutputStream output = null;
    ObjectInputStream input = null;


    public HighScore() {
        scores = new ArrayList<Score>();
    }

    /* loads the score from the high score file */
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    /* scores are pass to CompareScores */
    private void sort() {
        ScoreCompare comparator = new ScoreCompare();
        Collections.sort(scores, comparator);
    }

    /*
     * Loads the high score file
     * add new scores and update the file
     */
    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    /* Loads the high score file */
    public void loadScoreFile() {
        try {
            input = new ObjectInputStream(new FileInputStream(HIGHSCOREFILE));
            scores = (ArrayList<Score>) input.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    /**
     * updateScoreFile will make the new changes when its called
     */
    public void updateScoreFile() {
        try {
            output = new ObjectOutputStream(new FileOutputStream(HIGHSCOREFILE));
            output.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }


    public String getHighscoreString() {
        String highScoreString = "";
        int max = 10;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highScoreString += (i + 1) + ".\t" + scores.get(i).getNaam() + "\t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highScoreString;
    }

}


