package suza.project.wackyballs.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import suza.project.wackyballs.ResultActivity;

/**
 * Score helper class.
 *
 * Created by lmark on 20/09/2017.
 */

public class Score {

    /**
     * Save player name and score locally in Shared preferences
     *
     * @param name Player name.
     * @param score Player score.
     * @param sharedPreferences Application shared preferences.
     */
    public static void saveScoreLocally(String name, int score, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, score);
        editor.commit();
    }


    /**
     * @return Returns sorted map from all elements found in shared preferences.
     *
     * @param sharedPreferences Application shared preferences.
     * @return Returns unmodifiable ordered map of names and score.
     */
    public static Map<String, Integer> getSortedScoreMap(SharedPreferences sharedPreferences) {

        // Get current score map
        final Map<String, Integer> scoreMap = (Map<String, Integer>) sharedPreferences.getAll();

        // Initialize sorted map
        Map<String, Integer> sortedMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i1 = scoreMap.get(o1);
                int i2 = scoreMap.get(o2);

                if (i1 < i2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        sortedMap.putAll(scoreMap);

        return Collections.unmodifiableMap(sortedMap);
    }
}

