package classesTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTextField;

public class welcome_screen extends JFrame {

	private JPanel contentPane;
	private JTextField email_signin;
	private JTextField textField_title;
	private JTextField textField_uni;
	private JTextField textField_email_signup;
	private JTextField textField_firstname;
	private JTextField textField_lastname;
	private JPasswordField passwordField_signin;
	private JPasswordField passwordField_signup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcome_screen frame = new welcome_screen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public welcome_screen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 307, 381);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblSignIn = new JLabel("SIGN IN");
		lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn.setBounds(83, 35, 122, 33);
		panel.add(lblSignIn);

		JSeparator separator = new JSeparator();
		separator.setBounds(32, 92, 244, 2);
		panel.add(separator);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(29, 105, 62, 33);
		panel.add(lblNewLabel);

		email_signin = new JTextField();
		email_signin.setBounds(130, 105, 122, 33);
		panel.add(email_signin);
		email_signin.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(29, 149, 91, 33);
		panel.add(lblPassword);

		passwordField_signin = new JPasswordField();
		passwordField_signin.setBounds(130, 155, 122, 27);
		panel.add(passwordField_signin);

		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(29, 204, 62, 33);
		panel.add(lblRole);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Author", "Editor", "Reviewer" }));
		comboBox.setBounds(130, 204, 122, 33);
		panel.add(comboBox);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(30, 259, 246, 2);
		panel.add(separator_1);

		JButton btnLoginAsGuest = new JButton("Login as Guest");
		btnLoginAsGuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLoginAsGuest.setBounds(161, 272, 122, 33);
		panel.add(btnLoginAsGuest);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = email_signin.getText();
				String password = passwordField_signin.getText();
				String role = (String) comboBox.getItemAt(comboBox.getSelectedIndex());

				String queryCheck = "SELECT * FROM roles WHERE user = " + "\'" + email + "\'" + ";";
				try {
					if (loginExist(email, password) && (DAC.checkRole(role, queryCheck) == false)) {
						String query = "SELECT * FROM user WHERE email = ?";
						ArrayList<String> user = DAC.getUser(query, email);
						if (role == "Reviewer") {
							Reviewers reviewer = new Reviewers(user.get(0), user.get(1), user.get(2), user.get(3),
									user.get(4), user.get(5));
							JOptionPane.showMessageDialog(null, "Welcome: " + reviewer.getFirstName().toUpperCase());
							gui_Reviewe greviewer = new gui_Reviewe(reviewer);
							greviewer.main(null);
							dispose();
						} else if (role == "Author") {
							Author author = new Author(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4),
									user.get(5));
							JOptionPane.showMessageDialog(null, "Welcome: " + author.getFirstName().toUpperCase());
							gui_author gauthor = new gui_author(author);
							gauthor.main(null);
							dispose();
						} else {
							Editor editor = new Editor(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4),
									user.get(5));
							JOptionPane.showMessageDialog(null, "Welcome: " + editor.getFirstName().toUpperCase());
							gui_editor geditor = new gui_editor(editor);
							geditor.main(null);
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Login failed");
					}
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				email_signin.setText(null);
				passwordField_signin.setText(null);

			}
		});
		btnNewButton.setBounds(29, 272, 122, 33);
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(305, 0, 399, 381);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSignUp = new JLabel("SIGN UP");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setBounds(148, 38, 122, 33);
		panel_1.add(lblSignUp);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 69, 379, 2);
		panel_1.add(separator_2);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 95, 41, 33);
		panel_1.add(lblTitle);

		textField_title = new JTextField();
		textField_title.setColumns(10);
		textField_title.setBounds(51, 95, 122, 33);
		panel_1.add(textField_title);

		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(183, 89, 62, 33);
		panel_1.add(lblFirstName);

		textField_firstname = new JTextField();
		textField_firstname.setColumns(10);
		textField_firstname.setBounds(255, 95, 122, 33);
		panel_1.add(textField_firstname);

		JLabel lblLastName = new JLabel("Uni");
		lblLastName.setBounds(10, 133, 41, 33);
		panel_1.add(lblLastName);

		textField_uni = new JTextField();
		textField_uni.setColumns(10);
		textField_uni.setBounds(51, 139, 122, 33);
		panel_1.add(textField_uni);

		JLabel lblSurName = new JLabel("Last name");
		lblSurName.setBounds(183, 133, 62, 33);
		panel_1.add(lblSurName);

		textField_lastname = new JTextField();
		textField_lastname.setColumns(10);
		textField_lastname.setBounds(255, 139, 122, 33);
		panel_1.add(textField_lastname);

		JLabel label_5 = new JLabel("Email");
		label_5.setBounds(10, 177, 41, 33);
		panel_1.add(label_5);

		textField_email_signup = new JTextField();
		textField_email_signup.setColumns(10);
		textField_email_signup.setBounds(51, 183, 122, 33);
		panel_1.add(textField_email_signup);

		JLabel label_6 = new JLabel("Password");
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setBounds(183, 177, 62, 33);
		panel_1.add(label_6);

		passwordField_signup = new JPasswordField();
		passwordField_signup.setBounds(255, 183, 122, 27);
		panel_1.add(passwordField_signup);

		JLabel label_3 = new JLabel("Role");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(99, 221, 62, 33);
		panel_1.add(label_3);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "Author", "Editor" }));
		comboBox_1.setBounds(171, 221, 122, 33);
		panel_1.add(comboBox_1);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 279, 379, 2);
		panel_1.add(separator_3);

		JButton btnSignUp = new JButton("Sign Up\r\n");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = textField_title.getText();
				String firstname = textField_firstname.getText();
				String lastname = textField_lastname.getText();
				String email = textField_email_signup.getText();
				String password = passwordField_signup.getText();
				String uni = textField_uni.getText();
				String role = (String) comboBox_1.getItemAt(comboBox_1.getSelectedIndex());
				String query = "SELECT * FROM user WHERE email = ? ;";
				try {
					if (DAC.checkEmail(query , email)) {
						loginNew(title, firstname, lastname, email, password, uni);
						addroll(role, email);
					} else {
						// System.out.println("Invalid Email");
						JOptionPane.showMessageDialog(null, "Invalid Email");
					}
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField_title.setText(null);
				textField_firstname.setText(null);
				textField_lastname.setText(null);
				textField_uni.setText(null);
				textField_email_signup.setText(null);
				passwordField_signup.setText(null);

			}
		});
		btnSignUp.setBounds(148, 300, 122, 33);
		panel_1.add(btnSignUp);
	}

	static boolean addroll(String role, String email) throws SQLException {
		// TODO Auto-generated method stub
		String query = "INSERT INTO roles VALUES (?,?);";
		String queryCheck = "SELECT * FROM roles WHERE user = " + "\'" + email + "\'" + ";";
		if (DAC.checkRole(role, queryCheck) == false) {
			// System.out.println("Role already selected");
			JOptionPane.showMessageDialog(null, "Role already selected");
			return false;
		} else {
			DAC.addrolls(email, role, query);
			return true;
		}

	}

	public static boolean loginExist(String username, String passowrd) throws SQLException, NoSuchAlgorithmException {
		String query = "SELECT * FROM user WHERE email = ? ;";
		// ResultSet rs = connect.connection(query);
		if (DAC.checkEmail(query, username)) {
			// System.out.println("User Doesnt exist");
			JOptionPane.showMessageDialog(null, "User Does not exist");
			return false;
		} else {
			String pass = generateHash(passowrd);
			boolean check = DAC.checkUser(query, username ,pass);
			if (check == false) {
				JOptionPane.showMessageDialog(null, "Wrong Password");
			}
			return check;

		}

	}

	public static void loginNew(String title, String firstname, String lastname, String email, String passowrd,
			String uni) throws NoSuchAlgorithmException, SQLException {
		ArrayList<String> data = new ArrayList<String>();
		String query = "INSERT INTO user VALUES (?,?,?,?,?,?)";
		String pass = generateHash(passowrd);
		data.add(title);
		data.add(firstname);
		data.add(lastname);
		data.add(email);
		data.add(pass);
		data.add(uni);
		DAC.signUp(query, data);

	}

	private static String generateHash(String passowrd) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] hash = digest.digest(passowrd.getBytes());
		return toHex(hash);
	}

	private static String toHex(byte[] hashInBytes) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hashInBytes.length; i++) {
			sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();

	}

}
