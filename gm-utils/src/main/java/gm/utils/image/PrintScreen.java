package gm.utils.image;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import gm.utils.files.GFile;
import gm.utils.robo.Robo;

public class PrintScreen {

	private static final Robot robot = Robo.robot;

	public static void main(String[] args) {
		exec();
	}
	
	public static void exec(GFile output, int x, int y, int with, int height) {
		try {
			output.delete();
			output.create();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(x, y, with, height));
//			ImageIO.write(bi, "jpg", output.toFile());
			ImageIO.write(bi, output.getExtensao(), output.toFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static GFile ULTIMO_PRINT = GFile.get("C:\\dev\\tmp\\ULTIMO_PRINT.bmp");
	
	public static void exec() {
		exec(ULTIMO_PRINT, 0, 0, Screen.width, Screen.height);
	}
	
	public static Bitmap get() {
		exec();
		return new Bitmap(PrintScreen.ULTIMO_PRINT);
	}

	
		/*
		robot.mouseMove(350, 380);
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		sleep();

		for (int i = 0; i < 1; i+=5) {

			robot.mousePress(mask);
			sleep();
			robot.mouseRelease(mask);
			key(KeyEvent.VK_DELETE);
			String s = StringParse.get(i);
			while (!s.isEmpty()) {
				int x = IntegerParse.toInt(s.substring(0,1));
				s = s.substring(1);
				keyNumber(x);
			}
			key(KeyEvent.VK_ENTER);
			print(i);
		}
		*/
/*
	private static void print(int i) {
		
		GFile file = GFile.get("/opt/tmp/pie" + IntegerFormat.zerosEsquerda(i, 3)+".jpg");
		
		if (!file.exists()) {
			file.create();
		}

		USystem.sleepSegundos(1);
		int w = 84;

		try {
			BufferedImage bi = robot.createScreenCapture(new Rectangle(500, 498, w, w));
			ImageIO.write(bi, "jpg", file.toFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static void sleep() {
		USystem.sleepMiliSegundos(75);
	}

	private static void keyNumber(int i) {
		key(KeyEvent.VK_0+i);
	}

	private static void key(int i) {
		sleep();
		robot.keyPress(i);
		sleep();
		robot.keyRelease(i);
		sleep();
	}
	/**/

}
