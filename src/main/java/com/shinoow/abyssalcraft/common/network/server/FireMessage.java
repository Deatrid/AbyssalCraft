/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class FireMessage extends AbstractServerMessage<FireMessage> {

	private BlockPos pos;

	public FireMessage() {}

	public FireMessage(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		World world = player.worldObj;

		if(world.isRemote) return;
		if(world.getBlockState(pos).getBlock() != ACBlocks.mimic_fire &&
			world.getBlockState(pos).getBlock() != ACBlocks.coralium_fire &&
			world.getBlockState(pos).getBlock() != ACBlocks.dreaded_fire &&
			world.getBlockState(pos).getBlock() != ACBlocks.omothol_fire)
			return;

		world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
		world.setBlockToAir(pos);
	}

}
