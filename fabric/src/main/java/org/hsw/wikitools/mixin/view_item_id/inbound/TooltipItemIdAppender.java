package org.hsw.wikitools.mixin.view_item_id.inbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
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

@Mixin(ItemStack.class)
public class TooltipItemIdAppender {
    @Inject(method = "getTooltip", at = @At("TAIL"), cancellable = true)
    public void onGetTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        boolean toDisplaySkyBlockItemId = MinecraftClient.getInstance().options.advancedItemTooltips;

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
    private static void appendTooltipWithItemId(CallbackInfoReturnable<List<Text>> cir, String skyBlockItemId) {
        Style textStyle = Style.EMPTY.withColor(Colors.GRAY);
        List<Text> textsToAdd = Text.of("SkyBlock Item ID: " + skyBlockItemId).getWithStyle(textStyle);

        List<Text> textList = cir.getReturnValue();
        textList.addAll(textsToAdd);
        cir.setReturnValue(textList);
    }
}
