package cn.settile.lzjyzq2.sqlbuilder.lang3;

/**
 * 将apache-commons.lange3包的字符工具类源码移到了过来
 * @author Mr.cjd
 */
public class CharSequenceUtils {
	
	private CharSequenceUtils() {
		throw new IllegalAccessError("Utility class");
	}
	
	public static CharSequence subSequence(CharSequence cs, int start) {
		return cs == null ? null : cs.subSequence(start, cs.length());
	}

	public static int indexOf(CharSequence cs, int searchChar, int start) {
		if (cs instanceof String) {
			return ((String) cs).indexOf(searchChar, start);
		}
		int sz = cs.length();
		int begin = start < 0 ? 0 : start;
		for (int i = begin; i < sz; i++) {
			if (cs.charAt(i) == searchChar) {
				return i;
			}
		}
		return -1;
	}
	
	public static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
		return cs.toString().indexOf(searchChar.toString(), start);
	}
	
	public static int lastIndexOf(CharSequence cs, int searchChar, int start) {
		if (cs instanceof String) {
			return ((String) cs).lastIndexOf(searchChar, start);
		}
		int sz = cs.length();
		if (start < 0) {
			return -1;
		}
		int begin = start >= sz ? sz - 1 : start;
		for (int i = begin; i >= 0; i--) {
			if (cs.charAt(i) == searchChar) {
				return i;
			}
		}
		return -1;
	}
	
	public static int lastIndexOf(CharSequence cs, CharSequence searchChar, int start) {
		return cs.toString().lastIndexOf(searchChar.toString(), start);
	}
	
	public static char[] toCharArray(CharSequence cs) {
		if (cs instanceof String) {
			return ((String) cs).toCharArray();
		}
		int sz = cs.length();
		char[] array = new char[cs.length()];
		for (int i = 0; i < sz; i++) {
			array[i] = cs.charAt(i);
		}
		return array;
	}
	
	public static boolean regiMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start,
			int length) {
		if ((cs instanceof String) && (substring instanceof String)) {
			return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
		}
		int index1 = thisStart;
		int index2 = start;
		int tmpLen = length;
		while (tmpLen-- > 0) {
			char c1 = cs.charAt(index1++);
			char c2 = substring.charAt(index2++);
			if (c1 != c2) {
				if (!ignoreCase) {
					return false;
				}
				if ((Character.toUpperCase(c1) != Character.toUpperCase(c2))
						&& (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
					return false;
				}
			}
		}
		return true;
	}
	
}
