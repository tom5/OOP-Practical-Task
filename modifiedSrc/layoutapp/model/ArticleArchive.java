package layoutapp.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents an archive of articles. In a reaslitic application, the archive would be stored in a database.
 * For simplicity, however, we assume that all articles are loaded into main memory.
 */
public class ArticleArchive {
	protected final List<Article> articles;
	
	public ArticleArchive() {
		articles = new ArrayList<Article>();
	}
	public void addArticle(Article article) {
		articles.add(article);
	}
	public int getNumberOfArticles() {
		return articles.size();
	}
	public Iterator<Article> getArticles() {
		return articles.iterator();
	}
}
