package org.hsw.wikitools.mixin.view_item_id.inbound;

import org.hsw.wikitools.feature.view_item_id.app.GetItemIdHandler;
import org.hsw.wikitools.feature.view_item_id.outbound.HoveredItemIdFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

@Mixin(ItemStack.class)
public class TooltipItemIdAppender {
    @Inject(method = "getTooltipLines", at = @At("TAIL"), cancellable = true)
    public void onGetTooltip(Item.TooltipContext context, @Nullable Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir) {
        boolean toDisplaySkyBlockItemId = Minecraft.getInstance().options.advancedItemTooltips;

        if (!toDisplaySkyBlockItemId) {
            return;
        }

        Optional<String> skyBlockItemId = findItemId();

        if (skyBlockItemId.isEmpty()) {
            return;
        }

        appendTooltipWithItemId(cir, skyBlockItemId.get());
    }

    @Unique
    private static @NotNull Optional<String> findItemId() {
        HoveredItemIdFinder hoveredItemIdFinder = new HoveredItemIdFinder();
        GetItemIdHandler getItemIdHandler = new GetItemIdHandler(hoveredItemIdFinder);

        Optional<GetItemIdHandler.GetItemIdResponse> response = getItemIdHandler.getItemId(new GetItemIdHandler.GetItemIdRequest());
        return response.map(value -> value.itemId);
    }

    @Unique
    private static void appendTooltipWithItemId(CallbackInfoReturnable<List<Component>> cir, String skyBlockItemId) {
        Style textStyle = Style.EMPTY.withColor(CommonColors.GRAY);
        List<Component> textsToAdd = Component.nullToEmpty("SkyBlock Item ID: " + skyBlockItemId).toFlatList(textStyle);

        List<Component> textList = cir.getReturnValue();
        textList.addAll(textsToAdd);
        cir.setReturnValue(textList);
    }
}
