package sample;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ConfigurationScreen {
    private int difficulty = 0;
    private String name;
    private int weapon = 0;
    private Player player;
    private TextField nameInput;
    private ToggleGroup difficultyToggles;
    private ToggleGroup weaponToggles;

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
        nameInput = new TextField();
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
        difficultyToggles = selectToggleB(dOptions, dHb);
        //Setting up display
        Label groupLabel = new Label("Select difficulty: ");
        return new VBox(groupLabel, dHb);
    }

    private VBox weaponField() {
        String[] wOptions = new String[3];
        wOptions[0] = "1name";
        wOptions[1] = "2name";
        wOptions[2] = "3name";
        HBox wHb = new HBox();
        //Setting weapon for the player
        weaponToggles = selectToggleB(wOptions, wHb);
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
        //get user input from button
        name = nameInput.getText();
        ToggleButton selectD = (ToggleButton) difficultyToggles.getSelectedToggle();
        difficulty = selectD == null ? -1 :Integer.parseInt(selectD.getText().charAt(0) + "");
        ToggleButton selectW = (ToggleButton) weaponToggles.getSelectedToggle();
        weapon = selectW == null ? -1 :Integer.parseInt(selectW.getText().charAt(0) + "");
        Weapon playerW = null;
        Weapon[] wOptions = new Weapon[3];
        wOptions[0] = new Weapon("1name","1about",1,1);
        wOptions[1] = new Weapon("2name","2about",1,1);
        wOptions[2] = new Weapon("3name","3about",1,1);
        if (weapon != -1) {
            playerW = wOptions[weapon];
        }
        return new Player(name, playerW, difficulty);
    }
}
