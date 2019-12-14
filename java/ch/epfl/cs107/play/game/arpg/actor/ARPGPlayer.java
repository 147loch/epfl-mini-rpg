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
import ch.epfl.cs107.play.game.arpg.actor.battle.Arrow;
import ch.epfl.cs107.play.game.arpg.actor.battle.MagicWaterProjectile;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Bow;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.actor.collectable.WaterStaff;
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
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayer extends Player implements Inventory.Holder {

	private enum Behavior {
		IDLE,
		ATTACK_WITH_SWORD,
		ATTACK_WITH_BOW,
		ATTACK_WITH_STAFF
	}
	
	private final static int ANIMATION_DURATION = 2;
	private final static int BASE_MONEY = 100;
	private final static float INVINCIBILITY_TIME = 1.5f;
	private final static float SPEED_BOW = 0.8f;
	private final static float SPEED_STAFF = 0.8f;

	// ARPG Stuff
	private ARPGPlayerHandler handler;
	private ARPGInventory inventory;
    private ARPGPlayerStatusGUI gui;
    private ARPGInventoryGUI inventoryGui;
    private ARPGItem currentHoldingItem;
    
    //FloatingText
    private FloatingText floatingText;

    // Animations
	private Animation[] animationsIdle;
	private Animation[] animationsWithBow;
	private Animation[] animationsWithStaff;

	// Keyboard events
    private KeyboardEventRegister keyboardRegister;

	// Attributes
	private float hp;
	private float maxHp;
	private float invicibilityTime;
	private float lastTookDamage;
    private boolean isInventoryOpen;
    private float speedBow;
    private float speedStaff;
    private Behavior behavior;

	// Keyboard Events used for the player
	private class CycleItemKeyEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			cycleCurrentInventoryItem();
		}
	}
	
	private class OpenInventoryEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			isInventoryOpen = !isInventoryOpen;
		}
	}

	private class UseInventoryKeyItemEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			if (!isDisplacementOccurs())
				useInventoryItem();
		}

		@Override
		public void onKeyReleasedEvent(KeyboardAction previousAction) {
			if (currentHoldingItem != null && currentHoldingItem.equals(ARPGItem.BOW) && behavior.equals(Behavior.ATTACK_WITH_BOW)) {
				Arrow arrow = new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
				if (speedBow >= SPEED_BOW && getOwnerArea().canEnterAreaCells(arrow, getFieldOfViewCells()))
					getOwnerArea().registerActor(arrow);
				speedBow = 0;
				behavior = Behavior.IDLE;
				animationsWithBow[getOrientation().opposite().ordinal()].reset();
			} else if (currentHoldingItem != null && currentHoldingItem.equals(ARPGItem.STAFF) && behavior.equals(Behavior.ATTACK_WITH_STAFF)) {
				MagicWaterProjectile projectile = new MagicWaterProjectile(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
				if (speedStaff >= SPEED_STAFF && getOwnerArea().canEnterAreaCells(projectile, getFieldOfViewCells()))
					getOwnerArea().registerActor(projectile);
				behavior = Behavior.IDLE;
				animationsWithStaff[getOrientation().opposite().ordinal()].reset();
			}
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

	private class CheatKeysEventListener implements StaticKeyboardEventListener {
		@Override
		public void onKeyEvent() {
			getOwnerArea().registerActor(new Bomb(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates()));
		}
	}

	public ARPGPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);

		handler = new ARPGPlayerHandler();
		inventory = new ARPGInventory(BASE_MONEY);
		maxHp = 5.f;
		hp = maxHp;
		invicibilityTime = 0;
		lastTookDamage = 0;
		floatingText = new FloatingText(getPosition());
		isInventoryOpen = false;
		speedBow = 0;
		speedStaff = 0;
		behavior = Behavior.IDLE;

		keyboardRegister = new KeyboardEventRegister(getOwnerArea().getKeyboard());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.CYCLE_INVENTORY, new CycleItemKeyEventListener());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.USE_CURRENT_ITEM, new UseInventoryKeyItemEventListener());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.OPEN_INVENTORY, new OpenInventoryEventListener());
		keyboardRegister.registerKeyboardEvent(KeyboardAction.CHEAT_SPAWN_BOMB, new CheatKeysEventListener());
		keyboardRegister.registerKeyboardEvents(new MoveOrientateKeyEventListener(),true);

		if (!inventory.addEntry(ARPGItem.BOMB, 3)) System.out.println("Inventory item could not be added.");
		// if (!inventory.addEntry(ARPGItem.SWORD, 1)) System.out.println("Inventory item could not be added.");
		currentHoldingItem = (ARPGItem)inventory.getItemList().get(0);

		Sprite[][] sprites = RPGSprite.extractSprites("zelda/player", 4, 1, 2, this , 16, 32, new Orientation[]
			{Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT});
		animationsIdle = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);
		
		Sprite[][] spritesWithBow = RPGSprite.extractSprites("zelda/player.bow", 4, 2, 2, this , 32, 32, new Vector(-0.5f, 0), new Orientation[]
			{Orientation.UP , Orientation.DOWN , Orientation.LEFT, Orientation.RIGHT});
		animationsWithBow = RPGSprite.createAnimations(ANIMATION_DURATION * 2, spritesWithBow, false);
		
		Sprite[][] spritesWithStaff = RPGSprite.extractSprites("zelda/player.staff_water", 4, 2, 2, this , 32, 32, new Vector(-0.5f, 0), new Orientation[]
				{Orientation.UP , Orientation.DOWN , Orientation.LEFT, Orientation.RIGHT});
		animationsWithStaff = RPGSprite.createAnimations(ANIMATION_DURATION * 2, spritesWithStaff, false);

		gui = new ARPGPlayerStatusGUI(this);
		inventoryGui = new ARPGInventoryGUI();
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
				getOwnerArea().registerActor(new Bomb(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates()));
				inventory.removeEntry(ARPGItem.BOMB, 1);
				if (!this.possess(ARPGItem.BOMB)) {
					cycleCurrentInventoryItem();
				}
				break;
			case BOW:
				behavior = Behavior.ATTACK_WITH_BOW;
				break;
			case SWORD:
				break;
			case STAFF:
				behavior = Behavior.ATTACK_WITH_STAFF;
				break;
			case CASTLE_KEY:
			default:
				break;
		}
	}
	
	private void moveOrientate(Orientation orientation){
		if (getOrientation() == orientation)
			move(ANIMATION_DURATION * 2);
		else orientate(orientation);
	}

	public void takeDamage(float damage) {
		if (lastTookDamage <= 0) {
			if (hp >= damage) {
				hp -= damage;
				lastTookDamage = damage;
			}
			invicibilityTime = INVINCIBILITY_TIME;
			floatingText.init("❤", getPosition());
		}
		// TODO
		//   	turn player sprite red-er
		//   	if hp == death: die, game over, restart
	}

	public void takeDamage() {
		takeDamage(0.5f);
	}

	public float tookDamage() {
		return lastTookDamage;
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
		if (behavior.equals(Behavior.ATTACK_WITH_BOW)) {
			speedBow += deltaTime;
			animationsWithBow[getOrientation().opposite().ordinal()].update(deltaTime);
		} else if (behavior.equals(Behavior.ATTACK_WITH_STAFF)) {
			speedStaff += deltaTime;
			animationsWithStaff[getOrientation().opposite().ordinal()].update(deltaTime);
		}

		keyboardRegister.update();

		if (isDisplacementOccurs()) {
			behavior = Behavior.IDLE;
			animationsIdle[getOrientation().opposite().ordinal()].update(deltaTime);
		} else {
			animationsIdle[getOrientation().opposite().ordinal()].reset();
		}

		if (currentHoldingItem == null && inventory.getItemList().size() > 0) {
			cycleCurrentInventoryItem();
		}

		if (invicibilityTime > 0) {
			invicibilityTime -= deltaTime;
		} else if (invicibilityTime < 0) {
			invicibilityTime = 0;
			lastTookDamage = 0;
		}

		floatingText.update(deltaTime);
		
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
		return true; // TODO faut voir, les monstres peuvent traverser le joueur avec ça
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
		if (behavior.equals(Behavior.ATTACK_WITH_BOW))
			animationsWithBow[getOrientation().opposite().ordinal()].draw(canvas);
		else if (behavior.equals(Behavior.ATTACK_WITH_STAFF))
			animationsWithStaff[getOrientation().opposite().ordinal()].draw(canvas);
		else
			animationsIdle[getOrientation().opposite().ordinal()].draw(canvas);
		
		floatingText.draw(canvas);
		
		if (isInventoryOpen)
			inventoryGui.draw(canvas);
		else
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
		public void interactWith(Coin coin) {
			inventory.addMoney(coin.getMoneyBack());
			getOwnerArea().unregisterActor(coin);
		}
		
		@Override
		public void interactWith(Heart heart) {
			if (hp < maxHp) {
				addHp(heart.getHeartBack());
				getOwnerArea().unregisterActor(heart);
			}
		}
		
		@Override
		public void interactWith(CastleKey key) {
			inventory.addEntry(ARPGItem.CASTLE_KEY, 1);
			getOwnerArea().unregisterActor(key);
		}
		
		@Override
		public void interactWith(Bow bow) {
			inventory.addEntry(ARPGItem.BOW, 1);
			getOwnerArea().unregisterActor(bow);
		}
		
		@Override
		public void interactWith(WaterStaff staff) {
			inventory.addEntry(ARPGItem.STAFF, 1);
			getOwnerArea().unregisterActor(staff);
		}
	}
}
