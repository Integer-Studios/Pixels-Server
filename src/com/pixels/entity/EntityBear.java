package com.pixels.entity;

import java.util.Random;

import com.pixels.body.BodyQuadruped;
import com.pixels.piece.Piece;
import com.pixels.world.World;

public class EntityBear extends EntityAlive {
	
	public float hunger = 200.0F; 
	public Piece target = null;
	
	public EntityBear() {
		this.id = 4;
		body = new BodyQuadruped(this, 1.5f, 1.448f);
	}
	
	public EntityBear(float x, float y, boolean prop) {
		super(4, x, y, prop);
		body = new BodyQuadruped(this, 1.5f, 1.448f);
	}
	
	public void update(World w) {
		
		hunger -= 0.05F;
		
		if (hunger <= 50 && target == null) {
			target = findBerries(w);
			if (target == null) {
				wander();
			}
		} else if (target != null) {
			if (target.posX < (posX - 0.4F)) {
				velocityX = -speed;
			} else if (target.posX > (posX + 0.4F)) {
				velocityX = speed;
			} else {
				velocityX = 0f;
			}
			if (target.posY < (posY - 0.4F)) {
				velocityY = -speed;
			} else if (target.posY >  (posY + 0.4F)) {
				velocityY = speed;
			} else {
				velocityY = 0f;
			}
			
			float distance = (int)Math.sqrt((target.posX-posX)*(target.posX-posX) + (target.posY-posY)*(target.posY-posY));
			if (distance <= 0.4F) {
				velocityX = 0f;
				velocityY = 0f;
				hunger = 200F;
				if (target.id == 11) 
					w.setPieceID(target.posX, target.posY, 16);
				if (target.id == 12) 
					w.setPieceID(target.posX, target.posY, 17);
				target = null;
			}
			
		} else {
		
			wander();
		
		}
		super.update(w);

	}
	
	public void wander() {
		Random r = new Random();
		if (r.nextInt(60) == 0) {
			velocityX = speed;
		}
		if (r.nextInt(60) == 0) {
			velocityX = -speed;
		}
		if (r.nextInt(60) == 0) {
			velocityY = speed;
		}
		if (r.nextInt(60) == 0) {
			velocityY = -speed;
		}
		if (r.nextInt(60) == 0) {
			velocityX = 0f;
		}
		if (r.nextInt(60) == 0) {
			velocityY = 0f;
		}
		if (r.nextInt(60) == 0) {
			velocityX = 0f;
			velocityY = 0f;
		}
	}
	
	public Piece findBerries(World w) {
		float r = 7;
		boolean berry = false;
		float currentDistance = 1000000F;
		Piece berryPiece = null;
		for (int i = (int) (posX - r); i <= (int) (posX + r); i ++) {
			for (int j = (int) (posY - r); j <= (int) (posY + r); j ++) {
		        float a = i - posX;
		        float b = j - posY;
		        if (a*a + b*b <= r*r) {
		        	Piece p = w.getPiece(i, j);
		        	if (p != null) {
			        	if (p.getPieceID() == 11 || p.getPieceID() == 12) {
			    			float distance = (int)Math.sqrt((p.posX-posX)*(p.posX-posX) + (p.posY-posY)*(p.posY-posY));
			        		if (distance < currentDistance) {
			        			berry = true;
				        		berryPiece = p;
				        		currentDistance = distance;
			        		}
			        	}
		        	}
		        }
		    }
		}
		
		if (berry) {
			return berryPiece;
		} else {
			return null;
		}
		
	}
	
	float speed = 0.03f;

}
