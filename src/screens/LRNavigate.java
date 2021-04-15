package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Helper class that positions 2 buttons to be far left and right.
 */
public class LRNavigate {

    /**
     * Builds HBox with buttons in left and right side.
     *
     * @param left button
     * @param right button
     * @return HBox
     */
    public static HBox buildBox(Button left, Button right) {
        HBox leftBox = new HBox(left);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        HBox rightBox = new HBox(right);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        HBox bottom = new HBox(leftBox, rightBox);
        bottom.setPadding(new Insets(10));

        return bottom;
    }
}
