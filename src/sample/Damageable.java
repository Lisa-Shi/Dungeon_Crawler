package sample;

public interface Damageable extends Physical {
    int getHealth();
    void hurt(int healthDamage);
}
