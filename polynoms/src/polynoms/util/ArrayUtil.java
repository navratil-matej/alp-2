package polynoms.util;

public final class ArrayUtil
{
//	public static final <T> void reverse(T[] array)
//	{
//		T swap;
//		for(int i = 0; i < array.length / 2; i++)
//		{
//			swap = array[i];
//			array[i] = array[array.length - 1 - i];
//			array[array.length - 1 - i] = swap;
//		}
//	}

	public static final void reverse(double[] array)
	{
		double swap;
		for(int i = 0; i < array.length / 2; i++)
		{
			swap = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = swap;
		}
	}

	public static final double[] copy(double[] array)
	{
		double[] next = new double[array.length];
		System.arraycopy(array, 0, next, 0, array.length);
		return next;
	}
}
