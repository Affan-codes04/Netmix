import javafx.stage.Stage;

public class VideoPlayerSingleton {
    // The single instance of the VideoPlayer
    private static VideoPlayerSingleton instance;
    private VideoPlayer videoPlayer;

    // Private constructor to prevent instantiation
    private VideoPlayerSingleton() {}

    // Public method to get the instance of the Singleton class
    public static VideoPlayerSingleton getInstance() {
        if (instance == null) {
            instance = new VideoPlayerSingleton();
        }
        return instance;
    }

    // Method to initialize the VideoPlayer
    public void initializeVideoPlayer(Stage primaryStage) {
        if (videoPlayer == null) {
            videoPlayer = new VideoPlayer();
            videoPlayer.start(primaryStage);  // Start the video player
        }
    }

    // Method to access the VideoPlayer instance
    public VideoPlayer getVideoPlayer() {
        return videoPlayer;
    }
}
