/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ExpandedRedstone;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.ExpandedRedstone.Registry.RedstoneBlocks;
import Reika.ExpandedRedstone.Registry.RedstoneTiles;

public abstract class ExpandedRedstoneTileEntity extends TileEntityBase {

	protected static final ForgeDirection[] dirs = ForgeDirection.values();

	private ForgeDirection facing;

	protected boolean emit;

	private StepTimer pulsar = new StepTimer(0);
	private boolean isPulsing = false;

	public abstract int getTEIndex();

	public void updateEntity(World world, int x, int y, int z) {
		world.markBlockForUpdate(x, y, z);
		ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		if (isPulsing) {
			pulsar.update();
			if (pulsar.checkCap())
				emit = false;
		}
	}

	@Override
	protected String getTEName() {
		return RedstoneTiles.TEList[this.getTEIndex()].getName();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {}

	@Override
	public final int getTileEntityBlockID() {
		return RedstoneBlocks.TILEENTITY.getBlockID();
	}

	public boolean isEmitting() {
		return emit;
	}

	protected void sendPulse(int length) {
		pulsar = new StepTimer(length);
		isPulsing = true;
		emit = true;
	}

	public ForgeDirection getFacing() {
		return facing;
	}

	public void setFacing(ForgeDirection dir) {
		facing = dir;
	}

	public int getFacingX() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return xCoord+facing.offsetX;
	}

	public int getFacingY() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return yCoord+facing.offsetY;
	}

	public int getFacingZ() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return zCoord+facing.offsetZ;
	}

	public int getBackX() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return xCoord-facing.offsetX;
	}

	public int getBackY() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return yCoord-facing.offsetY;
	}

	public int getBackZ() {
		if (facing == null)
			return Integer.MIN_VALUE;
		return zCoord-facing.offsetZ;
	}

	public boolean isBinaryRedstone() {
		return true;
	}

	public int getEmission() {
		return 0;
	}

}
