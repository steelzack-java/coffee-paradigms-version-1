package com.steelzack.coffee.system.objects;

import com.steelzack.coffee.system.objecs.CoffeeStats;
import lombok.Getter;

@Getter
public class CoffeeStatsImpl implements CoffeeStats {
    private double averageCoffee;

    private int fastestProgrammerTime;

    private int slowesProgrammerTime;

    private int coffeSoldCash;

    private int coffeSoldCard;

    private int coffeExpresso;

    private int coffeeMachiatto;

    private int coffeCappuccino;

    public CoffeeStatsImpl(int coffeSoldCash, int coffeSoldCard,
                           int coffeExpresso, int coffeeMachiatto, int coffeCappuccino,
                           double averageCoffee, int fastestProgrammerTime,
                           int slowesProgrammerTime) {
        super();
        this.coffeSoldCash = coffeSoldCash;
        this.coffeSoldCard = coffeSoldCard;
        this.coffeExpresso = coffeExpresso;
        this.coffeeMachiatto = coffeeMachiatto;
        this.coffeCappuccino = coffeCappuccino;
        this.averageCoffee = averageCoffee;
        this.fastestProgrammerTime = fastestProgrammerTime;
        this.slowesProgrammerTime = slowesProgrammerTime;
    }

    @Override
    public String toString() {
        return "Totalcoffee sold: " + (coffeSoldCard + coffeSoldCash) + //
                "\npayment by credit card: " + coffeSoldCard + //
                "\npayment by cash: " + coffeSoldCash + //
                "\nTotal coffee dispensed:" + (coffeCappuccino + coffeeMachiatto + coffeExpresso) + //
                "\ncapuccinno: " + coffeCappuccino + //
                "\nmachiatto: " + coffeeMachiatto + //
                "\nexpresso: " + coffeExpresso + //
                "\nAverage time spent: " + averageCoffee + //
                "\nSlowest EmployeeImpl: " + slowesProgrammerTime + //
                "\nFastest EmployeeImpl: " + fastestProgrammerTime;
    }
}
