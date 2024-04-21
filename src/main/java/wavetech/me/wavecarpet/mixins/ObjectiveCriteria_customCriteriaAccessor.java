package wavetech.me.wavecarpet.mixins;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ObjectiveCriteria.class)
public interface ObjectiveCriteria_customCriteriaAccessor {
	@Invoker
	static ObjectiveCriteria callRegisterCustom(String name) {
		throw new AssertionError();
	}
}
