package com.pixels.util;

import com.pixels.entity.Entity;
import com.pixels.piece.Piece;

public class CollisionManager {
	
	public static void testPieceCollision(Entity e, Piece p) {
		if (p.doesCollide() && e.collisionBox.intersects(p.collisionBox)) {			
			
			// + if entity is coming from left, - if not
			float deltaX = (p.posX+0.5f) - e.posX;
			// + if entity is coming from top, - if not
			float deltaY = (p.posY+1) - e.posY;
			
			
			if (deltaY > 0 && e.velocityY > 0) {
				e.velocityY = 0;
			} else
			if (deltaY < 0 && e.velocityY < 0) {
				e.velocityY = 0;
			} else
			if (deltaX > 0 && e.velocityX > 0) {
				e.velocityX = 0;
			} else
			if (deltaX < 0 && e.velocityX < 0) {
				e.velocityX = 0;
			}
			
		}
	}
	
	public static void testEntityCollision(Entity e1, Entity e2) {
		if (e1.collisionBox.intersects(e2.collisionBox)) {
			// + if e1 is coming from left or e2 is coming from the right, - if not
			float deltaX = e2.posX - e1.posX;
			// + if e1 is coming from top or e2 from bottom, - if not
			float deltaY = e2.posY - e1.posY;
			
			if (deltaY > 0 && e1.velocityY > 0) {
				e1.velocityY = 0;
			} else
			if (deltaY < 0 && e1.velocityY < 0) {
				e1.velocityY = 0;
			} else if (deltaX > 0 && e1.velocityX > 0) {
				e1.velocityX = 0;
			} else
			if (deltaX < 0 && e1.velocityX < 0) {
				e1.velocityX = 0;
			}
			
			
			if (deltaY > 0 && e2.velocityY < 0) {
				e2.velocityY = 0;
			} else
			if (deltaY < 0 && e2.velocityY > 0) {
				e2.velocityY = 0;
			} else
			if (deltaX > 0 && e2.velocityX < 0) {
				e2.velocityX = 0;
			} else
			if (deltaX < 0 && e2.velocityX > 0) {
				e2.velocityX = 0;
			}
			
		}
	}

}
