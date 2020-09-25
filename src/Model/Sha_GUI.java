
package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import Application.Cryptogram;
import Application.EllipticPoint;
import Application.KGram;
import Application.keyPair;
import Application.sha3;
import Application.Signature;

/**
 * The main GUI controller for the cryptographic software
 * @author Matthew Molina
 * @author Joseph Rushford
 * @version 11 March 2020
 */
public class Sha_GUI extends JPanel {
	
	/**
	 * serialization ID
	 */
	private static final long serialVersionUID = 1234567890L;
	
	/* the text area shown on the GUI */
	private static JTextArea textArea;
	
	/* the run program button */
	private static JButton runButton;
	
	/* the button group */
	private static ButtonGroup buttGroup;
	
	/* Label describing the output length */
	private static JLabel outLenDesc;
	
	/* Text Area for user input of output length */
	private static JTextArea outLenInput;

	/* Label describing the Z input */
	private static JLabel zDesc;
	
	/* Text Area for user input of the Z */
	private static JTextArea zInput;
	
	/* Label describing Elliptic Point X */
	private static JLabel ellipticPtXDesc;
	
	/* Text Area for user input of their Elliptic Point X */
	private static JTextArea ellipticPtXInput;
	
	/* Label describing Elliptic Point Y */
	private static JLabel ellipticPtYDesc;
	
	/* Text Area for user input of their Elliptic Point Y */
	private static JTextArea ellipticPtYInput;
	
	/* Label describing the S input */
	private static JLabel sDesc;
	
	/* Text Area for user input of the S */
	private static JTextArea sInput;
	
	/* Label describing the Password input */
	private static JLabel passwordDesc;
	
	/* Text Area for user input of their password */
	private static JTextArea passwordInput;
	
	/* Label describing the T input */
	private static JLabel tDesc;
	
	/* Text Area for user input of the T */
	private static JTextArea tInput;
	
	/* Label describing the Data */
	private static JLabel dataDesc;
	
	/* Text Area for user input of their data */
	private static JTextArea dataInput;
	
	/* HashMap used to store the text areas, used when disabling all input text fields */
	private static HashMap<Integer, JTextArea> userInputs;
	
	/* variable to store what selection is by user for service */
	private static String selectedButton = "none";
	
	/* the cryptogram for part 2 made by user input */
	private static Cryptogram p2Crypto;
	
	/* the kgram for part 3 made by user input */
	private static KGram p3Crypto;
	
	/* the keypair generated for user */
	private static keyPair keypair;

	
	/**
	 * The main program.
	 * @param args the arguments.
	 * @throws IOException in case of file issues.
	 */
	public static void main(String[] args) throws IOException{
		createAndShowGui();
	}
	
	/**
	 * Creates and displays the GUI for user.
	 */
	private static void createAndShowGui() {
		JFrame frame = new JFrame("cryptoBytes");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Container pane = frame.getContentPane();
	    pane.setLayout(new BorderLayout(20, 20));
	    Border etched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	    Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);
	    
	    //create grid for input, pass borders as parameters
	    JPanel inputPanel = createDescInputPanel(etched, bottomBorder);
	    pane.add(inputPanel, BorderLayout.CENTER);
	    
	    //creates the text area and adds some padding
	    JPanel textPadding = new JPanel(new BorderLayout(20, 20));
	    textArea = new JTextArea(5, 5);
	    textArea.setBorder(etched);
	    textArea.setLineWrap(true);
	    textArea.setEditable(false);
	    textPadding.add(textArea, BorderLayout.NORTH);
	    pane.add(textPadding, BorderLayout.SOUTH);	 
	    
