package org.hsw.wikitools.mixin.common.outbound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
	@Accessor
	Slot getFocusedSlot();
}
