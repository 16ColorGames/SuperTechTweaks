/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.enums;

public class AlloyElement {

    private final Ores ore;

    private final int amount;

    public AlloyElement(Ores ore, int amount) {
        this.ore = ore;
        this.amount = amount;
    }

    public Ores getOre() {
        return ore;
    }

    public int getAmount() {
        return amount;
    }
}
