package ch.epfl.cs107.play.game.arpg;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.area.ARPGArea;
import ch.epfl.cs107.play.game.arpg.area.Ferme;
import ch.epfl.cs107.play.game.arpg.area.Route;
import ch.epfl.cs107.play.game.arpg.area.Village;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ARPG extends RPG {

    // we use the ARPGArea type to ensure it isn't an area from any tutorial or some other sort
    private int areaIndex;
    private final List<ARPGArea> areas = new ArrayList<>(
            Arrays.asList(
                    new Ferme(),
                    new Route(),
                    new Village()
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
    public void update(float deltaTime) {}
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	if (super.begin(window, fileSystem)) {
    	    createAreas();
    	    areaIndex = 0;
    		Area area = setCurrentArea(areas.get(areaIndex).getTitle(), true);
    		ARPGPlayer player = new ARPGPlayer(area, Orientation.DOWN, new DiscreteCoordinates(6, 10));
    		initPlayer(player);
    		
    		return true;
    	}
    	return false;
    }
}
