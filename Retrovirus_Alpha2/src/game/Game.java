package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import items.ItemData;

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener{
	
	ArrayList<ContextMenu> menus = new ArrayList<ContextMenu>();
	Timer general = new Timer(1000/120, this);
	public static final int serverSize = 8;
	public static Font classic = new Font("Arial", Font.PLAIN, 10);
	public Player[] serverPlayers = new Player[serverSize];
	public Player local;
	ItemData itemData = new ItemData("C:/Users/User/Desktop/Retrovirus_Data");
	ItemManager globalItems;
	long frameCount = 0, frameTime = System.currentTimeMillis();
	float frameRate;
	
	boolean pickup;
	boolean inv = false, context = false;
	
	//TODO when adding local multiplayer change this
	//singleplayer start
	
	JFrame frame = new JFrame();
	KeyBoard keys;
	void setup() {
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800,600));
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(true);
		
		//#####################################################
		
		itemData.reloadItemData();
		local = new Player(this, itemData);
		globalItems = new ItemManager(itemData, this);
		
		//start
		keys = new KeyBoard(this);
		frame.addKeyListener(keys);
		frame.addMouseListener(keys);
		general.start();
	}
	
	void update() {
		pickup = false;
		
		globalItems.update(local.x, local.y);
		local.update();
		runStatistics();
		context = false;
		for(int i = 0; i < menus.size(); i++) {
			if(menus.get(i).visible)
				context = true;
		}
	}
	void draw(Graphics g) {
		g.setFont(classic);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.BLACK);
		globalItems.draw(g);
		local.draw(g);
		g.setColor(Color.BLACK);
		if(keys.stats) {
			g.drawString(local.x+" "+local.y, 1, 11);
			g.drawString((int)frameRate+"fps", 1, 22);
		}
		keys.draw(g);
		for (int i = 0; i < menus.size(); i++) {
			menus.get(i).draw(g);
		}
	}
	
	public static void main(String[] args) {
		new Game().setup();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		update();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		draw(g);
	}
	
	void runStatistics() {
		if(System.currentTimeMillis() - frameTime < 1000) {
			frameCount++;
		} else {
			frameTime = System.currentTimeMillis();
			frameRate = frameCount;
			frameCount = 0;
		}
	}
	
	void setFrameCap(int cap) {
		general.setDelay(1000/cap);
	}
	
	void click(int x, int y) {
		if(!inv ) {
			globalItems.click(x, y);
		} else {
			local.inv.click(x, y);
		}
		System.out.println("Click "+x+" "+y);
	}
	
	public ContextMenu addContext() {
		ContextMenu r = new ContextMenu();
		menus.add(r);
		return r;
	}
	
	void closeAllContext() {
		for(int i = 0; i < menus.size(); i++) {
			menus.get(i).setVisible(false);
		}
	}
 }
