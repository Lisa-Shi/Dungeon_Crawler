package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * set up for the configuration screen.
 * user set up their character and select the game difficulty in this screen
 */
public class ConfigurationScreen {
    private int width;
    private int height;
    private int difficulty = 0;
    private String name;
    private int weapon = 0;
    private Player player;
    private TextField nameInput;
    private ToggleGroup difficultyToggles;
    private ToggleGroup weaponToggles;

    private Button goBack;
    private Button goRoom;


    public ConfigurationScreen(int width, int height) {
        this.width = width;
        this.height = height;
        goBack = new Button("Back");
        goRoom = new Button("Go to room");
    }
    /**layout setup for the configuration Screen
     * GUI objects are added to the pane in this method
     *
     * @return a setup pane that contains GUI objects
     */
    public Scene getScene() {
        //BorderPane border = new BorderPane();
        HBox nameIn = nameField();
        VBox diffIn = difficultyField();
        VBox weapIn = weaponField();
        VBox center = new VBox(nameIn, diffIn, weapIn);

        //Creating a BorderPane layout
        BorderPane border = new BorderPane();
        border.setCenter(center);

        //HBox bottom = ;
        HBox bottom = LRNavigate.buildBox(goBack, goRoom);
        bottom.setPadding(new Insets(10));
        border.setBottom(bottom);

        //creates scene
        Scene configScene = new Scene(border, width, height);
        return configScene;
    }

    /**
     * set up the text field for user to enter the name
     *
     * @return a Hbox (GUI object)
     */
    private HBox nameField() {
        Label nameLabel = new Label("Player name: ");
        nameInput = new TextField();
        nameInput.setId("nameInput");
        HBox hb = new HBox();
        hb.getChildren().addAll(nameLabel, nameInput);
        hb.setSpacing(15);
        return hb;
    }

    /**
     * set up the toggle buttons for user to select difficulty
     *
     * @return a group of toggle button for difficulty
     */
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

    /**
     * set up the toggle buttons for user to select initial weapon
     *
     * @return a group of toggle button for weapon
     */
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

    /**
     * set up helper method
     * initial a group of toggle buttons with the parameters
     *
     * @param buttonN list of string that will be put on the buttons
     * @param hb hbox that contains the toggle buttons
     * @return a group of toggle buttons
     */
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

    /**
     * method will get the user input from the GUI objects and initial character for the game
     *
     * @return  the player object created with the user inputs
     */
    public Player createChar() {
        //get user input from button
        name = nameInput.getText();
        ToggleButton selectD = (ToggleButton) difficultyToggles.getSelectedToggle();
        difficulty = selectD == null ? -1 : Integer.parseInt(selectD.getText().charAt(0) + "");
        ToggleButton selectW = (ToggleButton) weaponToggles.getSelectedToggle();
        weapon = selectW == null ? -1 : Integer.parseInt(selectW.getText().charAt(0) + "");
        Weapon playerW = null;
        Weapon[] wOptions = new Weapon[3];
        wOptions[0] = new Weapon("1name", "1about", 1, 1);
        wOptions[1] = new Weapon("2name", "2about", 1, 1);
        wOptions[2] = new Weapon("3name", "3about", 1, 1);
        if (weapon != -1) {
            playerW = wOptions[weapon - 1];
        }
        return new Player(name, playerW, difficulty);
    }

    /**
     * Getter method for button to go back
     *
     * @return back button
     */
    public Button getGoBack() {
        return goBack;
    }

    /**
     * Getter method for button to go to room
     *
     * @return room button
     */
    public Button getGoRoom() {
        return goRoom;
    }
}
