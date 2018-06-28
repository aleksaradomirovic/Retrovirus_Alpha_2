package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.ContextMenu;
import game.Inventory;

public class Item {
	public boolean global = true;
	String name, desc;
	int type;
	BufferedImage img;
	int x, y;
	int x_s, y_s;
	public Rectangle hitBox = new Rectangle();
	public ContextMenu context;
	Inventory local;
	int id;
	
	public Item(int id, ItemData i, int x, int y, ContextMenu m, Inventory p) {
		name = i.name(id);
		type = i.type(id);
		desc = i.description(id);
		this.id = id;
		System.out.println(name+", "+type+", "+desc);
		img = i.getItemImage(id);
		this.x = x;
		this.y = y;
		context = m;
		context.addContextOption("Pick up");
		local = p;
	}
	
	public void update(int px, int py) {
		x_s = x - px + 400;
		y_s = y - py + 300;
		hitBox.setBounds(x_s-img.getWidth(), y_s-img.getHeight(), img.getWidth()*3, img.getHeight()*3);
		context.setLoc(x_s, y_s);
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x_s, y_s, img.getWidth(), img.getHeight(), null);
	}
	
	public boolean click(int x, int y) {
		System.out.println("Item loc"+x_s+" "+y_s);
		if(hitBox.contains(x, y)) {
			System.out.println("Item clicked");
			context.setVisible(true);
			return true;
		}
		return false;
	}
	
	public void performAction(int action) {
		if(action == 0) {
			//pickup
			local.add(id, 1);
			global = false;
		}
	}
}