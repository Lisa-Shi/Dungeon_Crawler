package gameobjects.npc;

import gamemap.ChallengeRoom;
import gamemap.Room;
import gameobjects.Player;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Main;

import java.awt.*;
import java.awt.event.MouseEvent;


public class ChallengeRoomNPC extends NPC {
    private StackPane stackPane;
    private ChallengeRoom room;
    private boolean challenged = false;
    public ChallengeRoomNPC(Room room, double initialX, double initialY){
        super(room, initialX, initialY);
        if( !(room instanceof ChallengeRoom)){
            throw new IllegalArgumentException("Incorrect NPC in the room");
        }
        this.room = (ChallengeRoom)room;
    }
    private String getString(String playerName){
        return "Hi " + playerName + ",\n Welcome to the challenge room.\n In this room, \n"
                + "you will have the chance to accept the challenge.\n In the challenge,"
                + "the monsters that you will be \nfacing are a lot strongger than the"
                + "regular monsters.\n Of course defecting the monsters will give you the\n"
                + "items that you don't see outside the challenge room.\n Keep in mind"
                + "that you cannot leave the room\n until all the monsters are defected"
                + "\nif you take the challenge.\n\n\n\n\n";

    }
    @Override
    public void open(Player player, Pane pane) {
        if(!challenged) {
            setUpConversation(player, pane);
        }
    }
    private void setUpConversation(Player player, Pane pane){
        stackPane = new StackPane();
        stackPane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);
        Button background = new Button();
        background.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);
        background.getStylesheets().add("inventoryStyleSheet.css");
        background.getStyleClass().add("Transparent");
        background.setOnAction(e-> {
            pane.getChildren().remove(stackPane);
            setUpUI(player, pane);
            pane.getChildren().add(stackPane);
        });
        ImageView imageView = new ImageView(Main.NPC_BIG);
        imageView.setFitWidth(250);
        imageView.setFitHeight(300);
        Text text = new Text(getString(player.getName()));
        text.getStyleClass().add("font");
        HBox hbox = new HBox(imageView, text);
        hbox.getStylesheets().add("inventoryStyleSheet.css");
        hbox.getStyleClass().add("conversation");
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.getChildren().add(hbox);
        stackPane.getChildren().add(background);
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
            challenged = true;
            room.drawMonster(pane);
            room.setFinish(false);
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
