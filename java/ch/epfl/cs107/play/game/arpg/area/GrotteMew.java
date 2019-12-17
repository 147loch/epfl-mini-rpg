package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.LightHalo;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class GrotteMew extends ARPGArea {

    @Override public String getTitle() { return "GrotteMew"; }

    @Override
    protected void createArea() {
        registerActor(new Background(this));

        registerActor(new LightHalo(this));

        registerActor(new Door("zelda/Village", new DiscreteCoordinates(25, 17),
                Logic.TRUE, this, Orientation.LEFT,
                new DiscreteCoordinates(8,2)));

        registerActor(new LogMonster(this, Orientation.RIGHT, new DiscreteCoordinates(6, 7)));
        registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(10, 7)));

        for (int i = 4; i <= 12; i++) {
            registerActor(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(i, 11)));
        }

        registerActor(new Heart(this, Orientation.DOWN, new DiscreteCoordinates(4, 3)));
        registerActor(new Heart(this, Orientation.DOWN, new DiscreteCoordinates(5, 3)));
        registerActor(new Heart(this, Orientation.DOWN, new DiscreteCoordinates(11, 3)));
        registerActor(new Heart(this, Orientation.DOWN, new DiscreteCoordinates(12, 3)));

    }

}
