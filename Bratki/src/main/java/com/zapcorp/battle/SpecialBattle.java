package com.zapcorp.battle;

import com.zapcorp.enemy.Enemy;
import com.zapcorp.enemy.FactoryProducer;

public class SpecialBattle extends BattleDecorator {

    SpecialBattle(Battle battle) {
        super(battle);
    }

    @Override
    public void init() {
        Enemy enemy = FactoryProducer.getFactory(false).getEnemy(4);
        this.decoratedBattle.setEnemy(enemy);
        this.decoratedBattle.setEnemyCount(100);
        this.decoratedBattle.setEnemy();
    }

}
