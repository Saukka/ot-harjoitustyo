
package tetris.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class HighScores {
    
    public HighScores() {
        
    }
    
    public void addScore(int score, boolean thin) throws Exception {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            CharSequence scoreChar = String.valueOf(score);
            CharSequence mode = "";
            if (thin) {
                mode += "thin";
            } else {
                mode += "normal";
            }
            writer.append(scoreChar + " " + mode + "\n");
            writer.close();
        }
    }
    public Map<Integer,String> getScores() throws IOException {
        Map<Integer,String> top5 = new TreeMap<Integer,String>(new Comparator<Integer>() {
            @Override
            public int compare(Integer v1, Integer v2) {
                return v2.compareTo(v1);
            }
        });
        Scanner reader = new Scanner(Paths.get("scores.txt"));
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(" ");
            top5.put(Integer.valueOf(parts[0]), parts[1]);
        }
        return top5;
    }
}
