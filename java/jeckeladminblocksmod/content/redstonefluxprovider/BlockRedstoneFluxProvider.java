package jeckeladminblocksmod.content.redstonefluxprovider;

import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.base.blocks.ABlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRedstoneFluxProvider extends ABlockTile
{
	public BlockRedstoneFluxProvider()
	{
		super(Refs.ModId, "admin_redstone_flux_provider", Material.iron, Block.soundTypeMetal);
		this.isBlockContainer = true;
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileRedstoneFluxProvider(); }

	@Override public int damageDropped(int meta) { return 0; }

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }
}
