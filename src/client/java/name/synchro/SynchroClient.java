package name.synchro;

import name.synchro.blockModels.ModelPlugin;
import name.synchro.guidance.Guidance;
import name.synchro.registries.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class SynchroClient implements ClientModInitializer {
    public static final Identifier MOD_ICONS = Identifier.of(Synchro.MOD_ID, "textures/gui/mod_icons.png");
    public static boolean applyNewHud = true;
    public static boolean displayExtraCollisions = false;
    public static float debugNum0 = 0.0f;
    public static float debugNum1 = 1.0f;
    public static float debugNum2 = 1.0f;
    public static float debugNum3 = 1.0f;
    public static final ArrayList<Float> debugNumbers = new ArrayList<>();

    public static final TextureKey LAYER3 = TextureKey.of("layer3");
    public static final TextureKey LAYER4 = TextureKey.of("layer4");
    public static final Model GENERATED_FOUR_LAYERS = new Model(Optional.of(Identifier.of("item/generated")), Optional.empty(), TextureKey.LAYER0, TextureKey.LAYER1, TextureKey.LAYER2, LAYER3);
    public static final Model GENERATED_FIVE_LAYERS = new Model(Optional.of(Identifier.of("item/generated")), Optional.empty(), TextureKey.LAYER0, TextureKey.LAYER1, TextureKey.LAYER2, LAYER3, LAYER4);
    @Override
    public void onInitializeClient() {
        for (int i = 0; i < 16; ++i){
            debugNumbers.add(1.0f);
        }
        //DebugRendering.renderAll();
        BlockEntityRenderers.registerAll();
        ColorProviders.registerAll();
        ModelLoadingPlugin.register(new ModelPlugin());
        ClientNetworking.registerAll();
        SetupRenderLayer.setupAll();
        FluidRenderers.registerAll();
        Guidance.setupKeyBinding();
        ModScreens.registerAll();
        EntityRenderers.registerAll();
        RegisterEventsClient.registerAll();
        Synchro.LOGGER.info("Mod Synchro has been initialized in client.");
    }

}
