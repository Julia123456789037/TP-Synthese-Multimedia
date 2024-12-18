package org.multimedia.composants;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import org.multimedia.util.ImageUtils;
import org.multimedia.util.Text;

public class ImageTransform {
	
	private LinkedList<Operation> operations;
	
	private int currentIndex;
	
	public ImageTransform() {
		this.operations = new LinkedList<>();
		this.reset();
	}

	public void addRotation(int rotation) {
		this.addOperation(image -> ImageUtils.rotate(image, rotation));
	}
	
	public void invertH() {
		this.addOperation(image -> ImageUtils.invertHorizontal(image));
	}
	
	public void invertV() {
		this.addOperation(image -> ImageUtils.invertVertical(image));
	}

	public void fillColor( int x, int y, Color color ) {
		this.addOperation(image -> ImageUtils.fill(image, x, y, color));
	}

	public void applyBrightness( int brightness ) {
		this.addOperation(image -> ImageUtils.applyBrightness( image, brightness ));
	}    

	public void toGreyScale() { 
		this.addOperation(image -> ImageUtils.toGreyScale( image )); 
	}

	public void writeText(String text, int x, int y, int size, Color color) { 
		this.addOperation(image -> ImageUtils.writeText( image, new Text.Builder(text, x, y).size(size).color(color).build() )); 
	}
	
	public void undo() { 
		this.currentIndex = Math.max(-1, this.currentIndex - 1); 
	}
	public void redo() { 
		this.currentIndex = Math.min(this.operations.size() - 1, this.currentIndex + 1); 
	}
	
	private void addOperation(Operation o) {
		while (this.currentIndex + 1 != this.operations.size())
			this.operations.removeLast();
		this.operations.add(o);
		this.currentIndex++;
	}
	
	public BufferedImage applyTransforms(BufferedImage image) {
		BufferedImage res = ImageUtils.Builder.deepClone(image);
		for (int i = 0; i <= this.currentIndex; i++)
			res = this.operations.get(i).apply(res);
		return res;
	}
	
	public void reset() {
		this.operations.clear();
		this.currentIndex = -1;
	}
	
	public static interface Operation {
		public BufferedImage apply(BufferedImage image);
	}
	
}