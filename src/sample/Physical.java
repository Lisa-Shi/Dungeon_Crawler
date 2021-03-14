package sample;

public interface Physical {
    /**
     * Gets physics information of object
     *
     * @param camera camera that focuses on object
     */
    void update(Camera camera);
    PhysicsController getPhysics();
}
