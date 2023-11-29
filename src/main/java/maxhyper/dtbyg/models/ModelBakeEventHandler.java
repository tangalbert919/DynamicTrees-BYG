package maxhyper.dtbyg.models;

import maxhyper.dtbyg.DynamicTreesBYG;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DynamicTreesBYG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBakeEventHandler {

    @SubscribeEvent
    public static void onModelRegistryEvent(RegisterGeometryLoaders event) {
        event.register("palm_fronds", new PalmLeavesModelLoader());
        //ModelLoaderRegistry.registerLoader(new ResourceLocation(DynamicTreesBYG.MOD_ID, "palm_fronds"), new PalmLeavesModelLoader());
    }

    @SubscribeEvent
    public static void onModelBake(BakingCompleted event) {
        // Setup fronds models
        PalmLeavesBakedModel.INSTANCES.forEach(PalmLeavesBakedModel::setupModels);
    }

}