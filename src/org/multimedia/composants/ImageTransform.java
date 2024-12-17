package org.multimedia.composants;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import org.multimedia.util.ImageUtils;

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
	
	public void fillColor(int x, int y, Color color) {
		this.addOperation(image -> ImageUtils.fill(image, x, y, color));
	}
	
	private void addOperation(Operation o) {
		while (this.currentIndex + 1 != this.operations.size())
			this.operations.removeLast();
		this.operations.add(o);
		this.currentIndex++;
	}
	
	public void undo() {
		this.currentIndex = Math.max(-1, this.currentIndex - 1);
	}
	
	public void redo() {
		this.currentIndex = Math.min(this.operations.size() - 1, this.currentIndex + 1);
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