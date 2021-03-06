package com.zapcorp.enemy;

public class EnemyUnitFactory extends AbstractFactory {

    @Override
    public Enemy getEnemy(int lvl) {
        return EnemyUnits.getUnit(lvl);
    }

}
