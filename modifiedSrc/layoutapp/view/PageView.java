package layoutapp.view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import layoutapp.model.ArticleTextElement;
import layoutapp.model.Issue;
import layoutapp.model.Page;
import layoutapp.model.PageElement;
import layoutapp.model.PageListener;
import layoutapp.model.LayoutApp;

/**
 * Displays one page and its elements. It implements the View and the Controller parts of the
 * Model-View-Controller pattern. 
 */
public class PageView extends JPanel {
	/** A stroke (i.e., a type of line) used to draw dashed lines. */
	protected static final Stroke DASHED_STROKE = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[] { 2f, 2f }, 0f);
	/** This is the thickness of the shadow behind the page. */
	protected static final int SHADOW_WIDTH = 3;
	
	protected final PageListener pageListener;
	protected final MouseHandler mouseHandler;
	
	protected LayoutApp layoutApp;//modification
	protected Issue issue;
	protected Page currentPage;

	public PageView(Issue issue, LayoutApp layoutApp) {
		this.layoutApp = layoutApp;
		mouseHandler = new MouseHandler();
		pageListener = new PageViewPageListener();
		this.issue = issue;
		if(issue!=null)
			setCurrentPage(0);
	}
	
	public void setCurrentIssue(Issue issue) {
		this.issue = issue;
		//modification
		if(issue !=null)
			setCurrentPage(0);
		repaint();
	}
	
	public void setCurrentPage(int pageIndex) {
		// IMPORTANT: Omitting the following line would lead to memory leaks!
		// Without it, whenever a particular page would be displayed, the
		// listener would be registered afresh.
		if (currentPage != null)
			currentPage.removePageListener(pageListener);
		if (0 <= pageIndex && pageIndex < issue.getNumberOfPages())
			currentPage = issue.getPage(pageIndex);
		else
			currentPage = null;
		if (currentPage != null) {
			currentPage.addPageListener(pageListener);
			setPreferredSize(new Dimension(currentPage.getPageWidth() + SHADOW_WIDTH, currentPage.getPageHeight() + SHADOW_WIDTH));
		}
		else
			setPreferredSize(new Dimension(100,100));
		repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g; 
		// The state of the Graphics object should stay the same after the painting is finished.
		Color oldColor = g2d.getColor();
		Shape oldClip = g2d.getClip();

		// paints the background
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		// paint the page
		if (currentPage != null) {
			// paints the shadow behind the page
			g2d.setColor(Color.BLACK);
			g2d.fillRect(SHADOW_WIDTH, SHADOW_WIDTH, currentPage.getPageWidth(), currentPage.getPageHeight());
			// paints the page background
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, currentPage.getPageWidth(), currentPage.getPageHeight());
			// paints the page frame
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, currentPage.getPageWidth(), currentPage.getPageHeight());
		
			// A clip rectangle is like a mask specifying which part of the canvas can be changed.
			// The following call ensures that no subsequent drawing call can draw outside the
			// area taken by the page.
			g2d.clipRect(1, 1, currentPage.getPageWidth() - 2, currentPage.getPageHeight() - 2);
		
			// Now paint the actual page.
			currentPage.paint(g2d);
		}
		else {
			g2d.setColor(Color.BLACK);
			if(issue == null)
				g.drawString("There is currently no selected issue.", 50, 50);
			else if (issue.getNumberOfPages() == 0)
				g.drawString("The selected issue contains no pages.", 50, 50);
			else
				g.drawString("Please select a page in range between 1 and "+issue.getNumberOfPages()+".", 50, 50);
		}
		
		// paints the dragged box, if necessary
		mouseHandler.paint(g2d);

		// restore the original state of the Graphics object
		g2d.setColor(oldColor);
		g2d.setClip(oldClip);
	}

	protected class PageViewPageListener implements PageListener {

		public void pageElementAdded(Page page, PageElement pageElement) {
			repaint(pageElement.getX(), pageElement.getY(), pageElement.getWidth(), pageElement.getHeight());
		}
		public void pageElementChanged(Page page, PageElement pageElement) {
			repaint(pageElement.getX(), pageElement.getY(), pageElement.getWidth(), pageElement.getHeight());
		}
		public void pageElementWillChange(Page page, PageElement pageElement) {
			repaint(pageElement.getX(), pageElement.getY(), pageElement.getWidth(), pageElement.getHeight());
		}
	}
	
	protected class MouseHandler implements MouseListener, MouseMotionListener {
		protected Point stretchBoxOrigin;
		protected Point stretchBoxTarget;
		
		public MouseHandler() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		public void mouseEntered(MouseEvent e) {
			if (currentPage != null && stretchBoxOrigin != null)
				repaint();
		}
		public void mouseExited(MouseEvent e) {
			if (currentPage != null && stretchBoxOrigin != null)
				repaint();
		}
		public void mouseClicked(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
			if (currentPage != null && e.getButton() == MouseEvent.BUTTON1 && stretchBoxOrigin == null) {
				stretchBoxOrigin = e.getPoint();
				stretchBoxTarget = stretchBoxOrigin;
				repaint();
			}
		}
		public void mouseReleased(MouseEvent e) {
			if (currentPage != null && e.getButton() == MouseEvent.BUTTON1 && stretchBoxOrigin != null) {
				stretchBoxTarget = e.getPoint();
				repaint();
				boxStretchingFinished(stretchBoxOrigin,stretchBoxTarget);
				stretchBoxOrigin = null;
				stretchBoxTarget = null;
				repaint();
			}
		}
		public void mouseDragged(MouseEvent e) {
			if (currentPage != null && stretchBoxOrigin != null) {
				stretchBoxTarget = e.getPoint();
				repaint();
			}
		}
		public void mouseMoved(MouseEvent e) {
		}
		public void paint(Graphics2D g) {
			if (currentPage != null && stretchBoxOrigin != null) {
				Color oldColor = g.getColor();
				g.setColor(Color.GRAY);
				Stroke oldStroke = g.getStroke();
				g.setStroke(DASHED_STROKE);
				
				g.drawLine(stretchBoxOrigin.x, stretchBoxOrigin.y, stretchBoxTarget.x, stretchBoxOrigin.y);
				g.drawLine(stretchBoxTarget.x, stretchBoxOrigin.y, stretchBoxTarget.x, stretchBoxTarget.y);
				g.drawLine(stretchBoxOrigin.x, stretchBoxOrigin.y, stretchBoxOrigin.x, stretchBoxTarget.y);
				g.drawLine(stretchBoxOrigin.x, stretchBoxTarget.y, stretchBoxTarget.x, stretchBoxTarget.y);
				
				g.setStroke(oldStroke);
				g.setColor(oldColor);
			}
		}
		protected void boxStretchingFinished(Point boxOrigin,Point boxTarget) {
			int x = Math.min(boxOrigin.x, boxTarget.x);
			int y = Math.min(boxOrigin.y, boxTarget.y);
			int width = Math.abs(boxOrigin.x - boxTarget.x);
			int height = Math.abs(boxOrigin.y - boxTarget.y);
			ArticleTextElement e = new ArticleTextElement(currentPage, x, y, width, height);
			currentPage.addPageElement(e);
		}
	}

	public Issue getIssue() {
		return issue;
	}
}
