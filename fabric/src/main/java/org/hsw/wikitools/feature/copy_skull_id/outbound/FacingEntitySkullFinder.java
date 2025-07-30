package org.hsw.wikitools.feature.copy_skull_id.outbound;

import com.mojang.authlib.properties.Property;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PlayerHeadItem;
import org.hsw.wikitools.feature.copy_skull_id.app.FindFacingEntitySkull;
import org.hsw.wikitools.feature.copy_skull_id.app.Skull;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FacingEntitySkullFinder implements FindFacingEntitySkull {
    @Override
    public Optional<Skull> findFacingSkull() {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity targetedEntity = client.targetedEntity;

        if (targetedEntity == null) {
            return Optional.empty(); // No mouseover entity
        }

        if (!(targetedEntity instanceof LivingEntity)) {
            return Optional.empty(); // Not living entity
        }

        return findSkull((LivingEntity) targetedEntity);
    }

    private static @NotNull Optional<Skull> findSkull(LivingEntity livingEntity) {
        ItemStack headItemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);

        if (!(headItemStack.getItem() instanceof PlayerHeadItem)) {
            return Optional.empty(); // Not a player head
        }

        ComponentMap components = headItemStack.getComponents();

        ProfileComponent profileComponent = components.get(DataComponentTypes.PROFILE);

        if (profileComponent == null) {
            return Optional.empty(); // Cannot find profile component
        }

        Optional<Property> textureProperty = profileComponent.properties().get("textures").stream().findFirst();

        if (textureProperty.isEmpty()) {
            return Optional.empty(); // Cannot get texture property
        }

        String textureValue = textureProperty.get().value();

        return Optional.of(Skull.ofTextureValue(textureValue));
    }
}
