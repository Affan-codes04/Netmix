import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class VideoPlayer extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Video Player");

        // Convert local video file paths to valid URI format
        String lowQualityUrl = new File("G:/JavaFX/video1.mp4").toURI().toString();
        String mediumQualityUrl = new File("G:/JavaFX/video2.mp4").toURI().toString();
        String highQualityUrl = new File("G:/JavaFX/video3.mp4").toURI().toString();

        // Path to the subtitles file
        String subtitleUrl = "G:/JavaFX/subtitles.srt";

        // Initialize Singleton and get the VideoPlayer instance
        VideoPlayerSingleton videoPlayerSingleton = VideoPlayerSingleton.getInstance();
        videoPlayerSingleton.initializeVideoPlayer(primaryStage);

        // Create a MediaView for displaying the video
        MediaView mediaView = new MediaView();

        // Initialize BasicVideoPlayer with medium quality as the default video
        BasicVideoPlayer basicVideoPlayer = new BasicVideoPlayer(mediaView, mediumQualityUrl);

        // Apply the QualityDecorator
        QualityDecorator qualityDecorator = new QualityDecorator(basicVideoPlayer);

        // Apply SubtitlesDecorator to display subtitles
        SubtitlesDecorator subtitlesDecorator = new SubtitlesDecorator(mediaView, subtitleUrl);
        subtitlesDecorator.startSubtitleDisplay(); // Start subtitle display

        // Set up buttons to control video quality
        Button lowQualityButton = new Button("Low Quality");
        lowQualityButton.setOnAction(e -> {
            basicVideoPlayer.switchVideoWithState(lowQualityUrl); // Switch without stopping
        });

        Button mediumQualityButton = new Button("Medium Quality");
        mediumQualityButton.setOnAction(e -> {
            basicVideoPlayer.switchVideoWithState(mediumQualityUrl);
        });

        Button highQualityButton = new Button("High Quality");
        highQualityButton.setOnAction(e -> {
            basicVideoPlayer.switchVideoWithState(highQualityUrl);
        });

        // Set up Play and Pause buttons using the State pattern
        Button playButton = new Button("Play");
        playButton.setOnAction(e -> basicVideoPlayer.play());

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> basicVideoPlayer.pause());

        // Arrange buttons in a vertical layout
        VBox controls = new VBox(10, playButton, pauseButton, lowQualityButton, mediumQualityButton, highQualityButton);

        // Set up the root StackPane to hold the media view and controls
        StackPane root = new StackPane();
        root.getChildren().addAll(mediaView, subtitlesDecorator.getSceneRoot(), controls); // Add subtitle display and media

        // Set up the scene with the video, subtitles, and quality controls
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Play the video at default quality
        basicVideoPlayer.play();
    }
}