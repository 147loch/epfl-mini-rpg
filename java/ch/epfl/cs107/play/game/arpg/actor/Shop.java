package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.keybindings.KeyboardAction;
import ch.epfl.cs107.play.game.inventory.Inventory;
import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;
import java.util.Objects;

public class Shop extends AreaEntity implements Inventory.Holder {

    private ARPGInventory inventory;
    private ARPGInventoryGUI inventoryGUI;

    private Sprite sprite;

    private boolean transactionOkSoundPlayed;
    private boolean transactionNotOkSoundPlayed;
    private boolean isShopOpened;

    public Shop(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);

        inventory = new ARPGInventory(0);
        inventory.addEntry(ARPGItem.BOMB, 20);
        inventory.addEntry(ARPGItem.ARROW, 50);
        inventory.addEntry(ARPGItem.BOW, 1);

        transactionNotOkSoundPlayed = true;
        transactionOkSoundPlayed = true;
        isShopOpened = false;

        sprite = new RPGSprite("zelda/character", 1.f, 2.f, this, new RegionOfInterest(0, 32*2, 16, 32), new Vector(0, 1.f));

        inventoryGUI = new ARPGInventoryGUI(inventory, "Shop");
    }

    public void selectionUpdate(KeyboardAction action) {
        inventoryGUI.selectionUpdate(action);
    }

    public boolean isShopOpened() {
        return isShopOpened;
    }

    public void openShop() {
        isShopOpened = true;
    }

    public void closeShop() {
        isShopOpened = false;
    }

    public void buy(ARPGInventory playerInventory) {
        if (Objects.nonNull(inventoryGUI.getCurrentSelectedItem()) && playerInventory.getMoney() >= inventoryGUI.getCurrentSelectedItem().getPrice()) {
            playerInventory.addMoney(-inventoryGUI.getCurrentSelectedItem().getPrice());
            playerInventory.addEntry(inventoryGUI.getCurrentSelectedItem(), 1);
            inventory.removeEntry(inventoryGUI.getCurrentSelectedItem(), 1);
            transactionOkSoundPlayed = false;
        } else {
            transactionNotOkSoundPlayed = false;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        if (isShopOpened) {
            inventoryGUI.draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return List.of(getCurrentMainCellCoordinates(), getCurrentMainCellCoordinates().up());
    }

    @Override public boolean takeCellSpace() { return true; }
    @Override public boolean isCellInteractable() { return false; }
    @Override public boolean isViewInteractable() { return true; }

    @Override public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public boolean possess(InventoryItem item) {
        return inventory.isInInventory(item);
    }

    @Override
    public void bip(Audio audio) {
        if (!transactionOkSoundPlayed) {
            audio.playSound(audio.getSound(ResourcePath.getSounds("transactionOk")), false, 0.75f, false, false, false);
            transactionOkSoundPlayed = true;
        }

        if (!transactionNotOkSoundPlayed) {
            audio.playSound(audio.getSound(ResourcePath.getSounds("transactionFail")), false, 0.75f, false, false, false);
            transactionNotOkSoundPlayed = true;
        }
    }
}
