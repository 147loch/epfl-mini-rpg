package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.ActionableStaff;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.PressurePlate;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.LightHalo;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Temple extends ARPGArea {

    @Override public String getTitle() { return "zelda/Temple"; }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        registerActor(new Door("zelda/RouteTemple", new DiscreteCoordinates(5, 5),
                Logic.TRUE, this, Orientation.DOWN,
                new DiscreteCoordinates(4, 0)));

        registerActor(new LightHalo(this));

        registerActor(new PressurePlate(this, Orientation.UP, new DiscreteCoordinates(2, 2), new ActionableStaff(this, Orientation.UP, new DiscreteCoordinates(4, 3))));

        for (int i = 1; i <= 4; i++) {
            registerActor(new Coin(this, Orientation.UP, new DiscreteCoordinates(1, i)));
        }
    }
}
