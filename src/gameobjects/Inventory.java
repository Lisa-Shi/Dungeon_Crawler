package gameobjects;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Inventory {
    private StackPane inv;
    private Pane pane;
    private Player player;
    private Map<Potion, Integer> items;
    public Inventory(Map<Potion, Integer> items, Pane pane, Player player){
        this.items = items;
        this.pane = pane;
        this.player = player;
        inv = new StackPane();
        Bounds bound = pane.getLayoutBounds();
        Rectangle rewardRec = new Rectangle();
        Button close = new Button("X");
        close.setAlignment(Pos.TOP_RIGHT);
        close.setOnAction(event->{
            close();
        });
        GridPane inventory = new GridPane();
        inventory.setHgap(15);
        inventory.setVgap(20);
        inventory.getRowConstraints().add(new RowConstraints(5));
        inventory.getRowConstraints().add(new RowConstraints(5));
        inventory.getRowConstraints().add(new RowConstraints(5));
        inventory.getRowConstraints().add(new RowConstraints(5));
        inventory.getStylesheets().add("inventoryStyleSheet.css");
        inventory.add(close, 5, 0);
        int row = 1;
        int col = 0;
        Iterator<Map.Entry<Potion, Integer>> iterator = items.entrySet().iterator();
        for( int i = 0; i < 15 ; i++){
            Map.Entry<Potion, Integer> entry;
            if( iterator.hasNext()){
                entry = iterator.next();
            } else{
                entry = null;
            }
            if( col == 0 || col == 6){
                //Text spaceHolder = new Text("      ");
                //inventory.add(spaceHolder, col, row);
            } else {
                ItemButton itembutton = new ItemButton(entry.getKey());
                if( entry != null) {
                    itembutton.setGraphic(entry.getKey().getGraphic());
                } else{
                    itembutton.setGraphic(new ImageView(Main.TRANSPARENT_IMAGE));
                }
                itembutton.getStylesheets().add("inventoryStyleSheet.css");
                itembutton.getStyleClass().add("item_button");
                itembutton.setOnAction(event -> {
                    Potion consumable = itembutton.getPotion();
                    consumable.consume(player);
                    loseItem(potion);
                });
                inventory.add(itembutton, col, row);
            }
            if( col == 4){
                row++;
                col = 0;
            } else{
                col++;
            }
        }
        /*rewardRec.setOpacity(0.8);
        rewardRec.setHeight(bound.getHeight() / 2);
        rewardRec.setWidth(bound.getWidth() / 2);
        rewardRec.setX(bound.getWidth() / 2 - bound.getWidth() / 4);
        rewardRec.setY(bound.getHeight() / 2 - bound.getHeight() / 4);*/
        inv.getChildren().addAll(inventory);
        inv.setLayoutX(bound.getWidth() / 2 - bound.getWidth() / 4);
        inv.setLayoutY(bound.getHeight() / 2 - bound.getHeight() / 4);
    }
    public void show(){
        pane.getChildren().add(inv);
    }
    public void close(){
        pane.getChildren().remove(inv);
        //pane.getChildren().remove(rewardRec);
        player.setMoveability(true);
    }
    public void loseItem(Potion potion){
        if(items.containsKey(potion)){
            items.put(potion, items.get(potion) - 1);
        }
    }
    public void updateItem(Map<Potion, Integer> updataItems){
        for( Potion potion: updataItems.keySet()){
            if( items.containsKey(potion)){
                items.put(potion, items.get(potion) + updataItems.get(potion));
            } else{
                items.put(potion, updataItems.get(potion));
            }
        }
    }
    private class ItemButton extends Button{
        public Potion potion;

        public ItemButton(Potion potion){
            super();
            this.potion = potion;
        }
        public Potion getPotion() {
            return potion;
        }

        public void setPotion(Potion potion) {
            this.potion = potion;
        }
    }
}
