package com.sixteencolorgames.supertechtweaks.enums;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

public class Research {
	private final Research parent;
	private final DisplayInfo display;
	private final AdvancementRewards rewards;
	private final ResourceLocation id;
	private final Map<String, Criterion> criteria;
	private final String[][] requirements;
	private final Set<Research> children = Sets.<Research> newLinkedHashSet();
	private final ITextComponent displayText;

	public Research(ResourceLocation id, @Nullable Research parentIn, @Nullable DisplayInfo displayIn,
			AdvancementRewards rewardsIn, Map<String, Criterion> criteriaIn, String[][] requirementsIn) {
		this.id = id;
		this.display = displayIn;
		this.criteria = ImmutableMap.copyOf(criteriaIn);
		this.parent = parentIn;
		this.rewards = rewardsIn;
		this.requirements = requirementsIn;

		if (parentIn != null) {
			parentIn.addChild(this);
		}

		if (displayIn == null) {
			this.displayText = new TextComponentString(id.toString());
		} else {
			this.displayText = new TextComponentString("[");
			this.displayText.getStyle().setColor(displayIn.getFrame().getFormat());
			ITextComponent itextcomponent = displayIn.getTitle().createCopy();
			ITextComponent itextcomponent1 = new TextComponentString("");
			ITextComponent itextcomponent2 = itextcomponent.createCopy();
			itextcomponent2.getStyle().setColor(displayIn.getFrame().getFormat());
			itextcomponent1.appendSibling(itextcomponent2);
			itextcomponent1.appendText("\n");
			itextcomponent1.appendSibling(displayIn.getDescription());
			itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, itextcomponent1));
			this.displayText.appendSibling(itextcomponent);
			this.displayText.appendText("]");
		}
	}

	/**
	 * Creates a new research builder with the data from this research
	 */
	public Research.Builder copy() {
		return new Research.Builder(this.parent == null ? null : this.parent.getId(), this.display, this.rewards,
				this.criteria, this.requirements);
	}

	/**
	 * Get the {@code Research} that is this {@code Research}'s parent. This
	 * determines the tree structure that appears in the
	 * {@linkplain GuiScreenResearchs GUI}.
	 * 
	 * @return the parent {@code Research} of this {@code Research}, or
	 *         {@code null} to signify that this {@code
	 * Research} is a root with no parent.
	 */
	@Nullable
	public Research getParent() {
		return this.parent;
	}

	/**
	 * Get information that defines this {@code Research}'s appearance in GUIs.
	 * 
	 * @return information that defines this {@code Research}'s appearance in
	 *         GUIs. If {@code null}, signifies an invisible {@code Research}.
	 */
	@Nullable
	public DisplayInfo getDisplay() {
		return this.display;
	}

	public AdvancementRewards getRewards() {
		return this.rewards;
	}

	public String toString() {
		return "SimpleResearch{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId())
				+ ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria
				+ ", requirements=" + Arrays.deepToString(this.requirements) + '}';
	}

	/**
	 * Get the children of this {@code Research}.
	 * 
	 * @return an {@code Iterable} of this {@code Research}'s children.
	 * @see #getParent()
	 */
	public Iterable<Research> getChildren() {
		return this.children;
	}

	/**
	 * Get the {@link Criterion Criteria} used to decide the completion of this
	 * {@code Research}. Each key-value pair consists of a {@code Criterion} and
	 * its name.
	 * 
	 * @return the criteria used to decide the completion of this
	 *         {@code Research}
	 * @see #getRequirements()
	 */
	public Map<String, Criterion> getCriteria() {
		return this.criteria;
	}

	/**
	 * Get how many requirements this {@code Research} has.
	 * 
	 * @return {@code this.getRequirements().length}
	 * @see #getRequirements()
	 */
	@SideOnly(Side.CLIENT)
	public int getRequirementCount() {
		return this.requirements.length;
	}

	/**
	 * Add the given {@code Research} as a child of this {@code Research}.
	 * 
	 * @see #getParent()
	 */
	public void addChild(Research researchIn) {
		this.children.add(researchIn);
	}

	/**
	 * Get this {@code Research}'s unique identifier.
	 * 
	 * @return this {@code Research}'s unique identifier
	 */
	public ResourceLocation getId() {
		return this.id;
	}

	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof Research)) {
			return false;
		} else {
			Research research = (Research) p_equals_1_;
			return this.id.equals(research.id);
		}
	}

	public int hashCode() {
		return this.id.hashCode();
	}

	public String[][] getRequirements() {
		return this.requirements;
	}

	/**
	 * Returns the {@code ITextComponent} that is shown in the chat message sent
	 * after this {@code Research} is completed.
	 * 
	 * @return the {@code ITextComponent} that is shown in the chat message sent
	 *         after this {@code Research} is completed. If this
	 *         {@code Research} is {@linkplain #getDisplay() invisible}, then it
	 *         consists simply of {@link #getId()}. Otherwise, it is the
	 *         {@linkplain DisplayInfo#getTitle() title} inside square brackets,
	 *         colored by the
	 *         {@linkplain net.minecraft.researchs.FrameType#getFormat frame
	 *         type}, and hovering over it shows the
	 *         {@linkplain DisplayInfo#getDescription() description}.
	 */
	public ITextComponent getDisplayText() {
		return this.displayText;
	}

	public static class Builder {
		private final ResourceLocation parentId;
		private Research parent;
		private final DisplayInfo display;
		private final AdvancementRewards rewards;
		private final Map<String, Criterion> criteria;
		private final String[][] requirements;

		Builder(@Nullable ResourceLocation p_i47414_1_, @Nullable DisplayInfo p_i47414_2_,
				AdvancementRewards p_i47414_3_, Map<String, Criterion> p_i47414_4_, String[][] p_i47414_5_) {
			this.parentId = p_i47414_1_;
			this.display = p_i47414_2_;
			this.rewards = p_i47414_3_;
			this.criteria = p_i47414_4_;
			this.requirements = p_i47414_5_;
		}

		/**
		 * Tries to resolve the parent of this research, if possible. Returns
		 * true on success.
		 */
		public boolean resolveParent(Function<ResourceLocation, Research> lookup) {
			if (this.parentId == null) {
				return true;
			} else {
				this.parent = lookup.apply(this.parentId);
				return this.parent != null;
			}
		}

		public Research build(ResourceLocation id) {
			return new Research(id, this.parent, this.display, this.rewards, this.criteria, this.requirements);
		}

		public void writeTo(PacketBuffer buf) {
			if (this.parentId == null) {
				buf.writeBoolean(false);
			} else {
				buf.writeBoolean(true);
				buf.writeResourceLocation(this.parentId);
			}

			if (this.display == null) {
				buf.writeBoolean(false);
			} else {
				buf.writeBoolean(true);
				this.display.write(buf);
			}

			Criterion.serializeToNetwork(this.criteria, buf);
			buf.writeVarInt(this.requirements.length);

			for (String[] astring : this.requirements) {
				buf.writeVarInt(astring.length);

				for (String s : astring) {
					buf.writeString(s);
				}
			}
		}

		public String toString() {
			return "Task Research{parentId=" + this.parentId + ", display=" + this.display + ", rewards=" + this.rewards
					+ ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + '}';
		}

		public static Research.Builder deserialize(JsonObject json, JsonDeserializationContext context) {
			ResourceLocation resourcelocation = json.has("parent")
					? new ResourceLocation(JsonUtils.getString(json, "parent")) : null;
			DisplayInfo displayinfo = json.has("display")
					? DisplayInfo.deserialize(JsonUtils.getJsonObject(json, "display"), context) : null;
			AdvancementRewards researchrewards = (AdvancementRewards) JsonUtils.deserializeClass(json, "rewards",
					AdvancementRewards.EMPTY, context, AdvancementRewards.class);
			Map<String, Criterion> map = Criterion.criteriaFromJson(JsonUtils.getJsonObject(json, "criteria"), context);

			if (map.isEmpty()) {
				throw new JsonSyntaxException("Research criteria cannot be empty");
			} else {
				JsonArray jsonarray = JsonUtils.getJsonArray(json, "requirements", new JsonArray());
				String[][] astring = new String[jsonarray.size()][];

				for (int i = 0; i < jsonarray.size(); ++i) {
					JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonarray.get(i), "requirements[" + i + "]");
					astring[i] = new String[jsonarray1.size()];

					for (int j = 0; j < jsonarray1.size(); ++j) {
						astring[i][j] = JsonUtils.getString(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
					}
				}

				if (astring.length == 0) {
					astring = new String[map.size()][];
					int k = 0;

					for (String s2 : map.keySet()) {
						astring[k++] = new String[] { s2 };
					}
				}

				for (String[] astring1 : astring) {
					if (astring1.length == 0 && map.isEmpty()) {
						throw new JsonSyntaxException("Requirement entry cannot be empty");
					}

					for (String s : astring1) {
						if (!map.containsKey(s)) {
							throw new JsonSyntaxException("Unknown required criterion '" + s + "'");
						}
					}
				}

				for (String s1 : map.keySet()) {
					boolean flag = false;

					for (String[] astring2 : astring) {
						if (ArrayUtils.contains(astring2, s1)) {
							flag = true;
							break;
						}
					}

					if (!flag) {
						throw new JsonSyntaxException("Criterion '" + s1
								+ "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
					}
				}

				return new Research.Builder(resourcelocation, displayinfo, researchrewards, map, astring);
			}
		}

		public static Research.Builder readFrom(PacketBuffer buf) throws IOException {
			ResourceLocation resourcelocation = buf.readBoolean() ? buf.readResourceLocation() : null;
			DisplayInfo displayinfo = buf.readBoolean() ? DisplayInfo.read(buf) : null;
			Map<String, Criterion> map = Criterion.criteriaFromNetwork(buf);
			String[][] astring = new String[buf.readVarInt()][];

			for (int i = 0; i < astring.length; ++i) {
				astring[i] = new String[buf.readVarInt()];

				for (int j = 0; j < astring[i].length; ++j) {
					astring[i][j] = buf.readString(32767);
				}
			}

			return new Research.Builder(resourcelocation, displayinfo, AdvancementRewards.EMPTY, map, astring);
		}
	}
}