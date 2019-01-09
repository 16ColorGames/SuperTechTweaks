package com.sixteencolorgames.supertechtweaks.tileentities.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BakedModelExtractor implements IBakedModel {
	private static IModel modelExtractorFlat;

	private final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
	protected final VertexFormat format;
	protected final Map<Map<ArrayList<Boolean>, EnumFacing>, IBakedModel> cache = new HashMap<>();

	public BakedModelExtractor() {
		try {
			modelExtractorFlat = ModelLoaderRegistry
					.getModel(new ResourceLocation(SuperTechTweaksMod.MODID, "block/extractor"));
		}

		catch (Exception e) {
			SuperTechTweaksMod.logger.error(e);
		}

		this.textureGetter = resourceLocation -> Minecraft.getMinecraft().getTextureMapBlocks()
				.getAtlasSprite(resourceLocation.toString());
		this.format = DefaultVertexFormats.BLOCK;
	}

	private IBakedModel getActualModel(ArrayList<Boolean> state, EnumFacing facing) {
		IBakedModel bakedModel;

		Map<ArrayList<Boolean>, EnumFacing> cacheKey = Maps.newHashMap();
		cacheKey.put(state, facing);

		if (cache.containsKey(cacheKey))
			bakedModel = cache.get(cacheKey);

		else {
			IModel modelExtractor = modelExtractorFlat;

			bakedModel = modelExtractor.bake(new TRSRTransformation(facing), format, textureGetter::apply);

			cache.put(cacheKey, bakedModel);
		}

		return bakedModel;
	}

	@Override
	@Nonnull
	public List<BakedQuad> getQuads(@Nullable IBlockState blockState, @Nullable EnumFacing side, long rand) {
		ArrayList<Boolean> state = new ArrayList<>(Arrays.asList(false, false, false, false));
		EnumFacing facing = EnumFacing.NORTH;

		if (blockState instanceof IExtendedBlockState) {
			IExtendedBlockState extendedBlockState = (IExtendedBlockState) blockState;

			if (extendedBlockState.getUnlistedNames().contains(BlockExtractor.FACING))
				facing = extendedBlockState.getValue(BlockExtractor.FACING);
		}

		return getActualModel(state, facing).getQuads(blockState, side, rand);
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ExtractorItemOverrideList.INSTANCE;
	}

	private static class ExtractorItemOverrideList extends ItemOverrideList {
		static ExtractorItemOverrideList INSTANCE = new ExtractorItemOverrideList();

		ExtractorItemOverrideList() {
			super(ImmutableList.of());
		}

		@Override
		@Nonnull
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world,
				@Nullable EntityLivingBase entity) {
			return originalModel;
		}
	}

	@Override
	public boolean isAmbientOcclusion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGui3d() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return null;
	}

}