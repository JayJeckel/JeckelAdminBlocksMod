package jeckeladminblocksmod.proxy;

import jeckeladminblocksmod.content.fluidprovider.SimpleRendererFluidProvider;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override public boolean isClient() { return true; }

	@Override public void initialize(final String modId)
	{
		super.initialize(modId);

		SimpleRendererFluidProvider.renderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(SimpleRendererFluidProvider.renderType, new SimpleRendererFluidProvider());
	}
}
