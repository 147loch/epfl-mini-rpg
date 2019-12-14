package ch.epfl.cs107.play.game.arpg.actor.battle.weapon;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.actor.entity.Grass;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.MonsterEntity;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.Activator;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Arrow extends Projectile implements FlyableEntity {
	
	private static final int FRAME_SPEED = 3;
	private static final float MAX_LIFETIME = 1.f;
	private static final DamageType DAMAGE_TYPE = DamageType.PHYSICAL;
	
	private RPGSprite[] sprites;
	private ArrowHandler handler;

	public Arrow(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, DAMAGE_TYPE, FRAME_SPEED, MAX_LIFETIME);
		
		sprites = new RPGSprite[4];
		for (int i = 0; i < 4; i++) {
			sprites[i] = new RPGSprite("zelda/arrow", 1.f, 1.f, this, new RegionOfInterest(32*i, 0, 32, 32));	
		}
		
		handler = new ArrowHandler();
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);	
	}
	
	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}
	
	@Override
	public void draw(Canvas canvas) {
		sprites[getOrientation().ordinal()].draw(canvas);
	}
	
	private class ArrowHandler implements ARPGInteractionVisitor {
		
		@Override
		public void interactWith(Grass grass) {
			grass.cut(true);
			getOwnerArea().unregisterActor(Arrow.this);
		}
		
		@Override
		public void interactWith(Bomb bomb) {
			bomb.explode();
			getOwnerArea().unregisterActor(Arrow.this);
		}
		
		@Override
		public void interactWith(MonsterEntity monster) {
			monster.takeDamage(DamageType.PHYSICAL, 1.f); // TODO put constant
			getOwnerArea().unregisterActor(Arrow.this);
		}
		
		@Override
		public void interactWith(Activator activator) {
			activator.active();
		}
	}
}
