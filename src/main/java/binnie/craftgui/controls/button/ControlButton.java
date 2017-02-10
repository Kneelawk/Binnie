package binnie.craftgui.controls.button;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventButtonClicked;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ControlButton extends Control {
	@Nullable
	private ControlText textWidget;
	@Nullable
	private String text;

	public ControlButton(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height);
		this.addAttribute(Attribute.MouseOver);
		this.addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				ControlButton.this.callEvent(new EventButtonClicked(ControlButton.this.getWidget()));
				ControlButton.this.onMouseClick(event);
			}
		}.setOrigin(EventHandler.Origin.Self, this));
	}

	protected void onMouseClick(final EventMouse.Down event) {
	}

	public ControlButton(final IWidget parent, final int x, final int y, final int width, final int height, final String text) {
		this(parent, x, y, width, height);
		this.text = text;
		this.textWidget = new ControlText(this, this.getArea(), text, TextJustification.MiddleCenter);
	}

	@Override
	public void onUpdateClient() {
		if (this.textWidget != null) {
			String text = this.getText();
			if (text != null) {
				this.textWidget.setValue(text);
			}
		}
	}

	@Nullable
	public String getText() {
		return this.text;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = CraftGUITexture.ButtonDisabled;
		if (this.isMouseOver()) {
			texture = CraftGUITexture.ButtonHighlighted;
		} else if (this.isEnabled()) {
			texture = CraftGUITexture.Button;
		}
		CraftGUI.render.texture(texture, this.getArea());
	}
}
