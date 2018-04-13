package com.sixteencolorgames.supertechtweaks.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum ResearchState {
	OBTAINED(0), UNOBTAINED(1);

	private final int id;

	private ResearchState(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}