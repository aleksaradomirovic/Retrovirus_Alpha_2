package game;

import java.awt.Color;
import java.awt.Graphics;

public class InvContextMenu {
	int y, id;
	boolean visible = false;
	public InvContextMenu(int y, int id) {
		this.y = y;
		this.id = id;
	}
	
	void draw(Graphics g) {
		if(visible) {
			g.setColor(Color.GRAY);
			g.fillRect(575, 115+y*12, 200, 200);
			g.setColor(Color.BLACK);
			g.drawRect(575, 115+y*12, 200, 200);
		}
	}
	
	void setVisible(boolean b) {
		visible = b;
	}
}
