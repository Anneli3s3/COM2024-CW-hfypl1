package main;

import main.Score;

import java.util.Comparator;

public class ScoreCompare implements Comparator<Score> {

    /**
     * compare Compares the two numbers and sorts it out accordingly
     * @param score1 first initialized score to compare
     * @param score2 second initialized score to compare
     * @return
     */
    public int compare(Score score1, Score score2) {

        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2){
            return -1;
        }else if (sc1 < sc2){
            return +1;
        }else{
            return 0;
        }
    }

}