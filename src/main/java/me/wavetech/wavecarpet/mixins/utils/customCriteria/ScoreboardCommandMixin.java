package me.wavetech.wavecarpet.mixins.utils.customCriteria;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.server.commands.ScoreboardCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ScoreboardCommand.class)
public class ScoreboardCommandMixin {
	@ModifyExpressionValue(method = "register", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/ObjectiveCriteriaArgument;criteria()Lnet/minecraft/commands/arguments/ObjectiveCriteriaArgument;"))
	private static ObjectiveCriteriaArgument shareCriteriaArgument(ObjectiveCriteriaArgument original, @Share("criteriaArgument") LocalRef<ObjectiveCriteriaArgument> criteriaArgument) {
		criteriaArgument.set(original);
		return original;
	}

	@ModifyExpressionValue(
		method = "register",
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/commands/arguments/ObjectiveCriteriaArgument;criteria()Lnet/minecraft/commands/arguments/ObjectiveCriteriaArgument;"
			)
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/commands/Commands;argument(Ljava/lang/String;Lcom/mojang/brigadier/arguments/ArgumentType;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;",
			ordinal = 0
		)
	)
	private static <T> RequiredArgumentBuilder<CommandSourceStack, T> attachServersideCriteriaSuggestionsForClientToReceive(RequiredArgumentBuilder<CommandSourceStack, T> original, @Share("criteriaArgument") LocalRef<ObjectiveCriteriaArgument> criteriaArgument) {
		return original.suggests(criteriaArgument.get()::listSuggestions);
	}
}
