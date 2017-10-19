import java.io.*;
import java.util.*;

public class Project7
{
	public static void main( String args[] ) throws Exception
	{
		if (args.length < 2 )
		{
			System.out.println("\nusage: C:\\> java Project7 set1.txt set2.txt\n\n");
			System.exit(0);
		}
	
		BufferedReader infile1 = new BufferedReader( new FileReader( args[0] ) );
		BufferedReader infile2 = new BufferedReader( new FileReader( args[1] ) );

		String[] set1 = loadSet( infile1 );
		Arrays.sort( set1 );
		String[] set2 = loadSet( infile2 );
		Arrays.sort( set2 );
		printSet( "set1: ",set1 );
		printSet( "set2: ",set2 );

		String[] union = union( set1, set2 );
		Arrays.sort( union );
		printSet( "\nunion: ", union );


		String[] intersection = intersection( set1, set2 );
		Arrays.sort( intersection );
		printSet( "\nintersection: ",intersection );

		String[] difference = difference( set1, set2 );
		Arrays.sort( difference );
		printSet( "\ndifference: ",difference );

		String[] xor = xor( set1, set2 );
		Arrays.sort( xor );
		printSet("\nxor: ", xor );

		System.out.println( "\nSets Echoed after operations.");

		printSet( "set1: ", set1 );
		printSet( "set2: ", set2 );

	}// END MAIN

	// USE AS GIVEN - DO NOT MODIFY
	// CAVEAT: This method will not work *correctly* until you write a working upSize() method.

	static String[] loadSet( BufferedReader infile ) throws Exception
	{
		final int INITIAL_LENGTH = 5;
		int cnt=0;
		String[] set = new String[INITIAL_LENGTH];
		while( infile.ready() )
		{
				if (cnt >= set.length)
					set = upSize( set );
				set[ cnt++ ] = infile.readLine();
		}
		infile.close();
		return downSize( set, cnt );
	}

	// USE AS GIVEN - DO NOT MODIFY
	static void printSet( String caption, String [] set )
	{
		System.out.print( caption );
		for ( String s : set )
			System.out.print( s + " " );
		System.out.println();
	}


	/* ###############################################################
		For each of the following set operations you must execute the following steps:
		1) dimension an array that is just big enough to handle the largest possible set for that operation.
		2) add the appropriate elements to the array as the operation prescribes.
		3) before returning the array, downSize it to the exact size as the number of elements in it.
	*/

	static String[] union( String[] set1, String[] set2 )
	{
		int uCount = 0;
		String[] unionArr = new String[set1.length + set2.length];
		for (int i = 0; i<set1.length; i++)
		{
			unionArr[i] = set1[i];
			++uCount;
		}
		for(int i=0; i<set2.length; i++)
		{
			if ( contains(unionArr, uCount, set2[i]))
				unionArr[uCount++] = set2[i]; 
		}
		return downSize (unionArr, uCount); // change this to return a downSized full array
	}

	static String[] intersection( String[] set1, String[] set2 )
	{
		String[] instArr = new String[set1.length<set2.length ? set1.length : set2.length];			//Math.min(set1.length,set2.length)
		int iCount = 0;
		for (int i=0; i<set1.length; i++)
		{
			if (!contains(set2, set2.length, set1[i]))
				{
				instArr[iCount++] = set1[i];
				}
		}
		return downSize (instArr, iCount); // change this to return a downSized full array
	}

	static String[] difference( String[] set1, String[] set2 )
	{
		String[] diffArr = new String[set1.length];
		int dCount = 0;
		for (int i=0; i<set1.length; i++)
		{
			if (contains(set2, set2.length, set1[i]))
				{
				diffArr[dCount++] = set1[i];
				}
		}		
		return downSize (diffArr, dCount); // change this to return a downSized full array
	}

	static String[] xor( String[] set1, String[] set2)
	{
		String[] xorArr = difference (union(set1,set2), intersection(set1,set2));
		return xorArr;// change this to return a downSized full array
	}
	
	static boolean contains (String[] a, int cnt, String key)
	{
		for(int i=0; i<cnt; i++)
		{
			if (a[i].equals(key))
				return false;
		}
		return true;
	} 
	
	// return an array of length newSize with all data from the old array stored in the new array
	static String[] upSize( String[] old )
	{
		String[] upArr = new String[old.length*2];
		for(int i=0; i<=old.length-1; i++)
		{	
			upArr[i] = old[i];
		}
		return upArr; // you change accordingly
	}

	// return an array of length cnt with all data from the old array stored in the new array
	static String[] downSize( String[] old, int cnt )
	{
		String[] downArr = new String[cnt];
		for(int i=0; i<=cnt-1; i++)
		{
			downArr[i]=old[i];
		}
		return downArr; // you change accordingly
	}

} // END PROJECT7
