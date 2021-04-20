package gameobjects.npc;

import gamemap.ChallengeRoom;
import gamemap.Room;
import gameobjects.Player;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class ChallengeRoomNPC extends NPC {

    private StackPane stackPane;
    private ChallengeRoom room;
    public ChallengeRoomNPC(Room room, double initialX, double initialY){
        super(room, initialX, initialY);
        if( !(room instanceof ChallengeRoom)){
            throw new IllegalArgumentException("Incorrect NPC in the room");
        }
        this.room = (ChallengeRoom)room;
    }

    @Override
    public void open(Player player, Pane pane) {
        setUpUI(player, pane);
        pane.getChildren().add(stackPane);
    }
    private void setUpUI(Player player, Pane pane) {
        Bounds bound = pane.getLayoutBounds();
        stackPane = new StackPane();
        stackPane.setPrefSize(200, 50);
        stackPane.setLayoutX(bound.getWidth() / 3);
        stackPane.setLayoutY(bound.getHeight() / 3);
        GridPane gridPane = setupGrid(player, pane);
        stackPane.getChildren().addAll(gridPane);
    }
    private GridPane setupGrid(Player player, Pane pane){
        GridPane gridPane = new GridPane();
        Text text = new Text("Accept Challenge?");
        gridPane.getStylesheets().add("inventoryStyleSheet.css");
        gridPane.getStyleClass().add("inventory");
        Button accept = setUpStart(player, pane);
        Button decline = setUpDecline(player, pane);
        for( int i = 0 ; i < 4; i++){
            gridPane.add(new Text("   "), i, 0);
        }
        gridPane.add(text, 1, 1, 2, 1);
        for( int i = 0 ; i < 4; i++){
            gridPane.add(new Text("   "), i, 2);
        }
        gridPane.add(new Text("             "), 0, 3);
        gridPane.add(accept, 1,3);
        gridPane.add(decline, 2, 3);
        gridPane.add(new Text("             "), 3, 3);
        for( int i = 0 ; i < 4; i++){
            gridPane.add(new Text("   "), i, 4);
        }
        gridPane.setHgap(10);
        gridPane.setVgap(3);
        return gridPane;
    }
    private Button setUpStart(Player player, Pane pane){
        Button button = new Button("Yes");
        button.setPrefSize(75, 45);
        button.setOnAction(e->{
            room.setFinish(false);
            room.drawMonster(pane);
            pane.getChildren().remove(stackPane);
            player.setMoveability(true);
        });
        return button;
    }
    private Button setUpDecline(Player player, Pane pane){
        Button button = new Button("No");
        button.setPrefSize(75, 45);
        button.setOnAction(e->{
            pane.getChildren().remove(stackPane);
            player.setMoveability(true);
        });
        return button;
    }
}
