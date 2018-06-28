package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import items.ItemData;

public class InvContextMenu {
	int y, id;
	boolean visible = false;
	BufferedImage img;
	String name;
	int type;
	String[] desc = new String[4];
	ArrayList<Rectangle> contextButtons = new ArrayList<Rectangle>();
	ArrayList<String> contextText = new ArrayList<String>();
	
	public InvContextMenu(int y, int id, ItemData i) {
		this.y = y;
		this.id = id;
		name = i.name(id);
		img = i.getItemImage(id);
		type = i.type(id);
		breakText(i.description(id));
		configureButtons();
	}
	
	void draw(Graphics g) {
		if(visible) {
			g.setColor(Color.GRAY);
			g.fillRect(575, 115+y*12, 200, 200);
			g.setColor(Color.BLACK);
			g.drawRect(575, 115+y*12, 200, 200);
			g.drawImage(img, 576, 116+y*12, 50, 50, null);
			g.drawRect(575, 115+y*12, 51, 51);
			
			g.drawString(desc[0], 627, 127+y*12);
			g.drawString(desc[1], 627, 139+y*12);
			g.drawString(desc[2], 627, 151+y*12);
			g.drawString(desc[3], 627, 163+y*12);
			
			for(int i = 0; i < contextButtons.size(); i++) {
				Rectangle rect = contextButtons.get(i);
				g.drawRect(rect.x,rect.y,rect.width,rect.height);
				g.drawString(contextText.get(i), rect.x+1, rect.y+10);
			}
			
			g.setFont(Game.classic_bold);
			g.drawString(name, 576, 175+y*12);
			
			g.setFont(Game.classic);
		}
	}
	
	void setVisible(boolean b) {
		visible = b;
	}
	
	void breakText(String x) {
		if(x.length() < 104) {
			while(x.length() < 104) {
				x+=" ";
			}
		}
		try {
			desc[0] = x.substring(0,26);
			desc[1] = x.substring(26,52);
			desc[2] = x.substring(52,78);
			desc[3] = x.substring(78,104);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void configureButtons() {
		addButtons(0-y, "Drop");
		if(type == 1) {
			addButtons(1-y, "Use");
		}
	}
	void addButtons(int y, String e) {
		contextButtons.add(new Rectangle(575,303-y*12,100,12));
		contextText.add(e);
	}
	
	int click(int x, int y) {
		for(int i = 0; i < contextButtons.size(); i++) {
			if(contextButtons.get(i).contains(x-3, y-26)) {
				return i;
			}
		}
		return -1;
	}
}
