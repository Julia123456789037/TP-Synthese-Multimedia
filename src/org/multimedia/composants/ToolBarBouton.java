package org.multimedia.composants;

import java.awt.Color;
import java.io.Serial;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolTip;

public class ToolBarBouton extends JButton {
	
	@Serial
	private static final long serialVersionUID = -6378005367674150091L;
	
	public ToolBarBouton() {
        super(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public ToolBarBouton(Icon icon) {
        super(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public ToolBarBouton(String text) {
        super(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public ToolBarBouton(Action a) {
        super(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public ToolBarBouton(String text, Icon icon) {
      super(text, icon);
    }
	
	@Override
	public JToolTip createToolTip() {
		JToolTip tip = super.createToolTip();
		tip.setForeground(Color.BLACK);
		tip.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return tip;
	}
	
}