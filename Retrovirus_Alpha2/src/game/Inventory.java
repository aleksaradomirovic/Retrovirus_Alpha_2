package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import items.ItemData;

public class Inventory {
	int[] inv;
	int tab = 0;
	ItemData data;
	InvContextMenu context;
	Player parent;
	BufferedImage food, water, immune, hp;
	
	public static final int inventory = 0, radio = 1, stats = 2;
	
	public Inventory(ItemData i, Player p) {
		data = i;
		parent = p;
		inv = new int[i.itemAssets];
		
		try {
			food = ImageIO.read(new File("C:/Users/User/Desktop/Retrovirus_Data/Images/FoodIcon.png"));
			water = ImageIO.read(new File("C:/Users/User/Desktop/Retrovirus_Data/Images/WaterIcon.png"));
			hp = ImageIO.read(new File("C:/Users/User/Desktop/Retrovirus_Data/Images/HPIcon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add(int id, int amount) {
		inv[id]+=amount;
	}
	
	void draw(Graphics g) {
		g.setColor(new Color(0,0,0,100));
		g.fillRoundRect(450,50,300,450, 25, 25);
		g.setColor(Color.BLACK);
		g.drawString("<Q", 455, 85);
		g.drawString("E>", 731, 85);
		
		if(tab == inventory) g.setColor(Color.RED);
		g.drawString("Inventory", 477, 85);
		g.setColor(Color.BLACK);
		g.drawRect(475, 75, 50, 12);
		if(tab == stats) g.setColor(Color.RED);
		g.drawString("Status", 527, 85);
		g.setColor(Color.BLACK);
		g.drawRect(525, 75, 50, 12);
		if(tab == radio) g.setColor(Color.RED);
		g.drawString("Radio", 577, 85);
		g.setColor(Color.BLACK);
		g.drawRect(575, 75, 50, 12);
		if(tab == inventory) {
			drawInv(g);
		} else if(tab == stats) {
			drawStats(g);
		}
		if(context != null) {
			context.draw(g);
		}
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
	
	void drawStats(Graphics g) {
		g.drawString(getTime(), 477, 125);
		g.setColor(Color.RED);
		g.fillRect(475, 130, (int)(parent.hp/25), 15);
		g.drawImage(hp, 476, 133, 8, 8, null);
		g.setColor(Color.GREEN);
		g.fillRect(475, 145, (int)(parent.immune/25), 15);
		g.setColor(Color.ORANGE);
		g.fillRect(475, 160, (int)(parent.food/100), 15);
		g.drawImage(food, 476, 163, 8, 8, null);
		g.setColor(Color.BLUE);
		g.fillRect(475, 175, (int)(parent.water/25), 15);
		g.drawImage(water, 476, 177, 9, 10, null);
	}
	
	String getTime() {
		String r;
		int hour;
		if((int)(minutesAfterMidnight/60) < 12)
			hour = (int)(minutesAfterMidnight/60);
		else
			hour = (int)(minutesAfterMidnight/60)-12;
		
		if(hour == 0)
			hour = 12;
		
		if((int)(minutesAfterMidnight/60) < 12) {
			if(minutesAfterMidnight%60 < 10) {
				r = hour+":0"+minutesAfterMidnight%60;
			} else {
				r = hour+":"+minutesAfterMidnight%60;
			}
			r+="am";
		} else {
			if(minutesAfterMidnight%60 < 10) {
				r = hour+":0"+minutesAfterMidnight%60;
			} else {
				r = hour+":"+minutesAfterMidnight%60;
			}
			r+="pm";
		}
		return r;
	}
	
	void click(int x, int y) {
		if(context == null) {
			int setback = 0;
			for(int i = 0; i < inv.length; i++) {
				if(inv[i] > 0) {
					if(new Rectangle(478,141+(i-setback)*12,100,12).contains(x, y)) {
						context = new InvContextMenu(i-setback, i, data);
						context.setVisible(true);
					}
				} else {
					setback++;
				}
			}
		} else {
			int r = context.click(x, y);
			if(r > -1) {
				int id = context.id;
				if(r == 0) {
					drop(id);
				} else if(r == 1) {
					if(data.type(id) == 1) {
						parent.food+=data.parseConsumableVars(id)[0];
						parent.water+=data.parseConsumableVars(id)[1];
						parent.immune+=data.parseConsumableVars(id)[2];
						parent.hp+=data.parseConsumableVars(id)[3];
						inv[id]--;
						if(inv[id] == 0) {
							context = null;
						}
					}
				}
			}
		}
		
	}
	
	int tickSecond = 0;
	int minutesAfterMidnight = 480;
	
	void tick() {
		if(tickSecond < 100) {
			tickSecond++;
		} else {
			tickSecond-=100;
			if(minutesAfterMidnight < 1440)
				minutesAfterMidnight++;
			else
				minutesAfterMidnight = 0;
			stats();
			System.out.println("Minute");
		}
	}
	
	void stats() {
		parent.food--;
		parent.water--;
	}
	
	//ITEM FUNCTIONS
	void drop(int id) {
		inv[id]--;
		parent.parent.globalItems.spawnItem(parent.x, parent.y, id);
		if(inv[id] == 0)
			context = null;
	}
}
