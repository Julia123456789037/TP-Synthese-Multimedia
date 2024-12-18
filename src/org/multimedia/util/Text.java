package org.multimedia.util;

import java.awt.Color;
import java.awt.Font;

public class Text {
	
	private String text;
	
	private String font;
	
	private boolean isBold;
	
	private boolean isItalic;
	
	private Color color;
	
	private int size;
	
	private int x;
	private int y;
	
	public Text(String text, int x, int y) {
		this(text, x, y, null);
	}
	
	public Text(String text, int x, int y, String font) {
		this(text, x, y, font, Color.BLACK);
	}
	
	public Text(String text, int x, int y, String font, Color color) {
		this(text, x, y, font, color, 12);
	}
	
	public Text(String text, int x, int y, String font, Color color, int size) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font;
		this.color = color;
		this.size = size;
	}
	
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
	
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Font getFont() {
		int style = Font.PLAIN;
		if (this.isBold)
			style |= Font.BOLD;
		if (this.isItalic)
			style |= Font.ITALIC;
		return new Font(this.font, style, this.size);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y + this.size;
	}
	
	@Override
	public String toString() {
		String format = "%s[text=\"%s\",x=%d,y=%d,font=%s,bold=%b,italic=%b,color=%s]";
		return String.format(format,
				this.getClass().getName(),
				this.text,
				this.x(),
				this.y(),
				this.getFont(),
				this.isBold,
				this.isItalic,
				this.color);
	}
	
	public static class Builder {
		
		private Text instance;
		
		public Builder(String text, int x, int y) {
			this.instance = new Text(text, x, y);
		}
		
		public Builder font(String font) {
			if (font == null)
				font = "Arial";
			this.instance.font = font;
			return this;
		}
		
		public Builder isBold() {
			this.instance.isBold = true;
			return this;
		}
		
		public Builder isItalic() {
			this.instance.isItalic = true;
			return this;
		}
		
		public Builder color(Color color) {
			if (color == null)
				color = Color.BLACK;
			this.instance.color = color;
			return this;
		}
		
		public Builder size(int size) {
			if (size < 1)
				size = 12;
			this.instance.size = size;
			return this;
		}
		
		public Text build() {
			return this.instance;
		}
		
	}
	
}