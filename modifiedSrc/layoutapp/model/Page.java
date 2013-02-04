package layoutapp.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics2D;

/**
 * Represents a page of a magazine issue. The page consists of a list of elements.
 * It implements the Observable pattern and thus can be used as part of the
 * Model-View-Controller pattern.  
 */
public class Page {
	protected final List<PageListener> pageListeners;
	protected final int pageIndex;
	protected final List<PageElement> pageElements;
	
	public Page(int pageIndex) {
		pageListeners = new ArrayList<PageListener>();
		this.pageIndex = pageIndex;
		pageElements = new ArrayList<PageElement>();
	}
	/**
	 * The width of a page is hard-coded for the time being.
	 */
	public int getPageWidth() {
		return 600;
	}
	/**
	 * The width of a page is hard-coded for the time being.
	 */
	public int getPageHeight() {
		return 800;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public Iterator<PageElement> pageElements() {
		return pageElements.iterator();
	}
	public void addPageElement(PageElement e) {
		assert e.getPage() == this;
		pageElements.add(e);
		firePageElementAdded(e);
	}
	/**
	 * Paints the page. Note that the page does not need to know about various types of elements.
	 * Instead, it uses virtual method calls to call the appropriate method for each element. 
	 */
	public void paint(Graphics2D g) {
		for (PageElement e : pageElements)
			e.draw(g);
	}
	public void addPageListener(PageListener l) {
		pageListeners.add(l);
	}
	public void removePageListener(PageListener l) {
		pageListeners.remove(l);
	}
	/**
	 * Fires the "element added" event -- that is, it notifies the registered listeners that
	 * the supplied element has been added to the page.
	 */
	protected void firePageElementAdded(PageElement e) {
		for (PageListener l : pageListeners)
			l.pageElementAdded(this, e);
	}
	/**
	 * Fires the "element will change" event -- that is, it notifies the registered listeners that
	 * the supplied element will change. This event is useful necessary, for example, to allow
	 * the view to invalidate the portion of the screen that is covered by an element whose
	 * position is about to be updated.
	 */
	protected void firePageElementWillChange(PageElement e) {
		for (PageListener l : pageListeners)
			l.pageElementWillChange(this, e);
	}
	/**
	 * Fires the "element has changed" event -- that is, it notifies the registered listeners that
	 * the supplied element has changes. The view will typically respond to this event by repainting
	 * the part of the screen covered by the element.  
	 */
	protected void firePageElementChanged(PageElement e) {
		for (PageListener l : pageListeners)
			l.pageElementChanged(this, e);
	}
}