	    //the panel for the selection buttons
	    JPanel boxPanels = new JPanel();
	    boxPanels.setLayout(new FlowLayout());
	    boxPanels.setBorder(etched);
	    pane.add(addRadioButtons(boxPanels), BorderLayout.NORTH);
	    runButton = createRunButton();
	    boxPanels.add(runButton);
	    
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	/**
	 * Creates the descriptions and text areas for the user's input data.
	 * @param etched A border design
	 * @param bottomBorder A border design
	 * @return Panel with the text area and descriptions of user's input data
	 */
	private static JPanel createDescInputPanel(Border etched, Border bottomBorder) {
	    JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
	    inputPanel.setBorder(etched);
	    outLenDesc = new JLabel("Output Length: e.g. 32, 64, etc.");
	    outLenDesc.setBorder(bottomBorder);
	    outLenInput = new JTextArea(3, 0);
	    outLenInput.setBorder(etched);
	    outLenInput.setLineWrap(true);
	    
	    zDesc = new JLabel("Z: any positive integer.");
	    zDesc.setBorder(bottomBorder);
	    zInput = new JTextArea();
	    zInput.setBorder(etched);
	    zInput.setLineWrap(true);
	    
	    ellipticPtXDesc = new JLabel("Elliptic Point X: any Big Integer, utilized in elliptic curve cryptography");
	    ellipticPtXDesc.setBorder(bottomBorder);
	    ellipticPtXInput = new JTextArea();
	    ellipticPtXInput.setBorder(etched);
	    ellipticPtXInput.setLineWrap(true);
	    
	    ellipticPtYDesc = new JLabel("Elliptic Point Y: any Big Integer, utilized in elliptic curve cryptography");
	    ellipticPtYDesc.setBorder(bottomBorder);
	    ellipticPtYInput = new JTextArea();
	    ellipticPtYInput.setBorder(etched);
	    ellipticPtYInput.setLineWrap(true);
	    
	    sDesc = new JLabel("S: any string, possibly an input email or username");
	    sDesc.setBorder(bottomBorder);
	    sInput = new JTextArea();
	    sInput.setBorder(etched);
	    sInput.setLineWrap(true);
	    
	    passwordDesc = new JLabel("Password: two 2-byte chunks that are space separated in hexadecimal, e.g. A0 4F 65 DE");
	    passwordDesc.setBorder(bottomBorder);
	    passwordInput = new JTextArea();
	    passwordInput.setBorder(etched);
	    passwordInput.setLineWrap(true);
	    
	    tDesc = new JLabel("T: two 2-byte chunks that are space separated in hexadecimal, e.g. A0 4F 65 DE");
	    tDesc.setBorder(bottomBorder);
	    tInput = new JTextArea();
	    tInput.setBorder(etched);
	    tInput.setLineWrap(true);
	    
	    dataDesc = new JLabel("Data: two 2-byte chunks that are space separated in hexadecimal, e.g. A0 4F 65 DE");
	    dataDesc.setBorder(bottomBorder);
	    dataInput = new JTextArea();
	    dataInput.setBorder(etched);
	    dataInput.setLineWrap(true);

	    // Manually input the text areas the user will enter their information
	    userInputs = new HashMap<>();
	    userInputs.put(0, outLenInput);
	    userInputs.put(1, zInput);
	    userInputs.put(2, ellipticPtXInput);
	    userInputs.put(3, ellipticPtYInput);
	    userInputs.put(4, sInput);
	    userInputs.put(5, passwordInput);
	    userInputs.put(6, tInput);
	    userInputs.put(7, dataInput);
	    disableInput();
	    	    
	    inputPanel.add(outLenDesc);
	    inputPanel.add(outLenInput);
	    inputPanel.add(zDesc);
	    inputPanel.add(zInput);
	    inputPanel.add(ellipticPtXDesc);
	    inputPanel.add(ellipticPtXInput);
	    inputPanel.add(ellipticPtYDesc);
	    inputPanel.add(ellipticPtYInput);
	    inputPanel.add(sDesc);
	    inputPanel.add(sInput);
	    inputPanel.add(passwordDesc);
	    inputPanel.add(passwordInput);
	    inputPanel.add(tDesc);
	    inputPanel.add(tInput);
	    inputPanel.add(dataDesc);
	    inputPanel.add(dataInput);
	    return inputPanel;
	}
	
