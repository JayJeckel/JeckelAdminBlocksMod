package jeckeladminblocksmod.content.fluidprovider;

import java.awt.Point;
import java.awt.Rectangle;

import jeckeladminblocksmod.core.Refs;
import jeckelcorelibrary.base.guis.AScreenTileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScreenFluidProvider extends AScreenTileInventory<TileFluidProvider>
{
	public ScreenFluidProvider(EntityPlayer player, TileFluidProvider tile)
	{
		super(player, tile, new ContainerFluidProvider(player, tile), tile, 176, 180);
		this.setResourceLocation(Refs.ModId, "admin_fluid_provider.png");
	}

	public ResourceLocation getResourceLocation() { return this._resource; }
	protected void setResourceLocation(final String modId, final String subPath) { this._resource = new ResourceLocation(modId, "textures/guis/" + subPath);; }
	private ResourceLocation _resource;

	private Rectangle rectTank = new Rectangle(77, 38, 22, 23);

	private final OverlayInfo infoTankExchanger = new OverlayInfo(new Rectangle(107, 45, 8, 9), new Point(176, 0), new Point(176, 0), false, false, false);

	@Override protected void doDrawTitle()
	{
		this.drawTextCenter(this.getTitle(), 5);
	}

	@Override protected void onDrawTexts()
	{
		this.drawTextLeft(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 98 + 5);
	}

	@Override protected void onDrawOverlays()
	{
		if (this._tile.getTank().getFluidAmount() > 0) { this.drawFluidTank(this.rectTank, this._tile.getTank()); }

		if (this._tile.tankExchanger.isProcessing()) { this.drawProcessOverlay(this._tile.tankExchanger, this.infoTankExchanger); }
	}

	@Override protected void onDrawTooltips(int x, int y)
	{
		if (this.rectTank.contains(x, y)) { this.drawTankTooltip(x, y, this._tile.getTank()); }
		else if (this.infoTankExchanger.contains(x, y)) { this.drawProcessTooltip(x, y, "Tank Exchanging", this._tile.tankExchanger); }
	}
}
