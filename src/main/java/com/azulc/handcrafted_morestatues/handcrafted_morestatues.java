package com.azulc.handcrafted_morestatues;

import org.slf4j.Logger;

import com.azulc.handcrafted_morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.handcrafted_morestatues.block.entity.client.renderer.DynamicStatueRenderer;
import com.azulc.handcrafted_morestatues.block.statue.*;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(handcrafted_morestatues.MODID)
public class handcrafted_morestatues 
{
    public static final String MODID = "handcrafted_morestatues";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS                      = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS                        = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    // Register Blocks
    //public static final DeferredBlock<Block> WITHERSKELETON_STATUE        = registerStatue("wither_skeleton_statue", StatueVariant.TALL);
    public static final DeferredBlock<Block> ZOMBIE_STATUE                  = registerStatue("zombie_statue", StatueVariant.LONG);
    //public static final DeferredBlock<Block> BEAR_TROPHY                  = registerStatue("bear_trophy", StatueVariant.WALL);
    //public static final DeferredBlock<Block> CREEPER_STATUE               = registerStatue("creeper_statue", StatueVariant.TALL);
    // ---------------------------------------
    private static DeferredBlock<Block> registerStatue(String id, StatueVariant variant) {
    BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
        .mapColor(MapColor.STONE)
        .noOcclusion()
        .isSuffocating((blkState, blockGetter, blockPos) -> false);

    DeferredBlock<Block> registeredBlock = BLOCKS.register(id, () -> variant.create(props));
    ITEMS.registerSimpleBlockItem(id, registeredBlock);
    return registeredBlock;
    }
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoreStatueEntityBlock>> STATUE_ENTITY = 
        BLOCK_ENTITIES.register("more_statue_entity_block", () -> {
            Block[] validBlocks = BLOCKS.getEntries().stream()
                .map(DeferredHolder::get)
                .toArray(Block[]::new);

            BlockEntityType.Builder<MoreStatueEntityBlock> builder = BlockEntityType.Builder.of(MoreStatueEntityBlock::new, validBlocks);
            return (BlockEntityType<MoreStatueEntityBlock>) builder.build(null);
    });
    // Register CreativeModeTabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STATUE_TAB = CREATIVE_MODE_TABS.register(MODID, () -> 
    CreativeModeTab.builder()
    .title(Component.translatable("itemGroup." + MODID))
    .withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> 
        Blocks.GOLD_BLOCK.asItem().getDefaultInstance())
        .displayItems((parameters, output) -> {
            BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
        })
    .build());    


    public handcrafted_morestatues(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, event -> {event.registerBlockEntityRenderer(STATUE_ENTITY.get(), context -> new DynamicStatueRenderer());});
        CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    
        /*
    BEAR_TROPHY
    BLAZE_TROPHY
    FOX_TROPHY
    GOAT_TROPHY
    PUFFERFISH_TROPHY
    SALMON_TROPHY
    SILVERFISH_TROPHY
    SKELETON_HORSE_TROPHY
    SPIDER_TROPHY
    TROPICAL_FISH_TROPHY
    WITHER_SKELETON_TROPHY
    WOLF_TROPHY
    PHANTOM_TROPHY
    CREEPER_TROPHY
    SKELETON_TROPHY
    EVOKER_TROPHY
    PILLAGER_TROPHY
    VINDICATOR_TROPHY
    WITCH_TROPHY 
    */
}
