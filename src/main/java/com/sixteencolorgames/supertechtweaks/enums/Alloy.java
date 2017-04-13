/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.enums;

import java.util.ArrayList;
import java.util.List;
import scala.actors.threadpool.Arrays;

/**
 *
 * @author oa10712
 */
public class Alloy {

    public static ArrayList<Alloy> alloys = new ArrayList();

    AlloyElement result;
    List<AlloyElement> inputs;

    public Alloy(AlloyElement end, AlloyElement... input) {
        result = end;
        inputs = Arrays.asList(input);
    }

}