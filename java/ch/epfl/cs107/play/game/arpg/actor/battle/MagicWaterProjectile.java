package ch.epfl.cs107.play.game.arpg.actor.battle;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.actor.monster.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.monster.MonsterEntity;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class MagicWaterProjectile extends Projectile {

	private static final int FRAME_SPEED = 5;
	private static final float MAX_LIFETIME = 1.f;
	private static final DamageType DAMAGE_TYPE = DamageType.MAGICAL;
	
	private Animation animation;
	private MagicWaterProjectileHandler handler;
	
	public MagicWaterProjectile(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, DAMAGE_TYPE, FRAME_SPEED, MAX_LIFETIME);
		
		Sprite[] sprites = new RPGSprite[4];
		for (int i = 0; i < 4; i++) {
			sprites[i] = new RPGSprite("zelda/magicWaterProjectile", 1.f, 1.f, this, new RegionOfInterest(32*i, 0, 32, 32));	
		}
		animation = new Animation(4, sprites, true);
		
		handler = new MagicWaterProjectileHandler();
	}
	
	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);	
	}
	
	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

	private class MagicWaterProjectileHandler implements ARPGInteractionVisitor {
		
		@Override
		public void interactWith(Grass grass) {
			grass.cut(true);
			getOwnerArea().unregisterActor(MagicWaterProjectile.this);
		}
		
		@Override
		public void interactWith(Bomb bomb) {
			bomb.explode();
			getOwnerArea().unregisterActor(MagicWaterProjectile.this);
		}
		
		@Override
		public void interactWith(MonsterEntity monster) {
			for (DamageType dt : monster.getVulnerabilities()) {
				if (dt.equals(DAMAGE_TYPE))
				{
					monster.takeDamage();
					getOwnerArea().unregisterActor(MagicWaterProjectile.this);
				}
			}
		}
	}
}
