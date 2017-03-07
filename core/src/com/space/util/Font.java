package com.space.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Font.
 */
public class Font {

	private Font() {
	}

	/**
	 * Generate a bitmap font from a TTF file
	 * 
	 * @param fontFileName
	 * @param size
	 * @return a new BitmapFont of the specified size
	 */
	public static BitmapFont generateFont(String fontFileName, int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFileName));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		parameter.size = size;

		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	/**
	 * Generate a bitmap font from a TTF file
	 * 
	 * @param fontFileName
	 * @param size
	 * @param color
	 * @return a new BitmapFont of the specified size and color
	 */
	public static BitmapFont generateFont(String fontFileName, int size, Color color) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFileName));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		parameter.size = size;
		parameter.color = color;

		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

}
