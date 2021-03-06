package layoutapp;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import layoutapp.model.*;
import layoutapp.view.IssueChooserDialog;
import layoutapp.view.PageView;

public class ApplicationFrame extends JFrame {
//	protected final Issue issue;
	protected final PageView pageView;
	protected final JTextField pageNumberField;

	public ApplicationFrame(Issue issue, LayoutApp layoutApp/*modification*/) {
		super("The Page Layout Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//modification
		pageView = new PageView(issue, layoutApp);
		
		pageNumberField = new JTextField("1");
		pageNumberField.setEnabled(false);
		
		// The page view is displayed within a scroll pane.
		JScrollPane pageViewScrollPane = new JScrollPane(pageView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// The frame contains a toolbar and the page view (wrapped in a scroll pane).
		// These are wrapped in a content pane, which uses a border layout.
		// The border layout allows placing components at five
		// different positions: north, south, east, west, and centre.
		// The centre position is resized as the frame is resized.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(BorderLayout.NORTH, getToolBar());
		contentPane.add(BorderLayout.CENTER, pageViewScrollPane);
		contentPane.setPreferredSize(new Dimension(800, 900));
		
		// The content pane is now set into the frame.
		setContentPane(contentPane);
		
		// The following call computes the layout of the frame accordingly. 
		pack();
	}
	protected JToolBar getToolBar() {
		pageNumberField.setMinimumSize(new Dimension(50,20));
		// When the user presses <Enter> inside a JTextField, the text field
		// will fire an ActionEvent. When this is done, the handler for the
		// event reads the value in the field, parses it into an integer,
		// and displays the appropriate page.
		pageNumberField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pageNumberText = pageNumberField.getText();
				try {
					int pageNumber = Integer.parseInt(pageNumberText);
					pageView.setCurrentPage(pageNumber - 1);
				}
				catch (NumberFormatException nfe) {
					pageView.setCurrentPage(-1);
				}
			}
		});
		
		JButton newPageButton = new JButton("New Page");
		// When the user clicks a JButton, the button will fire
		// an ActionEvent. When this is done, the handler for the
		// event creates the new page and displays it in the viewer.
		// It also updates the pointer to the current page.
		newPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getCurrentIssue()==null)
					return;
				Page page = getCurrentIssue().createNewPage();
				pageNumberField.setText(String.valueOf(page.getPageIndex() + 1));
				pageNumberField.setEnabled(true);
				pageNumberField.selectAll();
				pageView.setCurrentPage(page.getPageIndex());
			}
		});
		
		//modification
		JButton chooseIssueButton = new JButton("Choose Issue");
		chooseIssueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IssueChooserDialog dialog = new IssueChooserDialog(ApplicationFrame.this);
				dialog.setVisible(true);
			}
		});
		
		JToolBar toolBar = new JToolBar();
		toolBar.setMargin(new Insets(10,10,10,10));
		toolBar.add(new JLabel("Current page: "));
		toolBar.add(pageNumberField);
		toolBar.add(newPageButton);
		toolBar.add(chooseIssueButton);
		return toolBar;
	}
	public static void main(String[] args) throws IssueNotFoundException, DuplicateIssueException {
		//modification
		LayoutApp layoutApp = LayoutApp.getInstance() ;
		Issue issue = layoutApp.getIssueArchive().createNewIssue(10, 2008);
		ApplicationFrame applicationFrame = new ApplicationFrame(null, layoutApp);
		applicationFrame.setLocation(100, 100);
		applicationFrame.setVisible(true);
	}
	
	//modification - added method
	public Issue getCurrentIssue() {
		return pageView.getIssue();
	}
	
	//modification - added method
	public void setCurrentIssue(Issue issue) {
		pageView.setCurrentIssue(issue);
		if(issue.getNumberOfPages() == 0) {
			pageNumberField.setEnabled(false);
			pageNumberField.setText("");
		}
		else {
			pageNumberField.setEnabled(true);
			pageNumberField.setText("1");
			pageView.setCurrentPage(0);
		}
		repaint();
	}
}
