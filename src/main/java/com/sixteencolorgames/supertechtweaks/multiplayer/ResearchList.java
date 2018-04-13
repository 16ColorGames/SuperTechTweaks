package com.sixteencolorgames.supertechtweaks.multiplayer;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sixteencolorgames.supertechtweaks.enums.Research;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResearchList {
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, Research> researchs = Maps.<ResourceLocation, Research> newHashMap();
	/** All researchs that do not have a parent. */
	private final Set<Research> roots = Sets.<Research> newLinkedHashSet();
	private final Set<Research> nonRoots = Sets.<Research> newLinkedHashSet();
	private ResearchList.Listener listener;

	@SideOnly(Side.CLIENT)
	private void remove(Research researchIn) {
		for (Research research : researchIn.getChildren()) {
			this.remove(research);
		}

		LOGGER.info("Forgot about research " + researchIn.getId());
		this.researchs.remove(researchIn.getId());

		if (researchIn.getParent() == null) {
			this.roots.remove(researchIn);

			if (this.listener != null) {
				this.listener.rootResearchRemoved(researchIn);
			}
		} else {
			this.nonRoots.remove(researchIn);

			if (this.listener != null) {
				this.listener.nonRootResearchRemoved(researchIn);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void removeAll(Set<ResourceLocation> ids) {
		for (ResourceLocation resourcelocation : ids) {
			Research research = this.researchs.get(resourcelocation);

			if (research == null) {
				LOGGER.warn("Told to remove research " + resourcelocation + " but I don't know what that is");
			} else {
				this.remove(research);
			}
		}
	}

	public void loadResearchs(Map<ResourceLocation, Research.Builder> researchsIn) {
		Function<ResourceLocation, Research> function = Functions.<ResourceLocation, Research> forMap(this.researchs,
				null);
		label42:

		while (!researchsIn.isEmpty()) {
			boolean flag = false;
			Iterator<Entry<ResourceLocation, Research.Builder>> iterator = researchsIn.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<ResourceLocation, Research.Builder> entry = (Entry) iterator.next();
				ResourceLocation resourcelocation = entry.getKey();
				Research.Builder research$builder = entry.getValue();

				if (research$builder.resolveParent(function)) {
					Research research = research$builder.build(resourcelocation);
					this.researchs.put(resourcelocation, research);
					flag = true;
					iterator.remove();

					if (research.getParent() == null) {
						this.roots.add(research);

						if (this.listener != null) {
							this.listener.rootResearchAdded(research);
						}
					} else {
						this.nonRoots.add(research);

						if (this.listener != null) {
							this.listener.nonRootResearchAdded(research);
						}
					}
				}
			}

			if (!flag) {
				iterator = researchsIn.entrySet().iterator();

				while (true) {
					if (!iterator.hasNext()) {
						break label42;
					}

					Entry<ResourceLocation, Research.Builder> entry1 = (Entry) iterator.next();
					LOGGER.error("Couldn't load research " + entry1.getKey() + ": " + entry1.getValue());
				}
			}
		}

		LOGGER.info("Loaded " + this.researchs.size() + " researchs");
	}

	public void clear() {
		this.researchs.clear();
		this.roots.clear();
		this.nonRoots.clear();

		if (this.listener != null) {
			this.listener.researchsCleared();
		}
	}

	public Iterable<Research> getRoots() {
		return this.roots;
	}

	public Iterable<Research> getResearchs() {
		return this.researchs.values();
	}

	@Nullable
	public Research getResearch(ResourceLocation id) {
		return this.researchs.get(id);
	}

	@SideOnly(Side.CLIENT)
	public void setListener(@Nullable ResearchList.Listener listenerIn) {
		this.listener = listenerIn;

		if (listenerIn != null) {
			for (Research research : this.roots) {
				listenerIn.rootResearchAdded(research);
			}

			for (Research research1 : this.nonRoots) {
				listenerIn.nonRootResearchAdded(research1);
			}
		}
	}

	public interface Listener {
		void rootResearchAdded(Research researchIn);

		@SideOnly(Side.CLIENT)
		void rootResearchRemoved(Research researchIn);

		void nonRootResearchAdded(Research researchIn);

		@SideOnly(Side.CLIENT)
		void nonRootResearchRemoved(Research researchIn);

		void researchsCleared();
	}
}