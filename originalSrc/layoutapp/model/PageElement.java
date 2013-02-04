package layoutapp.model;

import java.awt.Graphics2D;

/**
 * Represents an element of the page. This is a bace class for the hierarchy of elements.
 *
 */
public abstract class PageElement {
	protected final Page page;
	
	public PageElement(Page page) {
		this.page = page;
	}
	public Page getPage() {
		return page;
	}
	protected void firePageElementWillChange() {
		page.firePageElementWillChange(this);
	}
	protected void firePageElementChanged() {
		page.firePageElementChanged(this);
	}
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void moveTo(int x, int y, int width, int height);
	public abstract void draw(Graphics2D g);
}
