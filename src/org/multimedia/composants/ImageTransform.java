package org.multimedia.composants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.multimedia.util.ImageUtils;

public class ImageTransform {
	
	private List<Operation> operations;
	
	private int currentIndex;
	
	public ImageTransform() {
		this.operations = new ArrayList<>();
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