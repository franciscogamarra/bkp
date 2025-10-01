package br;

import java.awt.Dimension;

public class Screen {
	public static final Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int width = toInt(dimension.getWidth());
	public static final int height = toInt(dimension.getHeight());
	public static final int center_horizontal = width / 2;
	public static final int center_vertical = height / 2;
	
	private static int toInt(Double d) {
		return d.intValue();
	}
	
}
