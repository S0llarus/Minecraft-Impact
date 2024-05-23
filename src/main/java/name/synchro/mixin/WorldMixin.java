package name.synchro.mixin;

import name.synchro.mixinHelper.MetalsProvider;
import name.synchro.util.Metals;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(World.class)
public abstract class WorldMixin implements MetalsProvider, WorldAccess {
    @Unique private Metals metals;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void getWorldSeed(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates, CallbackInfo ci){
        this.metals = new Metals((World)(Object)this, biomeAccess, 16);
    }

    @Override
    public Metals getMetals() {
        return metals;
    }
}