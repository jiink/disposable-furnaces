package com.jiink.disposablefurnaces.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

import static com.jiink.disposablefurnaces.DisposableFurnaces.MOD_ID;

public class MyConfig {
    public static ConfigClassHandler<MyConfig> HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
            //.id(new ResourceLocation(MOD_ID, "my_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json"))
//                    .appendGsonBuilder(GsonBuilder::setPrettyPrint) // not needed, pretty print by default
//                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry (comment = "Disable explosions from certain powerful furnaces. May cause the mod to become unbalanced.")
    public boolean furnaceGriefing = true;

    @SerialEntry
    public int numItemsCanSmeltWooden = 8;
    @SerialEntry
    public float smeltDurationSecWooden = 8.0F;

    @SerialEntry
    public int numItemsCanSmeltDriedKelp = 16;
    @SerialEntry
    public float smeltDurationSecDriedKelp = 4.0F;

    @SerialEntry
    public int numItemsCanSmeltCoal = 48;
    @SerialEntry
    public float smeltDurationSecCoal = 10.0F;
    @SerialEntry

    public int numItemsCanSmeltCharcoal = 48;
    @SerialEntry
    public float smeltDurationSecCharcoal = 10.0F;
    @SerialEntry

    public int numItemsCanSmeltBlaze = 64;
    @SerialEntry
    public float smeltDurationSecBlaze = 9.0F;
    @SerialEntry

    public int numItemsCanSmeltLava = 400;
    @SerialEntry
    public float smeltDurationSecLava = 20.0F;
    @SerialEntry

    public int numItemsCanSmeltGunpowder = 40;
    @SerialEntry
    public float smeltDurationSecGunpowder = 2.0F;
}