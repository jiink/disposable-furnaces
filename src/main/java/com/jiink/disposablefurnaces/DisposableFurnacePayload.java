package com.jiink.disposablefurnaces;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import static com.jiink.disposablefurnaces.DisposableFurnaces.MOD_ID;

public record DisposableFurnacePayload(BlockPos blockPos) implements CustomPayload {
    public static final Id<DisposableFurnacePayload> ID = CustomPayload.id(MOD_ID + ":disposable_furnace_payload");
    public static final PacketCodec<PacketByteBuf, DisposableFurnacePayload> CODEC = PacketCodec.of(
            (value, buf) -> buf.writeBlockPos(value.blockPos),
            buf -> new DisposableFurnacePayload(buf.readBlockPos())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
