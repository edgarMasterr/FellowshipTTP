package com.fellowship.edgar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MyTextAnalyzer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyTextAnalyzer frame = new MyTextAnalyzer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This class creates the UI 
	 * @author: Edgar Roman
	 * @version: v1.0
	 */
	public MyTextAnalyzer() {
		setTitle("TextAnalyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Welcome to the Super Text Analyzer");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(105, 25, 310, 44);
		contentPane.add(lblTitle);
		
		JTextPane txtpnEnterTextTo = new JTextPane();
		txtpnEnterTextTo.setText("Enter text to analyze");
		txtpnEnterTextTo.setBounds(32, 80, 458, 233);
		contentPane.add(txtpnEnterTextTo);
		
		JLabel lblWordCount = new JLabel("Word Count");
		lblWordCount.setBounds(500, 80, 108, 21);
		contentPane.add(lblWordCount);
		
		JLabel lblSentenceCount = new JLabel("Sentence Count");
		lblSentenceCount.setBounds(500, 104, 108, 21);
		contentPane.add(lblSentenceCount);
		
		JLabel lblParagraphCount = new JLabel("Paragraph Count");
		lblParagraphCount.setBounds(500, 133, 108, 21);
		contentPane.add(lblParagraphCount);
		
		JLabel lblBigrams = new JLabel("Bigrams");
		lblBigrams.setBounds(500, 162, 108, 21);
		contentPane.add(lblBigrams);
		
		JLabel lblWordCountRslt = new JLabel("");
		lblWordCountRslt.setBounds(613, 80, 47, 21);
		contentPane.add(lblWordCountRslt);
		
		JLabel lblSentenceCountRslt = new JLabel("");
		lblSentenceCountRslt.setBounds(613, 104, 47, 21);
		contentPane.add(lblSentenceCountRslt);
		
		JLabel lblParagraphCountRslt = new JLabel("");
		lblParagraphCountRslt.setBounds(613, 136, 47, 21);
		contentPane.add(lblParagraphCountRslt);
		
		JLabel lblBigramsRslt = new JLabel("");
		lblBigramsRslt.setBounds(613, 162, 47, 21);
		contentPane.add(lblBigramsRslt);
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(500, 194, 108, 21);
		contentPane.add(lblLanguage);
		
		JLabel lblLanguageRslt = new JLabel("");
		lblLanguageRslt.setBounds(613, 194, 78, 21);
		contentPane.add(lblLanguageRslt);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(500, 238, 177, 210);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("Analyze");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					lblWordCountRslt.setText("");
					lblSentenceCountRslt.setText("");
					lblParagraphCountRslt.setText("");
					lblBigramsRslt.setText("");
					lblLanguageRslt.setText("");
					textArea.setText("");
					
					String thisText = txtpnEnterTextTo.getText();

					if (thisText.isEmpty()) {
						JOptionPane.showMessageDialog(null,
							    "             Enter text",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
					}
					else{
						String[] theResults = getResult(thisText);
						
						lblWordCountRslt.setText(theResults[0]);
						lblSentenceCountRslt.setText(theResults[1]);
						lblParagraphCountRslt.setText(theResults[2]);
						lblBigramsRslt.setText(theResults[3]);
						lblLanguageRslt.setText(theResults[4]);
					
						String[] staticsRslt = getWordsStatics(thisText);
						
						String newLine = "\n";
						textArea.append("Number of words with " + newLine);
						for (int i=0; i<staticsRslt.length; i++) {
							if(staticsRslt[i] != null) {
								textArea.append(i + " Characters are: " + staticsRslt[i] + newLine);				
							}
						}
						
						System.out.println("Process succesful!!!");
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(33, 338, 89, 29);
		contentPane.add(btnNewButton);
	

	}
	
	/**
	 * Method that send the user inputs the the class TextBreakDown to be processed.
	 * @param theText Text to be Analyzed.
	 * @return String with the statics [number of words, number of sentences, number of paragraphs, number of bigrams, language detected]
	 */	
	protected String[] getResult(String enteredText) throws IOException{
		
		String[] results = new String[6];
		TextBreakeDown myTBD = new TextBreakeDown();
		results[0] = myTBD.numberOfWords(enteredText);
		results[1] = myTBD.numberOfSentences(enteredText);
		results[2] = myTBD.numberOfParaphs(enteredText);
		results[3] = myTBD.numbersOfBigrams(enteredText);
		results[4] = myTBD.whatLanguage(enteredText);
		
		return results;
	}

	/**
	 * Method that send the user inputs the the class TextBreakDown to be get words statics.
	 * @param theText Text to be Analyzed.
	 * @return String with the statics for the words classified by length.
	 */	
	protected String[] getWordsStatics(String enteredText) {
		TextBreakeDown myTBD = new TextBreakeDown();
		return myTBD.wordsByLength(enteredText);
	}
}
