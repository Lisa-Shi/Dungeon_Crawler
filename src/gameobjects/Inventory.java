package gameobjects;

import gameobjects.potions.Potion;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    private static Inventory unique;
    public static synchronized Inventory getInstance(Map<Potion, Integer> items, Pane pane, Player player){
        if(unique == null){
            unique = new Inventory(items, pane, player);
        }
        return unique;
    }
    private Inventory(Map<Potion, Integer> items, Pane pane, Player player){
        this.items = items;
        this.pane = pane;
        this.player = player;
        setUpUI();
    }
    private void setUpUI(){
        Bounds bound = pane.getLayoutBounds();
        inv = new StackPane();
        inv.setPrefSize(250, 240);
        inv.setLayoutX(bound.getWidth() / 2 - bound.getWidth() / 4);
        inv.setLayoutY(bound.getHeight() / 2 - bound.getHeight() / 4);
        Button close = setupClose();
        GridPane inventory = setupGrid();
        inventory.add(close, 4, 1);
        inv.getChildren().addAll(inventory);
        inv.setLayoutX(bound.getWidth() / 2 - bound.getWidth() / 4);
        inv.setLayoutY(bound.getHeight() / 2 - bound.getHeight() / 4);

    }
    private Button setupClose(){
        Button close = new Button("X");
        close.getStyleClass().add("button");
        close.setPrefSize(50, 45);
        close.setOnAction(event->{
            close();
        });
        return close;
    }
    private GridPane setupGrid(){
        GridPane inventory = new GridPane();
        inventory.setHgap(5);
        inventory.setVgap(5);
        inventory.getRowConstraints().add(new RowConstraints(10));
        inventory.getRowConstraints().add(new RowConstraints(50));
        inventory.getRowConstraints().add(new RowConstraints(50));
        inventory.getRowConstraints().add(new RowConstraints(50));
        inventory.getRowConstraints().add(new RowConstraints(50));
        inventory.getStylesheets().add("inventoryStyleSheet.css");
        inventory.getStyleClass().add("inventory");
        addItems(inventory);
        return inventory;
    }
    private void addItems(GridPane inventory){
        int row = 2;
        int col = 0;
        Iterator<Map.Entry<Potion, Integer>> iterator = items.entrySet().iterator();
        for( int i = 0; i < 18 ; i++){
            Map.Entry<Potion, Integer> entry;
            if( col == 0 || col == 5){
                Text spaceHolder = new Text("      ");
                inventory.add(spaceHolder, col, row);
            } else {
                ItemButton itembutton;
                if( iterator.hasNext()){
                    entry = iterator.next();
                    Potion potion = entry.getKey();
                    itembutton = new ItemButton(potion);
                    itembutton.setText("        "+entry.getValue()+"");

                    itembutton.getStyleClass().add(potion.getName());
                } else{
                    itembutton = new ItemButton(null);
                    itembutton.getStyleClass().add("Transparent");
                }
                itembutton.setPrefSize(50, 45);
                itembutton.setOnAction(event -> {
                    if(itembutton.getPotion() != null) {
                        Potion consumable = itembutton.getPotion();
                        consumable.consume(player);
                        loseItem(consumable);
                        if(items.get(consumable) > 0) {
                            itembutton.setText("        "+items.get(consumable));
                        } else{
                            itembutton.getStyleClass().add("Transparent");
                            itembutton.setPotion(null);
                            itembutton.setText("");
                        }

                    }
                });
                inventory.add(itembutton, col, row);
            }
            if( col == 5){
                row++;
                col = 0;
            } else{
                col++;
            }
        }
        for( int i = 0 ; i< 6; i++){
            inventory.add(new Text("      "), i, row);
        }
        for( int i = 0 ; i< 6; i++){
            inventory.add(new Text("      "), i, 0);
        }
    }
    public void show(){
        if(!pane.getChildren().contains(inv)) {
            pane.getChildren().add(inv);
        }
    }
    public void close(){
        pane.getChildren().remove(inv);
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
