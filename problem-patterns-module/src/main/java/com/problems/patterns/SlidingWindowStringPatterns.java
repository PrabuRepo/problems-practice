package com.problems.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SlidingWindowStringPatterns {

	/***************************** Sliding Window: String ******************************/
	/* Window Sliding Technique: 
	 * 	This technique shows how a nested for loop in few problems can be converted to single for loop and hence reducing the time 
	 *  complexity.
	 *  Sliding Window String Pattern: This pattern needs,
	 *  	- Two pointers(l & r) to iterate the string from 0th index to n-1
	 *  	- Fixed hash array or map to store the required chars for the given logic
	 *  	- For few problems counter variable can be useful to find the window
	 *  
	 *  This Pattern can be solved by two approaches:
	 *  	1.Using Fixed-Size Frequency Arrays:
	 *  	  Uses HashMap to stores char -> count of t
	 *  		ASCII: Uses 7 bits ->  2^7=128 characters. int[128] array to track character frequencies 
	 *          Extended ASCII: 8 bits -> 2^8=256 characters (includes accented letters, some symbols)/  int[256] array to track character frequencies 
	 *  	  Time Complexity: O(n) - Each character is processed at most twice (once by right, once by left)
	 *        Space Complexity: O(1) - Since the frequency arrays are of size 128 or 256
	 *        Pros: Very fast due to use of arrays -> constant-time lookups/updates. No overhead of object boxing or hashing
	 *        Cons: Only suitable for ASCII (or Unicode up to a fixed set). Less readable (magic numbers like 128)
	 *        This is faster than using Map because:
	 *        	Array index lookup is constant-time
	 *        	Arrays are memory-contiguous (better cache locality)
	 *        	No need for hashing or boxing (primitive vs object overhead)
	 *  	2.Using HashMap
	 *  	  Uses HashMap to stores char -> count of t
	 *  	  Time Complexity: O(n) - Each character is processed at most twice (once by right, once by left). But operations on HashMap are O(1) amortized
	 *  	  Space Complexity: O(k) - k = number of unique characters in t
	 *        Pros: Works for any Unicode characters. More readable and flexible
	 *        Cons: Slower in practice due to: HashMap overhead (hashing, boxing/unboxing). No cache locality (HashMap spread in memory)
	 *     
	 *     Use Array when:
	 *     	  You're working with known, limited charset (ASCII or lowercase only). Performance is critical
	 *     Use HashMap when:
	 *     	  Characters may be outside ASCII (e.g. Unicode or case-sensitive). Readability or flexibility is more important
	 */

	/* Minimum Window Substring/Smallest window in a string containing all the char of another string:
	 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in
	 * complexity O(n). 
	 * Example: Input: S = "ADOBECODEBANC", T = "ABC" Output: "BANC"
	 */
	//Time:O(n), Constant space: O(128)
	public String minWindow1(String str, String pat) {
		int[] hash = new int[128];
		for (char c : pat.toCharArray())
			hash[c]++;

		int l = 0, r = 0, counter = 0, minLeft = -1, minWindow = Integer.MAX_VALUE;
		while (r < str.length()) {
			char c1 = str.charAt(r);
			// Increase the counter only if char is present in pattern
			if (hash[c1] > 0) counter++;
			hash[c1]--;
			// Move 'l' from zero and update minwindow & the string map
			while (counter == pat.length()) {
				if ((r - l + 1) < minWindow) {
					minWindow = r - l + 1;
					minLeft = l;
				}
				char c2 = str.charAt(l);
				hash[c2]++;
				if (hash[c2] > 0) counter--;
				l++;
			}
			r++;
		}

		return minLeft != -1 ? str.substring(minLeft, minLeft + minWindow) : "";
	}

	//Using Map: In this logic, map stores only pat chars. So space complexity is O(k), where k is pat length
	public String minWindow2(String str, String pat) {
		int len1 = str.length(), len2 = pat.length();
		if (len1 == 0 || len2 == 0 || (len1 < len2)) return "";

		Map<Character, Integer> patMap = new HashMap<>();
		for (char ch : pat.toCharArray()) {
			patMap.put(ch, patMap.getOrDefault(ch, 0) + 1);
		}

		int l = 0, r = 0, minWindow = Integer.MAX_VALUE, minLeft = -1, counter = 0;
		// Iterate "str" string one by one and consider only char present in the "pat" string.
		while (r < len1) {
			char ch = str.charAt(r);

			if (patMap.get(ch) != null && patMap.get(ch) > 0) counter++;
			//Reduce char count in map, only for "pat" chars
			if (patMap.get(ch) != null) patMap.put(ch, patMap.get(ch) - 1);

			// Move 'l' from zero and update minwindow & the string map
			while (counter == len2) {
				if ((r - l + 1) < minWindow) {
					minWindow = r - l + 1;
					minLeft = l;
				}
				// Decrease the char count in strMap
				ch = str.charAt(l);
				if (patMap.get(ch) != null) {
					patMap.put(ch, patMap.get(ch) + 1);
					if (patMap.get(ch) > 0) counter--;
				}
				l++;
			}
			r++;
		}
		return minLeft != -1 ? str.substring(minLeft, minLeft + minWindow) : "";
	}

	/*Find All Anagrams in a String/Anagram Pattern Search:
	 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s. Strings consists of
	 * lowercase English letters only and the length of both strings s and p will not be larger than 20,100. The order
	 * of output does not matter. 
	 * Example 1: Input: s: "cbaebabacd" p: "abc" Output: [0, 6] Explanation: The substring with start index = 0 is "cba", 
	 * which is an anagram of "abc". The substring with start index = 6 is "bac", which is an anagram of "abc".
	 */
	public List<Integer> findAnagrams1(String s, String p) {
		int l = 0, r = 0, counter = 0;
		int[] hash = new int[128];
		List<Integer> result = new ArrayList<>();

		for (char ch : p.toCharArray())
			hash[ch]++;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] > 0) counter++;
			hash[c1]--;

			while (counter == p.length()) {
				// This condition make sure that string is continuous substring
				if ((r - l + 1) == p.length()) {
					result.add(l);
				}

				char c2 = s.charAt(l);
				hash[c2]++;
				if (hash[c2] > 0) counter--;
				l++;
			}
			r++;
		}

		return result;
	}

	public List<Integer> findAnagrams2(String s, String p) {
		int[] hash = new int[26];
		for (char c : p.toCharArray())
			hash[c - 'a']++;

		int l = 0, r = 0;
		List<Integer> result = new ArrayList<>();

		while (r < s.length()) {
			int index = s.charAt(r) - 'a';
			hash[index]--;

			while (hash[index] < 0) {
				hash[s.charAt(l) - 'a']++;
				l++;
			}

			if (r - l + 1 == p.length()) {
				result.add(l);
				hash[s.charAt(l) - 'a']++;
				l++;
			}
			r++;
		}
		return result;
	}

	/* Longest Substring Without Repeating Characters:
	 * Given a string, find the length of the longest substring without repeating characters. 
	 * Example 1: Input:"abcabcbb" Output: 3 Explanation: The answer is "abc", with the length of 3. 
	 * Example 2: Input: "pwwkew" Output: 3 Explanation: The answer is "wke", with the length of 3. 
	 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
	 */
	// Approach1: using Array to store the data
	public int lengthOfLongestSubstring1(String s) {
		int l = 0, r = 0, maxLen = 0;
		int[] hash = new int[128];
		while (r < s.length()) {
			char c1 = s.charAt(r);
			hash[c1]++;
			while (hash[c1] > 1) {
				hash[s.charAt(l)]--;
				l++;
			}
			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}
		return maxLen;
	}

	//Using Set to store the data
	public int lengthOfLongestSubstring2(String s) {
		if (s == null || s.length() == 0) return 0;

		int l = 0, r = 0, n = s.length(), max = 0;
		Set<Character> set = new HashSet<>();
		while (r < n) {
			char ch = s.charAt(r);
			if (!set.contains(ch)) {
				set.add(ch);
				max = Math.max(max, set.size());
				r++;
			} else {
				while (set.contains(ch)) {
					set.remove(s.charAt(l++));
				}
			}
		}

		return max;
	}

	//Approach2: Using Map and index
	public int lengthOfLongestSubstring3(String s) {
		if (s == null || s.length() == 0) return 0;

		int l = 0, r = 0, n = s.length(), maxLen = 0;
		Map<Character, Integer> map = new HashMap<>();

		while (r < n) {
			char ch = s.charAt(r);
			//Note: Test input "abba" to understand this condition
			if (map.containsKey(ch) && map.get(ch) >= l) {
				l = map.get(ch) + 1;
			}
			map.put(ch, r);
			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	/*
	 * Longest Substring with At Most Two Distinct Characters:
	 * Given a string, find the longest substring that contains only two unique characters. For example, given "abcbbbbcccbdddadacb",
	 * the longest substring that contains 2 unique character is "bcbbbbcccb".
	 */
	public int lengthOfLongestSubstringTwoDistinct(String s) {
		int[] hash = new int[128];
		int l = 0, r = 0, maxLen = 0, counter = 0;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] == 0) counter++;
			hash[c1]++;

			while (counter > 2) {
				char c2 = s.charAt(l);
				hash[c2]--;
				if (hash[c2] == 0) counter--;
				l++;
			}

			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	public int lengthOfLongestSubstringTwoDistinct2(String s) {
		int maxLen = 0, l = 0, r = 0;
		HashMap<Character, Integer> map = new HashMap<>();

		while (r < s.length()) {
			char c1 = s.charAt(r);
			map.put(c1, map.getOrDefault(c1, 0) + 1);

			while (map.size() > 2) {
				char c2 = s.charAt(l);
				int count = map.get(c2);
				if (count == 1) {
					map.remove(c2);
				} else {
					map.put(c2, count - 1);
				}
				l++;
			}
			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	/*
	 * Longest Substring with At Most K Distinct Characters:
	 * Given a string s and an integer k, return the length of the longest substring that contains at most k distinct characters.
	 * Sample:
	 * 	s="eceba", k=2, output=3
	 *  s="aa", k=1, output=2
	 *  s="abcadcacacaca", k=3, output=11
	 */
	public int lengthOfLongestSubstringKDistinct1(String s, int k) {
		int[] hash = new int[128];
		int l = 0, r = 0, maxLen = 0, counter = 0;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] == 0) counter++;
			hash[c1]++;

			while (counter > k) {
				char c2 = s.charAt(l);
				hash[c2]--;
				if (hash[c2] == 0) counter--;
				l++;
			}

			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	public int lengthOfLongestSubstringKDistinct2(String s, int k) {
		int maxLen = 0, l = 0, r = 0;
		HashMap<Character, Integer> map = new HashMap<>();

		while (r < s.length()) {
			char c1 = s.charAt(r);
			map.put(c1, map.getOrDefault(c1, 0) + 1);

			while (map.size() > k) {
				char c2 = s.charAt(l);
				int count = map.get(c2);
				if (count == 1) {
					map.remove(c2);
				} else {
					map.put(c2, count - 1);
				}
				l++;
			}
			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	/* Longest Repeating Character Replacement: 
	 * Given a string that consists of only uppercase English letters, you can replace any letter in the string with
	 * another letter at most k times. Find the length of a longest substring containing all repeating letters you can
	 * get after performing the above operations. 
	 * Example: Input: s = "AABABBA", k = 1 Output: 4 Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA". 
	 * The substring "BBBB" has the longest repeating letters, which is 4.
	 */
	// Sliding Window approach
	// Time Complexity: O(n)
	public int characterReplacement1(String s, int k) {
		int[] charCount = new int[26];
		int l = 0, r = 0, maxCharCount = 0, maxLength = 0;
		while (r < s.length()) {
			char ch = s.charAt(r);
			charCount[ch - 'A']++;
			maxCharCount = Math.max(maxCharCount, charCount[ch - 'A']);
			while ((r - l + 1) - maxCharCount > k) {
				charCount[s.charAt(l) - 'A']--;
				l++;
			}
			maxLength = Math.max(maxLength, r - l + 1);
			r++;
		}
		return maxLength;
	}

	// Time Complexity: O(26n)
	public int characterReplacement2(String s, int k) {
		int n = s.length();
		if (n < k) return 0;

		// Added this to improve the performance; Solution will work without this
		int[] countArray = new int[26];
		for (int i = 0; i < n; i++)
			countArray[s.charAt(i) - 'A']++;

		int maxLen = 0;
		for (int i = 0; i < 26; i++) {
			char ch = (char) (i + 'A'); // Get the char one by one from A to Z;(input has only upper case)
			int l = 0, r = 0, count = 0;

			if (countArray[ch - 'A'] == 0) continue;

			while (r < n) {
				if (s.charAt(r) != ch) // if char mismatches, increase the count
					count++;

				while (k < count) { // Slide the window
					if (s.charAt(l) != ch) // Here, id char mismatches decrease the count
						count--;
					l++;
				}

				maxLen = Math.max(maxLen, r - l + 1); // Find the max length
				r++;
			}
		}

		return maxLen;
	}
}