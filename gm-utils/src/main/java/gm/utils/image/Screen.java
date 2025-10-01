package gm.utils.image;

import java.awt.Dimension;

import src.commom.utils.integer.IntegerParse;

public class Screen {
	public static final Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int width = IntegerParse.toInt(dimension.getWidth());
	public static final int height = IntegerParse.toInt(dimension.getHeight());
	public static final int center_horizontal = width / 2;
	public static final int center_vertical = height / 2;
	
}
