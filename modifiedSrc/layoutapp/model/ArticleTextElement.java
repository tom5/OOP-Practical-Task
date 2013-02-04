package layoutapp.model;

import java.awt.Graphics2D;

/**
 * A page element that represents the text of an article. It contains a reference to the article
 * and the coordinates on the page.
 */
public class ArticleTextElement extends PageElement {
	protected Article article; 
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public ArticleTextElement(Page page, int x, int y, int width, int height) {
		super(page);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void moveTo(int x, int y, int width, int height) {
		firePageElementWillChange();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		firePageElementChanged();
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		firePageElementWillChange();
		this.article = article;
		firePageElementChanged();
	}
	public void draw(Graphics2D g) {
		g.drawRect(x, y, width - 1, height - 1);
	}
}
