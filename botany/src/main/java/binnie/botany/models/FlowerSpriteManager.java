package binnie.botany.models;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.genetics.IFlowerType;
import binnie.core.resource.BinnieSprite;

public class FlowerSpriteManager {
	private static final Map<IFlowerType, FlowerSprites> flowerSprites = new HashMap<>();

	public static void initSprites(IFlowerType type) {
		if (flowerSprites.containsKey(type)) {
			return;
		}
		flowerSprites.put(type, new FlowerSprites(type));
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getStem(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getStem(type, section, flowered);
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getPetal(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getPetal(type, section, flowered);
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getVariant(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getVariant(type, section, flowered);
	}

	private static class FlowerSprites {
		private final BinnieSprite[] stem;
		private final BinnieSprite[] variant;
		private final BinnieSprite[] petal;
		private final BinnieSprite[] unflowered;
		private final int sections;

		public FlowerSprites(IFlowerType type) {
			sections = type.getSections();
			stem = new BinnieSprite[sections];
			petal = new BinnieSprite[sections];
			variant = new BinnieSprite[sections];
			unflowered = new BinnieSprite[sections];
			for (int section = 0; section < sections; ++section) {
				String suf = (section == 0) ? "" : (String.valueOf(section + 1));
				String pre = (sections == 1) ? "" : "double/";
				stem[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".0");
				petal[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".1");
				variant[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".2");
				unflowered[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".3");
			}
		}

		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getStem(IFlowerType type, int section, boolean flowered) {
			return stem[section % sections].getSprite();
		}

		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getPetal(IFlowerType type, int section, boolean flowered) {
			return (flowered ? petal[section % sections] : unflowered[section % sections]).getSprite();
		}

		@Nullable
		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getVariant(IFlowerType type, int section, boolean flowered) {
			return flowered ? variant[section % sections].getSprite() : null;
		}
	}
}
