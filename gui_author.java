package classesTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.LayeredHighlighter.LayerPainter;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class gui_author extends JFrame {
	private JPanel contentPane;
	private JTextField textField_title;
	private JTextField textField_abs;
	private JLayeredPane layeredPane;
	private JTextField textField_choice;
	private JTextField txtMr;
	private JTextField textField_firstname;
	private JTextField textField_email;
	private JTextField textField_lastname;
	private JTextField textField_uni;
	private JPasswordField passwordField;

	private static Author auth;
	private ArrayList<ArrayList<String>> data = null;
	private JTextField textField_pdf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_author frame = new gui_author(auth);
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

	public ArrayList<ArrayList<String>> selectarticle() throws SQLException {
		String queryCheck = "SELECT * FROM submission WHERE email = ? AND status = 'Submission';";
		ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, auth.getEmail());
		ArrayList<String> data = new ArrayList<String>();
		if (rows.size() > 0) {

			int count = 1;
			for (ArrayList<String> list : rows) {
				String put = (count + ") Title: " + list.get(1) + " Author: " + list.get(3));
				// textField_ID.setText((textField_ID.getText() + put + "\n"));
			}
		} else {
			JOptionPane.showMessageDialog(null, "No Articles to Select");
			// System.out.println("Empty");
		}
		return rows;
	}

	/**
	 * Create the frame.
	 */
	public gui_author(Author author) {
		auth = author;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(10, 11, 183, 36);
		contentPane.add(lblUsername);

		JLabel lblRole = new JLabel("Author");
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setBounds(0, 58, 193, 36);
		contentPane.add(lblRole);

		lblUsername.setText(auth.getFirstName().toUpperCase());

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				welcome_screen.main(null);
				dispose();
			}
		});
		btnLogOut.setBounds(40, 323, 122, 36);
		contentPane.add(btnLogOut);

		JPanel panel_select = new JPanel();
		panel_select.setBounds(0, 105, 193, 190);
		contentPane.add(panel_select);
		panel_select.setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(192, 0, 512, 381);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel panel_submit = new JPanel();
		panel_submit.setBackground(UIManager.getColor("Button.light"));
		layeredPane.add(panel_submit, "name_1673131819699400");
		panel_submit.setLayout(null);

		JLabel lblNewLabel = new JLabel("ARTICLE SUBMISSION");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(98, 0, 280, 43);
		panel_submit.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(98, 41, 294, 2);
		panel_submit.add(separator);

		textField_title = new JTextField();
		textField_title.setColumns(10);
		textField_title.setBounds(98, 87, 122, 33);
		panel_submit.add(textField_title);

		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(28, 87, 71, 33);
		panel_submit.add(lblNewLabel_1);

		textField_abs = new JTextField();
		textField_abs.setColumns(10);
		textField_abs.setBounds(98, 146, 122, 33);
		panel_submit.add(textField_abs);

		JLabel lblAbstract = new JLabel("Abstract\r\n");
		lblAbstract.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbstract.setBounds(28, 146, 71, 33);
		panel_submit.add(lblAbstract);

		JLabel lblPdf = new JLabel("PDF");
		lblPdf.setHorizontalAlignment(SwingConstants.CENTER);
		lblPdf.setBounds(28, 278, 71, 33);
		panel_submit.add(lblPdf);

		JButton button_2 = new JButton("Browse");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.showOpenDialog(null);
				textField_pdf.setText(fc.getSelectedFile().getAbsolutePath());
			}
		});
		button_2.setBounds(98, 276, 122, 36);
		panel_submit.add(button_2);

		textField_pdf = new JTextField();
		textField_pdf.setColumns(10);
		textField_pdf.setBounds(240, 284, 231, 20);
		panel_submit.add(textField_pdf);

		JComboBox comboBox_J = new JComboBox();
		comboBox_J.setModel(new DefaultComboBoxModel(new String[] { "Journal of Computer Science",
				"Journal of Software Engineering", "Journal of Artificial Intelligence" }));
		comboBox_J.setBounds(98, 209, 179, 33);
		panel_submit.add(comboBox_J);

		JLabel lblJournal = new JLabel("Journal");
		lblJournal.setHorizontalAlignment(SwingConstants.CENTER);
		lblJournal.setBounds(38, 209, 61, 33);
		panel_submit.add(lblJournal);

		JPanel panel_selected = new JPanel();
		panel_selected.setLayout(null);
		panel_selected.setBackground(SystemColor.controlHighlight);
		layeredPane.add(panel_selected, "name_1673131832239600");

		JLabel lblArticleTitle = new JLabel("ARTICLE TITLE");
		lblArticleTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticleTitle.setBounds(98, 0, 280, 43);
		panel_selected.add(lblArticleTitle);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(98, 41, 294, 2);
		panel_selected.add(separator_1);

		JPanel panel_getID = new JPanel();
		panel_getID.setLayout(null);
		panel_getID.setBackground(SystemColor.controlHighlight);
		layeredPane.add(panel_getID, "name_1675622202880500");

		JLabel lblArticleSelection = new JLabel("ARTICLE SELECTION");
		lblArticleSelection.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticleSelection.setBounds(98, 0, 280, 43);
		panel_getID.add(lblArticleSelection);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(98, 41, 294, 2);
		panel_getID.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 272, 512, 2);
		panel_getID.add(separator_3);

		JLabel lblChoice = new JLabel("Choice");
		lblChoice.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoice.setBounds(142, 285, 71, 33);
		panel_getID.add(lblChoice);

		textField_choice = new JTextField();
		textField_choice.setColumns(10);
		textField_choice.setBounds(203, 290, 122, 23);
		panel_getID.add(textField_choice);

		JButton btnNewButton = new JButton("SubmitArticle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_submit);
			}
		});
		btnNewButton.setBounds(40, 11, 122, 36);
		panel_select.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Submit\r\n");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = textField_choice.getText();
				try {
					if (Integer.parseInt(choice) <= data.size()) {
						String id = (data.get(Integer.parseInt(choice) - 1)).get(0);
						auth.setsubID(id);
						switchPanels(panel_selected);
						String title = (data.get(Integer.parseInt(choice) - 1)).get(1);
						lblArticleTitle.setText(title);

					} else {
						JOptionPane.showMessageDialog(null, "Wrong Choice");
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(194, 347, 97, 23);
		panel_getID.add(btnNewButton_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(0, 41, 512, 230);
		panel_getID.add(textArea);

		JPanel panel_coathur = new JPanel();
		layeredPane.add(panel_coathur, "name_1681158205396400");
		panel_coathur.setLayout(null);

		JLabel label = new JLabel("Title");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(34, 88, 41, 33);
		panel_coathur.add(label);

		txtMr = new JTextField();
		txtMr.setText("mr");
		txtMr.setColumns(10);
		txtMr.setBounds(75, 88, 122, 33);
		panel_coathur.add(txtMr);

		JLabel lblRegisterCoauthor = new JLabel("REGISTER COAUTHOR");
		lblRegisterCoauthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisterCoauthor.setBounds(119, 0, 280, 43);
		panel_coathur.add(lblRegisterCoauthor);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(129, 54, 280, -5);
		panel_coathur.add(separator_4);

		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(244, 88, 62, 33);
		panel_coathur.add(lblFirstname);

		textField_firstname = new JTextField();
		textField_firstname.setColumns(10);
		textField_firstname.setBounds(316, 88, 122, 33);
		panel_coathur.add(textField_firstname);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(265, 132, 41, 33);
		panel_coathur.add(lblEmail);

		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(316, 132, 122, 33);
		panel_coathur.add(textField_email);

		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastname.setBounds(10, 132, 65, 33);
		panel_coathur.add(lblLastname);

		textField_lastname = new JTextField();
		textField_lastname.setColumns(10);
		textField_lastname.setBounds(75, 132, 122, 33);
		panel_coathur.add(textField_lastname);

		JLabel lblUni = new JLabel("Uni");
		lblUni.setBounds(265, 176, 41, 33);
		panel_coathur.add(lblUni);

		textField_uni = new JTextField();
		textField_uni.setColumns(10);
		textField_uni.setBounds(316, 176, 122, 33);
		panel_coathur.add(textField_uni);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(20, 176, 55, 33);
		panel_coathur.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(75, 176, 122, 33);
		panel_coathur.add(passwordField);

		JButton btnNewButton_signup = new JButton("SignUp");
		btnNewButton_signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(txtMr.getSelectedText());
					auth.registerCoAuthor(txtMr.getText(), textField_firstname.getText(), textField_lastname.getText(),
							textField_email.getText(), passwordField.getText(), textField_uni.getText());
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtMr.setText(null);
				textField_firstname.setText(null);
				textField_lastname.setText(null);
				textField_email.setText(null);
				passwordField.setText(null);
				textField_uni.setText(null);
				switchPanels(panel_selected);

			}
		});
		btnNewButton_signup.setBounds(203, 231, 89, 43);
		panel_coathur.add(btnNewButton_signup);

		JButton btnNewButton_3 = new JButton("Respond to review");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String value = "";
					ArrayList<String> answer = new ArrayList<String>();
					ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
					if ((auth.mainAuthor(auth.getsubID())).compareTo("1") == 0) {
						data = auth.criticismQuestions(auth.getsubID());
						if (data.size() >= 3) {
							for (ArrayList<String> rows : data) {
								answer = new ArrayList<String>();
								for (String row : rows) {
									System.out.println(row);
									value = JOptionPane.showInputDialog(row);
									answer.add(value);
								}
								answers.add(answer);

							}
							auth.respondToReviews(answers);
						} else {
							JOptionPane.showMessageDialog(null, "Waiting for all Reviews");
						}
					} else {
						JOptionPane.showMessageDialog(null, "ERROR : Not a MAin Author");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(0, 122, 134, 23);
		panel_selected.add(btnNewButton_3);

		JButton btnTrackStatus = new JButton("Track Status");
		btnTrackStatus.setBounds(353, 156, 134, 23);
		panel_selected.add(btnTrackStatus);

		JButton btnRegisterCoAuthor = new JButton("Register Co Author");
		btnRegisterCoAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (auth.mainAuthor(auth.getsubID()).compareTo("1") == 0) {
						switchPanels(panel_coathur);
					} else {
						JOptionPane.showMessageDialog(null, "Not a Main author");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRegisterCoAuthor.setBounds(0, 88, 134, 23);
		panel_selected.add(btnRegisterCoAuthor);

		JButton btnSelectArticle = new JButton("Select article");
		btnSelectArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				switchPanels(panel_getID);
				try {
					String queryCheck = "SELECT * FROM submission WHERE email = ?;";
					ArrayList<ArrayList<String>> rows = DAC.getArticle(queryCheck, auth.getEmail());
					data = rows;
					if (rows.size() > 0) {
						int count = 1;
						for (ArrayList<String> list : rows) {
							String put = (count + ") Title: " + list.get(1) + " Author: " + list.get(3));
							textArea.setText((textArea.getText() + put + "\n"));
							count++;
						}
					} else {
						JOptionPane.showMessageDialog(null, "No Articles to Select");
						// System.out.println("Empty");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSelectArticle.setBounds(40, 58, 122, 36);
		panel_select.add(btnSelectArticle);

		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String password = JOptionPane.showInputDialog("Type The new password");
				try {
					auth.changePassword(password);
					JOptionPane.showMessageDialog(null, "Password changed");
				} catch (NoSuchAlgorithmException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnChangePassword.setBounds(40, 118, 122, 36);
		panel_select.add(btnChangePassword);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(191, 118, 513, 100);
		panel_select.add(scrollPane_2);

		JButton btnSubmitRevisedArticle = new JButton("Submit Final Article");
		btnSubmitRevisedArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (auth.mainAuthor(auth.getsubID()).compareTo("1") == 0) {
						try {
							String query = "SELECT * FROM review WHERE subid = ? AND status = 'Initial' ;";
							System.out.println(DAC.checkEmail(query, auth.getsubID()));
							if (auth.checkFinalarticle(auth.getsubID())
									&& (DAC.checkEmail(query, auth.getsubID()) == false)) {
								JFileChooser fc = new JFileChooser();
								fc.setCurrentDirectory(new java.io.File("."));
								fc.showOpenDialog(null);
								auth.submitRevisedArticle(fc.getSelectedFile().getAbsolutePath());
								JOptionPane.showMessageDialog(null, "Final Article Submitted");
							} else {
								JOptionPane.showMessageDialog(null, "Can't submit final article ");
							}
						} catch (FileNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {
						JOptionPane.showMessageDialog(null, "Main Author only");
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSubmitRevisedArticle.setBounds(0, 156, 134, 23);
		panel_selected.add(btnSubmitRevisedArticle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 190, 512, 191);
		panel_selected.add(scrollPane);

		JTextArea textArea_select = new JTextArea();
		scrollPane.setViewportView(textArea_select);

		JButton btnInitialVerdict = new JButton("Initial Verdict");
		btnInitialVerdict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_select.setText(null);
				try {
					ArrayList<ArrayList<String>> rows = auth.checkInitialVerdict();
					if (rows.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Waiting for Initial Verdict");
					} else {
						for (ArrayList<String> row : rows) {
							textArea_select.setText(textArea_select.getText() + "Reviwer ID: " + row.get(0) + "\n"
									+ "Verdict: " + row.get(4) + "\n");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnInitialVerdict.setBounds(178, 88, 134, 23);
		panel_selected.add(btnInitialVerdict);

		JButton btnFinalVerdict = new JButton("Final Verdict");
		btnFinalVerdict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_select.setText(null);
				try {
					ArrayList<ArrayList<String>> rows = auth.checkFinalVerdict();
					if (rows.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Waiting for Final Verdtict");
					} else {
						for (ArrayList<String> row : rows) {
							textArea_select.setText(textArea_select.getText() + "Reviwer ID: " + row.get(0) + "\n"
									+ "Verdict: " + row.get(4) + "\n");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		btnFinalVerdict.setBounds(178, 122, 134, 23);
		panel_selected.add(btnFinalVerdict);

		JButton btnCheckReview = new JButton("Check Review");
		btnCheckReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_select.setText("");
				try {
					String query = "SELECT * FROM review WHERE subid = ? AND status = 'Initial' ;";
					String put = "";
					ArrayList<ArrayList<String>> questions = new ArrayList<ArrayList<String>>();
					ArrayList<ArrayList<String>> rows = DAC.getreview(query, auth.getsubID());
					if (rows.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No Review Yet");
					} else {
						for (ArrayList<String> row : rows) {
							questions = auth.checkReview(row.get(0));
							textArea_select.append("Rev ID: " + row.get(0) + "\n");
							textArea_select.append("SUMMARY: " + row.get(2) + "\n");
							textArea_select.append("TYPO: " + row.get(3) + "\n");
							for (ArrayList<String> question : questions)
								textArea_select.append("QUESTION:  " + question.get(2) + "\n");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCheckReview.setBounds(178, 156, 134, 23);
		panel_selected.add(btnCheckReview);

		JButton btnNewButton_5 = new JButton("Initial Article");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_select.setText(null);
				try {
					ArrayList<ArrayList<String>> rows = auth.checkInitialArticle();
					if (rows == null) {
						JOptionPane.showInternalMessageDialog(null, "No Initial article");
					} else {
						textArea_select.setText("Article Details :" + rows.get(0));

					}
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_5.setBounds(353, 54, 134, 23);
		panel_selected.add(btnNewButton_5);

		JButton btnNewButton_4 = new JButton("Final Article");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_select.setText(null);
				try {
					String querypdf = "SELECT pdf FROM revised WHERE subid = ?";
					ArrayList<ArrayList<String>> rows = auth.checkFinalArticle();
					if (DAC.getpdf(querypdf, auth.getsubID())) {
						textArea_select.setText("Article Details :" + rows.get(0));
					} else {
						JOptionPane.showMessageDialog(null, "No Final Article");
					}
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(353, 88, 134, 23);
		panel_selected.add(btnNewButton_4);

		JButton btnNewButton_2 = new JButton("Check Response");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_select.setText(null);
				try {
					ArrayList<ArrayList<String>> responses = auth.checkResponse(auth.getsubID());
					if (responses.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Main Author has to reply");
					} else {
						for (ArrayList<String> response : responses) {
							textArea_select.append("Question: " + response.get(2) + "\n");
							textArea_select.append("Answer: " + response.get(3) + "\n");
							textArea_select.append("---------------------------- \n");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(353, 122, 134, 23);
		panel_selected.add(btnNewButton_2);

		JLabel lblMainAuthor = new JLabel("Main Author");
		lblMainAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainAuthor.setBounds(0, 58, 134, 19);
		panel_selected.add(lblMainAuthor);

		JButton button = new JButton("SubmitArticle");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = textField_title.getText();
				String abst = textField_abs.getText();
				String pdf = textField_pdf.getText();
				String journal = (String) comboBox_J.getItemAt(comboBox_J.getSelectedIndex());
				try {
					auth.submitArticle(title, abst, pdf, journal);
					JOptionPane.showMessageDialog(null, "ARTICLE SUBMITTED");
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField_title.setText(null);
				textField_abs.setText(null);
				textField_pdf.setText(null);
			}
		});
		button.setBounds(208, 334, 122, 36);
		panel_submit.add(button);

	}
}
