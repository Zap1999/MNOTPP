package com.zapcorp.enemy;

public class EnemyBossFactory extends AbstractFactory {

    @Override
    public Enemy getEnemy(int lvl) {
        return new EnemyBoss(lvl);
    }

}
