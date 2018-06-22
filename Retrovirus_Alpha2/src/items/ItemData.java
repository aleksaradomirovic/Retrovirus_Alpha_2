package items;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ItemData {
	String installLocation;
	String file;
	String[] data = new String[65536];
	String line = null;
	int read = 0;
	BufferedImage noTexture;
	public int itemAssets;
	
	
	public ItemData(String installLocation) {
		this.installLocation = installLocation;
		file = installLocation+"/ItemBundle.txt";
		try {
			noTexture = ImageIO.read(new File(installLocation+"/Images/Default.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("DEFAULT TEXTURE NOT FOUND");
		}
		reloadItemData();
	}
	
	public void reloadItemData() {
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			while((line = bufferedReader.readLine()) != null) {
				data[read] = line;
				
				read++;
			}
			bufferedReader.close();
			for(int i = 0; i < data.length; i++) {
				if(data[i] != null)
					System.out.println(data[i]);
			}
			System.out.println(name(0));
			System.out.println(type(0));
			itemAssets = (read)/10 - 2;
			System.out.println(itemAssets+" item assets");
		} catch (FileNotFoundException e) {
			System.out.println("File '"+file+"' not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String name(int id) {
		return data[(id+1)*10];
	}
	public int type(int id) {
		return Integer.parseInt(data[(id+1)*10+1]);
	}
	
	public BufferedImage getItemImage(int id) {
		try {
			return ImageIO.read(new File(installLocation+"/Images/"+data[(id+1)*10+2]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return noTexture;
		}
	}
}
