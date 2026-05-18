package com.azulc.morestatues;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.azulc.morestatues.block.entity.MoreStatueBlockItem;
import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.morestatues.block.entity.client.renderer.DynamicStatueRenderer;
import com.azulc.morestatues.block.statue.*;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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

@Mod(morestatues.MODID)
public class morestatues 
{
    // ---------------------------------------
    public static final String MODID = "morestatues";
    public static final Logger LOGGER = LogUtils.getLogger();
    // ---------------------------------------
    public static final DeferredRegister.Blocks BLOCKS                      = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS                        = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final Map<String, RenderStyle> STATUE_STYLES              = new HashMap<>();
    public static final Map<String, VoxelShape> STATUE_SHAPES               = new HashMap<>();
    public static final constants CONSTANTS = new constants();
    // ---------------------------------------
    //#region Register Blocks
    public static final DeferredBlock<Block> ZOMBIE_STATUE          = registerStatue("zombie_statue", StatueVariant.LONG);
    public static final DeferredBlock<Block> GUARDIAN_STATUE        = registerStatue("guardian_statue", StatueVariant.LONG, RenderStyle.COMPOSITE_ICE,Shapes.block());
    //
    public static final DeferredBlock<Block> WOLF_STATUE            = registerStatue("wolf_statue", StatueVariant.TALL,Block.box(2, 0, 2, 14, 18, 14));
    //public static final DeferredBlock<Block> SKELETON_STATUE      = registerStatue("skeleton_statue", StatueVariant.TALL);
    public static final DeferredBlock<Block> GHAST_STATUE           = registerStatue("ghast_statue", StatueVariant.TALL,CONSTANTS.GhastShape());
    public static final DeferredBlock<Block> GHAST_FIREBALL_STATUE  = registerStatue("ghast_fireball_statue", StatueVariant.TALL); 
    public static final DeferredBlock<Block> ENDERMAN_STATUE        = registerStatue("enderman_statue", StatueVariant.TALL);
    public static final DeferredBlock<Block> BLAZE_STATUE           = registerStatue("blaze_statue", StatueVariant.TALL);
    //
    public static final DeferredBlock<Block> BOSSWITHER_STATUE      = registerStatue("bosswither_statue", StatueVariant.WALL,Block.box(-1, -26, 1, 17, 16, 14));
    public static final DeferredBlock<Block> VEX_STATUE             = registerStatue("vex_statue", StatueVariant.WALL, RenderStyle.TRANSLUCENT);
    public static final DeferredBlock<Block> ALLAY_STATUE           = registerStatue("allay_statue", StatueVariant.WALL, RenderStyle.TRANSLUCENT);
    //#endregion
    // ---------------------------------------
    //#region Registration Helpers
    private static DeferredBlock<Block> registerStatue(String id, StatueVariant variant) {
        return registerStatue(id, variant, RenderStyle.SOLID,null);
    }
    private static DeferredBlock<Block> registerStatue(String id, StatueVariant variant, VoxelShape shape) {
        return registerStatue(id, variant, RenderStyle.SOLID, shape);
    }
    private static DeferredBlock<Block> registerStatue(String id, StatueVariant variant, RenderStyle style) {
        return registerStatue(id, variant, style, null);
    }
    private static DeferredBlock<Block> registerStatue(String id, StatueVariant variant, RenderStyle style, VoxelShape shape) {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .noOcclusion()
            .isSuffocating((blkState, blockGetter, pos) -> false);

        DeferredBlock<Block> registeredBlock = BLOCKS.register(id, () -> variant.create(props));
        ITEMS.register(id, () -> new MoreStatueBlockItem(registeredBlock.get(), new Item.Properties()));

        STATUE_STYLES.put(id, style); 
        if (shape != null) 
        { STATUE_SHAPES.put(id, shape); }
        return registeredBlock;
    }
    //#endregion
    // ---------------------------------------
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoreStatueEntityBlock>> STATUE_ENTITY = BLOCK_ENTITIES.register("more_statue_entity_block", () -> {Block[] validBlocks = BLOCKS.getEntries().stream().map(DeferredHolder::get).toArray(Block[]::new); BlockEntityType.Builder<MoreStatueEntityBlock> builder = BlockEntityType.Builder.of(MoreStatueEntityBlock::new, validBlocks); return (BlockEntityType<MoreStatueEntityBlock>) builder.build(null);});
    // ---------------------------------------
    // Register CreativeModeTabs
    // ---------------------------------------
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STATUE_TAB = CREATIVE_MODE_TABS.register(MODID, () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + MODID)).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> Blocks.GOLD_BLOCK.asItem().getDefaultInstance()).displayItems((parameters, output) -> { BLOCKS.getEntries().forEach(block -> output.accept(block.get()));}).build());    
    // ---------------------------------------
    // Constructor & Event Listeners
    // ---------------------------------------
    public morestatues(IEventBus modEventBus, ModContainer modContainer) 
    {
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
}
