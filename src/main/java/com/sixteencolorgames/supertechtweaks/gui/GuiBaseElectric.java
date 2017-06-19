/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author oa10712
 */
public abstract class GuiBaseElectric extends GuiBase {

    public GuiBaseElectric(Container inventorySlotsIn, ResourceLocation background, InventoryPlayer playerInv, IInventory tile) {
        super(inventorySlotsIn, background, playerInv, tile);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int k = this.getEnergyLeftScaled(48);
        this.drawTexturedModalRect(i + 10, j + 66 - k, 0, 30 - k + 206, 14, k);//render power
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX - i > 9 && mouseX - i < 24 && mouseY > j + 18 && mouseY < j + 66) {
            String s = getEnergy() + "/" + getMaxEnergy();
            this.fontRendererObj.drawString(s, mouseX - i, mouseY - j-10, 4210752);//Draw tile name
        }
    }

    abstract public int getEnergy();

    abstract public int getMaxEnergy();

    abstract public int getEnergyLeftScaled(int pixels);
}
