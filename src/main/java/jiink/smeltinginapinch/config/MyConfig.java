package jiink.smeltinginapinch.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

import static jiink.smeltinginapinch.SmeltingInAPinch.MOD_ID;

public class MyConfig {
    public static ConfigClassHandler<MyConfig> HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
            //.id(new ResourceLocation(MOD_ID, "my_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("smeltinginapinch.json"))
//                    .appendGsonBuilder(GsonBuilder::setPrettyPrint) // not needed, pretty print by default
//                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean myCoolBoolean = true;

    @SerialEntry
    public int myCoolInteger = 5;

    @SerialEntry(comment = "This string is amazing")
    public String myCoolString = "How amazing!";

}