package layoutapp.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import layoutapp.ApplicationFrame;
import layoutapp.model.Issue;
import layoutapp.model.IssueArchive;
import layoutapp.model.IssueArchiveListener;
import layoutapp.model.LayoutApp;
//TODO
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class IssueChooserDialog extends JDialog {
	public IssueChooserDialog(final ApplicationFrame frame) {
		//modal => dialog keeps focus
		super(frame, "Choose Issue", true);
		
		BorderLayout bl = new BorderLayout();
		this.getContentPane().setLayout(bl);
		
		//centre
		final IssueArchiveTableManager iatm = new IssueArchiveTableManager(LayoutApp.getInstance().getIssueArchive());
		final JTable table = new JTable(iatm);
		table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JScrollPane jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(400,300));
		this.getContentPane().add(jsp, BorderLayout.CENTER);
		
		//south
		JPanel southButtonPanel = new JPanel();
		southButtonPanel.setLayout(new FlowLayout());
		JButton ok = new JButton("ok");
		JButton cancel = new JButton("cancel");
		JButton newIssue = new JButton("new issue");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRowCount()==1) {
					Issue issue = (LayoutApp.getInstance().getIssueArchive()).getIssue((table.getSelectedRow()));
					//set issue here
					frame.setCurrentIssue(issue);
					dispose();
				}
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		newIssue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewIssueDialog newIssueDialog = new NewIssueDialog(IssueChooserDialog.this);
				newIssueDialog.setVisible(true);
			}
		});
		southButtonPanel.add(newIssue);
		southButtonPanel.add(ok);
		southButtonPanel.add(cancel);
		getContentPane().add(southButtonPanel, BorderLayout.SOUTH);
		
		//do sizing
		pack();
		setLocationRelativeTo(frame);
		
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				iatm.dispose();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				iatm.dispose();
			}
		});
	}
	
	//modification
	private class IssueArchiveTableManager extends AbstractTableModel implements IssueArchiveListener {
		private IssueArchive issueArchive;
		
		public IssueArchiveTableManager(IssueArchive ia) {
			issueArchive = ia;
			issueArchive.addIssueArchiveListener(this);
		}
		
		@Override
		public int getRowCount() {
			return issueArchive.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			assert 0<=columnIndex&&columnIndex<=1;
			assert 0<=rowIndex && rowIndex<=issueArchive.size();
			if(columnIndex == 0)
				return issueArchive.getIssueDate(rowIndex);
			else 
				return issueArchive.getIssue(rowIndex).getNumberOfPages();
		}
		
		@Override
		public boolean isCellEditable(int r, int c) {
			return false;
		}
		
		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return "Issue Month";
			case 1:
				return "Number Of Pages";
			}
			//should not be able to reach here
			assert false;
			return null;
		}

		@Override
		public void IssueArchiveChanged() {
			fireTableDataChanged();
		}
		
		public void dispose() {
			issueArchive.removeIssueArchiveListener(this);
		}
		
	}
}
