import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class BasicVideoPlayer {
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private PlayerState currentState;

    public BasicVideoPlayer(MediaView mediaView, String videoUrl) {
        this.mediaView = mediaView;
        setVideoUrl(videoUrl);
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setVideoUrl(String videoUrl) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(videoUrl);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    // State pattern methods
    public void setState(PlayerState state) {
        this.currentState = state;
    }

    // Play state management for seamless switching
    public void switchVideoWithState(String videoUrl) {
        boolean wasPlaying = mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
        setVideoUrl(videoUrl);
        if (wasPlaying) {
            mediaPlayer.play(); // Resume play state if it was previously playing
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
