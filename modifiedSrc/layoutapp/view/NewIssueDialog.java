package layoutapp.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import layoutapp.ApplicationFrame;
import layoutapp.model.DuplicateIssueException;
import layoutapp.model.Issue;
import layoutapp.model.LayoutApp;

public class NewIssueDialog extends JDialog {
	public NewIssueDialog(final IssueChooserDialog dialog) {
		//modal => dialog keeps focus
		super(dialog, "Select Month and Year", true);
		
		BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		this.getContentPane().setLayout(bl);
		
		JPanel centre = new JPanel();
		JLabel month = new JLabel("month");
		centre.add(month);
		final JTextField monthInput = new JTextField();
		monthInput.setPreferredSize(new Dimension(100,20));
		centre.add(monthInput);
		JLabel year = new JLabel("year");
		centre.add(year);
		final JTextField yearInput = new JTextField();
		yearInput.setPreferredSize(new Dimension(100,20));
		centre.add(yearInput);
		
		getContentPane().add(centre);
		
		JPanel bottom = new JPanel();
		JButton ok = new JButton("ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int issueMonth;
				int issueYear;
				try {
					issueMonth = Integer.parseInt(monthInput.getText());
					issueYear = Integer.parseInt(yearInput.getText());
					LayoutApp.getInstance().getIssueArchive().createNewIssue(issueMonth, issueYear);
				} catch(NumberFormatException nfe) {
					//do nothing
					return;
				} catch (DuplicateIssueException die) {
					//issue already exists - work done
					dispose();
				}
				
				dispose();
			}
		});
		bottom.add(ok);
		JButton cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		bottom.add(cancel);
		
		getContentPane().add(bottom);
		
		//do sizing
		pack();
		setLocationRelativeTo(dialog);
	}
}
