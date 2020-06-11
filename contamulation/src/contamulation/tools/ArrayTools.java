package contamulation.tools;

import java.util.List;
import java.util.Random;

public class ArrayTools
{
	public static <T> void shuffle(T[] array, Random rand)
	{
		for(int i = array.length-1; i > 0; i--)
		{
			int ind = rand.nextInt(i+1);
			T temp = array[ind];
			array[ind] = array[i];
			array[i] = temp;
		}
	}
	
	public static int binsearchLessEqual(double[] array, double target)
	{
		int low = 0;
		int high = array.length;
		int index = high - 1;
	     
	    while (low <= high)
	    {
	        int mid = (low + high) / 2;
	        if (mid < array.length && array[mid] <= target)
	        {
	        	index = mid;
	            low = mid + 1;
	        }
	        else 
	            high = mid - 1;
	    }
	    
	    return index;
	}
	
	public static int binsearchLessEqual(List<Double> list, double target)
	{
		int low = 0;
		int high = list.size();
		int index = high - 1;
	     
	    while (low <= high)
	    {
	        int mid = (low + high) / 2;
	        if (mid < list.size() && list.get(mid) <= target)
	        {
	        	index = mid;
	            low = mid + 1;
	        }
	        else 
	            high = mid - 1;
	    }
	    
	    return index;
	}
}
