package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class KeyBoard implements KeyListener, MouseListener {
	Game parent;
	ArrayList<String> chat = new ArrayList<String>();
	public KeyBoard(Game p) {
		parent = p;
		chat.add("");
		chat.add("");
		chat.add("");
		chat.add("");
		chat.add("");
		lastMSCheck = System.currentTimeMillis();
	}
	
	/* Keyboard bind inputs
	 * up, down, left right
	 */
	public static final int movement = 0;
	boolean up, down, left, right;
	boolean console = false;
	String text = "";
	long lastMSCheck;
	
	//cheats
	boolean gui = false;
	boolean stats = true;
	
	int[] keybinds = new int[] 
			{KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D} /* movement binds */;
	void bindControls(int id, int vk) {
		keybinds[id] = vk;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!console && !parent.context && !parent.inv) {
			System.out.println("Pressed"+e.getKeyChar());

			if(e.getKeyChar() == 'w') {
				up = true;
			} if(e.getKeyChar() == 's') {
				down = true;
			} if(e.getKeyChar() == 'a') {
				left = true;
			} if(e.getKeyChar() == 'd') {
				right = true;
			}
		}
		if(console) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK || e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_WINDOWS || e.getKeyChar() == '`') {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(text.length() > 0) {
						enterCommand(text);
					} else {
						System.err.println("Invalid input: Command is null");
					}
				}
			} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				try {
					text = text.substring(0, text.length()-1);
				} catch (Exception e1) {
					System.err.println("Array size is 0");
					//e1.printStackTrace();
				}
			} else {
				text+=e.getKeyChar();
			}
		}
		
		//universal
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(parent.local.inv.context != null) {
				parent.local.inv.context = null;
			}
			else {
				parent.inv = false;
				parent.closeAllContext();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!console && !parent.context) {
			if(e.getKeyChar() == 'w') {
				up = false;
			} if(e.getKeyChar() == 's') {
				down = false;
			} if(e.getKeyChar() == 'a') {
				left = false;
			} if(e.getKeyChar() == 'd') {
				right = false;
			} if(e.getKeyChar() == 'g') {
				if(parent.inv) {
					parent.inv = false;
					parent.local.inv.context = null;
				} else
					parent.inv = true;
				System.out.println(parent.inv);
			}
			if(parent.inv) {
				if(e.getKeyChar() == 'q') {
					if(parent.local.inv.tab < 2)
						parent.local.inv.tab++;
					else
						parent.local.inv.tab = 0;
				} if(e.getKeyChar() == 'e') {
					if(parent.local.inv.tab > 0)
						parent.local.inv.tab--;
					else
						parent.local.inv.tab = 2;
				}
			}
		}
		
		if(e.getKeyChar() == '`') {
			if(console) {
				console = false;
				System.out.println("Consolefalse");
			} else {
				console = true;
				System.out.println("Consoletrue");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	void draw(Graphics g) {
		if(console) {
			g.setColor(new Color(0,0,0,125));
			g.fillRect(0, 485, 800, 20);
			g.setColor(Color.WHITE);
			if(System.currentTimeMillis() - lastMSCheck < 500) {
				g.drawString(text, 1, 500);
			} else if(System.currentTimeMillis() - lastMSCheck < 1000) {
				g.drawString(text+"_", 1, 500);
			} else {
				lastMSCheck = System.currentTimeMillis();
			}
			
			g.setColor(Color.BLACK);
			for(int i = chat.size()-1, j = 0; i > -1; i--, j++) {
				g.drawString(chat.get(i), 1, 480 - (12*j));
			}
		}
	}
	
	void enterCommand(String command) {
		addPlayerMessage(command);
		text = "";
		
		if(command.equals("gui")) {
			if(gui)
				gui = false;
			else 
				gui = true;
			
			writeLine("Toggled GUI");
		}
		
		if(command.equals("stats") || command.equals("statistics")) {
			if(stats)
				stats = false;
			else 
				stats = true;
			
			writeLine("Toggled performance & statistics");
		}
		
		if(command.length() > 7 && command.substring(0, 8).equals("framecap")) {
			if(command.length() < 10) {
				writeLine(1000/parent.general.getDelay()+"fps");
			} else {
				try {
					parent.setFrameCap(Integer.parseInt(command.substring(9,command.length())));
					writeLine("Updated frame cap");
				} catch (NumberFormatException e) {
					System.err.println("Bad number input");
				}
			}
		}
		if(command.length() > 6 && command.substring(0, 7).equals("settime")) {
			try {
				parent.local.inv.minutesAfterMidnight = Integer.parseInt(command.substring(8,command.length()));
				writeLine("Updated time");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.err.println("Bad number input");
			}
		}
	}
	
	public void writeLine(String msg) {
		chat.add("[@]: "+msg);
	}
	
	void addPlayerMessage(String msg) {
		chat.add("[PLAYER_LOCAL]: "+msg);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		parent.click(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
