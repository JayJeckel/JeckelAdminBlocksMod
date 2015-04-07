package jeckeladminblocksmod.content.fluidprovider;

import java.util.ArrayList;
import java.util.List;

import jeckeladminblocksmod.JeckelAdminBlocksMod;
import jeckeladminblocksmod.content.ContentManager;
import jeckeladminblocksmod.core.InfiniteFluidTank;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.api.tiles.ITileProcessor;
import jeckelcorelibrary.api.tiles.ITileTanker;
import jeckelcorelibrary.base.tiles.ATileInventory;
import jeckelcorelibrary.core.processes.special.TankExchangeProcess;
import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileFluidProvider
extends ATileInventory
implements ITileInteractable, ITileGuiActivator, IFluidHandler, ITileProcessor, ITileTanker
{
	public TileFluidProvider()
	{
		this(false);
	}

	public TileFluidProvider(final boolean dummy)
	{
		super(3);
		this.setTileName(ContentManager.ModBlocks.fluid_provider.getUnlocalizedName() + ".name");

		this._processes = new ArrayList<ITickProcess>();
		this._tanks = new ArrayList<FluidTank>();

		this._tank = new InfiniteFluidTank(32000);
		this._tanks.add(this._tank);
		this.tankExchanger = new TankExchangeProcess("fluid_transfer", 10, false, this, this.getTank(), 0, 1);
		this._processes.add(this.tankExchanger);
	}

	private final List<ITickProcess> _processes;

	private final List<FluidTank> _tanks;

	private final FluidTank _tank;

	public final ITickProcess tankExchanger;

	public FluidStack getLocalFluidStack()
	{
		final ItemStack stack = this.getStackInSlot(2);
		if (stack == null) { return null; }
		return FluidUtil.getFluid(stack);
	}

	@Override public void updateEntity()
	{
		boolean dirty = false;

		if (!this.worldObj.isRemote)
		{
			final FluidStack local = this.getLocalFluidStack();
			final FluidStack internal = this.getTankFluid();
			boolean changed = false;
			if (local == null && internal != null) { this.getTank().setFluid(null); changed = true; }
			else if (local != null && internal == null) { this.getTank().setFluid(local); changed = true; }
			else if (local != null && internal != null && !local.isFluidEqual(internal)) { this.getTank().setFluid(local); changed = true; }

			if (changed)
			{
				dirty = true;
				final int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, (meta + 1) % 2, 3);
				this.tankExchanger.cancelCycle(this.worldObj);
			}

			if (this.tankExchanger.updateProcess(this.worldObj)) { dirty = true; }
		}

		if (dirty) { this.markDirty(); }
	}


	// ##################################################
	//
	// Tank Capacity
	//
	// ##################################################

	public FluidTank getTank() { return this._tank; }

	public FluidStack getTankFluid() { return this._tank.getFluid(); }


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
	// ITileGuiActivator
	//
	// ##################################################

	@Override public Object createContainer(EntityPlayer player) { return new ContainerFluidProvider(player, this); }

	@Override public Object createScreen(EntityPlayer player) { return new ScreenFluidProvider(player, this); }


	// ##################################################
	//
	// ITileProcessor
	//
	// ##################################################

	@Override public List<ITickProcess> getProcesses() { return this._processes; }


	// ##################################################
	//
	// ITileTanker
	//
	// ##################################################

	@Override public List<FluidTank> getTanks() { return this._tanks; }


	// ##################################################
	//
	// IFluidHandler
	//
	// ##################################################

	@Override public int fill(ForgeDirection from, FluidStack resource, boolean doFill) { return 0; }

	@Override public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		FluidStack drained = this._tank.drain(resource.amount, doDrain);
		if (doDrain && drained != null) { this.markDirty(); }
		return drained;
	}

	@Override public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		FluidStack drained = this._tank.drain(maxDrain, doDrain);
		if (doDrain && drained != null) { this.markDirty(); }
		return drained;
	}

	@Override public boolean canFill(ForgeDirection from, Fluid fluid) { return false; }

	@Override public boolean canDrain(ForgeDirection from, Fluid fluid) { return true; }

	@Override public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[] { this._tank.getInfo() };
	}
}
