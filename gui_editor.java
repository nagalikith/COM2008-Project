package classesTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class gui_editor extends JFrame {

	
	private static Editor editorObj;
	private JPanel contentPane;
	private JTextField txtGetJournalId;
	private JLayeredPane layeredPane;
	private ArrayList<String> journalList;
	private JTextField textField_title;
	private JTextField textField_first;
	private JTextField textField_last;
	private JTextField textField_email;
	private JTextField textField_uni;
	private JPasswordField passwordField;
	private JTextField textField_getEditor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_editor frame = new gui_editor(editorObj);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	/**
	 * Create the frame.
	 */
	public gui_editor(Editor editor) {
		editorObj = editor;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 127, 198, 133);
		contentPane.add(panel);
		panel.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(199, 0, 504, 373);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		layeredPane.add(panel_1, "name_666617729346400");
		
		JPanel panel_menu = new JPanel();
		layeredPane.add(panel_menu, "name_553911879997100");
		panel_menu.setLayout(null);
		
		JPanel panel_journal = new JPanel();
		layeredPane.add(panel_journal, "name_1675622202880500");
		panel_journal.setLayout(null);
		
		JPanel panel_register_editor = new JPanel();
		layeredPane.add(panel_register_editor, "name_572822981334500");
		panel_register_editor.setLayout(null);
		
		JPanel panel_pass_role = new JPanel();
		layeredPane.add(panel_pass_role, "name_622758760955800");
		panel_pass_role.setLayout(null);
		
		JPanel panel_articles = new JPanel();
		layeredPane.add(panel_articles, "name_643769624604500");
		panel_articles.setLayout(null);
		
		JTextArea txtrSelectJournal = new JTextArea();
		txtrSelectJournal.setText("select journal");
		txtrSelectJournal.setBounds(29, 30, 446, 192);
		panel_journal.add(txtrSelectJournal);
		
		
		JButton btnSelectJournal = new JButton("select journal");
		btnSelectJournal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSelectJournal.setBounds(22, 55, 134, 39);
		panel.add(btnSelectJournal);
		
		JLabel lblSelectJournal = new JLabel("select journal to begin");
		lblSelectJournal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectJournal.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectJournal.setBounds(22, 13, 147, 29);
		panel.add(lblSelectJournal);
		btnSelectJournal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//display list of journals to select
				ArrayList<String> list = new ArrayList<String>();
				txtrSelectJournal.setText(null);
				txtGetJournalId.setText(null);
				switchPanels(panel_journal);
				try {
					//journalList = editorObj.getJournalList();
					list = editorObj.getJournalList();
					String output = "";
					
					if(list.size() != 0) {
						for(int i=0; i<list.size(); i++) {
							output = output + (i+1) +". "+ list.get(i) +"\n";
						}
					}
					else {
						list.add("Journal of Computer Science");
						list.add("Journal of Software Engineering");
						list.add("Journal of Artificial Intelligence");
						for(int i=0; i<list.size(); i++) {
							output = output + (i+1) +". "+ list.get(i) +"\n";
						}
					}
					txtrSelectJournal.setText(output);
					journalList = list;
					}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton btnSubmitSelection = new JButton("submit selection");
		btnSubmitSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = Integer.parseInt(txtGetJournalId.getText());
				if(choice <= journalList.size() && choice>0)
				{
					String journal = journalList.get(choice-1);
					editorObj.setJournal(journal);
					try {
						editorObj.addChief();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					switchPanels(panel_menu);
				}
				else {
					JOptionPane.showMessageDialog(null, "invalid selection");
				}
			}
		});
		btnSubmitSelection.setBounds(29, 307, 125, 25);
		panel_journal.add(btnSubmitSelection);
		
		txtGetJournalId = new JTextField();
		txtGetJournalId.setText("Select one by typing the number");
		txtGetJournalId.setBounds(143, 269, 332, 25);
		panel_journal.add(txtGetJournalId);
		txtGetJournalId.setColumns(10);
		
		JLabel lblTypeNumber = new JLabel("TYPE NUMBER : ");
		lblTypeNumber.setHorizontalAlignment(SwingConstants.LEFT);
		lblTypeNumber.setBounds(29, 269, 102, 25);
		panel_journal.add(lblTypeNumber);
		
		JLabel lblTitle = new JLabel("title :");
		lblTitle.setBounds(26, 106, 41, 16);
		panel_register_editor.add(lblTitle);
		
		textField_title = new JTextField();
		textField_title.setBounds(104, 103, 116, 22);
		panel_register_editor.add(textField_title);
		textField_title.setColumns(10);
		
		JLabel lblFirstName = new JLabel("first name :");
		lblFirstName.setBounds(265, 106, 79, 16);
		panel_register_editor.add(lblFirstName);
		
		textField_first = new JTextField();
		textField_first.setBounds(356, 103, 116, 22);
		panel_register_editor.add(textField_first);
		textField_first.setColumns(10);
		
		JLabel lblLastName = new JLabel("last name :");
		lblLastName.setBounds(26, 175, 79, 16);
		panel_register_editor.add(lblLastName);
		
		textField_last = new JTextField();
		textField_last.setBounds(104, 172, 116, 22);
		panel_register_editor.add(textField_last);
		textField_last.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("email :");
		lblNewLabel.setBounds(265, 175, 64, 16);
		panel_register_editor.add(lblNewLabel);
		
		textField_email = new JTextField();
		textField_email.setBounds(356, 172, 116, 22);
		panel_register_editor.add(textField_email);
		textField_email.setColumns(10);
		
		JLabel lblPassword = new JLabel("password :");
		lblPassword.setBounds(26, 246, 79, 16);
		panel_register_editor.add(lblPassword);
		
		JLabel lblUniAff = new JLabel("uni aff :");
		lblUniAff.setBounds(273, 246, 56, 16);
		panel_register_editor.add(lblUniAff);
		
		textField_uni = new JTextField();
		textField_uni.setBounds(356, 243, 116, 22);
		panel_register_editor.add(textField_uni);
		textField_uni.setColumns(10);
		
		JButton btnRegisterEditor = new JButton("register editor");
		btnRegisterEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get editor details and add to the db
				String out = "";
				try {
					out = editorObj.register(textField_title.getText(), textField_first.getText(), textField_last.getText(),
							textField_email.getText(), passwordField.getText(), textField_uni.getText());
					JOptionPane.showMessageDialog(null, out);
				} 
				catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				textField_title.setText(null);
				textField_first.setText(null);
				textField_last.setText(null);
				textField_uni.setText(null);
				textField_email.setText(null);
				passwordField.setText(null);
				switchPanels(panel_menu);
			}
		});
		btnRegisterEditor.setBounds(104, 298, 116, 25);
		panel_register_editor.add(btnRegisterEditor);
		
		JButton btnAddEditors = new JButton("add editors");
		btnAddEditors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!editorObj.isChief()) {
						String a = "you're not chief editor for the selected journal, cant access this content";
						JOptionPane.showMessageDialog(null, a);
						switchPanels(panel_menu);
					}
					else {
					switchPanels(panel_register_editor);
					}
				} 
				catch (HeadlessException e1) {
					e1.printStackTrace();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddEditors.setBounds(60, 110, 120, 25);
		panel_menu.add(btnAddEditors);
				
		passwordField = new JPasswordField();
		passwordField.setBounds(104, 243, 116, 22);
		panel_register_editor.add(passwordField);
		
		JLabel lblRegisterEditor = new JLabel("REGISTER EDITOR");
		lblRegisterEditor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegisterEditor.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegisterEditor.setBounds(175, 13, 144, 33);
		panel_register_editor.add(lblRegisterEditor);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(12, 64, 480, 2);
		panel_register_editor.add(separator_2);
		
		textField_getEditor = new JTextField();
		textField_getEditor.setBounds(33, 272, 446, 35);
		panel_pass_role.add(textField_getEditor);
		textField_getEditor.setColumns(10);
		
		JButton btnSelectEditor = new JButton("select editor");
		btnSelectEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get input
				String email = textField_getEditor.getText();
				System.out.println("*"+email);
				String out = "";
				//pass it to func pass role
				try {
					if(editorObj.isEditor(email)) {
						editorObj.passRole(email);
					}
					else {
						out = "please check spelling properly";
						JOptionPane.showMessageDialog(null, out);
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				textField_getEditor.setText(null);
				switchPanels(panel_menu);
			}
		});
		btnSelectEditor.setBounds(33, 320, 122, 25);
		panel_pass_role.add(btnSelectEditor);
		
		JTextArea textField_select_editor = new JTextArea();
		textField_select_editor.setBounds(33, 91, 446, 119);
		panel_pass_role.add(textField_select_editor);
		
		JLabel lblSelectEditorTo = new JLabel("SELECT EDITOR TO PASS ROLE");
		lblSelectEditorTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectEditorTo.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectEditorTo.setBounds(33, 43, 311, 35);
		panel_pass_role.add(lblSelectEditorTo);
		
		JLabel lblEnterOneEmail = new JLabel("enter one email ID");
		lblEnterOneEmail.setBounds(33, 227, 166, 32);
		panel_pass_role.add(lblEnterOneEmail);
		
		JButton btnPassRole = new JButton("pass role");
		btnPassRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!editorObj.isChief()) {
						String a = "you're not chief editor for the selected journal, cant access this content";
						JOptionPane.showMessageDialog(null, a);
						switchPanels(panel_menu);
					}
					else {
									
						switchPanels(panel_pass_role);
						textField_select_editor.setText(null);
						//get list of editors
						ArrayList<String> editors;
						String output = "";
						
						try {
							editors = editorObj.getEditorList();
							
							if(editors.size() != 0) {
								for(int i=0; i<editors.size(); i++) {
									textField_select_editor.append(editors.get(i)+"\n");
								}
							}
							else {
								output = "youre the sole editor, no one to choose from";
								textField_select_editor.setText(output);
								switchPanels(panel_menu);
							}
						} 
						catch (SQLException e1) {
							e1.printStackTrace();
						}
							
					}
				} 
				catch (SQLException e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnPassRole.setBounds(60, 148, 120, 25);
		panel_menu.add(btnPassRole);
		
		JButton btnRetire = new JButton("retire");
		btnRetire.setBounds(60, 186, 120, 25);
		panel_menu.add(btnRetire);
		
		JButton btnViewArticles = new JButton("view articles");
		btnViewArticles.setBounds(60, 224, 120, 25);
		panel_menu.add(btnViewArticles);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		separator.setBounds(12, 84, 480, 13);
		panel_menu.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 302, 480, 13);
		panel_menu.add(separator_1);
		
		JLabel lblMenu = new JLabel("MENU");
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setBounds(60, 13, 120, 58);
		panel_menu.add(lblMenu);
		
		JButton btnPublishEdition = new JButton("publish edition");
		btnPublishEdition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//publish edition
				//add from current edition to edition
				try {
					editorObj.publishNextEdition();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPublishEdition.setBounds(60, 264, 120, 25);
		panel_menu.add(btnPublishEdition);
		
		btnRetire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "you will no longer be a user, confirm");
				if(confirm ==0) {
					//retire
					try {
						if(editorObj.retire()) {
							JOptionPane.showMessageDialog(null, "retired");
						}
						else {
							JOptionPane.showMessageDialog(null, "cant retire, sole editor");
						}
					} 
					catch (HeadlessException e1) {
						e1.printStackTrace();
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "cancelled");
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 70, 433, 210);
		panel_articles.add(scrollPane);
		
		JTextArea textArea_article = new JTextArea();
		scrollPane.setViewportView(textArea_article);
		
		Object[] options = {"accept", "reject", "next"};
		
		JButton btnTakeAction = new JButton("decide");
		btnTakeAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				//accept, reject, next
				//get list of [list of id] and [[list of title]]
				textArea_article.setText(null);
				ArrayList<String> acceptedId = new ArrayList<String>();
				ArrayList<String> usedId = new ArrayList<String>();
				
				try {
					ArrayList<String> idList = new ArrayList<String>();
					ArrayList<String> uni = new ArrayList<String>();
					
					ArrayList<String> noConflictsId = new ArrayList<String>();
					idList = editorObj.getLists();
					
					for(int i=0; i<idList.size(); i++)
					{
						String id = idList.get(i);
						uni = editorObj.getUniList(id);
						if(!uni.contains(editorObj.getUniAffiliation())) {
							//does not contain the conflict uni
							noConflictsId.add(id);
						}
					}
					
					for(int i=0; i<noConflictsId.size(); i++)
					{
						String id = idList.get(i);
						String title = editorObj.getTitle(id);
						String verdict = editorObj.getVerdicts(id);
						
						String line = id+" "+title + " " + verdict;
						
						
						int n = JOptionPane.showOptionDialog(null, 
								"ArticleId : "+id+"\n"
								+ "Title : "+title+"\n"
								+ "Verdicts : "+verdict+"\n", 
								"Accept or reject article", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE,
								null, 
								options, 
								null);
						
						if(n == -1) {
							break;
						}
						else if(n == 0) {
							//accepted
							acceptedId.add(id);
							usedId.add(id);
						}
						else if(n == 1) {
							//rejected
							usedId.add(id);
						}
						
					}
					editorObj.addIntoCurrentEdition(acceptedId);
					editorObj.delFromReviewed(usedId);
					System.out.println(acceptedId.toString() +" " +usedId.toString());
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnTakeAction.setBounds(40, 306, 97, 25);
		panel_articles.add(btnTakeAction);
		
		JLabel lblNewLabel_1 = new JLabel("ARTICLES PENDING DECISION");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(40, 27, 305, 30);
		panel_articles.add(lblNewLabel_1);
		
		JLabel lblEditor = new JLabel("EDITOR");
		lblEditor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEditor.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditor.setBounds(36, 13, 129, 33);
		contentPane.add(lblEditor);
		
		JButton btnNewButton = new JButton("Log Out");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				welcome_screen.main(null);
				dispose();
			}
		});
		btnNewButton.setBounds(36, 290, 141, 33);
		contentPane.add(btnNewButton);
		
		btnViewArticles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_articles);

				//get list of [list of id] and [[list of title]]

				try {
					ArrayList<String> idList = new ArrayList<String>();
					ArrayList<String> uni = new ArrayList<String>();
					ArrayList<String> noConflictsId = new ArrayList<String>();
					idList = editorObj.getLists();
					

					System.out.println(idList.toString());
					
					for(int i=0; i<idList.size(); i++)
					{
						String id = idList.get(i);
						uni = editorObj.getUniList(id);
						if(!uni.contains(editorObj.getUniAffiliation())) {
							//does not contain the conflict uni
							noConflictsId.add(id);
						}
					}
					
					System.out.println(noConflictsId.toString());
					
					for(int i=0; i<noConflictsId.size(); i++) {
						String id = noConflictsId.get(i);
						String verdict = editorObj.getVerdicts(id);
						String title = editorObj.getTitle(id);
						textArea_article.append(id+"  "+title+"  "+verdict+"\n");
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}
}
