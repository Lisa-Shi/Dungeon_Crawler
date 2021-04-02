/**the Image source for the player object
 *
 */
package gameobjects.graphics.functionality;

public class CharacterImageSheet extends ImageSheet {
    // Variables
    private DirectionalImageSheet standSheet;
    private DirectionalImageSheet walkSheet;
    private DirectionalImageSheet attackSheet;

    // Constructors
    public CharacterImageSheet(DirectionalImageSheet standSheet, DirectionalImageSheet walkSheet, DirectionalImageSheet attackSheet) {
        this.standSheet = standSheet;
        this.walkSheet = walkSheet;
        this.attackSheet = attackSheet;
        setInitialReel(standSheet.getInitialReel());
    }

    // Getters
    public DirectionalImageSheet getStandSheet() {
        return standSheet;
    }
    public DirectionalImageSheet getWalkSheet() {
        return walkSheet;
    }
    public DirectionalImageSheet getAttackSheet() {
        return attackSheet;
    }
}
