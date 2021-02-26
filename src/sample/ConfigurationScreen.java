package sample;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ConfigurationScreen {
    private int difficulty = 0;
    private String name;
    private int weapon = 0;
    private Player player;

    public BorderPane configLayout() {
        //BorderPane border = new BorderPane();

        HBox nameIn = nameField();
        VBox diffIn = difficultyField();
        VBox weapIn = weaponField();

        VBox all = new VBox(nameIn, diffIn, weapIn);

        //Creating a BorderPane layout
        BorderPane border = new BorderPane();

        //Retrieving the observable list object
        border.setCenter(all);

        return border;
    }

    private HBox nameField() {
        Label nameLabel = new Label("Player name: ");
        TextField nameInput = new TextField();

        HBox hb = new HBox();
        hb.getChildren().addAll(nameLabel, nameInput);
        hb.setSpacing(15);
        return hb;
    }

    private VBox difficultyField() {
        String[] dOptions = new String[3];
        dOptions[0] = "1: easy";
        dOptions[1] = "2: medium";
        dOptions[2] = "3: hard";

        HBox dHb = new HBox();

        //Setting difficulty for the player
        ToggleGroup dTogGroup = selectToggleB(dOptions, dHb);

        ToggleButton dButton =
                (ToggleButton) dTogGroup.getSelectedToggle();

        if (dButton != null) {
            difficulty = Integer.parseInt(dButton.getText().charAt(0) + "");
        }


        //Setting up display
        Label groupLabel = new Label("Select difficulty: ");
        return new VBox(groupLabel, dHb);
    }

    private VBox weaponField() {
        String[] wOptions = new String[3];
        wOptions[0] = new String("1name");
        wOptions[1] = new String("2name");
        wOptions[2] = new String("3name");

        HBox wHb = new HBox();

        //Setting difficulty for the player
        ToggleGroup dTogGroup = selectToggleB(wOptions, wHb);

        ToggleButton wButton =
                (ToggleButton) dTogGroup.getSelectedToggle();

        if (wButton != null) {
            weapon = Integer.parseInt(wButton.getText().charAt(0) + "");
        }

        //Setting up display
        Label groupLabel = new Label("Select weapon: ");
        return new VBox(groupLabel, wHb);
    }

    private ToggleGroup selectToggleB(String[] buttonN, HBox hb) {
        hb.setSpacing(15);

        ToggleGroup togGroup = new ToggleGroup();

        ToggleButton[] togBut = new ToggleButton[buttonN.length];

        for (int i = 0; i < buttonN.length; i++) {
            //adds button to toggle button array
            togBut[i] = new ToggleButton(buttonN[i]);

            //adds button to toggle group
            togBut[i].setToggleGroup(togGroup);

            //adds button to h box container
            hb.getChildren().add(togBut[i]);
        }

        return togGroup;
    }

    public Player createChar() {
        Player p;
        Weapon playerW = null;

        Weapon[] wOptions = new Weapon[3];
        wOptions[0] = new Weapon("1name","1about",1,1);
        wOptions[1] = new Weapon("2name","2about",1,1);
        wOptions[2] = new Weapon("3name","3about",1,1);

        if (weapon != 0) {
            playerW = wOptions[weapon];
        }

        if (difficulty != 0 && playerW != null && name != null) {
            p = new Player(name, playerW, difficulty);
        } else {
            p = new Player();
        }

        return p;
        //will need to add default stuff later when player does not select an option
    }
}
