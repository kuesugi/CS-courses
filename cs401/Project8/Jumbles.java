import java.io.*;
import java.util.*;

public class Jumbles
{
	public static void main (String[] args) throws Exception
	{	
		if (args.length < 2)
		{
			System.out.println("Enter: java Project8 <dictionary file> <jumbles file>\n");
			System.exit(0);
		}	
	
		BufferedReader dictionary = new BufferedReader (new FileReader (args[0]));
		ArrayList<String> dArr = new ArrayList<String> ();
		while (dictionary.ready())
			dArr.add(dictionary.readLine());
		dictionary.close();
		Collections.sort(dArr);
		
		BufferedReader jumbles = new BufferedReader (new FileReader (args[1]));
		ArrayList<String> jArr = new ArrayList<String> ();
		while (jumbles.ready())
			jArr.add(jumbles.readLine());
		jumbles.close();
		Collections.sort(jArr);
		
		
		ArrayList<String> answer = new ArrayList<String> (); 
		for (String dWord : dArr)
		{
			String cWord = toCanonical(dWord);
			int index = bSearch (answer, cWord);
			
			if (index < 0)
				answer.add(-(index+1), cWord +" "+ dWord);
			else
				answer.set(index, answer.get(index) + " " + dWord);
		}

	for (String jWord : jArr)
		{
			System.out.print(jWord + " ");
			String cJword = toCanonical(jWord);
			int index = bSearchJum (answer,cJword);
			if (index > 0)
			{
				int space = answer.get(index).indexOf(" ");
				System.out.println(answer.get(index).substring(space+1));
			}
			else 
				System.out.println();
		}
		
	}
		
	static int bSearch (ArrayList<String> answer, String key)
	{
		int hi = answer.size() -1, lo = 0;
		while (hi >= lo)
		{
			int mid = (hi-lo)/2 + lo;	
			int space = answer.get(mid).indexOf(" ");
			
			if (answer.get(mid).substring(0,space).equals(key))	return mid;
			int compare = key.compareTo(answer.get(mid));
			if (compare<0)	hi = mid - 1;
			else	lo = mid + 1;
		}
		return -(lo+1);	
	}
	
	static String toCanonical (String d)
	{
		char[] letters = d.toCharArray();
		Arrays.sort(letters);
		return new String (letters);
	}
			
}
