package layoutapp.model;

/**
 * Represents an article consisting of a name and the text. In a more realisitc application,
 * article's name and the text could be changed. In this application, however, for simplicity
 * we assume that, once an article is written, it does not change any further.
 */
public class Article {
	protected final String articleName;
	protected final String articleText;
	
	public Article(String articleName,String articleText) {
		this.articleName = articleName;
		this.articleText = articleText;
	}
	public String getArticleName() {
		return articleName;
	}
	public String getArticleText() {
		return articleText;
	}
}
