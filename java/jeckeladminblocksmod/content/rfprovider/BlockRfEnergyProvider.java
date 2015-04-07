package jeckeladminblocksmod.content.rfprovider;

import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.base.blocks.ABlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRfEnergyProvider extends ABlockTile
{
	public BlockRfEnergyProvider()
	{
		super(Refs.ModId, "admin_rf_energy_provider", Material.iron, Block.soundTypeMetal);
		this.isBlockContainer = true;
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileRedstoneFluxProvider(); }

	@Override public int damageDropped(int meta) { return 0; }

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }
}