	/**
	 * Creates the run program button.
	 * @return the run program button.
	 */
	private static JButton createRunButton() {
		JButton button = new JButton("Run");
	    button.setPreferredSize(new Dimension(75,35));
	    button.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(final ActionEvent theEvent) {
	    		getSelectedButtonText(buttGroup);
	    		textArea.setText("");
	    		
	    		//switch for whatever service chosen by user on when chooses
	    		//to run the program
	    		switch(selectedButton) {
	    			case "Hash":
	    				textArea.setText(runHash());
	    				break;
	    			
	    			case "Symmetric Cryptogram Encrypt":
	    				textArea.setText(symEncrypt());
	    				break;
	    			
	    			case "Symmetric Cryptogram Decrypt":
	    				textArea.setText(symDecrypt());
	    				break;
	    				
	    			case "Elliptic Key":
	    				textArea.setText(ellipticKey());
	    				break;
	    				
	    			case "Public Key Encrypt":
	    				textArea.setText(pubKeyEncrypt());
	    				break;
	    				
	    			case "Public Key Decrypt":
	    				textArea.setText(pubKeyDecrypt());
	    				break;
	    				
	    			case "Sign Given Password":
	    				textArea.setText(genSig());
	    				break;
	    				
	    			default: 
	    				textArea.setText("No Choice Selected");
	    				break;	
	    		}
	    	}
	    });
		return button;
	}
	
	/**
	 * Creates the radio buttons for user to choose service.
	 * @param thePane the content pane of GUI.
	 * @return the container holding the service buttons.
	 */
	private static Container addRadioButtons(Container thePane) {
		String[] boxLabels = {"Hash", "Symmetric Cryptogram Encrypt", "Symmetric Cryptogram Decrypt", "Elliptic Key",
							  "Public Key Encrypt", "Public Key Decrypt", "Sign Given Password"};
		
		//adds to button group so only one button can be selected by user
		buttGroup = new ButtonGroup();
		for (int i = 0; i < boxLabels.length; i++) {
			JRadioButton button = new JRadioButton(boxLabels[i]);
			buttGroup.add(button);
			thePane.add(button);
			button.addActionListener(new ActionListener() {
		    	@Override
		    	public void actionPerformed(final ActionEvent theEvent) {
		    		enableCorrectInputFromRadioSelection(button.getText());
		    	}});
		}
		return thePane;
	}
	
	/**
	 * Method used to select/de-select specified user input text areas based on the string passed in.
	 * @param buttonText the string on the button, in this case the text is the user-desired function from the radio button.
	 */
	private static void enableCorrectInputFromRadioSelection(String buttonText) {
		disableInput();
		switch(buttonText) {
		case "Hash":
			setEditableAndWhite(outLenInput);
			setEditableAndWhite(sInput);
			setEditableAndWhite(dataInput);
			break;
		
		case "Symmetric Cryptogram Encrypt":
			setEditableAndWhite(dataInput);
			setEditableAndWhite(passwordInput);
			break;
		
		case "Symmetric Cryptogram Decrypt":
			setEditableAndWhite(dataInput);
			setEditableAndWhite(passwordInput);
			setEditableAndWhite(tInput);
			setEditableAndWhite(zInput);
			break;
			
		case "Elliptic Key":
			setEditableAndWhite(passwordInput);
			break;
			
		case "Public Key Encrypt":
			setEditableAndWhite(passwordInput);
			setEditableAndWhite(dataInput);
			break;
			
		case "Public Key Decrypt":
			setEditableAndWhite(passwordInput);
			setEditableAndWhite(dataInput);
			setEditableAndWhite(tInput);
			setEditableAndWhite(ellipticPtXInput);
			setEditableAndWhite(ellipticPtYInput);
			break;
			
		case "Sign Given Password":
			setEditableAndWhite(dataInput);
			setEditableAndWhite(passwordInput);
			break;
			
		default: 
			textArea.setText("No Choice Selected");
			break;	
		}
		
	}
	
	/**
	 * Sets the input text area as editable, and the background color as the default white.
	 * @param inputTextArea desired text area to be altered
	 */
	private static void setEditableAndWhite(JTextArea inputTextArea) {
		inputTextArea.setEditable(true);
		inputTextArea.setBackground(Color.WHITE);
	}
	
	/**
	 * Disables the area for the user to input information.
	 */
	private static void disableInput() {
		userInputs.forEach((key,value) -> {
			value.setEditable(false);
			value.setBackground(Color.LIGHT_GRAY);
		});
	}
	
	/**
	 * Gets the text from the service selected by user via radio button.
	 * @param buttonGroup
	 */
    private static void getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                selectedButton = button.getText();
            }
        }
    }
    
    /**
     * Converts a string to a byte array.
     * @param str the input string.
     * @return the byte array.
     */
    private static byte[] toByteArr(String str) {
      String[] arr = str.split("\\s+");
      byte[] barr = new byte[arr.length];
      for(int i = 0; i < barr.length;i++) {
    	  int in = Integer.parseInt(arr[i], 16);
    	  BigInteger big = BigInteger.valueOf(in);
    	  byte k = big.byteValue();
    	  barr[i] = k;
      }
      return barr;
    }
    
    /**
     * Runs the hashing service.
     * @return the hash
     */
    private static String runHash() {
    	String emp = "";
    	byte[] empty  = emp.getBytes();
    	try {
        	Integer.parseInt(outLenInput.getText());
    	} catch (NumberFormatException e) {
    		textArea.setText("Not a valid integer for your output length");
    	}
    	int outputLength = Integer.parseInt(outLenInput.getText());
    	byte[] barr = toByteArr(dataInput.getText());
    	byte[] hashBoi = sha3.kmacxof256(empty, barr, outputLength/8, sInput.getText());
    	return sha3.getHash(hashBoi);
    }
    
    /**
     * Runs the symmetric encryption service.
     * @return the encryption.
     */
    private static String symEncrypt() {
    	byte[] dataArr = toByteArr(dataInput.getText());
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	p2Crypto = sha3.encryptByteArray(dataArr, pwArr);
    	byte[] cryptArr = p2Crypto.getC();
    	return sha3.getHash(cryptArr);
    }
    
    /**
     * Runs the symmetric decryption service.
     * @return the decrypted data.
     */
    private static String symDecrypt() {
    	//System.out.println(p2Crypto);
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	byte[] dataArr = toByteArr(dataInput.getText());
    	byte[] tArr = toByteArr(tInput.getText());
    	try {
    		Integer.parseInt(zInput.getText());
    	} catch (NumberFormatException e) {
    		textArea.setText("Not a valid integer for your Z.");
    	}
		int z = Integer.parseInt(zInput.getText());
    	Cryptogram crypt = new Cryptogram(z, dataArr, tArr);
    	byte[] decryptArr = sha3.decryptByteArray(pwArr, crypt);
    	String str = sha3.getHash(decryptArr);
    	return formatOutput(str);
    }
    
    /**
     * Runs the elliptic key service.
     */
    private static String ellipticKey() {
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	keypair = sha3.genKeyPair(pwArr);
    	EllipticPoint pubEP = keypair.getPublic();
    	BigInteger priEP = keypair.getPrivate();
    	BigInteger myX = pubEP.getX();
    	BigInteger myY = pubEP.getY();
    	return "PUBLIC KEY\nX: " + myX + "\nY: " + myY + "\n\nPRIVATE KEY\n" + priEP;
    }
    
    /**
     * Runs the public key encryption service.
     * @return the encryption.
     */
    private static String pubKeyEncrypt() {
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	keypair = sha3.genKeyPair(pwArr);
    	byte[] dataArr = toByteArr(dataInput.getText());
    	p3Crypto  = sha3.encryptByteArrayWithKey(dataArr, keypair);
    	byte[] kgramArr = p3Crypto.getC();
    	return sha3.getHash(kgramArr);
    	
    }
    
    /**
     * Runs the public decryption service.
     * @return the decryption.
     */
    private static String pubKeyDecrypt() {
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	byte[] dataArr = toByteArr(dataInput.getText());
    	byte[] tArr = toByteArr(tInput.getText());
    	try {
    		new BigInteger(ellipticPtXInput.getText());
    		new BigInteger(ellipticPtYInput.getText());
    	} catch (NumberFormatException e) {
    		textArea.setText("At least one of your elliptic points are invalid.");
    	}
		BigInteger ellipX = new BigInteger(ellipticPtXInput.getText());
		BigInteger ellipY = new BigInteger(ellipticPtYInput.getText());
    	EllipticPoint ep = new EllipticPoint(ellipX, ellipY);
    	KGram kg = new KGram(ep, dataArr, tArr);
    	byte[] decrypt = sha3.decryptByteArrayWithKey(pwArr, kg);
    	String str = sha3.getHash(decrypt);
    	return formatOutput(str);
    	
    }
    
    /**
     * Runs the sign given password service.
     */
    private static String genSig() {
    	byte[] dataArr = toByteArr(dataInput.getText());
    	byte[] pwArr = toByteArr(passwordInput.getText());
    	Signature sig = new Signature(dataArr, pwArr);
    	byte[] hArr = sig.getH();
    	byte[] zArr = sig.getZ();
    	String h = sha3.getHash(hArr);
    	String zed = sha3.getHash(zArr);
    	return "GENERATED SIGNATURE\n\nH: " + h + "\nZ: " + zed;	
    }
    
    /**
     * Formats the input T: and/or Data: back into 2-long segments whitespace separated.
     * @param str the data.
     * @return the formatted string.
     */
    private static String formatOutput(String str) {
    	StringBuilder sb = new StringBuilder();
    	char[] ch = str.toCharArray();
    	for (int i = 0; i < ch.length; i++) {
    		boolean bool = Character.isLetter(ch[i]);
    		int modulus = i % 2;
    		switch (modulus) {
    		case 0:
    			if(bool) {
    				sb.append(Character.toUpperCase(ch[i]));
    			} else {
    				sb.append(ch[i]);
    			}
    			break;
    			
    		case 1:
    			if(bool) {
    				sb.append(Character.toUpperCase(ch[i]));
    			} else {
    				sb.append(ch[i]);
    			}
    			sb.append(" ");
    			break;
    			
    		default:
    			break;
    		}
    	}
    	String string = sb.toString();
    	return string;
    }
}