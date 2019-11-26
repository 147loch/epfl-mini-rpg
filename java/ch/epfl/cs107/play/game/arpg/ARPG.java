package ch.epfl.cs107.play.game.arpg;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ARPG extends RPG {
	
    @Override
    public String getTitle() {
        return "ZeldIC";
    }
    
    @Override
    public void update(float deltaTime) {}
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	if (super.begin(window, fileSystem)) {
    		Area area = setCurrentArea("zelda/Ferme", true);
    		ARPGPlayer player = new ARPGPlayer(area, Orientation.DOWN, new DiscreteCoordinates(6, 10));
    		initPlayer(player);
    		
    		return true;
    	}
    	return false;
    }
}
