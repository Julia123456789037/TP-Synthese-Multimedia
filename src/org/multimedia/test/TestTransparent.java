package org.multimedia.test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import javax.swing.JColorChooser;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class TestTransparent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static void removeTransparencySlider(JColorChooser jc) throws Exception {

	    AbstractColorChooserPanel[] colorPanels = jc.getChooserPanels();
	    for (int i = 1; i < colorPanels.length; i++) {
	        AbstractColorChooserPanel cp = colorPanels[i];

	        Field f = cp.getClass().getDeclaredField("panel");
	        f.setAccessible(true);

	        Object colorPanel = f.get(cp);
	        Field f2 = colorPanel.getClass().getDeclaredField("spinners");
	        f2.setAccessible(true);
	        Object spinners = f2.get(colorPanel);

	        Object transpSlispinner = Array.get(spinners, 3);
	        if (i == colorPanels.length - 1) {
	            transpSlispinner = Array.get(spinners, 4);
	        }
	        Field f3 = transpSlispinner.getClass().getDeclaredField("slider");
	        f3.setAccessible(true);
	        JSlider slider = (JSlider) f3.get(transpSlispinner);
	        slider.setEnabled(false);
	        Field f4 = transpSlispinner.getClass().getDeclaredField("spinner");
	        f4.setAccessible(true);
	        JSpinner spinner = (JSpinner) f4.get(transpSlispinner);
	        spinner.setEnabled(false);
	    }
	}
	
}