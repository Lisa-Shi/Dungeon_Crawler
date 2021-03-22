package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.*;


public class Monster extends GameObject implements Physical, Collideable, Drawable {
    //monster needs a name too. give some respect to the monster :)
    private String name;
    private int hp;
    private int demage;
    private Sprite sprite;
    private DynamicCollisionBox collisionBox;
    private boolean isDead = false;
    private long lastAttack = System.nanoTime();
    private Timeline timeline = new Timeline();
    private KeyFrame frame;
    private int shootingRange = 3;
    private DynamicCollisionBox attackRange =
            new DynamicCollisionBox(getPhysics(),
                    new RectangleWireframe(shootingRange * Main.TILE_WIDTH, shootingRange * Main.TILE_HEIGHT));
    private int imageindex = 0;
    private int monsterType;
    private List<Image> attackImage;
    private List<Image> EastImage;
    private List<Image> WestImage;
    private List<Image> imageFrom;
    public long lastMove = System.nanoTime();
    public Monster(Room room, String inputName, int hp, int inputDemage, double initialX, double initialY) {
        //trivial image
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT
                    , Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, Main.FLOORTILE);
        monsterType = new Random().nextInt(Main.MONSTERS_ATTACK.size());
        attackImage = Main.MONSTERS_ATTACK.get(monsterType);
        EastImage = Main.MONSTERS_EAST.get(monsterType);
        WestImage = Main.MONSTERS_WEST.get(monsterType);
        imageFrom = WestImage;
        name = inputName;
        this.hp = hp;
        demage = inputDemage;
        sprite = new Sprite((int) initialX, (int) initialY, (int) Main.MONSTER_WIDTH,
                (int) Main.MONSTER_HEIGHT, WestImage.get(0));
        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        this.collisionBox.generate();
        attackRange.generate();
        frame = new KeyFrame(Duration.millis(150), e -> {
            sprite.setImage(imageFrom.get(((imageindex++) % imageFrom.size())));
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public boolean playerInRange(Player player){
        //attack every 1 second
        if( (System.nanoTime() - lastAttack >= 1000000000)
                && attackRange.containsPoint(player.getCollisionBox().getPhysics().getAbsolutePosition())){
            imageFrom = attackImage;
            lastAttack = System.nanoTime();
            player.hurt(demage);
            System.out.println("attacking " + player);
        }
        return attackRange.containsPoint(player.getCollisionBox().getPhysics().getAbsolutePosition());
    }
    public void update(Camera camera, Player player){
        this.update(camera);
        boolean playerInRange = playerInRange(player);
        if( !playerInRange){
            imageFrom = WestImage;
        }
    }
    @Override
    public void update(Camera camera) {
        if( hp <= 0){
            isDead = true;
            collisionBox.setSolid(false);
            getSprite().setImage(Main.CHEST_IMAGE.get(0));
        }

        getPhysics().update();
        updateSprite(camera);
    }
    private void updateSprite(Camera camera) {
        sprite.setTranslateX(getPhysics().getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.MONSTER_WIDTH / 2);
        sprite.setTranslateY(getPhysics().getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.MONSTER_HEIGHT / 2);
    }
    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }

    /**
     * using manhattan distance as heuristic. each path cost is one
     */
    public String heuristicSearch(Player player, Room room){
        Vector2D playerLoc = new Vector2D(Math.round(player.getPhysics().getPosition().getX() / 64)
                , Math.round(player.getPhysics().getPosition().getY() / 64));
        Vector2D monsterLoc = new Vector2D(Math.round(getPhysics().getPosition().getX() / 64)
                , Math.round(getPhysics().getPosition().getY() / 64));
        if( playerLoc.distanceSquared(monsterLoc) <= Math.pow(shootingRange / 2, 2) * 2){
            return "";
        }
        PriorityQueue<State> thePQ = new PriorityQueue<>(1, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if( o1.cost < o2.cost)  return -1;
                if( o1.cost > o2.cost)  return 1;
                return 0;
            }
        });
        LinkedList<Vector2D> visited = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        Map<Vector2D, Double> locAndCost = new HashMap<>();
        State currentState = new State( monsterLoc, path, 0);
        thePQ.add(currentState);
        locAndCost.put(monsterLoc, 0.0);
        while( !thePQ.isEmpty()){
            State popped = thePQ.poll();
            Vector2D current = new Vector2D(popped.state.getX(), popped.state.getY());
            LinkedList<String> currPath = popped.path;
            double cost = popped.cost;
            if( playerLoc.distanceSquared(current) <= 1){
                if(currPath.size() >= 1) {
                    String a = currPath.remove(0);
                    System.out.println(a);
                    return a;
                }
            }
            ArrayList<Vector2D> successors = new ArrayList<>(
                    List.of(new Vector2D(1, 0),
                            new Vector2D(-1, 0),
                            new Vector2D(0, 1),
                            new Vector2D(0, -1))
            );
            ArrayList<Vector2D> removeList = new ArrayList<>();
            for(Vector2D successor: successors){
                if(room.checkObstacle(current.add(successor).multiply(Main.TILE_HEIGHT))){
                    removeList.add(successor);
                }
            }
            successors.removeAll(removeList);
            LinkedList<String> successorPath;
            for( Vector2D successor: successors){
                successorPath = new LinkedList<>(currPath);
                Vector2D successorStateV = current.add(successor);
                double pathCost = cost + successorStateV.distanceSquared(playerLoc) - current.distanceSquared(playerLoc);
                if( successor.getX() > 0){
                    successorPath.add("D");
                } else if( successor.getX() < 0){
                    successorPath.add("A");
                } else if( successor.getY() > 0){
                    successorPath.add("S");
                } else if( successor.getY() < 0){
                    successorPath.add("W");
                }
                State successorState = new State(successorStateV, successorPath, pathCost);
                if( !visited.contains(successorStateV)){
                    thePQ.add(successorState);
                    visited.add(successorStateV);
                    locAndCost.put(successorStateV, pathCost);
                } else if(locAndCost.get(successorStateV) > pathCost){
                    thePQ.add(successorState);
                    locAndCost.put(successorStateV, pathCost);
                }
            }
        }
        return "";
    }
    private class State{
        public Vector2D state;
        public LinkedList<String> path;
        public double cost;
        public State(Vector2D inputState, LinkedList<String> inputPath, double pathCost){
            state = inputState;
            path = inputPath;
            cost = pathCost;
        }
    }
}
