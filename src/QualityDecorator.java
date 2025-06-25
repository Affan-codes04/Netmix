import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class QualityDecorator extends Decorator {
    private BasicVideoPlayer player;
    private String currentQuality;
    private MediaView mediaView;  // Reference to MediaView

    // Paths for different video qualities
    private String lowQualityUrl = new File("G:/JavaFX/video1.mp4").toURI().toString();
    private String mediumQualityUrl = new File("G:/JavaFX/video2.mp4").toURI().toString();
    private String highQualityUrl = new File("G:/JavaFX/video3.mp4").toURI().toString();

    public QualityDecorator(BasicVideoPlayer player) {
        this.player = player;
        this.mediaView = player.getMediaView();  // Get the MediaView from the player
        this.currentQuality = "Medium Quality";  // Default quality
    }

    public void play() {
        switchQuality(currentQuality);  // Ensure the correct quality is set when playing
        player.play();  // Play the video using the wrapped player
    }

    public void switchQuality(String quality) {
        String videoUrl;

        // Choose the correct video URL based on quality
        switch (quality) {
            case "Low Quality":
                videoUrl = lowQualityUrl;
                break;
            case "Medium Quality":
                videoUrl = mediumQualityUrl;
                break;
            case "High Quality":
            default:
                videoUrl = highQualityUrl;
                break;
        }

        // Stop the current video before switching
        if (player.getMediaPlayer() != null) {
            player.stop();  // Stop the current video
        }

        // Create a new Media and MediaPlayer for the chosen quality
        Media media = new Media(videoUrl);
        MediaPlayer newMediaPlayer = new MediaPlayer(media);

        // IMPORTANT: Update the MediaView to use the new MediaPlayer
        mediaView.setMediaPlayer(newMediaPlayer);

        // Start playing the new video
        newMediaPlayer.play();

        // Update the current quality
        currentQuality = quality;
        System.out.println("Playing in " + quality);
    }

    public String getCurrentQuality() {
        return currentQuality;
    }

    @Override
    void addComponent() {

    }
}
