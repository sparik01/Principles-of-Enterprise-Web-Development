package com.sparikh6.week6homework;

/**
 * Sheetal Parikh
 * Homework 6
 * Main Runner Class
 * 
 */

import com.sparikh6.week6homework.Rates.HIKE;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ItemEvent;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import java.awt.SystemColor;


public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btnGetCost;
	private JComboBox cBTour;
	private JComboBox cBDuration;
	private JDateChooser startDate;
	private JLabel lblDuration;
	private JLabel lblTour;
	private JLabel lblBeginningDate;
	private JLabel lblCost;
	private JLabel lblTitle;
	private JLabel lblCostDisplay;
	
	static Rates rate;
	private JLabel lblDateNote;
	int hike_id;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		
		//creating the JFrame
		setFont(new Font("Elephant", Font.PLAIN, 12));
		setTitle("Beartooth Hiking Tour Cost");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 411);
		
		//creating the JPanel
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{130, 42, 92, 91, 57, 27, 0};
		gbl_contentPane.rowHeights = new int[]{21, 32, 26, 54, 28, 52, 28, 31, 21, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		//creating label that will serve as title of the window
		lblTitle = new JLabel("Tour Cost Estimator");
		lblTitle.setFont(new Font("Elephant", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridwidth = 6;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		contentPane.add(lblTitle, gbc_lblTitle);
		
		//creating label telling the user to select the date
		lblBeginningDate = new JLabel("Select Date* :");
		lblBeginningDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblBeginningDate = new GridBagConstraints();
		gbc_lblBeginningDate.gridwidth = 2;
		gbc_lblBeginningDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeginningDate.gridx = 0;
		gbc_lblBeginningDate.gridy = 2;
		contentPane.add(lblBeginningDate, gbc_lblBeginningDate);
		
		//creating the JDateChooser from toedter.com
		startDate = new JDateChooser();
		GridBagConstraints gbc_startDate = new GridBagConstraints();
		gbc_startDate.anchor = GridBagConstraints.NORTH;
		gbc_startDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_startDate.insets = new Insets(0, 0, 5, 5);
		gbc_startDate.gridwidth = 2;
		gbc_startDate.gridx = 2;
		gbc_startDate.gridy = 2;
		contentPane.add(startDate, gbc_startDate);
		
		//creating label telling the user more info about the current season
		lblDateNote = new JLabel("*Current Season:  June 1, 2007 to September 30, 2020 ");
		lblDateNote.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		GridBagConstraints gbc_lblDateNote = new GridBagConstraints();
		gbc_lblDateNote.anchor = GridBagConstraints.NORTH;
		gbc_lblDateNote.gridwidth = 5;
		gbc_lblDateNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateNote.gridx = 1;
		gbc_lblDateNote.gridy = 3;
		contentPane.add(lblDateNote, gbc_lblDateNote);
		
		//creating label telling the user to select an available tour
		lblTour = new JLabel("Available Tours :");
		lblTour.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblTour = new GridBagConstraints();
		gbc_lblTour.gridwidth = 2;
		gbc_lblTour.insets = new Insets(0, 0, 5, 5);
		gbc_lblTour.gridx = 0;
		gbc_lblTour.gridy = 4;
		contentPane.add(lblTour, gbc_lblTour);
		
		//creating JComboBox that will be the drop down box of available tours
		cBTour = new JComboBox();
		cBTour.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		cBTour.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cBTour.setModel(new DefaultComboBoxModel(new String[] {"Choose Tour...", "HELLROARING", "GARDINER", "BEATEN"}));
		GridBagConstraints gbc_cBTour = new GridBagConstraints();
		gbc_cBTour.anchor = GridBagConstraints.NORTH;
		gbc_cBTour.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBTour.insets = new Insets(0, 0, 5, 5);
		gbc_cBTour.gridwidth = 2;
		gbc_cBTour.gridx = 2;
		gbc_cBTour.gridy = 4;
		contentPane.add(cBTour, gbc_cBTour);
		
		//creating JComboBox that will be the drop down box of durations
		cBDuration = new JComboBox();
		cBDuration.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		cBDuration.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cBDuration.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4", "5", "7"}));
		GridBagConstraints gbc_cBDuration = new GridBagConstraints();
		gbc_cBDuration.anchor = GridBagConstraints.NORTHWEST;
		gbc_cBDuration.insets = new Insets(0, 0, 5, 5);
		gbc_cBDuration.gridx = 2;
		gbc_cBDuration.gridy = 6;
		contentPane.add(cBDuration, gbc_cBDuration);
		
		//creating telling the label that will tell the user where to find the total calculated cost
		lblCost = new JLabel("Total Cost : ");
		lblCost.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblCost = new GridBagConstraints();
		gbc_lblCost.gridwidth = 2;
		gbc_lblCost.anchor = GridBagConstraints.NORTH;
		gbc_lblCost.insets = new Insets(0, 0, 0, 5);
		gbc_lblCost.gridx = 2;
		gbc_lblCost.gridy = 8;
		contentPane.add(lblCost, gbc_lblCost);
		
		//creating blank label where the calculated cost will populate 
		lblCostDisplay = new JLabel("");
		lblCostDisplay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblCostDisplay = new GridBagConstraints();
		gbc_lblCostDisplay.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCostDisplay.gridwidth = 2;
		gbc_lblCostDisplay.gridx = 4;
		gbc_lblCostDisplay.gridy = 8;
		contentPane.add(lblCostDisplay, gbc_lblCostDisplay);
		
		//creating label telling user to select a duration
		lblDuration = new JLabel("Duration (days) :");
		lblDuration.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblDuration = new GridBagConstraints();
		gbc_lblDuration.gridwidth = 2;
		gbc_lblDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuration.gridx = 0;
		gbc_lblDuration.gridy = 6;
		contentPane.add(lblDuration, gbc_lblDuration);
		
		//creating JButton that when clicked will calculate the cost
		btnGetCost = new JButton("Get Total Cost");
		btnGetCost.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		btnGetCost.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnGetCost = new GridBagConstraints();
		gbc_btnGetCost.anchor = GridBagConstraints.NORTH;
		gbc_btnGetCost.insets = new Insets(0, 0, 5, 0);
		gbc_btnGetCost.gridwidth = 3;
		gbc_btnGetCost.gridx = 3;
		gbc_btnGetCost.gridy = 7;
		contentPane.add(btnGetCost, gbc_btnGetCost);
		
		////////////////////////////////////////
		//creating listeners to handle events
		////////////////////////////////////////
		
		//creating dynamic drop down box for durations so available durations based on selected tour
				cBTour.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						ArrayList<String> array = new ArrayList<>();
						Iterator<String> iter = array.iterator();
						
						//if "Choose Tour" is selected there will be no durations to choose from 
						if (cBTour.getSelectedItem().equals("Choose Tour...")) {
							
							hike_id = -1;
							
							cBDuration.removeAllItems();
							array.add("");
							iter = array.iterator();
							while(iter.hasNext()) {
								
								cBDuration.addItem(iter.next());
							}
						
						}
						else if (cBTour.getSelectedItem().equals("HELLROARING")) {
			                
							hike_id = 1;
							
							//selecting Hellroaring Plateau will populate durations 2, 3 and 4
							cBDuration.removeAllItems();
							array.add("2");
							array.add("3");
							array.add("4");
							iter = array.iterator();
							while(iter.hasNext()) {
								
								cBDuration.addItem(iter.next());
							}
							
						} 
						else if (cBTour.getSelectedItem().equals("GARDINER")) {
							
							hike_id = 0;
							
							//selecting Gardiner will populate durations 3 and 5
							cBDuration.removeAllItems();
							array.add("3");
							array.add("5");
							iter = array.iterator();
							while(iter.hasNext()) {
								
								cBDuration.addItem(iter.next());
							}
							
						} 
						else if (cBTour.getSelectedItem().equals("BEATEN")) {
							
							hike_id = 2;
							
							//selecting Beaten will populate durations 3 and 5
							cBDuration.removeAllItems();
							array.add("5");
							array.add("7");
							iter = array.iterator();
							while(iter.hasNext()) {
								
								cBDuration.addItem(iter.next());
							}
						}
					}
				});
		
		//listener for if Get Total Cost button is selected
		btnGetCost.addActionListener(new ActionListener() {
			
			//initializing socket and input stream
			private Socket socket = null;
			private PrintWriter out = null;
			private BufferedReader in = null;
			private String getRate = null;
			private double serverCost = 0;
			private String serverText;
		
			public void actionPerformed(ActionEvent e) {
			
				try {
					// getting the chosen duration
					int duration = Integer.parseInt(cBDuration.getSelectedItem().toString());
					
						//getting integer value of year from chosen date
						String yearString = new SimpleDateFormat("yyyy").format(startDate.getDate());
						int beginYear = Integer.parseInt(yearString);
						
						//getting integer value of day from chosen date
						String dayString = new SimpleDateFormat("dd").format(startDate.getDate());
						int beginDay = Integer.parseInt(dayString);
						
						//getting integer value of month from chosen date
						String monthString = new SimpleDateFormat("MM").format(startDate.getDate());
						int beginMonth = Integer.parseInt(monthString);
						
						//System.out.println("Beginning Year: " + beginYear);
						//System.out.println("Beginning Day: " + beginDay);
						//System.out.println("Beginning Month: " + beginMonth);
							
						try
						{	
							//creating socket connecting to class server
							socket = new Socket("web7.jhuep.com",20025);
							//System.out.println("Connected");
							
							//output stream sending data to socket
							out = new PrintWriter(socket.getOutputStream(), true);
							
							//input stream reading the server response
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							
							//creating output stream to server
							String serverInput = hike_id + ":" + beginYear + ":" + beginMonth + ":" + beginDay + ":" + duration;
							
							//sending output stream to server
							out.println(serverInput);
							
							//reading in server output
							getRate = in.readLine();
							
							//closing connection		
							out.close();
							in.close();
							socket.close();
							
							//process return string into an array
							String[] serverOutput = getRate.split(":");
							//1st index contains the total reservation cost
							serverCost = Double.parseDouble(serverOutput[0]);
							//2nd index contains the additional text included in the return string
							serverText = serverOutput[1];
							System.out.println(serverCost);
							System.out.println(serverText);
							
							if(serverCost > 0) {
								
								//displaying total reservation cost to GUI
								lblCostDisplay.setText(String.format("$%.2f", serverCost));
							
									} else {
										JOptionPane.showMessageDialog(new JFrame(),"Please select a date within the current season.", 
												"Invalid Date", JOptionPane.ERROR_MESSAGE);	
								
										}
							
							} catch (UnknownHostException eu) {
								JOptionPane.showMessageDialog(new JFrame(),"Dont know about the host: web7.jhuep.com",
										"Unknown Host", JOptionPane.ERROR_MESSAGE);
								System.exit(1);
								
							} catch (IOException eu) {
								JOptionPane.showMessageDialog(new JFrame(),"Couldn't get the I/O for the connection to:"
										+ "web7.jhuep.com","Unable to Connect", JOptionPane.ERROR_MESSAGE);
								System.exit(1);
								
							} 
						
				//exception if "Choose Tour..." selected as an available tour
				} catch(Exception exe ) {
					JOptionPane.showMessageDialog(new JFrame(),"Please choose from HELLROARING, "
							+ "GARDINER, or BEATEN", "Invalid Tour", JOptionPane.ERROR_MESSAGE);
				}
							
				}  
				
			});    

		}
}
