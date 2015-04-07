package jeckeladminblocksmod.content.fluidprovider;

import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.base.blocks.ABlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFluidProvider extends ABlockTile
{
	public BlockFluidProvider()
	{
		super(Refs.ModId, "admin_fluid_provider", Material.iron, Block.soundTypeMetal);
		this.isBlockContainer = true;
		this.hasTransparency(true);
		this.setLightOpacity(0);
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileFluidProvider(); }

	@Override public int damageDropped(int meta) { return 0; }

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }

	@Override public int getRenderType() { return SimpleRendererFluidProvider.renderType; }
}
