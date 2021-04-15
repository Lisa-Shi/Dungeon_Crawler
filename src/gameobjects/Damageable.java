package gameobjects;

import gameobjects.physics.collisions.Physical;

public interface Damageable extends Physical {
    int getHealth();
    void hurt(int healthDamage);
}
