package name.synchro.mixin.FluidConcerned;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import name.synchro.fluids.ChunkFluidUpdateS2CPacket;
import name.synchro.fluids.FluidUtil;
import name.synchro.fluids.SingleFluidUpdateS2CPacket;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {

    @Shadow @Final private ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider;

    @Shadow @Final ChunkPos pos;

    @Inject(method = "flushUpdates", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ChunkHolder;sendPacketToPlayersWatching(Lnet/minecraft/network/packet/Packet;Z)V",
            ordinal = 1, shift = At.Shift.AFTER))
    private void sendSingleFluidUpdatePacket(WorldChunk chunk, CallbackInfo ci, @Local BlockPos pos) {
        SingleFluidUpdateS2CPacket packet = new SingleFluidUpdateS2CPacket(pos, chunk.getWorld().getFluidState(pos));
        FluidUtil.sendToPlayersWatching(this.playersWatchingChunkProvider, this.pos, packet);
    }

    @Inject(method = "flushUpdates", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ChunkHolder;sendPacketToPlayersWatching(Lnet/minecraft/network/packet/Packet;Z)V",
            ordinal = 2, shift = At.Shift.AFTER))
    private void sendChunkFluidUpdatePacket(WorldChunk chunk, CallbackInfo ci, @Local ChunkSectionPos chunkSectionPos,
                                            @Local ShortSet shortSet, @Local ChunkSection chunkSection) {
        ChunkFluidUpdateS2CPacket packet = new ChunkFluidUpdateS2CPacket(chunkSectionPos, shortSet, chunkSection);
        FluidUtil.sendToPlayersWatching(this.playersWatchingChunkProvider, this.pos, packet);
    }

}
