package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.npc.Emotion;
import ch.epfl.cs107.play.game.arpg.actor.npc.NPC;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class MaisonFerme extends ARPGArea {

    @Override
    public String getTitle() {
        return "PetalburgTimmy";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(6, 10), Logic.TRUE, this,
                Orientation.DOWN, new DiscreteCoordinates(3,  0), new DiscreteCoordinates(4,  0)));
        
        registerActor(new Sign("Tiens c'est marrant, il y a une gameboy à côté.", this, Orientation.UP, new DiscreteCoordinates(2, 7)));
        
        registerActor(new NPC("Sauve le roi, mon fils. Il est prisonnier des monstres !", Emotion.EXCLAMATION, this, Orientation.UP, new DiscreteCoordinates(4, 4)));
    }
}
