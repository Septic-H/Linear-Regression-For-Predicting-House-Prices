package org.linearregressionforpredictinghouseprices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DataVisualization {

    public void generateVisualizations(String scriptPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Python script execution failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
