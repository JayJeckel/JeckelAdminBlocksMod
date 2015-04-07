package jeckeladminblocksmod.content.euprovider;

import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.base.blocks.ABlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEuEnergyProvider extends ABlockTile
{
	public BlockEuEnergyProvider()
	{
		super(Refs.ModId, "admin_eu_energy_provider", Material.iron, Block.soundTypeMetal);
		this.isBlockContainer = true;
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileEuProvider(); }

	@Override public int damageDropped(int meta) { return 0; }

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }
}
