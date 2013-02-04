package layoutapp.model;

/**
 * This interface can be used to receive notifications about the changes in Page.
 */
public interface PageListener {
	void pageElementAdded(Page page,PageElement pageElement);
	void pageElementWillChange(Page page,PageElement pageElement);
	void pageElementChanged(Page page,PageElement pageElement);
}
