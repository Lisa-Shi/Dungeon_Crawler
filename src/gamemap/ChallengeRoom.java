package gamemap;

import gameobjects.npc.ChallengeRoomNPC;
import gameobjects.npc.NPC;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.monsters.Monster;
import gameobjects.physics.Camera;
import gameobjects.physics.collisions.Physical;
import javafx.scene.layout.Pane;

import java.util.Random;

public class ChallengeRoom extends Room{
    private boolean finish = false;

    public boolean isPrizeCollected() {
        return prizeCollected;
    }

    public void setPrizeCollected(boolean prizeCollected) {
        this.prizeCollected = prizeCollected;
    }

    private boolean prizeCollected = false;
    private NPC npc;
    public ChallengeRoom(int width, int height){
        super(width, height);
    }
    public void generateMonsters() {
        if (!isGeneratedMonster()) {
            Monster monster = null;
            Random ran = new Random();
            int numOfMon = ran.nextInt(5) + 1;
            for (int i = 1; i <= numOfMon; i++) {
                monster = this.getRandomMonster();
                monster.evolution(20 / numOfMon);
                add(monster);
            }
        }
        setGeneratedMonster(true);
    }

    public void addNPC() {
        if (npc == null) {
            npc = new ChallengeRoomNPC(this, this.getWidth()/2 - 1, this.getHeight()/2);
            add(npc);
        }
    }
    public void update(Camera camera){
        super.update(camera);
        if (this.getMonsters().size() == 0) {
            this.finish = true;
        }
    }
    public void addRoomLayout() {
        this.setLayout(RoomLayout.design(this, true));
    }
    public void setFinish(Boolean finish) {
        this.finish = finish;
    }
    public void finalize(Pane pane) {
        addRoomLayout();
        addNPC();
        addFloorTiles();
        addSurroundingWalls();
        addAllSprites(pane);
    }
    public void drawMonster(Pane pane){
        generateMonsters();
        for (Monster monster : this.getMonsters()) {
            pane.getChildren().add(monster.getGraphics().getSprite());
        }
        for (Monster monster: getMonsters()) {
            monster.addHPBar(this, pane);
        }
    }
    public boolean isFinish() {
        return finish;
    }

    public NPC getNpc() {
        return npc;
    }
}
