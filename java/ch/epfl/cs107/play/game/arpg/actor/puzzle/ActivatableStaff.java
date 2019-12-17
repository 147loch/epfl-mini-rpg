package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ActivatableStaff implements Activatable {

    private Area area;
    private Orientation orientation;
    private DiscreteCoordinates coordinates;

    public ActivatableStaff(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        this.area = area;
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    @Override
    public void activeEntity() {
        area.registerActor(new Staff(area, orientation, coordinates));
    }
}
