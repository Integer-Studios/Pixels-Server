package com.pixels.util;

import org.newdawn.slick.geom.Rectangle;

import com.pixels.entity.Entity;
import com.pixels.piece.Piece;

public class CollisionManager {
	
	public static void testPieceCollision(Entity e, Piece p) {
		
		if (!p.doesCollide())
			return;
				
		Rectangle box = e.collisionBox;
		
		float baseX = e.posX - (box.getWidth()/2);
		float baseY = e.posY - box.getHeight();
		
		//test anticipated position
		box.setLocation(baseX+e.velocityX, baseY+e.velocityY);
		if (box.intersects(p.collisionBox)) {
			
			//see if stopping X velocity fixes collision
			box.setLocation(baseX, baseY+e.velocityY);
			if (!box.intersects(p.collisionBox)) {
				e.velocityX = 0;
				return;
			}
			
			//see if stopping Y velocity fixes collision
			box.setLocation(baseX+e.velocityX, baseY);
			if (!box.intersects(p.collisionBox)) {
				e.velocityY = 0;
				return;
			}
			
			//see if stopping both velocities fixes collision
			box.setLocation(baseX, baseY);
			if (!box.intersects(p.collisionBox)) {
				e.velocityX = 0;
				e.velocityY = 0;
				return;
			} else {
				//give up
				return;
			}
			
		}
		
	}
	
	public static void testEntityCollision(Entity e1, Entity e2) {

		Rectangle box1 = e1.collisionBox;
		
		float baseX1 = e1.posX - (box1.getWidth()/2);
		float baseY1 = e1.posY - box1.getHeight();
		
		Rectangle box2 = e2.collisionBox;
		
		float baseX2 = e2.posX - (box2.getWidth()/2);
		float baseY2 = e2.posY - box2.getHeight();
		
		//test anticipated positions
		box1.setLocation(baseX1+e1.velocityX, baseY1+e1.velocityY);
		box2.setLocation(baseX2+e2.velocityX, baseY2+e2.velocityY);
		if (box1.intersects(box2)) {
			
			//see if stopping e1 X velocity fixes collision
			box1.setLocation(baseX1, baseY1+e1.velocityY);
			if (!box1.intersects(box2)) {
				e1.velocityX = 0;
				return;
			}
			
			//see if stopping e2 X velocity fixes collision
			box1.setLocation(baseX1+e1.velocityX, baseY1+e1.velocityY);
			box2.setLocation(baseX2, baseY2+e2.velocityY);
			if (!box1.intersects(box2)) {
				e2.velocityX = 0;
				return;
			}
			
			//see if stopping e1 Y velocity fixes collision
			box1.setLocation(baseX1+e1.velocityX, baseY1);
			box2.setLocation(baseX2+e2.velocityX, baseY2+e2.velocityY);
			if (!box1.intersects(box2)) {
				e1.velocityY = 0;
				return;
			}
			
			//see if stopping e2 Y velocity fixes collision
			box1.setLocation(baseX1+e1.velocityX, baseY1+e1.velocityY);
			box2.setLocation(baseX2+e2.velocityX, baseY2);
			if (!box1.intersects(box2)) {
				e2.velocityY = 0;
				return;
			}
			
			//see if stopping both X velocity fixes collision
			box1.setLocation(baseX1, baseY1+e1.velocityY);
			box2.setLocation(baseX2, baseY2+e2.velocityY);
			if (!box1.intersects(box2)) {
				e1.velocityX = 0;
				e2.velocityX = 0;
				return;
			}
			
			//see if stopping both Y velocity fixes collision
			box1.setLocation(baseX1+e1.velocityX, baseY1);
			box2.setLocation(baseX2+e2.velocityX, baseY2);
			if (!box1.intersects(box2)) {
				e1.velocityY = 0;
				e2.velocityY = 0;
				return;
			}
			
			//see if stopping all but e1 X velocity fixes collision
			box1.setLocation(baseX1+e1.velocityX, baseY1);
			box2.setLocation(baseX2, baseY2);
			if (!box1.intersects(box2)) {
				e1.velocityX = 0;
				e2.velocityX = 0;
				e2.velocityY = 0;
				return;
			}
			
			//see if stopping all but e2 X velocity fixes collision
			box1.setLocation(baseX1, baseY1);
			box2.setLocation(baseX2+e2.velocityX, baseY2);
			if (!box1.intersects(box2)) {
				e2.velocityX = 0;
				e1.velocityX = 0;
				e1.velocityY = 0;
				return;
			}
			
			//see if stopping all but e1 Y velocity fixes collision
			box1.setLocation(baseX1, baseY1+e1.velocityY);
			box2.setLocation(baseX2, baseY2);
			if (!box1.intersects(box2)) {
				e1.velocityX = 0;
				e2.velocityX = 0;
				e2.velocityY = 0;
				return;
			}
			
			//see if stopping all but e1 Y velocity fixes collision
			box1.setLocation(baseX1, baseY1);
			box2.setLocation(baseX2, baseY2+e2.velocityY);
			if (!box1.intersects(box2)) {
				e2.velocityX = 0;
				e1.velocityX = 0;
				e1.velocityY = 0;
				return;
			}
			
			//see if stopping all velocity fixes collision
			box1.setLocation(baseX1, baseY1);
			box2.setLocation(baseX2, baseY2);
			if (!box1.intersects(box2)) {
				e1.velocityY = 0;
				e2.velocityY = 0;
				e1.velocityX = 0;
				e2.velocityX = 0;
				return;
			} else {
			}
			
		}
	}

}
