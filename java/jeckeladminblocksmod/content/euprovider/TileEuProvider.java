package jeckeladminblocksmod.content.euprovider;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import jeckeladminblocksmod.JeckelAdminBlocksMod;
import jeckeladminblocksmod.content.ContentManager;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.base.tiles.ATileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEuProvider
extends ATileInventory
implements ITileInteractable, ITileGuiActivator, IEnergySource
{
	public TileEuProvider()
	{
		super(1);
		this.setTileName(ContentManager.ModBlocks.redstone_flux_provider.getUnlocalizedName() + ".name");
	}

	@Override public void onChunkUnload()
	{
		if (!this.worldObj.isRemote) { MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this)); }
		super.onChunkUnload();
	}

	@Override public void invalidate()
	{
		if (!this.worldObj.isRemote) { MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this)); }
		super.invalidate();
	}

	private boolean _first = true;
	@Override public void updateEntity()
	{
		boolean dirty = false;

		if (!this.worldObj.isRemote)
		{
			if (this._first)
			{
				this._first = false;
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			}
		}

		if (dirty) { this.markDirty(); }
	}


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	@Override public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
	}

	@Override public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
	}


	// ##################################################
	//
	// ITileInteractable
	//
	// ##################################################

	@Override public void interact(EntityPlayer player, World world, int x, int y, int z, int side)
	{
		if (player.isSneaking()) { return; }
		player.openGui(JeckelAdminBlocksMod.INSTANCE, 0, world, x, y, z);
	}


	// ##################################################
	//
	// ITileGui
	//
	// ##################################################

	@Override public Object createContainer(EntityPlayer player) { return null; }//new ContainerEuProvider(player, this); }

	@Override public Object createScreen(EntityPlayer player) { return null; }//new ScreenEuProvider(player, this); }


	// ##################################################
	//
	// IEnergySource
	//
	// ##################################################

	@Override public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) { return true; }

	@Override public double getOfferedEnergy() { return 32000; }

	@Override public void drawEnergy(double amount) { }

	@Override public int getSourceTier() { return 1; }
}
