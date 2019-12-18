package ch.epfl.cs107.play.game.arpg.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.arpg.ARPG;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardAction;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventRegister;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGInventoryGUI implements Graphics {
	
	private static final int GUI_DEPTH = 2000;

	private static final ARPGItem[][] slotList = {
			{ ARPGItem.BOMB,  ARPGItem.ARROW, null,           ARPGItem.CASTLE_KEY },
			{ ARPGItem.SWORD, ARPGItem.BOW,   ARPGItem.STAFF, null                }
	};

	private DiscreteCoordinates currentSelection;
	private ARPGInventory inventory;
	private ARPGItem currentSelectedItem;
	private String dialogTitle;

	public ARPGInventoryGUI(ARPGInventory inventory, String dialogTitle) {
		this.inventory = inventory;
		this.dialogTitle = dialogTitle;
		this.currentSelection = new DiscreteCoordinates(0,1);
	}

	protected ARPGItem getCurrentSelectedItem() { return currentSelectedItem; }

	void selectionUpdate(KeyboardAction action) {
		switch (action) {
			case MOVE_UP:
				if (currentSelection.y == 0) {
					currentSelection = currentSelection.up();
				}
				break;
			case MOVE_DOWN:
				if (currentSelection.y == 1) {
					currentSelection = currentSelection.down();
				}
				break;
			case MOVE_LEFT:
				if (currentSelection.x > 0) {
					currentSelection = currentSelection.left();
				}
				break;
			case MOVE_RIGHT:
				if (currentSelection.x < 3) {
					currentSelection = currentSelection.right();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		
		//Background
		new ImageGraphics(ResourcePath.getSprite("zelda/inventory.background"),
				9.5f, 9.5f, new RegionOfInterest(0, 0, 240, 240),
				anchor.add(new Vector(3.f, 0.f)), 1, GUI_DEPTH).draw(canvas);
		
		//Title
		TextGraphics title = new TextGraphics(dialogTitle, 1.f, Color.BLACK);
		title.setDepth(GUI_DEPTH);
		title.setRelativeTransform(Transform.I.translated(canvas.getPosition().sub(width/2, height/2)));
		title.setAnchor(new Vector(5.25f, 8.f));
		title.draw(canvas);

		//Slots
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == currentSelection.y && j == currentSelection.x) {
					new ImageGraphics(ResourcePath.getSprite("zelda/inventory.selector"),
							2.f, 2.f, new RegionOfInterest(0, 0, 64, 64),
							anchor.add(new Vector(2*j + 3.7f, 3.f*i + 2.f)), 1, GUI_DEPTH+1).draw(canvas);
				} else {
					new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
						2.f, 2.f, new RegionOfInterest(0, 0, 64, 64),
						anchor.add(new Vector(2*j + 3.7f, 3.f*i + 2.f)), 1, GUI_DEPTH).draw(canvas);
				}
			}
		}

		for (int i = 0; i < slotList.length; i++) {
			for (int j = 0; j < slotList[i].length; j++) {
				int k = slotList.length - 1 - i;
				if (slotList[i][j] != null) {
					ARPGItem item = slotList[i][j];
					int itemQty = inventory.getAmountOf(item);

					if (itemQty > 0) {
						new ImageGraphics(item.getResourcePath(),
								2.f, 2.f, new RegionOfInterest(0, 0, 16, 32),
								anchor.add(new Vector(2.f*j + 3.7f, 2.f + k * 3.f)), 1, GUI_DEPTH).draw(canvas);
						TextGraphics number = new TextGraphics(inventory.getAmountOf(item) + "x", 0.6f, Color.BLACK);
						number.setDepth(GUI_DEPTH);
						number.setRelativeTransform(Transform.I.translated(canvas.getPosition().sub(width/2, height/2)));
						number.setAnchor(new Vector(2.f*j + 4.25f, 1.5f + k * 3.f));
						number.draw(canvas);
					}

					if (currentSelection.x == j && currentSelection.y == k) {
						if (itemQty > 0) {
							currentSelectedItem = item;
						} else {
							currentSelectedItem = null;
						}
					}
				}
			}
		}
	}

}
