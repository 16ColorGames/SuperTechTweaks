/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import com.sixteencolorgames.supertechtweaks.enums.Material;

/**
 *
 * @author oa10712
 */
public interface IMaterialListener {

    public void addMaterial(Material added);

    public void removeMaterial(String removed);
}
