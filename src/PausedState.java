public class PausedState implements PlayerState {
    @Override
    public void play(BasicVideoPlayer player) {
        player.getMediaPlayer().play();
        player.setState(new PlayingState());
        System.out.println("Playing.");
    }

    @Override
    public void pause(BasicVideoPlayer player) {
        System.out.println("Already paused.");
    }
}
