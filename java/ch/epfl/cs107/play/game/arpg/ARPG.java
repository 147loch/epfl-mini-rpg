package ch.epfl.cs107.play.game.arpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.area.*;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ARPG extends RPG {

    private final static int STARTING_AREA = 7;
    private final static DiscreteCoordinates STARTING_COORDINATES = new DiscreteCoordinates(2, 4);

    // we use the ARPGArea type to ensure it isn't an area from any tutorial or some other sort
    private final List<ARPGArea> areas = new ArrayList<>(
            Arrays.asList(
                    new Ferme(),
                    new Route(),
                    new Village(),
                    new RouteChateau(),
                    new Chateau(),
                    new RouteTemple(),
                    new Temple(),
                    new MaisonFerme(),
                    new GrotteMew()
            )
    );

    private void createAreas() {
        areas.forEach(this::addArea);
    }

    @Override
    public String getTitle() {
        return "ZeldIC";
    }
    
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	if (super.begin(window, fileSystem)) {
    	    createAreas();
    		Area area = setCurrentArea(areas.get(STARTING_AREA).getTitle(), true);
    		ARPGPlayer player = new ARPGPlayer(area, Orientation.DOWN, STARTING_COORDINATES);
    		initPlayer(player);

    		return true;
    	}
    	return false;
    }
}
