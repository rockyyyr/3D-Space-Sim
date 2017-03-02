package com.space.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Font.
 */
public class Font {

	public static BitmapFont generateFont(String fontFileName, int size) {
		BitmapFont font;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFileName));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

}
