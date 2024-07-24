package me.wavetech.wavecarpet.mixins.utils.customCriteria;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ObjectiveCriteria.class)
public interface ObjectiveCriteriaAccessor {
	@Invoker
	static ObjectiveCriteria callRegisterCustom(String name) {
		throw new AssertionError();
	}
}
