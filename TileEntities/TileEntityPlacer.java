/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ExpandedRedstone.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaRedstoneHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.ExpandedRedstone.InventoriedRedstoneTileEntity;
import Reika.ExpandedRedstone.Registry.RedstoneTiles;

public class TileEntityPlacer extends InventoriedRedstoneTileEntity {

	private boolean lastPower;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateEntity(world, x, y, z);
		if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower) && this.canPlace(world)) {
			this.placeBlock(world);
		}
		lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
	}

	private boolean canPlace(World world) {
		int dx = this.getFacingX();
		int dy = this.getFacingY();
		int dz = this.getFacingZ();
		return (ReikaWorldHelper.softBlocks(world, dx, dy, dz));
	}

	private void placeBlock(World world) {
		int dx = this.getFacingX();
		int dy = this.getFacingY();
		int dz = this.getFacingZ();
		for (int i = 0; i < inv.length; i++) {
			ItemStack is = inv[i];
			if (is != null) {
				int id = ReikaItemHelper.getWorldBlockIDFromItem(is);
				int meta = ReikaItemHelper.getWorldBlockMetaFromItem(is);
				if (id != 0) {
					world.setBlock(dx, dy, dz, id, meta, 3);
					return;
				}
			}
		}
	}

	@Override
	public int getTEIndex() {
		return RedstoneTiles.PLACER.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		int id = ReikaItemHelper.getWorldBlockIDFromItem(itemstack);
		return id != 0;
	}
}
