package me.wavetech.wavecarpet.core;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class ContainerMerger {
	public static void transfer(Container source, Container target) {
		ArrayList<Integer> sourceSlots = getPopulatedSlots(source);
		ArrayList<Integer> targetSlots = getAvailableSlots(target);
		if (sourceSlots.isEmpty() || targetSlots.isEmpty()) return;

		int sourceIndex = 0;
		int targetIndex = 0;

		while (sourceIndex < sourceSlots.size()) {
			if (targetIndex == targetSlots.size()) {
				targetIndex = 0;
				sourceIndex++;
				continue;
			}

			int currentSourceSlot = sourceSlots.get(sourceIndex);
			int currentTargetSlot = targetSlots.get(targetIndex);

			ItemStack sourceItemStack = source.getItem(currentSourceSlot);
			ItemStack targetItemStack = target.getItem(currentTargetSlot);

			if (targetItemStack.getCount() >= targetItemStack.getMaxStackSize()) {
				targetIndex++;
				continue;
			}

			if (targetItemStack.isEmpty()) {
				source.setItem(currentSourceSlot, ItemStack.EMPTY);
				target.setItem(currentTargetSlot, sourceItemStack);
				sourceIndex++;
				targetIndex = 0;
				continue;
			}

			if (ItemStack.isSameItemSameTags(sourceItemStack, targetItemStack)) {
				int stackSizeDiff = Math.min(sourceItemStack.getCount(), targetItemStack.getMaxStackSize() - targetItemStack.getCount());
				sourceItemStack.shrink(stackSizeDiff);
				targetItemStack.grow(stackSizeDiff);
				if (sourceItemStack.isEmpty()) {
					sourceIndex++;
					targetIndex = 0;
				}
				continue;
			}
			targetIndex++;
		}
		target.setChanged();
	}

	private static ArrayList<Integer> getAvailableSlots(Container container) {
		ArrayList<Integer> availableSlots = new ArrayList<>();
		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack itemStack = container.getItem(i);
			if (itemStack.getCount() >= itemStack.getMaxStackSize()) continue;
			availableSlots.add(i);
		}
		return availableSlots;
	}

	private static ArrayList<Integer> getPopulatedSlots(Container container) {
		ArrayList<Integer> populatedSlots = new ArrayList<>();
		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack itemStack = container.getItem(i);
			if (itemStack.isEmpty()) continue;
			populatedSlots.add(i);
		}
		return populatedSlots;
	}
}
