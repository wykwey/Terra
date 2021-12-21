package com.dfsek.terra.fabric.mixin.implementations;

import com.dfsek.terra.api.Handle;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;


/**
 * A ton of Minecraft classes must implement Handle identically, we can just take care of it here
 */
@Mixin({
        ServerWorld.class,
        ChunkRegion.class,
        
        Block.class,
        
        BlockEntity.class,
        LootableContainerBlockEntity.class,
        LockableContainerBlockEntity.class,
        
        ProtoChunk.class,
        WorldChunk.class,
        
        Entity.class,
        EntityType.class,
        
        ServerCommandSource.class,
        
        Item.class,
        ItemStack.class,
        Enchantment.class,
        
        Biome.class
})
@Implements(@Interface(iface = Handle.class, prefix = "terra$", remap = Interface.Remap.NONE))
public class HandleImplementationMixin {
    @Intrinsic
    public Object terra$getHandle() {
        return this;
    }
}
