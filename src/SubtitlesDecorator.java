/*
import javafx.animation.AnimationTimer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubtitlesDecorator extends Decorator {
    private List<Subtitle> subtitles;
    private Text subtitleText;
    private MediaPlayer mediaPlayer;
    private Rectangle background; // Rectangle to serve as the background for subtitles

    public SubtitlesDecorator(MediaView mediaView, String subtitleUrl) {
        this.mediaPlayer = mediaView.getMediaPlayer();
        this.subtitles = loadSubtitles(subtitleUrl); // Load subtitles from local file

        // Debug statement to check if subtitles are loaded
        System.out.println("Loaded subtitles: " + subtitles.size());

        // Create a Rectangle for the background
        this.background = new Rectangle();
        this.background.setFill(Color.BLACK); // Set background color to black

        // Create a Text node for displaying subtitles
        this.subtitleText = new Text();
        this.subtitleText.setStyle("-fx-font-size: 24px; -fx-fill: white;"); // Increased font size and white text color

        // Create the StackPane and add the mediaView, background, and subtitleText
        StackPane stackPane = new StackPane(mediaView, background, subtitleText);

        // Align the subtitle text and background
        StackPane.setAlignment(subtitleText, javafx.geometry.Pos.BOTTOM_CENTER); // Align subtitles at the bottom center
        StackPane.setAlignment(background, javafx.geometry.Pos.BOTTOM_CENTER); // Align background with text

        // Set up subtitle display
        startSubtitleDisplay();

        // Make sure the media view is visible in your scene (optional)
        mediaView.setFitWidth(800); // Set appropriate width
        mediaView.setFitHeight(600); // Set appropriate height
    }

    private List<Subtitle> loadSubtitles(String subtitleFilePath) {
        List<Subtitle> subtitleList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(subtitleFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                // Parse the subtitle number (not used)
                int subtitleIndex = Integer.parseInt(line.trim());

                // Read the time range
                String timeLine = br.readLine().trim();
                String[] times = timeLine.split(" --> ");
                long startTime = parseSRTTime(times[0]);
                long endTime = parseSRTTime(times[1]);

                // Read the subtitle text
                StringBuilder textBuilder = new StringBuilder();
                while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                    textBuilder.append(line).append("\n");
                }
                String subtitleText = textBuilder.toString().trim();

                // Add to subtitle list
                subtitleList.add(new Subtitle(startTime, endTime, subtitleText));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Subtitle format error: " + e.getMessage());
        }
        return subtitleList;
    }

    private long parseSRTTime(String srtTime) {
        // Example input: "00:00:01,600"
        String[] parts = srtTime.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        String[] secondsAndMillis = parts[2].split(",");
        long seconds = Long.parseLong(secondsAndMillis[0]);
        long milliseconds = Long.parseLong(secondsAndMillis[1]);

        // Convert to milliseconds
        return ((hours * 3600 + minutes * 60 + seconds) * 1000) + milliseconds;
    }

    public void startSubtitleDisplay() {
        // Logic to periodically check the current playback time and display subtitles
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTimeMillis = getCurrentPlaybackTime();

                boolean subtitleDisplayed = false; // Flag to track if subtitle is displayed

                for (Subtitle subtitle : subtitles) {
                    if (currentTimeMillis >= subtitle.startTime && currentTimeMillis <= subtitle.endTime) {
                        subtitleText.setText(subtitle.text);
                        background.setWidth(subtitleText.getLayoutBounds().getWidth() + 20); // Adjust background width
                        background.setHeight(subtitleText.getLayoutBounds().getHeight() + 10); // Adjust background height
                        background.setVisible(true); // Show background
                        subtitleDisplayed = true; // Subtitle is displayed
                        break; // Exit loop after displaying subtitle
                    }
                }

                if (!subtitleDisplayed) {
                    subtitleText.setText(""); // Clear if no subtitle is to be displayed
                    background.setVisible(false); // Hide background if no subtitle is displayed
                }
            }
        }.start();
    }

    private long getCurrentPlaybackTime() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            return (long) currentTime.toMillis(); // Convert Duration to milliseconds
        }
        return 0; // Return 0 if mediaPlayer is null
    }

    @Override
    void addComponent() {

    }

    // Inner class for subtitles
    static class Subtitle {
        long startTime;
        long endTime;
        String text;

        public Subtitle(long startTime, long endTime, String text) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.text = text;
        }
    }

    public StackPane getSceneRoot() {
        return (StackPane) subtitleText.getParent(); // Return the StackPane used to hold the mediaView and subtitles
    }
}
*/
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubtitlesDecorator extends Decorator {
    private List<Subtitle> subtitles;
    private Text subtitleText;
    private MediaPlayer mediaPlayer;
    private Rectangle background; // Rectangle to serve as the background for subtitles
    private StackPane root; // Root StackPane for MediaView and subtitles

    public SubtitlesDecorator(MediaView mediaView, String subtitleUrl) {
        this.mediaPlayer = mediaView.getMediaPlayer();
        this.subtitles = loadSubtitles(subtitleUrl); // Load subtitles from local file

        // Debug statement to check if subtitles are loaded
        System.out.println("Loaded subtitles: " + subtitles.size());

        // Create a Rectangle for the background
        this.background = new Rectangle();
        this.background.setFill(Color.BLACK); // Set background color to black

        // Create a Text node for displaying subtitles
        this.subtitleText = new Text();
        this.subtitleText.setStyle("-fx-font-size: 24px; -fx-fill: white;"); // Increased font size and white text color

        // Create the StackPane and add the mediaView, background, and subtitleText
        root = new StackPane(mediaView, background, subtitleText);

        // Align the subtitle text and background
        StackPane.setAlignment(subtitleText, javafx.geometry.Pos.BOTTOM_CENTER); // Align subtitles at the bottom center
        StackPane.setAlignment(background, javafx.geometry.Pos.BOTTOM_CENTER); // Align background with text

        // Set up subtitle display
        startSubtitleDisplay();

        // Make sure the media view is visible in your scene (optional)
        mediaView.setFitWidth(800); // Set appropriate width
        mediaView.setFitHeight(600); // Set appropriate height
    }

    private List<Subtitle> loadSubtitles(String subtitleFilePath) {
        List<Subtitle> subtitleList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(subtitleFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                // Parse the subtitle number (not used)
                int subtitleIndex = Integer.parseInt(line.trim());

                // Read the time range
                String timeLine = br.readLine().trim();
                String[] times = timeLine.split(" --> ");
                long startTime = parseSRTTime(times[0]);
                long endTime = parseSRTTime(times[1]);

                // Read the subtitle text
                StringBuilder textBuilder = new StringBuilder();
                while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                    textBuilder.append(line).append("\n");
                }
                String subtitleText = textBuilder.toString().trim();

                // Add to subtitle list
                subtitleList.add(new Subtitle(startTime, endTime, subtitleText));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Subtitle format error: " + e.getMessage());
        }
        return subtitleList;
    }

    private long parseSRTTime(String srtTime) {
        // Example input: "00:00:01,600"
        String[] parts = srtTime.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        String[] secondsAndMillis = parts[2].split(",");
        long seconds = Long.parseLong(secondsAndMillis[0]);
        long milliseconds = Long.parseLong(secondsAndMillis[1]);

        // Convert to milliseconds
        return ((hours * 3600 + minutes * 60 + seconds) * 1000) + milliseconds;
    }

    public void startSubtitleDisplay() {
        // Logic to periodically check the current playback time and display subtitles
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTimeMillis = getCurrentPlaybackTime();

                boolean subtitleDisplayed = false; // Flag to track if subtitle is displayed

                for (Subtitle subtitle : subtitles) {
                    if (currentTimeMillis >= subtitle.startTime && currentTimeMillis <= subtitle.endTime) {
                        subtitleText.setText(subtitle.text);
                        background.setWidth(subtitleText.getLayoutBounds().getWidth() + 20); // Adjust background width
                        background.setHeight(subtitleText.getLayoutBounds().getHeight() + 10); // Adjust background height
                        background.setVisible(true); // Show background
                        subtitleDisplayed = true; // Subtitle is displayed
                        break; // Exit loop after displaying subtitle
                    }
                }

                if (!subtitleDisplayed) {
                    subtitleText.setText(""); // Clear if no subtitle is to be displayed
                    background.setVisible(false); // Hide background if no subtitle is displayed
                }
            }
        }.start();
    }

    private long getCurrentPlaybackTime() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            return (long) currentTime.toMillis(); // Convert Duration to milliseconds
        }
        return 0; // Return 0 if mediaPlayer is null
    }

    @Override
    void addComponent() {
        // Add any additional components or logic here if necessary
    }

    // Inner class for subtitles
    static class Subtitle {
        long startTime;
        long endTime;
        String text;

        public Subtitle(long startTime, long endTime, String text) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.text = text;
        }
    }

    public StackPane getSceneRoot() {
        return root; // Return the root StackPane used to hold the mediaView and subtitles
    }
}
