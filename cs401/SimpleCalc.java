// Demonstrates JPanel, GridLayout and a few additional useful graphical features.
// adapted from an example by john ramirez (cs prof univ pgh)
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleCalc
{
	JFrame window;  // the main window which contains everything
	Container content ;
	JButton[] digits = new JButton[12]; 
	JButton[] ops = new JButton[4];
	JTextField expression;
	JButton equals;
	private JTextField result;
	
	public SimpleCalc()
	{
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		content.setLayout(new GridLayout(2,1)); // 2 row, 1 col
		ButtonListener listener = new ButtonListener();
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3)); // 1 row, 3 col
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");
		
		equals = new JButton("=");
		equals.setFont(new Font("verdana", Font.BOLD, 20 ));
		equals.addActionListener( listener ); 
		
		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(equals);
		topPanel.add(result);
						
		// bottom panel holds the digit buttons in the left sub panel and the operators in the right sub panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2)); // 1 row, 2 col
	
		JPanel digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(4,3));	
		
		for (int i=0 ; i<10 ; i++ )
		{
			digits[i] = new JButton( ""+i );
			digitsPanel.add( digits[i] );
			digits[i].addActionListener( listener ); 
		}
		digits[10] = new JButton( "C" );
		digitsPanel.add( digits[10] );
		digits[10].addActionListener( listener ); 

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		
	
		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(4,1));
		String[] opCodes = { "+", "-", "*", "/" };
		for (int i=0 ; i<4 ; i++ )
		{
			ops[i] = new JButton( opCodes[i] );
			opsPanel.add( ops[i] );
			ops[i].addActionListener( listener ); 
		}
		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );
		
		content.add( topPanel );
		content.add( bottomPanel );
	
		window.setSize( 640,480);
		window.setVisible( true );
		
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component whichButton = (Component) e.getSource();
			
			// NUMBERS
			for (int i=0 ; i<10 ; i++ )
				if (whichButton == digits[i])
					expression.setText( expression.getText() + i );
			
			// OPERATORS: + - * / =	
			if (whichButton.equals(ops[0]))
				expression.setText(expression.getText()+"+");
					
			if (whichButton.equals(ops[1]))
				expression.setText(expression.getText()+"-");
			
			if (whichButton.equals(ops[2]))
				expression.setText(expression.getText()+"*");
			
			if (whichButton.equals(ops[3]))
				expression.setText(expression.getText()+"/");
			
			if (whichButton == equals)
			{
				result.setText ((result.getText()).substring(0,0));
				double outcome = equal (expression.getText());
				result.setText (result.getText() + outcome);
 			}
 		
			//C and CE		
			if (whichButton == digits[10])
			{
				expression.setText((expression.getText()).substring(0, 0));
				result.setText((result.getText()).substring(0,0));
			}	

			if (whichButton==digits[11])
				if ( (expression.getText()).length() == 0)
					expression.setText(expression.getText());
				else  expression.setText((expression.getText()).substring(0, (expression.getText()).length() - 1));					
		}
		
		public double equal(String expr)
		{
			ArrayList<String> operatorList = new ArrayList<String>();
			ArrayList<String> operandList = new ArrayList<String>();
		
			StringTokenizer st = new StringTokenizer( expr,"+-*/", true );  
			while (st.hasMoreTokens())
			{
			String token = st.nextToken();
			if ("+-/*".contains(token))
				operatorList.add(token);
			else
				operandList.add(token);
    		}
		
		ArrayList<Double> operands = new ArrayList<Double>();
		for ( String op : operandList )
			operands.add(Double.parseDouble(op));
		int index;			
		while (operatorList.contains("*") || operatorList.contains("/"))
		{
			int indexOfMul = operatorList.indexOf("*");
			int indexOfDiv = operatorList.indexOf("/");
			if (indexOfMul == -1)			
				index = indexOfDiv;
			else if (indexOfDiv == -1)		
				index = indexOfMul;
			else 
			    index = indexOfMul < indexOfDiv ? indexOfMul : indexOfDiv;
			
			if (operatorList.get(index).equals("*"))
			{
				operands.set(index, operands.get(index)*operands.get(index+1));
				operands.remove (index+1);
				operatorList.remove (index);
			}
			else 
			{
				operands.set(index, operands.get(index)/operands.get(index+1));
				operands.remove (index+1);
				operatorList.remove (index);
			}
		}
		
		while (operatorList.contains("+") || operatorList.contains("-"))
		{
			index = 0;			
			if (operatorList.get(index).equals("+"))
			{
				operands.set(index, operands.get(index)+operands.get(index+1));
				operands.remove (index+1);
				operatorList.remove (index);
			}
			else 
			{
				operands.set(index, operands.get(index)-operands.get(index+1));
				operands.remove (index+1);
				operatorList.remove (index);
			}
		}
		return operands.get(0);
		}
		
	}
	
	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}

