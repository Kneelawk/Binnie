package binnie.craftgui.resource;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet implements IStyleSheet {
	protected Map<Object, Texture> textures;

	public StyleSheet() {
		this.textures = new HashMap<>();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Texture getTexture(final Object key) {
		if (!this.textures.containsKey(key)) {
			return StyleSheetManager.getTexture(key);
		}
		return this.textures.get(key);
	}
}
