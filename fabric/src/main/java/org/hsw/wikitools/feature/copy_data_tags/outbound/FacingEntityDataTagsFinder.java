package org.hsw.wikitools.feature.copy_data_tags.outbound;

import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.hsw.wikitools.feature.copy_data_tags.app.EntityDataTags;
import org.hsw.wikitools.feature.copy_data_tags.app.FindFacingEntityDataTags;

import java.util.Optional;

public class FacingEntityDataTagsFinder implements FindFacingEntityDataTags {
    @Override
    public Optional<EntityDataTags> findFacingEntityDataTags() {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity targetedEntity = client.targetedEntity;

        if (targetedEntity == null) {
            return Optional.empty(); // No mouseover entity
        }

        NbtCompound nbtCompound = new NbtCompound();
        targetedEntity.writeNbt(nbtCompound);

        Optional<Text> entityCustomNameText = Optional.ofNullable(targetedEntity.getCustomName());
        Optional<String> entityCustomName = entityCustomNameText.map(Text::getString);

        Optional<String> textureValue = findGameProfile(targetedEntity);

        EntityDataTags entityDataTags = new EntityDataTags(nbtCompound.toString(), entityCustomName, textureValue);
        return Optional.of(entityDataTags);
    }

    private static Optional<String> findGameProfile(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return Optional.empty(); // Not a player entity
        }

        PropertyMap propertyMap = ((PlayerEntity) entity).getGameProfile().getProperties();

//        // Extract texture value
//        Optional<String> textureValue = propertyMap.get("textures").stream().findFirst().map(Property::value);

        return Optional.of(propertyMap.toString());
    }
}
