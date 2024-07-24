package me.wavetech.wavecarpet.mixins.utils;

import carpet.helpers.EntityPlayerActionPack;
import com.llamalad7.mixinextras.sugar.Local;
import me.wavetech.wavecarpet.access.PlayerLoader;
import me.wavetech.wavecarpet.core.InventoryMerger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Hey, I didn't come up with this naming
@Mixin(targets = "carpet/helpers/EntityPlayerActionPack$ActionType$1")
public class CarpetEntityPlayerActionPack_inventoryLoaderMixin {
	@Inject(method = "execute", at = @At(value = "RETURN", ordinal = 2))
	private void loadItemsInBlock(ServerPlayer player, EntityPlayerActionPack.Action action, CallbackInfoReturnable<Boolean> cir, @Local BlockHitResult target) {
		if (target != null && player.getLevel().getBlockEntity(target.getBlockPos()) instanceof Container inventory) {
			transfer(player, inventory);
		}
	}

	@Unique
	private void transfer(ServerPlayer player, Container inventory) {
		if (!inventory.stillValid(player)) {
			return;
		}

		PlayerLoader playerLoader = (PlayerLoader) player;
		if (!playerLoader.wavecarpet$getLoadItems()) {
			return;
		}

		InventoryMerger.transfer(player.getInventory(), inventory);
		player.closeContainer();
	}
}