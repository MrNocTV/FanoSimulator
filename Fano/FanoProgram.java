import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FanoProgram extends JFrame{
	private JTable outputTable;
	private DefaultTableModel outputTableModel;
	private JTable inputTable;
	private DefaultTableModel inputTableModel;
	private JPanel inputPanel;
	private JPanel outputPanel;
	private JTextField inputNameTextField;
	private JTextField inputProbabilityTextField;
	private JButton encodeButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton clearButton;
	private JComboBox kindOfSourceComboBox;
	private JLabel entropyLabel;
	private JLabel averageLengthLabel;
	private JLabel efficiencyLabel;
	private JTextField valueAtRowTextField;
	private JLabel valueAtRowLabel;
	
	public FanoProgram() {
		super("Fano Program");
		try {
			LookAndFeelInfo[] lafInfos = UIManager.getInstalledLookAndFeels();
			for(LookAndFeelInfo lafInfo : lafInfos) 
				System.out.println(lafInfo.getClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		setLayout(null);
		
		Font font = new Font("Serif", Font.BOLD, 15);
		inputPanel = new JPanel(null);
		inputPanel.setSize(287, 450);
		inputPanel.setLocation(10, 10);
		inputPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GREEN, 2), "Input", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, font, Color.GREEN));
		inputPanel.setBackground(Color.BLACK);
		JLabel inputNameLabel = new JLabel("Name");
		inputNameLabel.setSize(80, 20);
		inputNameLabel.setForeground(Color.GREEN);
		inputNameLabel.setFont(font);
		inputNameLabel.setLocation(10, 25);
		inputNameLabel.setBackground(Color.BLACK);
		inputPanel.add(inputNameLabel);
		inputNameTextField = new JTextField();
		inputNameTextField.setSize(145, 20);
		inputNameTextField.setLocation(130, 25);
		inputPanel.add(inputNameTextField);
		JLabel inputProbabilityLabel = new JLabel("Probability");
		inputProbabilityLabel.setSize(80, 20);
		inputProbabilityLabel.setLocation(10, 55);
		inputProbabilityLabel.setFont(font);
		inputProbabilityLabel.setForeground(Color.GREEN);
		inputProbabilityLabel.setBackground(Color.BLACK);
		inputPanel.add(inputProbabilityLabel);
		JLabel kindOfSourceLabel = new JLabel("Source");
		kindOfSourceLabel.setSize(80,20);
		kindOfSourceLabel.setFont(font);
		kindOfSourceLabel.setForeground(Color.GREEN);
		kindOfSourceLabel.setBackground(Color.BLACK);
		kindOfSourceLabel.setLocation(10,85);
		inputPanel.add(kindOfSourceLabel);
		kindOfSourceComboBox = new JComboBox<String>(new String[]{"X","X.X","X.X.X"});
		kindOfSourceComboBox.setMaximumRowCount(3);
		kindOfSourceComboBox.setSize(145,20);
		kindOfSourceComboBox.setLocation(130, 85);
		kindOfSourceComboBox.setSelectedIndex(0);
		inputPanel.add(kindOfSourceComboBox);
		
		inputProbabilityTextField = new JTextField();
		inputProbabilityTextField.setSize(145, 20);
		inputProbabilityTextField.setLocation(130, 55);
		inputPanel.add(inputProbabilityTextField);
		addButton = new JButton("Add");
		addButton.setSize(90, 35);
		addButton.setLocation(5, 108);
		removeButton = new JButton("Remove");
		removeButton.setSize(90, 35);
		removeButton.setLocation(100,108);
		inputPanel.add(removeButton);
		inputPanel.add(addButton);
		clearButton = new JButton("Clear");
		clearButton.setSize(90, 35);
		clearButton.setLocation(192, 108);
		inputPanel.add(clearButton);
		inputTable = new JTable(new DefaultTableModel(new Object[]{"Name","Probability"}, 0)){
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		inputTable.setGridColor(Color.BLACK);
		inputTable.setFillsViewportHeight(true);
		inputTable.setRowSelectionAllowed(false);
		inputTableModel = (DefaultTableModel) inputTable.getModel();
		JScrollPane inputTableScrollPane = new JScrollPane(inputTable);
		inputTableScrollPane.setSize(265, 250);
		inputTableScrollPane.setLocation(10, 150);
		inputTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(inputTableScrollPane);
		encodeButton = new JButton("Encode");
		encodeButton.setSize(90,40);
		encodeButton.setLocation(100,405);
		inputPanel.add(encodeButton);
		add(inputPanel);
		
		outputPanel = new JPanel(null);
		outputPanel.setSize(287, 450);
		outputPanel.setLocation(300, 10);
		outputPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GREEN, 2), "Output", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, font, Color.GREEN));
		outputPanel.setBackground(Color.BLACK);
		outputTable = new JTable(new DefaultTableModel(new Object[]{"Name", "Probability","Encoded"}, 0)) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		outputTable.setFillsViewportHeight(true);
		outputTable.setGridColor(Color.BLACK);
		outputTable.setRowSelectionAllowed(false);
		outputTableModel = (DefaultTableModel) outputTable.getModel();
		JScrollPane outputTableScrollPane = new JScrollPane(outputTable);
		outputTableScrollPane.setSize(265, 300);
		outputTableScrollPane.setLocation(10, 25);
		outputTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outputPanel.add(outputTableScrollPane);
		valueAtRowLabel = new JLabel("Value At:");
		valueAtRowLabel.setSize(100, 20);
		valueAtRowLabel.setForeground(Color.GREEN);
		valueAtRowLabel.setBackground(Color.BLACK);
		valueAtRowLabel.setFont(font);
		valueAtRowLabel.setLocation(10,330);
		outputPanel.add(valueAtRowLabel);
		valueAtRowTextField = new JTextField();
		valueAtRowTextField.setSize(195, 20);
		valueAtRowTextField.setLocation(80, 330);
		outputPanel.add(valueAtRowTextField);
		entropyLabel = new JLabel("Entropy: ");
		entropyLabel.setSize(200, 20);
		entropyLabel.setLocation(10,360);
		entropyLabel.setFont(font);
		entropyLabel.setForeground(Color.GREEN);
		entropyLabel.setBackground(Color.BLACK);
		//mouse hover effect
		entropyLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				entropyLabel.setForeground(Color.YELLOW);
			}
			
			@Override
			public void mouseExited(MouseEvent event) {
				entropyLabel.setForeground(Color.GREEN);
			}
		});
		outputPanel.add(entropyLabel);
		averageLengthLabel = new JLabel("Avg Length: ");
		averageLengthLabel.setSize(200, 20);
		averageLengthLabel.setLocation(10, 390);
		averageLengthLabel.setFont(font);
		averageLengthLabel.setForeground(Color.GREEN);
		averageLengthLabel.setBackground(Color.BLACK);
		//mouse hover effect
		averageLengthLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				averageLengthLabel.setForeground(Color.YELLOW);
			}
			
			@Override
			public void mouseExited(MouseEvent event) {
				averageLengthLabel.setForeground(Color.GREEN);
			}
		});
		outputPanel.add(averageLengthLabel);
		efficiencyLabel = new JLabel("Efficiency: ");
		efficiencyLabel.setSize(200, 20);
		efficiencyLabel.setLocation(10, 420);
		efficiencyLabel.setFont(font);
		efficiencyLabel.setForeground(Color.RED);
		efficiencyLabel.setBackground(Color.BLACK);
		//mouse hover effect
		efficiencyLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				efficiencyLabel.setForeground(Color.ORANGE);
			}
			
			@Override
			public void mouseExited(MouseEvent event) {
				efficiencyLabel.setForeground(Color.RED);
			}
		});
		outputPanel.add(efficiencyLabel);
		add(outputPanel);
		
		HandlingInputNameAndProbability handlingInputNAP = new HandlingInputNameAndProbability();
		inputProbabilityTextField.addActionListener(handlingInputNAP);
		addButton.addActionListener(handlingInputNAP);
		removeButton.addActionListener(handlingInputNAP);
		clearButton.addActionListener(handlingInputNAP);
		EncodeButtonHandler encodeButtonHandler = new EncodeButtonHandler();
		encodeButton.addActionListener(encodeButtonHandler);
		SearchInputHandler searchInputHandler = new SearchInputHandler();
		valueAtRowTextField.addActionListener(searchInputHandler);
		
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(595, 500);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	/*
	 * 
	 * Handling events 
	 * (functioning code)
	 * 
	 * */
	
	//handling event for input name and probability
	private class HandlingInputNameAndProbability implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String name = inputNameTextField.getText();
			//for adding 
			if(event.getSource() == inputProbabilityTextField || event.getSource() == addButton) {
				//check if name is empty and not duplicated, if probability is valid
				if(!name.equals("")) {
					for(int count = 0; count < inputTable.getRowCount(); ++count) {
						if( ((String)(inputTable.getValueAt(count, 0))).equals(name)) {
							JOptionPane.showMessageDialog(FanoProgram.this, "Duplicated Name");
							return;
						}
					}
					
					try {
						Double probability = Double.parseDouble((String)inputProbabilityTextField.getText() );
						//all input are valid, ready to put into input table
						
						inputTableModel.addRow(new Object[]{name, inputProbabilityTextField.getText()});
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(FanoProgram.this, "Invalid probability");
					}
				}
			}
			
			//for removing
			if(event.getSource() == removeButton) {
				//search the row with name equal name in inputNameTextField and remove it
				for(int count = 0; count < inputTable.getRowCount(); ++count ) {
					if ( ((String)inputTable.getValueAt(count, 0)).equals(name) ) {
						//remove it then get the hell outta here :V 
						inputTableModel.removeRow(count);
						return;
					}
				}
			}
			
			//fore clearing input table
			if(event.getSource() == clearButton) {
				//remove every single row in inputTable (except the heading row)
				clearInputTable();
			}
		}
	}
	
	//handling event for encodeButton
	private class EncodeButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			clearOutputTable();
			int n = inputTable.getRowCount();
			ArrayList<Node> list = new ArrayList<Node>();
			for(int count = 0; count < n; ++count) {
				String name = (String) inputTable.getValueAt(count, 0);
				BigDecimal val = new BigDecimal((String) inputTable.getValueAt(count, 1));
				list.add(new Node(name, val));
			}
			
			Collections.sort(list, new NodeComparator());
			BigDecimal totalProb = BigDecimal.ZERO;
			for(Node node : list)  
				totalProb = totalProb.add(node.val);
			
			//check if total probability = 1
			if(totalProb.compareTo(BigDecimal.ONE) == 0) {
				//start encoding
				//source x
				if(kindOfSourceComboBox.getSelectedIndex() == 0) {
					fano(list, list.size());
					for(Node node : list) {
						outputTableModel.addRow(new Object[]{node.name, node.val.toString(), node.encryptedString});
					}
				}//source x.x
				else if(kindOfSourceComboBox.getSelectedIndex() == 1) {
					ArrayList<Node> tempList = new ArrayList<Node>(n*n);
					for(Node n1 : list) {
						for(Node n2 : list) {
							String name = n1.name+n2.name;
							BigDecimal val = n1.val.multiply(n2.val);
							tempList.add(new Node(name,val));
						}
					}
					Collections.sort(tempList, new NodeComparator());
					fano(tempList, tempList.size());
					for(Node node : tempList) {
						outputTableModel.addRow(new Object[]{node.name, node.val.toString(), node.encryptedString});
					}
				}//source x.x.x
				else if(kindOfSourceComboBox.getSelectedIndex() == 2) {
					ArrayList<Node> tempList = new ArrayList<Node>(n*n);
					for(Node n1 : list) {
						for(Node n2 : list) {
							String name = n1.name+n2.name;
							BigDecimal val = n1.val.multiply(n2.val);
							tempList.add(new Node(name,val));
						}
					}
					ArrayList<Node> finalList = new ArrayList<Node>(n*n*n);
					for(Node n1 : tempList) {
						for(Node n2 : list) {
							String name = n1.name + n2.name;
							BigDecimal val = n1.val.multiply(n2.val);
							finalList.add(new Node(name,val));
						}
					}
					Collections.sort(finalList, new NodeComparator());
					fano(finalList, finalList.size());
					for(Node node : finalList) {
						outputTableModel.addRow(new Object[]{node.name, node.val.toString(), node.encryptedString});
					}
				}
				
				//calculate entropy
				BigDecimal entropy = BigDecimal.ZERO;
				for(int count = 0; count < outputTable.getRowCount(); ++count) {
					Double log_i = logOfBase(2, Double.parseDouble((String)outputTable.getValueAt(count, 1)));
					entropy = entropy.add(BigDecimal.valueOf(Double.parseDouble((String)outputTable.getValueAt(count, 1))).multiply(BigDecimal.valueOf(log_i)));
				}
				entropy = entropy.multiply(BigDecimal.valueOf(-1));
				entropyLabel.setText("Entropy: " + entropy.toString());
				//calculate average length
				BigDecimal avgLength = BigDecimal.ZERO;
				for(int count = 0; count < outputTable.getRowCount(); ++count) {
					avgLength = avgLength.add(BigDecimal.valueOf(Double.parseDouble((String)outputTable.getValueAt(count, 1))).multiply(BigDecimal.valueOf(((String)outputTable.getValueAt(count, 2)).length())));
				}
				averageLengthLabel.setText("Avg Length: " + avgLength.toString());
				//calculate efficiency
				double efficiency = Double.parseDouble(entropy.toString()) / Double.parseDouble(avgLength.toString()) * 100f;
				efficiencyLabel.setText("Efficiency: " + String.format("%.2f", efficiency) + " %");
			} else{
				JOptionPane.showMessageDialog(FanoProgram.this, "Total probability must be 1");
			}
			
		}
	}
	
	//handle searchInput
	private class SearchInputHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String name = valueAtRowTextField.getText();
			if(!name.equals("")) {
				for(int count = 0; count < outputTable.getRowCount(); ++count) {
					if( ((String) outputTable.getValueAt(count, 0)).equals(name) ) {
						valueAtRowTextField.setText((String) outputTable.getValueAt(count, 2));
						return;
					}
				}
			}
		}
	}
	
	private void clearInputTable() {
		while(inputTable.getRowCount() > 0)
			inputTableModel.removeRow(0);
	}
	
	private void clearOutputTable() {
	
		while(outputTable.getRowCount() > 0)
			outputTableModel.removeRow(0);
	}
	
	private double logOfBase(double base, double val) {
		return (Math.log(val) / Math.log(base));
	}
	
	public static void main(String[] args) {
		new FanoProgram();
	}
	
	private class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node n1, Node n2) {
			return n2.val.compareTo(n1.val);
		}
	}
	
	private class Node {
		String name;
		BigDecimal val;
		String encryptedString = "";
		public Node (String name, BigDecimal val) { 
			this.name = name;
			this.val = val;
		}
	}
	
	private static void fano(java.util.List<Node> list, int size) {
		if(size == 2) {
			list.get(0).encryptedString += "0";
			list.get(1).encryptedString += "1";
			return;
		}
		
		BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
		int cutPoint = 0;
		
		BigDecimal sum = BigDecimal.valueOf(0);
		for(int i = 0; i <= size-2; ++i) {
			sum = sum.add(list.get(i).val);
			
			BigDecimal subSum = BigDecimal.valueOf(0);
			for(int j = i+1; j <= size-1; ++j) {
				subSum = subSum.add(list.get(j).val);
			}
			BigDecimal differ = sum.subtract(subSum);
			if(differ.compareTo(BigDecimal.ZERO) < 0) 
				differ = differ.multiply(BigDecimal.valueOf(-1));
			if(differ.compareTo(min) < 0) {
				min = differ;
				cutPoint = i+1;
			}
			
		}
		
		System.out.println("Cut point = " + cutPoint);
		ArrayList<Node> aboveList = new ArrayList<Node>();
		for(int i = 0; i < cutPoint; ++i) {
			list.get(i).encryptedString += "0";
			aboveList.add(list.get(i));
		}
			
		ArrayList<Node> belowList = new ArrayList<Node>();
		for(int j = cutPoint; j < size; ++j) {
			list.get(j).encryptedString += "1";
			belowList.add(list.get(j));
		}
		
		
		if(aboveList.size() > 1) 
			fano(aboveList, aboveList.size());
		if(belowList.size() > 1)
			fano(belowList, belowList.size());
		
	}
}
