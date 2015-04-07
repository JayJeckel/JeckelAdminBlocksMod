package jeckeladminblocksmod.content.fluidprovider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

	public boolean hasTransparency() { return this._transparency; }
	public void hasTransparency(boolean value) { this._transparency = value; }
	private boolean _transparency;

	@SideOnly(Side.CLIENT)
	@Override public int getRenderBlockPass() { return (this.hasTransparency() ? 1 : 0); }

	@Override public boolean isOpaqueCube() { return !this.hasTransparency(); }

	@Override public TileEntity createNewTileEntity(World world, int meta) { return new TileFluidProvider(); }

	@Override public int damageDropped(int meta) { return 0; }

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return true;
	}

	@Override public int getRenderType() { return SimpleRendererFluidProvider.renderType; }
}
