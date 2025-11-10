package com.haloofwar.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {
    public String message;
    private final BitmapFont font;
    private final GlyphLayout layout;
    public Color color;

    private float x;
    private float y;

    public Text(String text, BitmapFont font) {
        this.message = text;
        this.font = font;
        this.layout = new GlyphLayout();
        this.color = Color.WHITE; 
        this.x = 0;
        this.y = 0;
    }

    public void draw(SpriteBatch batch) {
        this.draw(batch, this.x, this.y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        final Color oldColor = font.getColor();
        this.font.setColor(this.color);
        this.font.draw(batch, this.message, x, y);
        this.font.setColor(oldColor); 
    }

    public float getWidth() {
        layout.setText(font, message);
        return layout.width;
    }
    
    public float getHeight() {
    	layout.setText(font, message);
    	return layout.height;
    }

    public void setScale(float scale) {
        font.getData().setScale(scale);
    }

    public void resetScale() {
        font.getData().setScale(1f);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public GlyphLayout getLayout() {
		return layout;
	}
    
    public BitmapFont getFont() {
		return font;
	}
    
    public float getX() { return x; }
    public float getY() { return y; }
}
