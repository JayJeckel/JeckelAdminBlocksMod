package jeckeladminblocksmod.content.fluidprovider;

import jeckelcorelibrary.base.guis.AContainerTileInventory;
import jeckelcorelibrary.core.slots.SlotLiquidContainer;
import jeckelcorelibrary.core.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerFluidProvider extends AContainerTileInventory<TileFluidProvider>
{
	public ContainerFluidProvider(EntityPlayer player, TileFluidProvider tile)
	{
		super(player, tile, tile, 176, 180);

		// Liquid Container Input
		this.addSlotToContainer(new SlotLiquidContainer(tile, 0, 103, 26, true, false));

		// Liquid Container Output
		this.addSlotToContainer(new SlotOutput(tile, 1, 103, 57));

		// Liquid Container Input
		this.addSlotToContainer(new SlotLiquidContainer(tile, 2, 57, 42, false, true));

		// Player Inventory
		this.addPlayerInventorySlots(this._player.inventory, 8, this._height);
		this.addPlayerHotbarSlots(this._player.inventory, 8, this._height);
	}

	@Override protected int getMergeSlotCount(final int slotIndex)
	{
		switch (slotIndex) { case 0: case 2: { return 1; } default: { return 0; } }
	}

	@Override protected boolean isValidSlotItem(final EntityPlayer player, final int slotIndex, final ItemStack stack)
	{
		return this.getSlot(slotIndex).isItemValid(stack);
	}
}
