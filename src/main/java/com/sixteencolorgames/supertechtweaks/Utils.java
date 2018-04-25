/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.core.util.Loader;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author oa10712
 */
public class Utils {

	public static void copyFileUsingStream(String source, File dest) throws IOException {

		InputStream is = Loader.getResource(source, null).openStream();
		OutputStream os = new FileOutputStream(dest);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		os.close();
	}

	public static void copyFileUsingStream(String source, String dest) throws IOException {

		copyFileUsingStream(source, new File(dest));
	}

	public static void setNBTInt(ItemStack stack, String key, int val) {
		getTag(stack).setInteger(key, val);
	}

	public static int getNBTInt(ItemStack stack, String key) {
		return stack.hasTagCompound() ? getTag(stack).getInteger(key) : 0;
	}

	public static NBTTagCompound getTag(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}

}
