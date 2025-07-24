package org.hsw.wikitools.feature.get_skull_id.outbound;

import com.mojang.authlib.properties.Property;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.hsw.wikitools.feature.get_skull_id.app.FindFacingBlockSkull;
import org.hsw.wikitools.feature.get_skull_id.app.Skull;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FacingBlockSkullFinder implements FindFacingBlockSkull {
    @Override
    public Optional<Skull> getFacingSkull() {
        Optional<BlockEntity> blockEntity = findFacingBlock();

        if (blockEntity.isEmpty()) {
            return Optional.empty(); // Cannot find block entity
        }

        return getSkull(blockEntity.get());
    }

    private static @NotNull Optional<BlockEntity> findFacingBlock() {
        MinecraftClient client = MinecraftClient.getInstance();

        HitResult crosshairTarget = client.crosshairTarget;

        if (crosshairTarget == null ||
                !crosshairTarget.getType().equals(HitResult.Type.BLOCK)) {
            return Optional.empty(); // No mouseover block
        }

        if (client.world == null) {
            return Optional.empty(); // No world
        }

        if (!client.isOnThread()) {
            return Optional.empty(); // Not on thread
        }

        BlockPos blockPos = BlockPos.ofFloored(crosshairTarget.getPos());
        BlockEntity blockEntity = client.world.getBlockEntity(blockPos);

        return Optional.ofNullable(blockEntity);
    }

    private static @NotNull Optional<Skull> getSkull(BlockEntity blockEntity) {
        if (!(blockEntity instanceof SkullBlockEntity)) {
            return Optional.empty(); // Not a skull
        }

        ProfileComponent profileComponent = ((SkullBlockEntity) blockEntity).getOwner();
        if (profileComponent == null) {
            return Optional.empty(); // Cannot find profile
        }

        Optional<Property> textureProperty = profileComponent.properties().get("textures").stream().findFirst();

        if (textureProperty.isEmpty()) {
            return Optional.empty(); // Cannot find textures
        }

        String textureValue = textureProperty.get().value();

        return Optional.of(Skull.ofTextureValue(textureValue));
    }
}
