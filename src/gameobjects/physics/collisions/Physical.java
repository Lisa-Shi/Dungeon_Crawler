package gameobjects.physics.collisions;

import gameobjects.physics.Camera;
import gameobjects.physics.PhysicsController;

public interface Physical {
    /**
     * Gets gameobjects.physics information of object
     *
     * @param camera camera that focuses on object
     */
    void update(Camera camera);
    PhysicsController getPhysics();
}
