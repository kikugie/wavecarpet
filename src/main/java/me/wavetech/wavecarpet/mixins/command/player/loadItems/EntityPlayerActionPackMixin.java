package me.wavetech.wavecarpet.mixins.command.player.loadItems;

import carpet.helpers.EntityPlayerActionPack;
import com.llamalad7.mixinextras.sugar.Local;
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
@Mixin(targets = "carpet/helpers/EntityPlayerActionPack$ActionType$1") // 1 == USE
public class EntityPlayerActionPackMixin {
	@Inject(method = "execute", at = @At(value = "RETURN", ordinal = 2))
	private void loadItemsInBlock(ServerPlayer player, EntityPlayerActionPack.Action action, CallbackInfoReturnable<Boolean> cir, @Local BlockHitResult target) {
		if (player.getLevel().getBlockEntity(target.getBlockPos()) instanceof Container container) {
			transfer(player, container);
		}
	}

	@Unique
	private void transfer(ServerPlayer player, Container container) {
		if (!container.stillValid(player) || !player.getLoadItems$wavecarpet())
			return;

		InventoryMerger.transfer(player.getInventory(), container);
		player.closeContainer();
	}
}
