package tp_02;

//TP2 Réseaux et Systèmes Répartis 2021-2022

//Nom:HADJAZI
//Prenom: Mohammed Hisham
//Specialite:   RSSI      Groupe: 01

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class CLIENT_GUI {

	private JFrame frmClientTp;
	private JTextField input1;
	private JTextField input2;
	private JTextField input3;
	static JTextArea txtout;
	private JTextField input4;
	private InetAddress add;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CLIENT_GUI window = new CLIENT_GUI();
					window.frmClientTp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CLIENT_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClientTp = new JFrame();
		frmClientTp.getContentPane().setBackground(new Color(240, 255, 240));
		frmClientTp.setBackground(new Color(102, 205, 170));
		frmClientTp.setTitle("Client TP_02");
		frmClientTp.setBounds(100, 100, 889, 491);
		frmClientTp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClientTp.getContentPane().setLayout(null);

		input1 = new JTextField();
		input1.setText("127.0.0.1");
		input1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 26));
		input1.setHorizontalAlignment(SwingConstants.CENTER);
		input1.setBounds(215, 33, 208, 38);
		frmClientTp.getContentPane().add(input1);
		input1.setColumns(10);

		input2 = new JTextField();
		input2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 26));
		input2.setHorizontalAlignment(SwingConstants.CENTER);
		input2.setColumns(10);
		input2.setBounds(215, 80, 208, 38);
		frmClientTp.getContentPane().add(input2);

		JLabel lblNewLabel = new JLabel("Enter server address :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(35, 33, 159, 29);
		frmClientTp.getContentPane().add(lblNewLabel);

		JLabel lblEnterServerPort = new JLabel("Enter server port :");
		lblEnterServerPort.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterServerPort.setBounds(35, 80, 159, 29);
		frmClientTp.getContentPane().add(lblEnterServerPort);

		JLabel label = new JLabel("");
		label.setBounds(35, 130, 208, 40);
		frmClientTp.getContentPane().add(label);

		JLabel lblResult = new JLabel("Result :");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(12, 182, 114, 29);
		frmClientTp.getContentPane().add(lblResult);

		input3 = new JTextField();
		input3.setFont(new Font("Dialog", Font.BOLD, 20));
		input3.setHorizontalAlignment(SwingConstants.CENTER);
		input3.setBounds(472, 104, 388, 52);
		frmClientTp.getContentPane().add(input3);
		input3.setColumns(10);

		JLabel lblEnterNumbers = new JLabel("What are you searching for ?");
		lblEnterNumbers.setBounds(472, 77, 388, 15);
		frmClientTp.getContentPane().add(lblEnterNumbers);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int SERVER_PORT = Integer.parseInt(input2.getText());
				try {
					add = InetAddress.getByName(input1.getText());
				} catch (UnknownHostException e1) {

					e1.printStackTrace();
					System.out.println("ERROR : " + e1);

				}

				new SwingWorker() {

					@Override
					protected Object doInBackground() throws Exception {

						// Sending and Receiving solution from server

						try {

							EchoClient client = new EchoClient(add, SERVER_PORT);

							String UserName = input4.getText();
							String Search = input3.getText();

							String msg = client.sendEcho(UserName + "," + Search);

							System.out.println(msg);

							txtout.setFont(new Font("Dialog", Font.BOLD, 9));
							txtout.setForeground(new Color(0, 255, 0));
							txtout.append(msg);

						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}

				}.execute();

			}
		});
		btnSend.setBackground(new Color(175, 238, 238));
		btnSend.setBounds(740, 168, 120, 29);
		frmClientTp.getContentPane().add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 223, 825, 213);
		frmClientTp.getContentPane().add(scrollPane);

		txtout = new JTextArea();
		scrollPane.setViewportView(txtout);
		txtout.setForeground(Color.GREEN);
		txtout.setBackground(Color.GRAY);

		JLabel lblEnterUserName = new JLabel("Enter User Name :");
		lblEnterUserName.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterUserName.setBounds(472, 33, 159, 29);
		frmClientTp.getContentPane().add(lblEnterUserName);

		input4 = new JTextField();
		input4.setHorizontalAlignment(SwingConstants.CENTER);
		input4.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 26));
		input4.setColumns(10);
		input4.setBounds(652, 33, 208, 38);
		frmClientTp.getContentPane().add(input4);

	}

	static class EchoClient {

		private DatagramSocket socket;
		private InetAddress address;
		private int port;

		private byte[] buf = new byte[65535];

		public EchoClient(InetAddress adr, int por) throws SocketException, UnknownHostException {
			socket = new DatagramSocket();
			address = adr;
			port = por;
		}

		public String sendEcho(String msg) throws Exception {
			buf = msg.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
			buf = new byte[65535];
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			String received = new String(packet.getData(), 0, packet.getLength());
			return received;
		}

		public void close() {
			socket.close();
		}
	}

}
