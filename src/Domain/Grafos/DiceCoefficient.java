package Domain.Grafos;

import java.util.Arrays;

/**
*	Dice's Coefficient algorithm
*
*/

public class DiceCoefficient {

	public static double diceCoefficientOptimized(String s, String t)
	{
		if (s == null || t == null) return 0; //Si no hi ha res --> 0
		if (s == t) return 1; //Strings iguals --> 1 (100%)
		if (s.length() < 2 || t.length() < 2) return 0; //Si nomes 1 caràcter i no són iguals --> 0 (evitar excepció)
	 
		// Create the bigrams for string s:
		final int n = s.length()-1;
		final int[] sPairs = new int[n];
		for (int i = 0; i <= n; i++)
			if (i == 0) sPairs[i] = s.charAt(i) << 16;
			else if (i == n) sPairs[i-1] |= s.charAt(i);
			else sPairs[i] = (sPairs[i-1] |= s.charAt(i)) << 16;
	 
		// Create the bigrams for string t:
		final int m = t.length()-1;
		final int[] tPairs = new int[m];
		for (int i = 0; i <= m; i++)
			if (i == 0) tPairs[i] = t.charAt(i) << 16;
			else if (i == m) tPairs[i-1] |= t.charAt(i);
			else tPairs[i] = (tPairs[i-1] |= t.charAt(i)) << 16;
	 
		// Sort the bigram lists:
		Arrays.sort(sPairs);
		Arrays.sort(tPairs);
	 
		// Count the matches:
		int matches = 0, i = 0, j = 0;
		while (i < n && j < m)
		{
			if (sPairs[i] == tPairs[j])
			{
				matches += 2;
				i++;
				j++;
			}
			else if (sPairs[i] < tPairs[j])
				i++;
			else
				j++;
		}
		return (double)matches/(n+m);
	}
}
