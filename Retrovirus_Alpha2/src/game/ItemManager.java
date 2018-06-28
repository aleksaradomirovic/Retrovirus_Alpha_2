package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import items.Item;
import items.ItemData;

public class ItemManager {
	ItemData itemData;
	Game parent;
	ArrayList<Item> worldItems = new ArrayList<Item>();
	
	public ItemManager(ItemData i, Game p) {
		itemData = i;
		parent = p;
		
		init();
	}
	
	void init() {
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 0);
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 0);
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 0);
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 3);
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 2);
		spawnItem(new Random().nextInt(800)-400, new Random().nextInt(600)-300, 1);
	}
	
	Item spawnItem(int x, int y, int id) {
		Item r;
		r = new Item(id, itemData, x, y, parent.addContext(),parent.local.inv);
		worldItems.add(r);
		return r;
	}
	
	void update(int px, int py) {
		for(int i = 0; i < worldItems.size(); i++) {
			if(worldItems.get(i).global == true) {
				worldItems.get(i).update(px, py);
				if(worldItems.get(i).hitBox.intersects(parent.local.interactBox)) {
					parent.pickup = true;
				}
			} else {
				worldItems.remove(i);
				i--;
			}
		}
	}
	
	void draw(Graphics g) {
		for(int i = 0; i < worldItems.size(); i++) {
			worldItems.get(i).draw(g);
		}
	}
	
	void click(int x, int y) {
		for(int i = 0; i < worldItems.size(); i++) {
			if(!parent.context) {
				if(worldItems.get(i).hitBox.intersects(parent.local.interactBox)) {
					worldItems.get(i).click(x-5, y-27);
				}
			} else {
				int option;
				if((option = worldItems.get(i).context.contextClicked(x, y)) != -1) {
					worldItems.get(i).context.setVisible(false);
					worldItems.get(i).performAction(option);
				}
			}
		}
	}
}
