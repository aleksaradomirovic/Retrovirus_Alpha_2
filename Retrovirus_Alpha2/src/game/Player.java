package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import items.ItemData;

public class Player {
	int x = 0, y = 0;
	int speed = 5;
	Game parent;
	Rectangle interactBox = new Rectangle();
	Inventory inv;
	
	public Player(Game p, ItemData i) {
		parent = p;
		inv = new Inventory(i);
	}
	
	void update() {
		if(parent.keys.up) {
			y-=speed;
		} if(parent.keys.down) {
			y+=speed;
		} if(parent.keys.left) {
			x-=speed;
		} if(parent.keys.right) {
			x+=speed;
		}
		//System.out.println("XY: "+x+ " "+y);
		interactBox.setBounds(250, 150, 300, 300);
	}
	void draw(Graphics g) {
		g.drawRect(390, 275, 20, 50);
		
		if(parent.pickup) {
			g.drawString("Click to interact", 360, 340);
		}
		
		if(parent.keys.gui) {
			g.setColor(Color.RED);
			g.drawRect(250,150,300,300);
			g.drawString("Interact box", 251, 149);
		}
		
		if(parent.inv) {
			inv.draw(g);
		}
	}
}
