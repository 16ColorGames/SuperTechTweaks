package com.sixteencolorgames.supertechtweaks.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

/**
 * Parses through the config file
 * 
 * @author oa10712
 *
 */
public class GenerationParser {

	public static ArrayList<WorldGeneratorBase> parseScripts(File config) {
		ArrayList<WorldGeneratorBase> generators = new ArrayList<>();

		JsonParser parser = new JsonParser();
		JsonObject genList = null;
		try {
			genList = (JsonObject) parser.parse(new InputStreamReader(new FileInputStream(config), "utf8"));
		} catch (Throwable t) {
			System.err.println("Critical error reading from a world generation file: " + config
					+ " > Please be sure the file is correct!");
		}
		for (Entry<String, JsonElement> genEntry : genList.entrySet()) {
			String key = genEntry.getKey();// Generator Name
			JsonObject entry = genEntry.getValue().getAsJsonObject();// Generator
																		// Properties
			System.out.println("Loading generator: " + key);

			String[] required = new String[] { "generator", "minHeight", "maxHeight", "chance" };// ensures
																									// that
																									// each
																									// generator
																									// has
																									// all
																									// required
																									// components
			boolean valid = true;
			for (String tag : required) {
				if (!entry.has(tag)) {
					valid = false;
					break;
				}
			}
			if (!valid) {
				System.err.println("Generator is missing required tags: " + key);
				break;
			}
			String generatorType = entry.get("generator").getAsString();
			switch (generatorType) {
			// case "cluster":
			// WorldGeneratorGeneric cluster = parseCluster(entry);
			// cluster.setName(key);
			// generators.add(cluster);
			// break;
			case "vein":
				WorldGeneratorBase vein = parseVein(entry);
				vein.setName(key);
				generators.add(vein);
				break;
			default:
				System.err.println("Unrecognized generator type: " + generatorType);
				break;
			}

		}
		return generators;
	}

	private static WorldGeneratorVein parseVein(JsonObject array) {
		Map<Ores, Double> ores = parseOres(array.get("ore"));
		HashMap<String, Object> params = new HashMap();
		if (array.has("properties") && array.get("properties").isJsonObject()) {
			JsonObject props = array.get("properties").getAsJsonObject();
			if (props.has("branchLength") && props.get("branchLength").isJsonPrimitive()) {
				params.put("branchLength", props.get("branchLength").getAsInt());
			} else {
				System.out.println("Invalid/missing entry for branchLength, setting to 5");
				params.put("branchLength", 5);
			}
		} else {
			params.put("branchLength", 5);
		}
		return new WorldGeneratorVein(ores, array.get("size").getAsInt(), array.get("minHeight").getAsInt(),
				array.get("maxHeight").getAsInt(), array.get("chance").getAsInt(), params);
	}

	private static Map<Ores, Double> parseOres(JsonElement oreElement) {
		HashMap<Ores, Double> ores = new HashMap();
		if (oreElement.isJsonArray()) {
			JsonArray array = oreElement.getAsJsonArray();
			for (JsonElement element : array) {
				if (element.isJsonPrimitive()) {
					ores.put(Ores.valueOf(element.getAsString().toUpperCase()), 1.0);
				} else {
					Object[] weightedOre = getWeightedOre(element.getAsJsonObject());
					ores.put((Ores) weightedOre[0], (Double) weightedOre[1]);
				}
			}
		} else {
			if (oreElement.isJsonPrimitive()) {
				ores.put(Ores.valueOf(oreElement.getAsString().toUpperCase()), 1.0);
			} else {
				Object[] weightedOre = getWeightedOre(oreElement.getAsJsonObject());
				ores.put((Ores) weightedOre[0], (Double) weightedOre[1]);
			}
		}
		ores.forEach((k, v) -> {
			System.out.println(k.getName() + ":" + v);
		});
		return ores;
	}

	private static Object[] getWeightedOre(JsonObject ore) {
		double weight;
		if (ore.has("weight")) {
			weight = ore.get("weight").getAsDouble();
		} else {
			weight = 1.0;
		}
		String name = ore.get("ore").getAsString();
		return new Object[] { Ores.valueOf(name.toUpperCase()), weight };
	}
}
