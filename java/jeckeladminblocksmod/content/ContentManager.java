package jeckeladminblocksmod.content;

import jeckeladminblocksmod.content.euprovider.BlockEuProvider;
import jeckeladminblocksmod.content.euprovider.TileEuProvider;
import jeckeladminblocksmod.content.fluidprovider.BlockFluidProvider;
import jeckeladminblocksmod.content.fluidprovider.TileFluidProvider;
import jeckeladminblocksmod.content.redstonefluxprovider.BlockRedstoneFluxProvider;
import jeckeladminblocksmod.content.redstonefluxprovider.TileRedstoneFluxProvider;
import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.GlobalRefs;
import jeckelcorelibrary.api.managers.IContentManager;
import jeckelcorelibrary.utils.GameRegUtil;
import net.minecraft.block.Block;

public class ContentManager implements IContentManager
{
	public static class ModBlocks
	{
		public static Block fluid_provider;

		public static Block redstone_flux_provider;

		public static Block eu_provider;
	}

	public static class ModItems
	{

	}

	@Override public void pre()
	{
		ModBlocks.fluid_provider = new BlockFluidProvider();
		{
			GameRegUtil.block(ModBlocks.fluid_provider, null, TileFluidProvider.class);
			GlobalRefs.getTabManager().addMachineBlock(Refs.ModId, ModBlocks.fluid_provider);
		}

		ModBlocks.redstone_flux_provider = new BlockRedstoneFluxProvider();
		{
			GameRegUtil.block(ModBlocks.redstone_flux_provider, null, TileRedstoneFluxProvider.class);
			GlobalRefs.getTabManager().addMachineBlock(Refs.ModId, ModBlocks.redstone_flux_provider);
		}

		ModBlocks.eu_provider = new BlockEuProvider();
		{
			GameRegUtil.block(ModBlocks.eu_provider, null, TileEuProvider.class);
			GlobalRefs.getTabManager().addMachineBlock(Refs.ModId, ModBlocks.eu_provider);
		}
	}

	@Override public void initialize()
	{
	}

	@Override public void post()
	{
	}
}
