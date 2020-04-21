import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
private static final char WILDCARE = '*';
	public static void main(String[] args) {


		Scanner scanner = new Scanner( System.in );

		int T= scanner.nextInt();


		for(int useCase = 0;useCase < T;useCase++) {
			System.out.println(String.format("Case #%s: %s", useCase+1,solve(scanner)));


		}

	}

	private static String solve(Scanner scanner) {

		int N= scanner.nextInt();

		List<String> patterns = new ArrayList<>();

		String prefix ="";
		String suffix ="";
		boolean foundPref = true;
		boolean foundSuff = true;

		for(int i = 0; i< N;i++) {
			String pattern = scanner.next().trim();
			patterns.add(pattern);
			
			if(foundPref && pattern.indexOf(WILDCARE)!=0) {
				prefix = determinePrefix(prefix,pattern.substring(0, pattern.indexOf(WILDCARE)));
				if(prefix.isEmpty()) foundPref=false; 
			}
			if(foundSuff && pattern.lastIndexOf(WILDCARE)!=pattern.length()-1) {
				suffix = determineSuffix(suffix,pattern.substring(pattern.lastIndexOf(WILDCARE)+1, pattern.length()));
				if(suffix.isEmpty()) foundSuff=false; 
			}
		}
		
		if(!foundSuff || !foundPref) return "*";
		
		return generateOutput(patterns, prefix, suffix);


	}

	private static String generateOutput(List<String> patterns, String prefix, String suffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		for(String str: patterns) {
			sb.append(formatPattern(str));
		}
		if(!sb.toString().endsWith(suffix)) sb.append(suffix);
		return sb.toString();
	}
	
	private static String determinePrefix(String prefix, String substring) {
		String max ="";
		String min ="";
		if(prefix.isEmpty()) return substring;
		if(substring.isEmpty()) return prefix;
		
		if(prefix.length() > substring.length()) {
			max = prefix;
			min = substring;
		}
		else {
			max = substring;
			min = prefix;
		}

		
		if(max.startsWith(min)) {
			return max;
		}
		else {
			return "";
		}
		
		
		
	}
	
	private static String determineSuffix(String prefix, String substring) {
		String max ="";
		String min ="";
		if(prefix.isEmpty()) return substring;
		if(substring.isEmpty()) return prefix;
		
		if(prefix.length() > substring.length()) {
			max = prefix;
			min = substring;
		}
		else {
			max = substring;
			min = prefix;
		}

		
		if(max.endsWith(min)) {
			return max;
		}
		else {
			return "";
		}
		
		
		
	}
	
	private static String formatPattern(String str) {

		if(str.indexOf(WILDCARE) == str.lastIndexOf(WILDCARE)) {//Only one * so should be contained in prefix suffix
			return "";
		}
		
		if(str.startsWith("*")) {
			while(!str.isEmpty() && str.charAt(0)==WILDCARE) {
				str = str.replaceFirst("\\*", "");
			}
		}
		else if(str.contains("*")){
			str = str.substring(str.indexOf(WILDCARE)+1,str.length());
		}

		if(str.endsWith("*")) {
			while(str.charAt(str.length()-1)==WILDCARE) {
				str = removeSuffix(str,"*");
			}
		}else if(str.contains("*")){
			str = str.substring(0,str.lastIndexOf(WILDCARE));
		}


		return str.replaceAll("\\*", "A");

	}
	
	public static String removeSuffix(final String s, final String suffix)
	{
		if (s != null && suffix != null && s.endsWith(suffix)){
			return s.substring(0, s.length() - suffix.length());
		}
		return s;
	}

}