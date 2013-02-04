package layoutapp.model;

/**
 * Represents the state of the application, which consists of archives for issues and for articles.
 */
public class LayoutApp {
	protected final ArticleArchive articleArchive;
	protected final IssueArchive issueArchive;
	protected static LayoutApp instance;//modification
	
	
	//modification new method
	public static LayoutApp getInstance() {
		if(instance==null)
			instance = new LayoutApp();
		return instance;
	}
	
	//modification to protected
	protected LayoutApp() {
		articleArchive = new ArticleArchive();
		issueArchive = new IssueArchive();
		//modification
		articleArchive.addArticle(new Article("one", "this is article one"));
		articleArchive.addArticle(new Article("two", "this is article two"));
		articleArchive.addArticle(new Article("three", "this is article three"));
		
		try {
			issueArchive.createNewIssue(1, 2000);
			issueArchive.createNewIssue(2, 2000);
			issueArchive.createNewIssue(3, 2000);
			issueArchive.createNewIssue(4, 2000);
		} catch (DuplicateIssueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArticleArchive getArticleArchive() {
		return articleArchive;
	}
	public IssueArchive getIssueArchive() {
		return issueArchive;
	}
}
