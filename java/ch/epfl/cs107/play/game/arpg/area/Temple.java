package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.rpg.actor.Door;
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

        registerActor(new Staff(this, Orientation.UP, new DiscreteCoordinates(4, 3)));
        registerActor(new Coin(this, Orientation.UP, new DiscreteCoordinates(1, 4)));
        registerActor(new Coin(this, Orientation.UP, new DiscreteCoordinates(1, 3)));
        registerActor(new Coin(this, Orientation.UP, new DiscreteCoordinates(1, 2)));
        registerActor(new Coin(this, Orientation.UP, new DiscreteCoordinates(1, 1)));
    }
}
