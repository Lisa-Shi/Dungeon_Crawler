package sample;

public interface Physical {
    /**
     * Gets physics information of object
     *
     * @return physics information
     */
    void update(Camera camera);
    PhysicsController getPhysics();
}
