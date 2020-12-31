package moriyashiine.bewitchment.client;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.bewitchment.client.misc.SpriteIdentifiers;
import moriyashiine.bewitchment.client.network.packet.*;
import moriyashiine.bewitchment.client.particle.CauldronBubbleParticle;
import moriyashiine.bewitchment.client.renderer.blockentity.WitchAltarBlockEntityRenderer;
import moriyashiine.bewitchment.client.renderer.blockentity.WitchCauldronBlockEntityRenderer;
import moriyashiine.bewitchment.client.renderer.entity.SilverArrowEntityRenderer;
import moriyashiine.bewitchment.client.renderer.entity.living.*;
import moriyashiine.bewitchment.common.Bewitchment;
import moriyashiine.bewitchment.common.block.entity.BWChestBlockEntity;
import moriyashiine.bewitchment.common.registry.BWBlockEntityTypes;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
import moriyashiine.bewitchment.common.registry.BWObjects;
import moriyashiine.bewitchment.common.registry.BWParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BewitchmentClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSidePacketRegistry.INSTANCE.register(CreateNonLivingEntityPacket.ID, CreateNonLivingEntityPacket::handle);
		ClientSidePacketRegistry.INSTANCE.register(SyncWitchAltarBlockEntity.ID, SyncWitchAltarBlockEntity::handle);
		ClientSidePacketRegistry.INSTANCE.register(SyncClientSerializableBlockEntity.ID, SyncClientSerializableBlockEntity::handle);
		ClientSidePacketRegistry.INSTANCE.register(SpawnSmokeParticlesPacket.ID, SpawnSmokeParticlesPacket::handle);
		ClientSidePacketRegistry.INSTANCE.register(SpawnPortalParticlesPacket.ID, SpawnPortalParticlesPacket::handle);
		ClientSidePacketRegistry.INSTANCE.register(SpawnExplosionParticlesPacket.ID, SpawnExplosionParticlesPacket::handle);
		ParticleFactoryRegistry.getInstance().register(BWParticleTypes.CAULDRON_BUBBLE, CauldronBubbleParticle.Factory::new);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? 0x7f0000 : 0xffffff, BWObjects.BOTTLE_OF_BLOOD);
		FabricModelPredicateProviderRegistry.register(BWObjects.TAGLOCK, new Identifier(Bewitchment.MODID, "variant"), (stack, world, entity) -> stack.hasTag() && stack.getOrCreateTag().contains("OwnerUUID") ? 1 : 0);
		BlockEntityRendererRegistry.INSTANCE.register(BWBlockEntityTypes.BW_CHEST, ChestBlockEntityRenderer::new);
		BlockEntityRendererRegistry.INSTANCE.register(BWBlockEntityTypes.WITCH_ALTAR, WitchAltarBlockEntityRenderer::new);
		BlockEntityRendererRegistry.INSTANCE.register(BWBlockEntityTypes.WITCH_CAULDRON, WitchCauldronBlockEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.JUNIPER_BOAT, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.CYPRESS_BOAT, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.ELDER_BOAT, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.DRAGONS_BLOOD_BOAT, (dispatcher, context) -> new BoatEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.SILVER_ARROW, (dispatcher, context) -> new SilverArrowEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.OWL, (dispatcher, context) -> new OwlEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.RAVEN, (dispatcher, context) -> new RavenEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.SNAKE, (dispatcher, context) -> new SnakeEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.TOAD, (dispatcher, context) -> new ToadEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.GHOST, (dispatcher, context) -> new GhostEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.BLACK_DOG, (dispatcher, context) -> new BlackDogEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.HELLHOUND, (dispatcher, context) -> new HellhoundEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(BWEntityTypes.DEMON, (dispatcher, context) -> new DemonEntityRenderer(dispatcher));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.SALT_LINE, BWObjects.TEMPORARY_COBWEB);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.ACONITE_CROP, BWObjects.BELLADONNA_CROP, BWObjects.GARLIC_CROP, BWObjects.MANDRAKE_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.JUNIPER_SAPLING, BWObjects.POTTED_JUNIPER_SAPLING, BWObjects.JUNIPER_DOOR, BWObjects.JUNIPER_TRAPDOOR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.CYPRESS_SAPLING, BWObjects.POTTED_CYPRESS_SAPLING, BWObjects.CYPRESS_DOOR, BWObjects.CYPRESS_TRAPDOOR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.ELDER_SAPLING, BWObjects.POTTED_ELDER_SAPLING, BWObjects.ELDER_DOOR, BWObjects.ELDER_TRAPDOOR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.DRAGONS_BLOOD_SAPLING, BWObjects.POTTED_DRAGONS_BLOOD_SAPLING, BWObjects.DRAGONS_BLOOD_DOOR, BWObjects.DRAGONS_BLOOD_TRAPDOOR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.SPANISH_MOSS, BWObjects.GLOWING_BRAMBLE, BWObjects.ENDER_BRAMBLE, BWObjects.FRUITING_BRAMBLE, BWObjects.SCORCHED_BRAMBLE, BWObjects.THICK_BRAMBLE, BWObjects.FLEETING_BRAMBLE);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.STONE_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.MOSSY_COBBLESTONE_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.PRISMARINE_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.NETHER_BRICK_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.BLACKSTONE_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.GOLDEN_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.END_STONE_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.OBSIDIAN_WITCH_ALTAR);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BWObjects.PURPUR_WITCH_ALTAR);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.JUNIPER);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_JUNIPER);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.JUNIPER_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_JUNIPER_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.JUNIPER_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_JUNIPER_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.CYPRESS);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_CYPRESS);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.CYPRESS_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_CYPRESS_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.CYPRESS_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_CYPRESS_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.ELDER);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_ELDER);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.ELDER_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_ELDER_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.ELDER_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_ELDER_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.DRAGONS_BLOOD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_DRAGONS_BLOOD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.DRAGONS_BLOOD_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_DRAGONS_BLOOD_LEFT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.DRAGONS_BLOOD_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(SpriteIdentifiers.TRAPPED_DRAGONS_BLOOD_RIGHT);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, BWObjects.JUNIPER_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, BWObjects.CYPRESS_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, BWObjects.ELDER_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, BWObjects.DRAGONS_BLOOD_SIGN.getTexture()));
		BlockEntity JUNIPER_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.JUNIPER, false);
		BlockEntity TRAPPED_JUNIPER_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.JUNIPER, true);
		BlockEntity CYPRESS_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.CYPRESS, false);
		BlockEntity TRAPPED_CYPRESS_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.CYPRESS, true);
		BlockEntity ELDER_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.ELDER, false);
		BlockEntity TRAPPED_ELDER_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.ELDER, true);
		BlockEntity DRAGONS_BLOOD_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.DRAGONS_BLOOD, false);
		BlockEntity TRAPPED_DRAGONS_BLOOD_CHEST = new BWChestBlockEntity(BWBlockEntityTypes.BW_CHEST, BWChestBlockEntity.Type.DRAGONS_BLOOD, true);
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.JUNIPER_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(JUNIPER_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.TRAPPED_JUNIPER_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(TRAPPED_JUNIPER_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.CYPRESS_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(CYPRESS_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.TRAPPED_CYPRESS_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(TRAPPED_CYPRESS_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.ELDER_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(ELDER_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.TRAPPED_ELDER_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(TRAPPED_ELDER_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.DRAGONS_BLOOD_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(DRAGONS_BLOOD_CHEST, matrices, vertexConsumers, light, overlay));
		BuiltinItemRendererRegistry.INSTANCE.register(BWObjects.TRAPPED_DRAGONS_BLOOD_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> BlockEntityRenderDispatcher.INSTANCE.renderEntity(TRAPPED_DRAGONS_BLOOD_CHEST, matrices, vertexConsumers, light, overlay));
	}
}
