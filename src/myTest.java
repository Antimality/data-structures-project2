import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;


public class myTest {

    public static void main(String[] args) {
		
			PerformExp(1);


		
	}
    

    public static void PerformExp(int expNum) {
    	for (int j=1;j<6;j++) {
        	System.out.println("Starting "+j+"th exp-");
        	int n= (int) (Math.pow(3, j+7)-1);
	    	long startTime=0,endTime=0,duration=0, totDur=0;
	    	double size=0, links=0, cuts=0, trees=0;
	    	double totSize=0, totLinks=0, totCuts=0, totTrees=0;
	    	 // Start timing
	    	for (int i =0;i<20;i++) {
	    		FibonacciHeap heap = new FibonacciHeap();
	    		int[] arr = randomArr(n);
	    		FibonacciHeap.HeapNode[] nodeRef = new FibonacciHeap.HeapNode[n];
	    		startTime = System.nanoTime();
	    		for (int k =0; k<n;k++) {
	    			nodeRef[arr[k]-1] = heap.insert(arr[k], null);
	    		}
	    		switch (expNum) {
	    			case 1:
	    				heap.deleteMin();
	    				break;
	    			case 2:
	    				for (int l=0;l<n/2;l++)
	    					heap.deleteMin();
	    				break;
	    			case 3:
	
	    				heap.deleteMin();
	    				int c=n-1;
	    				while (c>31) {
	    					heap.delete(nodeRef[c]);
	    					c--;}
	    					break;
	    				
	    		}
	    		endTime = System.nanoTime();
	    		totDur += endTime - startTime;
	    		totSize += heap.size();
	    		totLinks += heap.totalLinks();
	    		totCuts += heap.totalCuts();
	    		totTrees += heap.numTrees();
	    	}
	    	   // End timing
	    	
	    	duration = totDur/20; 
	    	size = (double)totSize/20;
	    	links = (double)totLinks/20;
	    	cuts = (double)totCuts/20;
	    	trees = (double)totTrees/20;
	    	System.out.println("Averate exec time: " + (double)duration/1000000 + " milliseconds");
	    	System.out.println("Average size " + size );
	    	System.out.println("Average links " + links );
	    	System.out.println("Average cuts " + cuts );
	    	System.out.println("Average trees " + trees );
	    }}
    
    public static int[] randomArr(int n) {
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			numbers.add(i);
		}
		

		Collections.shuffle(numbers, new Random());
		//Collections.reverse(numbers);
		// Convert the list to an array
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = numbers.get(i);
		}

		return array;
	}
   
}