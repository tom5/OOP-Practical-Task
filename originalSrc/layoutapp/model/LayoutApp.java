package layoutapp.model;

/**
 * Represents the state of the application, which consists of archives for issues and for articles.
 */
public class LayoutApp {
	protected final ArticleArchive articleArchive;
	protected final IssueArchive issueArchive;
	
	public LayoutApp() {
		articleArchive = new ArticleArchive();
		issueArchive = new IssueArchive();
	}
	public ArticleArchive getArticleArchive() {
		return articleArchive;
	}
	public IssueArchive getIssueArchive() {
		return issueArchive;
	}
}
