package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import items.ItemData;

public class Inventory {
	int[] inv;
	int tab = 0;
	ItemData data;
	InvContextMenu context;
	
	public Inventory(ItemData i) {
		data = i;
		inv = new int[i.itemAssets];
	}
	
	public void add(int id, int amount) {
		inv[id]+=amount;
	}
	
	void draw(Graphics g) {
		g.setColor(new Color(0,0,0,100));
		g.fillRoundRect(450,50,300,450, 25, 25);
		g.setColor(Color.BLACK);
		if(tab == 0) g.setColor(Color.RED);
		g.drawString("Inventory", 477, 85);
		g.setColor(Color.BLACK);
		g.drawRect(475, 75, 50, 12);
		if(tab == 1) g.setColor(Color.RED);
		g.drawString("Status", 527, 85);
		g.setColor(Color.BLACK);
		g.drawRect(525, 75, 50, 12);
		if(tab == 2) g.setColor(Color.RED);
		g.drawString("Radio", 577, 85);
		g.setColor(Color.BLACK);
		g.drawRect(575, 75, 50, 12);
		drawInv(g);
	}
	
	void drawInv(Graphics g) {
		int setback = 0;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] > 0) {
				g.drawString(data.name(i)+" ["+inv[i]+"]", 477, 125+i*12-setback*12);
				g.drawRect(475, 115+(i-setback)*12, 100, 12);
			} else {
				setback++;
			}
		}
	}
	
	void click(int x, int y) {
		int setback = 0;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] > 0) {
				if(new Rectangle(475,115+(i-setback)*12,100,12).contains(x, y)) {
					context = new InvContextMenu(i-setback, i);
				}
			} else {
				setback++;
			}
		}
	}
}
