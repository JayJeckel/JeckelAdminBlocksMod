package jeckeladminblocksmod.core;

import jeckelcorelibrary.base.configs.AConfigFactory;
import net.minecraft.client.gui.GuiScreen;

public class ConfigFactory extends AConfigFactory
{
	@Override public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigScreen.class; }
}
