package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.entity.CastleDoor;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardAction;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventListener;
import ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventRegister;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.Arrays;
import java.util.List;

public class RouteChateau extends ARPGArea {

	private KeyboardEventRegister keyboardEventRegister;

	private class CheatKeysEventListener implements KeyboardEventListener {
		@Override
		public List<KeyboardAction> getActions() {
			return Arrays.asList(
					KeyboardAction.CHEAT_SPAWN_FLAMESKULL,
					KeyboardAction.CHEAT_SPAWN_LOGMONSTER
			);
		}

		@Override
		public void onKeyEvent(KeyboardAction action) {
			switch (action) {
				case CHEAT_SPAWN_FLAMESKULL:
					registerActor(new FlameSkull(RouteChateau.this, Orientation.DOWN, new DiscreteCoordinates(8, 10)));
					break;
				case CHEAT_SPAWN_LOGMONSTER:
					registerActor(new LogMonster(RouteChateau.this, Orientation.DOWN, new DiscreteCoordinates(8, 10)));
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		keyboardEventRegister.update();
	}

	@Override
	public String getTitle() {
		return "zelda/RouteChateau";
	}

	@Override
	protected void createArea() {
		registerActor(new Background(this));
		registerActor(new Foreground(this));
		
		registerActor(new Door("zelda/Route", new DiscreteCoordinates(9, 18),
				Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(9, 0), new DiscreteCoordinates(10, 0)));
		
		registerActor(new CastleDoor("zelda/Chateau", new DiscreteCoordinates(7, 1), this, Orientation.UP,
				new DiscreteCoordinates(9, 13), new DiscreteCoordinates(10, 13)));

		keyboardEventRegister = new KeyboardEventRegister(getKeyboard());
		keyboardEventRegister.registerKeyboardEvents(new CheatKeysEventListener());
	}
}
