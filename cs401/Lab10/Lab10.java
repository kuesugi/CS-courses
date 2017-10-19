import java.io.*;
import java.util.*;

public class Lab10
{
	public static void main (String[] args) throws Exception
	{	
		if (args.length < 2)
		{
			System.out.println("Enter: java Lab10 <dictionary file> <jumbles file>\n");
			System.exit(0);
		}	
	
		BufferedReader dictionary = new BufferedReader (new FileReader (args[0]));
		TreeSet<String> dArr = new 	TreeSet<String> ();
		while (dictionary.ready())
			dArr.add(dictionary.readLine());
		dictionary.close();
		
		BufferedReader jumbles = new BufferedReader (new FileReader (args[1]));
		TreeSet<String> jArr = new 	TreeSet<String> ();
		while (jumbles.ready())
			jArr.add(jumbles.readLine());
		jumbles.close();
		
		TreeMap<String, String> answer = new TreeMap<String,String> (); 
		for (String dWord : dArr)
		{
			String cWord = toCanonical(dWord);
			if (answer.containsKey (cWord))
				answer.put(cWord, answer.get(cWord)+" "+dWord);
			else
				answer.put(cWord, dWord);
		}

		for (String jWord : jArr)
		{
			System.out.print(jWord + " ");
			String cJword = toCanonical(jWord);
			if (answer.containsKey(cJword))
				System.out.println(answer.get(cJword));
			else 
				System.out.println();
		}
		
	}
		
	static String toCanonical (String d)
	{
		char[] letters = d.toCharArray();
		Arrays.sort(letters);
		return new String (letters);
	}
			
}
