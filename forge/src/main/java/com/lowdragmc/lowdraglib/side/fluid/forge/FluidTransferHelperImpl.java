package com.lowdragmc.lowdraglib.side.fluid.forge;

import com.lowdragmc.lowdraglib.msic.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.IFluidTransfer;
import com.lowdragmc.lowdraglib.side.item.IItemTransfer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

/**
 * @author KilaBash
 * @date 2023/2/10
 * @implNote FluidTransferHelper
 */
public class FluidTransferHelperImpl {

    public static IFluidTransfer toFluidTransfer(IFluidHandler handler) {
        if (handler instanceof IFluidTransfer fluidTransfer) {
            return fluidTransfer;
        } else {
            return new FluidTransferWrapper(handler);
        }
    }

    public static IFluidTransfer getFluidTransfer(Level level, BlockPos pos, @Nullable Direction direction) {
        BlockState state = level.getBlockState(pos);
        if (state.hasBlockEntity()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                var handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction);
                if (handler.isPresent()) {
                    return toFluidTransfer(handler.orElse(null));
                }
            }
        }
        return null;
    }

    public static IFluidTransfer getFluidTransfer(IItemTransfer itemTransfer, int slot) {
        var itemStack = itemTransfer.getStackInSlot(slot);
        var handler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (handler.isPresent()) {
            return toFluidTransfer(handler.orElse(null));
        }
        return null;
    }

    public static IFluidTransfer getFluidTransfer(Player player, AbstractContainerMenu screenHandler) {
        var itemStack = screenHandler.getCarried();
        var handler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (handler.isPresent()) {
            return toFluidTransfer(handler.orElse(null));
        }
        return null;
    }

    public static IFluidTransfer getFluidTransfer(Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        var handler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (handler.isPresent()) {
            return toFluidTransfer(handler.orElse(null));
        }
        return null;
    }

    public static IFluidTransfer getFluidTransfer(Player player, int slot) {
        var itemStack = player.getInventory().getItem(slot);
        var handler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (handler.isPresent()) {
            return toFluidTransfer(handler.orElse(null));
        }
        return null;
    }

    public static ItemStack getContainerItem(ItemStackTransfer copyContainer, IFluidTransfer handler) {
        if (handler instanceof FluidTransferWrapper wrapper && wrapper.getHandler() instanceof IFluidHandlerItem fluidHandlerItem) {
            return fluidHandlerItem.getContainer();
        }
        return copyContainer.getStackInSlot(0);
    }

}
