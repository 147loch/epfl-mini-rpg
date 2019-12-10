package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.actor.monster.FlameSkull;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardAction;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventListener;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventRegister;
import ch.epfl.cs107.play.game.arpg.keybindings.StaticKeyboardEventListener;
import ch.epfl.cs107.play.game.inventory.Inventory;
import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayer extends Player implements Inventory.Holder {

	private final static int ANIMATION_DURATION = 2;
	private final static int BASE_MONEY = 100;

	// ARPG Stuff
	private ARPGPlayerHandler handler;
	private ARPGInventory inventory;
    private ARPGPlayerStatusGUI gui;
    private ARPGItem currentHoldingItem;

    // Animations
	private Animation[] animations;
	private Animation currentAnimation;

	// Keyboard events
    private KeyboardEventRegister keyboardRegister;

	// Attributes
	private float hp;
	private float maxHp;

	// Keyboard Events used for the player
	private class CycleItemKeyEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			cycleCurrentInventoryItem();
		}
	}

	private class UseInventoryKeyItemEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			if (!isDisplacementOccurs())
				useInventoryItem();
		}
	}

	private class MoveOrientateKeyEventListener implements KeyboardEventListener {
		@Override
		public List<KeyboardAction> getActions() {
			return Arrays.asList(
				KeyboardAction.MOVE_DOWN,
				KeyboardAction.MOVE_LEFT,
				KeyboardAction.MOVE_UP,
				KeyboardAction.MOVE_RIGHT
			);
		}

		@Override
		public void onKeyEvent(KeyboardAction action) {
			if (!isDisplacementOccurs()) {
				switch (action) {
					case MOVE_LEFT:
						moveOrientate(Orientation.LEFT);
						break;
					case MOVE_UP:
						moveOrientate(Orientation.UP);
						break;
					case MOVE_RIGHT:
						moveOrientate(Orientation.RIGHT);
					 	break;
					case MOVE_DOWN:
						moveOrientate(Orientation.DOWN);
						break;
					default:
						break;
				}
			}
		}
	}

	private class CheatKeysEventListener implements KeyboardEventListener {
		@Override
		public List<KeyboardAction> getActions() {
			return Arrays.asList(
				KeyboardAction.CHEAT_SPAWN_BOMB,
				KeyboardAction.CHEAT_SPAWN_FLAMESKULL
			);
		}

		@Override
		public void onKeyEvent(KeyboardAction action) {
			switch (action) {
				case CHEAT_SPAWN_BOMB:
					getOwnerArea().registerActor(new Bomb(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates()));
					break;
				case CHEAT_SPAWN_FLAMESKULL:
					getOwnerArea().registerActor(new FlameSkull(getOwnerArea(), Orientation.DOWN, new DiscreteCoordinates(8, 10)));
					break;
				default:
					break;
			}
		}
	}

	public ARPGPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);

		handler = new ARPGPlayerHandler();
		inventory = new ARPGInventory(BASE_MONEY);
		maxHp = 5.f;
		hp = maxHp;

		// TODO put the list of action in the listener instead
		keyboardRegister = new KeyboardEventRegister(getOwnerArea().getKeyboard());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.CYCLE_INVENTORY, new CycleItemKeyEventListener());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.USE_CURRENT_ITEM, new UseInventoryKeyItemEventListener());
		keyboardRegister.registerKeyboardEvents(new MoveOrientateKeyEventListener(),true);
		keyboardRegister.registerKeyboardEvents(new CheatKeysEventListener());

		if (!inventory.addEntry(ARPGItem.BOMB, 3)) System.out.println("Inventory item could not be added.");
		// if (!inventory.addEntry(ARPGItem.SWORD, 1)) System.out.println("Inventory item could not be added.");
		currentHoldingItem = (ARPGItem)inventory.getItemList().get(0);

		Sprite[][] sprites = RPGSprite.extractSprites("zelda/player", 4, 1, 2, this , 16, 32, new Orientation[]
			{Orientation.DOWN , Orientation.RIGHT , Orientation.UP, Orientation.LEFT});
		animations = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);

		gui = new ARPGPlayerStatusGUI(this);
	}

	public void cycleCurrentInventoryItem() {
		List<InventoryItem> list = inventory.getItemList();
		if (list.size() == 0) currentHoldingItem = null;
		else {
			int cur = list.indexOf(currentHoldingItem);
			if (cur == list.size() - 1) currentHoldingItem = (ARPGItem) list.get(0);
			else currentHoldingItem = (ARPGItem) list.get(cur + 1);
		}
	}

	public void useInventoryItem() {
		if (currentHoldingItem == null) return;
		switch (currentHoldingItem) {
			case BOMB:
				// TODO try to catch when the bomb cannot be placed properly, because otherwise we remove an unused item from the inventory
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

	private void moveOrientate(Orientation orientation){
		if (getOrientation() == orientation)
			move(ANIMATION_DURATION * 2);
		else orientate(orientation);
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
	
	
	protected float getHp() {
		return hp;
	}
	
	protected float getMaxHp() {
		return maxHp;
	}
	
	private void addHp(float hp) {
		this.hp += hp;
		
		if (this.hp > maxHp)
			this.hp = maxHp;
	}

	@Override
	public void update(float deltaTime) {
		if (this.getOrientation() == Orientation.UP) {
			currentAnimation = animations[0];
		} else if (this.getOrientation() == Orientation.DOWN) {
			currentAnimation = animations[2];
		} else if (this.getOrientation() == Orientation.RIGHT) {
			currentAnimation = animations[1];
		} else {
			currentAnimation = animations[3];
		}

		keyboardRegister.update();

		if (isDisplacementOccurs()) {
			currentAnimation.update(deltaTime);
		} else {
			currentAnimation.reset();
		}

		if (currentHoldingItem == null && inventory.getItemList().size() > 0) {
			cycleCurrentInventoryItem();
		}

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
		return false;
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
		return KeyboardAction.VIEW_INTERACTION.getAssignedButton(getOwnerArea().getKeyboard()).isPressed();
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
			if (door instanceof CastleDoor)
				interactWith((CastleDoor) door);
			else
				setIsPassingADoor(door);
		}
		
		@Override
		public void interactWith(CastleDoor castleDoor) {
			if (wantsViewInteraction()) {
				if (currentHoldingItem.equals(ARPGItem.CASTLE_KEY))
					castleDoor.changeSignal();
			} else {
				setIsPassingADoor(castleDoor);
				castleDoor.changeSignal();
			}
		}

		@Override
		public void interactWith(Grass grass) {
			grass.cut(true);
		}
		
		@Override
		public void interactWith(Coin collec) {
			inventory.addMoney(collec.getMoneyBack());
			getOwnerArea().unregisterActor(collec);
		}
		
		@Override
		public void interactWith(Heart collec) {
			if (hp < maxHp) {
				addHp(collec.getHeartBack());
				getOwnerArea().unregisterActor(collec);
			}
		}
		
		@Override
		public void interactWith(CastleKey key) {
			inventory.addEntry(ARPGItem.CASTLE_KEY, 1);
			getOwnerArea().unregisterActor(key);
		}
		
		@Override
		public void interactWith(FlameSkull skull) {
			
		}
	}
}
