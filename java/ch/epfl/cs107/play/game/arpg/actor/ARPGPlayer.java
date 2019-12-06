package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.inventory.Inventory;
import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ARPGPlayer extends Player implements Inventory.Holder {

	private final static int ANIMATION_DURATION = 4;
	private final static int BASE_MONEY = 146;

	private ARPGPlayerHandler handler;
	private ARPGInventory inventory;
	private Animation[] animations;
	private Animation currentAnimation;
	private ARPGPlayerStatusGUI gui;

	private ARPGItem currentHoldingItem;
	private float hp;

	public ARPGPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);

		handler = new ARPGPlayerHandler();
		inventory = new ARPGInventory(BASE_MONEY);
		hp = 3.5f;

		if (!inventory.addEntry(ARPGItem.BOMB, 3)) System.out.println("Inventory item could not be added.");
		// if (!inventory.addEntry(ARPGItem.SWORD, 1)) System.out.println("Inventory item could not be added.");
		currentHoldingItem = (ARPGItem)inventory.getItemList().get(0);

		Sprite[][] sprites = RPGSprite.extractSprites("zelda/player", 4, 1, 2, this , 16, 32, new Orientation[]
			{Orientation.DOWN , Orientation.RIGHT , Orientation.UP, Orientation.LEFT});
		animations = RPGSprite.createAnimations(ANIMATION_DURATION/2, sprites);

		// TODO je ne suis pas persuadé que cela soit une bonne idée de donner l'inventaire au GUI alors j'ai enlevé mais implémenté une méthode protected dans ARGPPlayer
		gui = new ARPGPlayerStatusGUI(this);
	}

	public void cycleCurrentInventoryItem() {
		List<InventoryItem> list = inventory.getItemList();
		if (list.size() == 0) this.currentHoldingItem = null;
		else {
			int cur = list.indexOf(currentHoldingItem);
			if (cur == list.size() - 1) this.currentHoldingItem = (ARPGItem) list.get(0);
			else this.currentHoldingItem = (ARPGItem) list.get(cur + 1);
		}
	}

	public void useInventoryItem() {
		if (currentHoldingItem == null) return;
		switch (currentHoldingItem) {
			case BOMB:
				getOwnerArea().registerActor(new Bomb(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates().jump(getOrientation().toVector())));
				inventory.removeEntry(ARPGItem.BOMB, 1);
				if (!this.possess(ARPGItem.BOMB)) {
					cycleCurrentInventoryItem();
				}
				break;
			case ARROW:
			case SWORD:
			case STAFF:
			case CASTLE_KEY:
			case BOW:
			default:
				break;
		}
	}

	private void moveOrientate(Orientation orientation, Button b){
		if (b.isDown()) {
			if (getOrientation() == orientation)
				 move(ANIMATION_DURATION);
			else orientate(orientation);
		}
	}

	public void takeDamage() {
		if (hp >= 0.5f)
			hp -= 0.5f;
		// TODO animation and stuff also death
	}

	protected ARPGItem getCurrentItem() {
		return currentHoldingItem;
	}

	protected int getInventoryMoney() {
		return inventory.getMoney();
	}

	@Override
	public void update(float deltaTime) {
		Keyboard keyboard = getOwnerArea().getKeyboard();
		moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
		moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		if (this.getOrientation() == Orientation.UP) {
			currentAnimation = animations[0];
		} else if (this.getOrientation() == Orientation.DOWN) {
			currentAnimation = animations[2];
		} else if (this.getOrientation() == Orientation.RIGHT) {
			currentAnimation = animations[1];
		} else
		{
			currentAnimation = animations[3];
		}

		if (this.isDisplacementOccurs()) {
			currentAnimation.update(deltaTime);
		} else {
			currentAnimation.reset();
		}

		// TODO KeyboardEvents Register avec gestionnaire de touches au lieu de hardcode.
		if (keyboard.get(Keyboard.TAB).isPressed())
			cycleCurrentInventoryItem();

		if (currentHoldingItem == null && inventory.getItemList().size() > 0) {
			cycleCurrentInventoryItem();
		}

		if (keyboard.get(Keyboard.SPACE).isPressed() && !this.isDisplacementOccurs())
			useInventoryItem();

		super.update(deltaTime);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		currentAnimation.draw(canvas);
		gui.draw(canvas);
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return getOwnerArea().getKeyboard().get(Keyboard.E).isPressed();
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public boolean possess(InventoryItem item) {
		return inventory.isInInventory(item);
	}

	private class ARPGPlayerHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(Door door) {
			setIsPassingADoor(door);
		}

		@Override
		public void interactWith(Grass grass) {
			grass.setInactive();
		}
	}

	protected float getHp() {
		return hp;
	}
}
