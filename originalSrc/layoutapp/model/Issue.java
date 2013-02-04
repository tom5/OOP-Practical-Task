package layoutapp.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an issue of a magazine. Each issue is uniquely identified by the month and the year.
 * An issue consists of a number of pages.
 */
public class Issue {
	protected final int issueMonth;
	protected final int issueYear;
	protected final List<Page> pages;
	
	public Issue(int issueMonth, int issueYear) {
		this.issueMonth = issueMonth;
		this.issueYear = issueYear;
		pages = new ArrayList<Page>();
	}
	public int getIssueMonth() {
		return issueMonth;
	}
	public int getIssueYear() {
		return issueYear;
	}
	public int getNumberOfPages() {
		return pages.size();
	}
	public Iterator<Page> ragesIterator() {
		return pages.iterator();
	}
	public Page getPage(int pageIndex) {
		return pages.get(pageIndex);
	}
	public Page createNewPage() {
		Page newPage = new Page(pages.size());
		pages.add(newPage);
		return newPage;
	}
}
