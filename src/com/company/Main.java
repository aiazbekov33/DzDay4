package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 1000; //Жизнь Бооса
    public static int bossDamage = 50;  //сила урона
    public static String bossDefenceType = " ";
    public static int heroesHelp = 15; //Лечение
    public static Random random = new Random();
    public static int[] heroesHealth = {250, 250, 250, 250, 800, 300, 500, 400}; //Жизнь героев
    public static int[] heroesDamage = {20, 20, 20, 0, 10, 5, 30, 35}; //Урон героев
    public static String[] heroesAttackType = {"Physical", "Magical", "Mental", "Hippocrates", "Juggernaut", "Flash", "Berserk", "Thor"};
    public static void main(String[] args) {
        fightInfo();
        while (!isFinished()) {
            round();
        }
    }

    public static void round() {
        changeBossDefence();
        if (randomAbilityThor())
            bossHit();
        heroesTreatment();
        flashAbility();
        heroesHit();
        fightInfo();
        changeBerserkDefence(bossDamage / 2);
    }

    public static boolean isFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0 && heroesHealth[3] <= 0
                && heroesHealth[4] <= 0 && heroesHealth[5] <= 0 && heroesHealth[6] <= 0 && heroesHealth[7] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static boolean randomAbilityThor() {
        if (heroesHealth[7] > 0)
            return random.nextBoolean();
        return true;
    }

    public static void changeBossDefence() { //блок Босса
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void changeBerserkDefence(int damage) {
        heroesHealth[6] -= damage;
        heroesDamage[6] += damage;

    }

    public static void bossHit() { //Босс
        for (int i = 0; i < heroesHealth.length; i++) {

            if (heroesHealth[4] > 0) {
                if (6 == i && heroesHealth[6] > 0) {
                    int abilityDamage = bossDamage / 2;
                    changeBerserkDefence(abilityDamage / 2);

                } else
                    heroesHealth[i] -= bossDamage / 2;

                heroesHealth[4] -= bossDamage / 2;
            } else if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0;
            } else if (6 == i && heroesHealth[6] > 0)
                changeBerserkDefence(bossDamage / 2);
            else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;

            }
        }
    }

    public static void flashAbility() {
        boolean flashChance = random.nextBoolean();
        if (heroesHealth[5] <= 0) {
            heroesHealth[5] = 0;
        } else {
            if (flashChance) {
                heroesHealth[4] += bossDamage;
                System.out.println("Flash dodged!");
            }
        }
    }

    public static void heroesTreatment() {        //Медик

        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0)
                    heroesHealth[i] += heroesHelp;
            }
            System.out.println("Medik save Heroes");
        }
    }


    public static void heroesHit() { //Герои
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType.equals(heroesAttackType[i])) {
                    int koef = random.nextInt(9) + 2;
                    if (bossHealth - heroesDamage[i] * koef < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * koef;
                    }
                    System.out.println(heroesAttackType[i] + " critical hit " + heroesDamage[i] * koef);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void fightInfo() {
        heroesDamage[6] = 30;
        System.out.println("_______________________");
        System.out.println("Boss health: " + bossHealth);
        System.out.println("Warrior health: " + heroesHealth[0]);
        System.out.println("Magic health: " + heroesHealth[1]);
        System.out.println("Kinetic health: " + heroesHealth[2]);
        System.out.println("Hippocrates health: " + heroesHealth[3]);
        System.out.println("Juggernaut health: " + heroesHealth[4]);
        System.out.println("Flash health: " + heroesHealth[5]);
        System.out.println("Berserk health: " + heroesHealth[6]);
        System.out.println("Thor health: " + heroesHealth[7]);

        System.out.println("_______________________");
    }

}
