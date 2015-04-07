package jeckeladminblocksmod.content.redstonefluxprovider;

import jeckeladminblocksmod.JeckelAdminBlocksMod;
import jeckeladminblocksmod.content.ContentManager;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.base.tiles.ATileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileRedstoneFluxProvider
extends ATileInventory
implements ITileInteractable, ITileGuiActivator, IEnergyProvider, IEnergyConnection
{
	public static int min(final int a, final int b) { return (a <= b ? a : b); }

	public TileRedstoneFluxProvider()
	{
		super(1);
		this.setTileName(ContentManager.ModBlocks.redstone_flux_provider.getUnlocalizedName() + ".name");
	}

	@Override public void updateEntity()
	{
		boolean dirty = false;

		if (!this.worldObj.isRemote)
		{
			for (int i = 0; i < 6; i++)
			{
				final ForgeDirection dir = ForgeDirection.getOrientation(i);
				final TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if (tile != null && tile instanceof IEnergyReceiver)
				{
					final IEnergyReceiver receiver = (IEnergyReceiver) tile;
					if (receiver.canConnectEnergy(dir.getOpposite()))
					{
						final int max = receiver.getMaxEnergyStored(dir.getOpposite());
						final int amount = receiver.getEnergyStored(dir.getOpposite());
						final int space = max - amount;
						if (space > 0)
						{
							if (receiver.receiveEnergy(dir.getOpposite(), space, true) > 0)
							{
								receiver.receiveEnergy(dir.getOpposite(), space, false);
							}
						}
					}
				}
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

	@Override public Object createContainer(EntityPlayer player) { return null; }//new ContainerRedstoneFluxProvider(player, this); }

	@Override public Object createScreen(EntityPlayer player) { return null; }//new ScreenRedstoneFluxProvider(player, this); }


	// ##################################################
	//
	// IEnergyProvider
	//
	// ##################################################

	@Override public boolean canConnectEnergy(ForgeDirection from) { return true; }

	@Override public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) { return min(32000, maxExtract); }

	@Override public int getEnergyStored(ForgeDirection from) { return 32000; }

	@Override public int getMaxEnergyStored(ForgeDirection from) { return 32000; }
}
