package com.zapcorp.enemy;

public class FactoryProducer {

    public static AbstractFactory getFactory(boolean boss) {

        if(boss) {
            return new EnemyBossFactory();
        }else  {
            return new EnemyUnitFactory();
        }

    }

}
