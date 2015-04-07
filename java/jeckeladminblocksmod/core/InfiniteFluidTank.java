package jeckeladminblocksmod.core;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class InfiniteFluidTank extends FluidTank
{
	public static int min(final int a, final int b) { return (a <= b ? a : b); }

	public InfiniteFluidTank(int capacity)
	{
		super(capacity);
	}

	public InfiniteFluidTank(FluidStack stack, int capacity)
	{
		super(stack.getFluid(), capacity, capacity);
	}

	public InfiniteFluidTank(Fluid fluid, int capacity)
	{
		super(fluid, capacity, capacity);
	}

	@Override public void setFluid(FluidStack fluid)
	{
		this.fluid = (fluid == null ? null : new FluidStack(fluid.getFluid(), this.getCapacity()));
	}

	@Override public void setCapacity(int capacity)
	{
		this.capacity = capacity;
		if (this.fluid != null) { this.fluid.amount = this.getCapacity(); }
	}

	@Override public int fill(FluidStack resource, boolean doFill) { return 0; }

	@Override public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (fluid == null) { return null; }

		final int drained = min(maxDrain, this.getCapacity());
		final FluidStack stack = new FluidStack(fluid, drained);
		if (doDrain)
		{
			if (tile != null)
			{
				FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluid, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this, drained));
			}
		}
		return stack;
	}
}
