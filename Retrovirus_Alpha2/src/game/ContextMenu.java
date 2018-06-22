package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ContextMenu {
	ArrayList<String> list = new ArrayList<String>();
	ArrayList<Rectangle> listBox = new ArrayList<Rectangle>();
	int x, y;
	int width = 0, height;
	boolean visible = false;
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ContextMenu(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public ContextMenu() {}
	
	public void draw(Graphics g) {
		height = (11*list.size())+1;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).length()*5.2 > width) {
				width = (int) (list.get(i).length()*5.2) + 2;
			}
			listBox.get(i).setBounds(x+3, y+11*i+26, width, height);
		}
		
		if(visible) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);

			for(int i = 0; i < list.size(); i++) {
				g.drawLine(x, y+i*11, x+width, y+i*11);
				g.drawString(list.get(i), x+2, y+10);
			}
		}
	}
	
	public void addContextOption(String e) {
		list.add(e);
		listBox.add(new Rectangle());
	}
	
	public void setVisible(boolean b) {
		visible = b;
		//System.out.println(listBox.get(0).getX()+" "+listBox.get(0).getY()+" "+listBox.get(0).getWidth()+" "+listBox.get(0).getHeight());
	}
	
	public int contextClicked(int x, int y) {
		for(int i = 0; i < listBox.size(); i++) {
			if(listBox.get(i).contains(x, y)) {
				return i;
			}
		}
		return -1;
	}
}
