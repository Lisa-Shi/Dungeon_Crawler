package sample;

import javafx.scene.control.ProgressBar;

public class RoomProgressBar {

    private ProgressBar pbar;

    public RoomProgressBar(int room) {
        pbar = new ProgressBar();
        pbar.setProgress(room * 1.0);
    }

    public RoomProgressBar() {
        this(0);
    }

    public void setProgress(int room) {
        pbar.setProgress(room * 1.0);
    }

    public ProgressBar getPbar() {
        return pbar;
    }
}
