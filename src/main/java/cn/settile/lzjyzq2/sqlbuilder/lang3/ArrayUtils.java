package cn.settile.lzjyzq2.sqlbuilder.lang3;

/**
 * 将apache-commons.lange3包的数组工具类源码移到了过来
 * @author Mr.cjd
 */
public class ArrayUtils {
	
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	public static final int[] EMPTY_INT_ARRAY = new int[0];
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
	public static final int INDEX_NOT_FOUND = -1;
	
	private ArrayUtils() {
		throw new IllegalAccessError("Utility class");
	}
	
	public static boolean isEmpty(Object[] arrays){
		return (arrays == null) || (arrays.length == 0);
	}
	
	public static boolean isNotEmpty(Object[] arrays) {
		return !isEmpty(arrays);
	}
	
}