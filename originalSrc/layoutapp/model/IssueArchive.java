package layoutapp.model;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents an archive of the issues in a system. In a realisitc application, this would be stored in a database.
 * For simplicity, however, we assume that all issues are stored in main memory.
 */
public class IssueArchive {
	protected final Map<String,Issue> issues;
	
	public IssueArchive() {
		issues = new HashMap<String,Issue>();
	}
	public Issue createNewIssue(int issueMonth, int issueYear) throws DuplicateIssueException {
		String issueKey = String.valueOf(issueMonth) + "/" + String.valueOf(issueYear); 
		if (issues.containsKey(issueKey))
			throw new DuplicateIssueException(issueKey);
		Issue newIssue = new Issue(issueMonth,issueYear);
		issues.put(issueKey, newIssue);
		return newIssue;
	}
	public Issue getIssue(int month, int year) throws IssueNotFoundException {
		Issue issue = issues.get(month+"/"+year);
		if (issue == null)
			throw new IssueNotFoundException(month+"/"+year);
		return issue;
	}
}
