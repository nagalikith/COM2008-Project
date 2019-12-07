package classesTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class gui_Reviewe extends JFrame {

	private JPanel contentPane;
	private JTextField textField_C1;
	private JTextField textField_C2;
	private JTextField textField_C3;
	private JTextField textField_artchoice;

	private static Reviewers reviewer;
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	private JLayeredPane layeredPane;
	private boolean check = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_Reviewe frame = new gui_Reviewe(reviewer);
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
	public gui_Reviewe(Reviewers review) {
		ArrayList<String> questions = new ArrayList<String>();
		reviewer = review;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 694, 376);
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel);

		JLabel label = new JLabel("USERNAME");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 11, 183, 36);
		panel.add(label);

		label.setText(reviewer.getFirstName());

		JLabel lblReviewer = new JLabel("REVIEWER");
		lblReviewer.setHorizontalAlignment(SwingConstants.CENTER);
		lblReviewer.setBounds(0, 58, 193, 36);
		panel.add(lblReviewer);

		JButton button = new JButton("Log Out");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				welcome_screen.main(null);
				dispose();
			}
		});
		button.setBounds(40, 323, 122, 36);
		panel.add(button);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 105, 193, 190);
		panel.add(panel_1);

		JButton btnChangePassword = new JButton("Change password");
		btnChangePassword.setBounds(40, 114, 122, 44);
		panel_1.add(btnChangePassword);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(194, 11, 505, 128);
		panel_1.add(textArea_2);

		layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.DARK_GRAY);
		layeredPane.setBounds(192, 0, 512, 376);
		panel.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel panel_welcome = new JPanel();
		layeredPane.add(panel_welcome, "name_1785457208702400");
		panel_welcome.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("WELCOME");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(115, 101, 264, 64);
		panel_welcome.add(lblNewLabel_3);

		JPanel panel_initial = new JPanel();
		panel_initial.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_initial, "name_1689996478926200");
		panel_initial.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Summary");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 47, 96, 32);
		panel_initial.add(lblNewLabel_1);

		JLabel lblTypographicalError = new JLabel(" Typographical error");
		lblTypographicalError.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypographicalError.setBounds(0, 150, 132, 24);
		panel_initial.add(lblTypographicalError);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 78, 502, 70);
		panel_initial.add(scrollPane);

		JTextArea textArea_sumI = new JTextArea();
		scrollPane.setViewportView(textArea_sumI);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 174, 502, 70);
		panel_initial.add(scrollPane_1);

		JTextArea textArea_typoI = new JTextArea();
		scrollPane_1.setViewportView(textArea_typoI);

		JComboBox comboBox_I = new JComboBox();
		comboBox_I.setModel(new DefaultComboBoxModel(
				new String[] { "Strong Accept", "Weak Accept", "Weak Reject", "Strong Reject" }));
		comboBox_I.setBounds(0, 333, 243, 32);
		panel_initial.add(comboBox_I);

		JLabel lblJudgement = new JLabel("Verdict");
		lblJudgement.setHorizontalAlignment(SwingConstants.CENTER);
		lblJudgement.setBounds(0, 298, 96, 32);
		panel_initial.add(lblJudgement);

		JLabel lblNewLabel = new JLabel("INITIAL REVIEW");
		lblNewLabel.setBounds(200, 0, 100, 36);
		panel_initial.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_final = new JPanel();
		layeredPane.add(panel_final, "name_1692316316589000");
		panel_final.setLayout(null);
		panel_final.setBackground(Color.LIGHT_GRAY);

		JLabel label_2 = new JLabel("Summary");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(0, 47, 96, 32);
		panel_final.add(label_2);

		JLabel label_3 = new JLabel(" Typographical error");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(0, 150, 132, 24);
		panel_final.add(label_3);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 78, 502, 70);
		panel_final.add(scrollPane_2);

		JTextArea textArea_sumf = new JTextArea();
		scrollPane_2.setViewportView(textArea_sumf);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(0, 174, 502, 70);
		panel_final.add(scrollPane_3);

		JTextArea textArea_typoF = new JTextArea();
		scrollPane_3.setViewportView(textArea_typoF);

		JComboBox comboBox_F = new JComboBox();
		comboBox_F.setModel(new DefaultComboBoxModel(
				new String[] { "Strong Accept", "Weak Accept", "Weak Reject", "Strong Reject" }));
		comboBox_F.setBounds(106, 255, 243, 32);
		panel_final.add(comboBox_F);

		JLabel lblVerdict = new JLabel("Verdict");
		lblVerdict.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerdict.setBounds(0, 255, 96, 32);
		panel_final.add(lblVerdict);

		JLabel lblFinalReview = new JLabel("FINAL REVIEW");
		lblFinalReview.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalReview.setBounds(200, 0, 100, 36);
		panel_final.add(lblFinalReview);

		JPanel panel_select3 = new JPanel();
		panel_select3.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_select3, "name_1690330076326300");
		panel_select3.setLayout(null);

		textField_C1 = new JTextField();
		textField_C1.setBounds(88, 310, 86, 20);
		panel_select3.add(textField_C1);
		textField_C1.setColumns(10);

		textField_C2 = new JTextField();
		textField_C2.setColumns(10);
		textField_C2.setBounds(225, 310, 86, 20);
		panel_select3.add(textField_C2);

		textField_C3 = new JTextField();
		textField_C3.setColumns(10);
		textField_C3.setBounds(358, 310, 86, 20);
		panel_select3.add(textField_C3);

		JLabel lblChoose = new JLabel("Choose");
		lblChoose.setBounds(10, 313, 46, 14);
		panel_select3.add(lblChoose);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(0, 56, 502, 215);
		panel_select3.add(scrollPane_5);

		JTextArea textArea_select3 = new JTextArea();
		scrollPane_5.setViewportView(textArea_select3);

		JPanel panel_article = new JPanel();
		panel_article.setLayout(null);
		panel_article.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_article, "name_1700711583285900");

		textField_artchoice = new JTextField();
		textField_artchoice.setColumns(10);
		textField_artchoice.setBounds(217, 225, 86, 20);
		panel_article.add(textField_artchoice);

		JLabel label_4 = new JLabel("Choose");
		label_4.setBounds(161, 228, 46, 14);
		panel_article.add(label_4);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(0, 0, 502, 210);
		panel_article.add(scrollPane_6);

		JTextArea textArea_choice = new JTextArea();
		scrollPane_6.setViewportView(textArea_choice);

		JPanel panel_choice = new JPanel();
		panel_choice.setLayout(null);
		panel_choice.setBackground(SystemColor.controlHighlight);
		layeredPane.add(panel_choice, "name_1760863790851700");

		JLabel label_1 = new JLabel("ARTICLE TITLE");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(98, 0, 280, 43);
		panel_choice.add(label_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(98, 41, 294, 2);
		panel_choice.add(separator);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(0, 190, 512, 191);
		panel_choice.add(scrollPane_4);

		JTextArea textArea_choice3 = new JTextArea();
		scrollPane_4.setViewportView(textArea_choice3);

		JButton button_11 = new JButton("Initial Verdict");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (reviewer.checkSubmission(reviewer.getsubID())) {
						switchPanels(panel_initial);
					} else {
						JOptionPane.showMessageDialog(null, "No Submission");
					}
				} catch (HeadlessException | NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_11.setBounds(0, 88, 148, 43);
		panel_choice.add(button_11);

		JButton button_12 = new JButton("Final Verdict");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (reviewer.checkInitialreview(reviewer.getsubID()) && check) {
						switchPanels(panel_final);
					} else if (check == false) {
						JOptionPane.showMessageDialog(null, "Waiting for Response");
					} else {
						JOptionPane.showMessageDialog(null, "Waiting for Initial");
					}
				} catch (HeadlessException | NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_12.setBounds(354, 88, 148, 45);
		panel_choice.add(button_12);

		JPanel panel_questions = new JPanel();
		layeredPane.add(panel_questions, "name_1762396303312300");
		panel_questions.setLayout(null);

		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(0, 115, 503, 165);
		panel_questions.add(scrollPane_7);

		JTextArea txtrquestion = new JTextArea();
		txtrquestion.setText("Enter 1 error/question at a time and click submit\r\nclick close when finished\r\n");
		scrollPane_7.setViewportView(txtrquestion);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_initial);
			}
		});
		btnClose.setBounds(0, 334, 107, 42);
		panel_questions.add(btnClose);

		JButton btnSubmit_2 = new JButton("Submit");
		btnSubmit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questions.add(txtrquestion.getText());
				txtrquestion.setText(null);
				JOptionPane.showMessageDialog(null, "Question added");
			}
		});
		btnSubmit_2.setBounds(375, 283, 128, 42);
		panel_questions.add(btnSubmit_2);

		JLabel lblNewLabel_2 = new JLabel("Errors/Questions");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(112, 0, 224, 42);
		panel_questions.add(lblNewLabel_2);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(97, 53, 259, 2);
		panel_questions.add(separator_1);

		JButton btnNewButton = new JButton("Errors");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_questions);
			}
		});
		btnNewButton.setBounds(0, 255, 122, 32);
		panel_initial.add(btnNewButton);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (reviewer.checkSubmission(review.getsubID())) {
						String sum = textArea_sumI.getText();
						String typo = textArea_typoI.getText();
						String verdict = (String) comboBox_I.getItemAt(comboBox_I.getSelectedIndex());
						reviewer.submitInitialVerdict(sum, typo, verdict, questions);
					} else {
						JOptionPane.showMessageDialog(null, "No Submission");
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switchPanels(panel_choice);
			}
		});
		btnSubmit.setBounds(336, 330, 132, 38);
		panel_initial.add(btnSubmit);

		JButton button_2 = new JButton("Select article");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (reviewer.articleCheck()) {
						// textArea_select3
						textArea_select3.setText(null);
						switchPanels(panel_select3);
						data = reviewer.showArticleForSelection();
						System.out.println("*"+data);
						ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
						for (ArrayList<String> dataset : data) {
							if (reviewer.uniAffliation(reviewer.getUniAffiliation(), dataset.get(0))) {
								rows.add(dataset);
							}
						}
						System.out.println(reviewer.getUniAffiliation());
						System.out.println(rows);
						if (rows.size() >= 3) {
							int count = 1;
							for (ArrayList<String> list : rows) {
								String put = (count + ") Title: " + list.get(1) + " Author: " + list.get(3));
								textArea_select3.setText((textArea_select3.getText() + put + "\n"));
								count++;
							}
							data = rows;
						} else {
							JOptionPane.showMessageDialog(null, "Not Enough Articles to Select");
							// System.out.println("Empty");
						}

					} else {
						switchPanels(panel_article);
						textArea_choice.setText(null);
						data = reviewer.viewArticle();
						int count = 1;
						for (ArrayList<String> list : data) {
							String put = (count + ") Title: " + Author.articleTitle(list.get(1)) + "Submission ID: "
									+ list.get(1));
							textArea_choice.setText((textArea_choice.getText() + put + "\n"));
							count++;
						}
					}
				} catch (NoSuchAlgorithmException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_2.setBounds(40, 34, 122, 52);
		panel_1.add(button_2);

		JButton btnSubmit_1 = new JButton("Submit");
		btnSubmit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice1 = textField_C1.getText();
				String choice2 = textField_C2.getText();
				String choice3 = textField_C3.getText();
				try {
					if (Integer.parseInt(choice1) <= data.size() && Integer.parseInt(choice2) <= data.size()
							&& Integer.parseInt(choice3) <= data.size()) {
						reviewer.selectArticleReview(data, choice1, choice2, choice3);
						JOptionPane.showMessageDialog(null, "Article Choosen");
						switchPanels(panel_article);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Input");
					}
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSubmit_1.setBounds(160, 340, 151, 36);
		panel_select3.add(btnSubmit_1);

		JLabel lblArticleSelection = new JLabel("ARTICLE SELECTION");
		lblArticleSelection.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticleSelection.setBounds(133, 0, 197, 27);
		panel_select3.add(lblArticleSelection);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(98, 25, 262, 2);
		panel_select3.add(separator_2);

		JButton button_1 = new JButton("Submit");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = textField_artchoice.getText();
				reviewer.setSubId(data, choice);
				JOptionPane.showMessageDialog(null, "Article selected");
				try {
					label_1.setText(label_1.getText() + " " + Author.articleTitle(Reviewers.getsubID()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switchPanels(panel_choice);
			}
		});
		button_1.setBounds(161, 256, 151, 36);
		panel_article.add(button_1);

		JButton button_3 = new JButton("Submit");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sum = textArea_sumf.getText();
					String typo = textArea_typoF.getText();
					String verdict = (String) comboBox_F.getItemAt(comboBox_F.getSelectedIndex());
					reviewer.submitFinalVerdict(sum, typo, verdict);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switchPanels(panel_choice);
			}
		});
		button_3.setBounds(168, 327, 132, 38);
		panel_final.add(button_3);

		JButton button_4 = new JButton("Check Response");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_choice3.setText(null);
				try {
					ArrayList<ArrayList<String>> rows = reviewer.checkResponse(reviewer.getsubID());
					if (rows.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Waiting for response");
					} else {
						for (ArrayList<String> row : rows) {
							textArea_choice3.append("Question: " + row.get(2) + "\n");
							textArea_choice3.append("Answer: " + row.get(3) + "\n");
						}
						check = true;
					}
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_4.setBounds(175, 88, 148, 43);
		panel_choice.add(button_4);
		
		JButton btnInitialArticle = new JButton("Initial Article");
		btnInitialArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String querypdf = "SELECT pdf FROM submission WHERE id = ? AND status = 'Submission' AND mainauthor = TRUE ;";
				try {
					DAC.getpdf(querypdf, reviewer.getsubID());
					JOptionPane.showMessageDialog(null, "Initial Article Downloaded");
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnInitialArticle.setBounds(0, 144, 148, 43);
		panel_choice.add(btnInitialArticle);
		
		JButton btnFinalArticle = new JButton("Final Article");
		btnFinalArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String querypdf = "SELECT pdf FROM revised WHERE subid = ?";
					if (DAC.getpdf(querypdf, reviewer.getsubID())) {
						JOptionPane.showMessageDialog(null, "Final Article Downloaded");
					} else {
						JOptionPane.showMessageDialog(null, "No Final Article");
					}
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnFinalArticle.setBounds(354, 144, 148, 43);
		panel_choice.add(btnFinalArticle);
	}
}