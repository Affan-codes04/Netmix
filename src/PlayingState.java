public class PlayingState implements PlayerState {
    @Override
    public void play(BasicVideoPlayer player) {
        System.out.println("Already playing.");
    }

    @Override
    public void pause(BasicVideoPlayer player) {
        player.getMediaPlayer().pause();
        player.setState(new PausedState());
        System.out.println("Paused.");
    }
}
