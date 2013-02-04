package layoutapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents an archive of the issues in a system. In a realistic application, this would be stored in a database.
 * For simplicity, however, we assume that all issues are stored in main memory.
 */
public class IssueArchive {
	protected final Map<String,Issue> issues;
	//modification
	protected final ArrayList<String> issueKeysArray;
	protected final List<IssueArchiveListener> issueArchiveListeners;
	
	public IssueArchive() {
		issues = new HashMap<String,Issue>();
		//modification
		issueKeysArray = new ArrayList<String>();
		issueArchiveListeners = new ArrayList<IssueArchiveListener>();
	}
	public Issue createNewIssue(int issueMonth, int issueYear) throws DuplicateIssueException {
		String issueKey = String.valueOf(issueMonth) + "/" + String.valueOf(issueYear); 
		if (issues.containsKey(issueKey))
			throw new DuplicateIssueException(issueKey);
		Issue newIssue = new Issue(issueMonth,issueYear);
		issues.put(issueKey, newIssue);
		issueKeysArray.add(issueKey);
		//modification
		fireIssueArchiveChanged();
		return newIssue;
	}
	public Issue getIssue(int month, int year) throws IssueNotFoundException {
		Issue issue = issues.get(month+"/"+year);
		if (issue == null)
			throw new IssueNotFoundException(month+"/"+year);
		return issue;
	}
	
	//modification
	public int size() {
		return issueKeysArray.size();
	}
	
	public String getIssueDate(int i) {
		return issueKeysArray.get(i);
	}
	
	public Issue getIssue(int i) {
		return issues.get(getIssueDate(i));
	}
	
	public void addIssueArchiveListener(IssueArchiveListener ial) {
		issueArchiveListeners.add(ial);
	}
	public void removeIssueArchiveListener(IssueArchiveListener ial) {
		issueArchiveListeners.remove(ial);
	}
	public void fireIssueArchiveChanged() {
		for(IssueArchiveListener ial : issueArchiveListeners) {
			ial.IssueArchiveChanged();
		}
	}
	
}
