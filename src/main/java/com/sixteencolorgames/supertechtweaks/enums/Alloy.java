/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author oa10712
 */
public class Alloy {

    public static ArrayList<Alloy> alloys = new ArrayList();
    public static int ENERGY_DUST = 1600;
    public static int ENERGY_INGOT = 2400;

    AlloyElement result;
    List<AlloyElement> inputs;

    public Alloy(AlloyElement end, AlloyElement... input) {
        result = end;
        inputs = new ArrayList();
        inputs.addAll(Arrays.asList(input));
    }

    public static ArrayList<Alloy> alloysContain(Material ore) {
        ArrayList<Alloy> ret = new ArrayList<>();
        alloys.forEach((alloy) -> {
            alloy.inputs.stream().filter((element) -> (element.getOre() == ore)).forEachOrdered((_item) -> {
                ret.add(alloy);
            });
        });
        return ret;
    }

    public AlloyElement getResult() {
        return result;
    }

    public List<AlloyElement> getInputs() {
        return inputs;
    }

    public int getInCount() {
        int ret = 0;
        ret = this.getInputs().stream().map((ae) -> ae.getAmount()).reduce(ret, Integer::sum);
        return ret;
    }

}
