package jeckeladminblocksmod.content.fluidprovider;

import jeckeladminblocksmod.content.ContentManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class SimpleRendererFluidProvider implements ISimpleBlockRenderingHandler
{
	public static int renderType = 0;

	@Override public int getRenderId() { return SimpleRendererFluidProvider.renderType; }

	@Override public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (this.getRenderId() != modelId) { return; }
		final float min = 0.0F;
		final float max = 1.0F;
		renderInvCuboid(renderer, block, block.getIcon(0, 0), min, min, min, max, max, max);
	}

	@Override public boolean shouldRender3DInInventory(int modelId) { return (this.getRenderId() == modelId); }

	@Override public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (this.getRenderId() != modelId) { return false; }

		renderer.renderStandardBlock(ContentManager.ModBlocks.fluid_provider, (int)x, (int)y, (int)z);

		final TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			final TileFluidProvider tile = (TileFluidProvider) te;
			final FluidStack fluid = tile.getTankFluid();
			if (fluid != null && fluid.getFluid() != null)
			{
				final double min = 0.015;
				final double max = 0.985;
				renderer.setRenderBounds(min, min, min, max, max, max);
				renderFluidBlock(fluid.getFluid(), renderer, x, y, z, false);
			}
		}


		return true;
	}

	public static void renderFluidBlock(Fluid fluid, RenderBlocks renderer, int x, int y, int z, boolean flowing)
	{
		if (fluid == null) { return; }

		IIcon icon = (flowing ? fluid.getFlowingIcon() : fluid.getStillIcon());
		if (icon == null) { return; }

		renderer.setOverrideBlockTexture(icon);
		renderer.renderAllFaces = true;
		renderer.renderStandardBlock(flowing ? Blocks.flowing_water : Blocks.water, x, y, z);
		renderer.clearOverrideBlockTexture();
	}

	public static void renderInvCuboid(RenderBlocks renderer, Block block, IIcon icon, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		renderer.setRenderBoundsFromBlock(block);
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 1.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
