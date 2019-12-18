package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ActionableStaff implements Actionable {

    private Area area;
    private Orientation orientation;
    private DiscreteCoordinates coordinates;

    /**
     * Constructor for the ActionnableStaff
     * @param area the area
     * @param orientation the orientation
     * @param coordinates the coordinates in the area
     */
    public ActionableStaff(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        this.area = area;
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    @Override
    public void activeEntity() {
        area.registerActor(new Staff(area, orientation, coordinates));
    }
}
