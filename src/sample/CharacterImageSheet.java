package sample;

public class CharacterImageSheet extends ImageSheet {
    // Variables
    private DirectionalImageSheet standSheet;
    private DirectionalImageSheet walkSheet;

    // Constructors
    public CharacterImageSheet(DirectionalImageSheet standSheet, DirectionalImageSheet walkSheet) {
        this.standSheet = standSheet;
        this.walkSheet = walkSheet;
        setInitialReel(standSheet.getInitialReel());
    }

    // Getters
    public DirectionalImageSheet getStandSheet() {
        return standSheet;
    }
    public DirectionalImageSheet getWalkSheet() {
        return walkSheet;
    }
}
