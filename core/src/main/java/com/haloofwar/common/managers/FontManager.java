package com.haloofwar.common.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.haloofwar.engine.utils.GraphicsUtils;

public class FontManager {
    private BitmapFont mediumFont;
    private BitmapFont titleFont;
    private BitmapFont smallFont;

    public FontManager() {
        this.loadFonts();
    }

    private BitmapFont generateFont(final FreeTypeFontGenerator generator, final int size, final Color color) {
        final FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = size;
        param.color = color;
        param.magFilter = TextureFilter.Linear;
        param.minFilter = TextureFilter.Linear;
        return generator.generateFont(param);
    }

    private void loadFonts() {
        final float SCALE_FACTOR = Gdx.graphics.getHeight() / 720f;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(GraphicsUtils.MAIN_FONT_PATH));

        this.mediumFont = generateFont(generator, (int)(24 * SCALE_FACTOR), Color.WHITE);
        this.titleFont = generateFont(generator, (int)(48 * SCALE_FACTOR), Color.WHITE);
        this.smallFont = generateFont(generator, (int)(16 * SCALE_FACTOR), Color.WHITE);

        generator.dispose();
    }

    public BitmapFont getMediumFont() {
        return this.mediumFont;
    }

    public BitmapFont getTitleFont() {
        return this.titleFont;
    }

    public BitmapFont getSmallFont() {
        return this.smallFont;
    }

    public void dispose() {
        if (this.mediumFont != null) {
        	this.mediumFont.dispose();
        	this.mediumFont = null;
        }
        if (this.titleFont != null) {
        	this.titleFont.dispose();
        	this.titleFont = null;
        }
        if (this.smallFont != null) {
        	this.smallFont.dispose();
        	this.smallFont = null;
        }
    }
}
