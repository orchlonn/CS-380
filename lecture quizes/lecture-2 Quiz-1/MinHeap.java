
/**
 * An implementation of a MinHeap.
 * <p>
 * Uses an array to store the keys.
 * @author Orchlon Chinbat (50291063)
 *
 */
public class MinHeap {

	 // - - - - Private Variables - - - -
	
	/**
	 * An integer array that will hold the keys.
	 */
    private int[] keys = null;
    
    
    /**
     *A field which will store the size of the heap.
     */
    private int size = 0;

    
    

    // - - - - Constructors - - - -
    
    /**
     * Initializes an empty heap with the specified capacity.
     * @param capacity Capacity of the heap.
     */
    public MinHeap(int capacity){
        keys = new int[capacity];
    }
    
    /**
     * Initializes a heap with given keys.
     * @param keys Keys to be stored in the heap.
     */
    public MinHeap(int[] keys){
        this.keys = keys;
        size = keys.length;

        for (int i = keys.length / 2 - 1; i >= 0; i--){
            heapify(i);
        }
    }


    // - - - - Getter Functions - - - -
    /**
     * A getter method that returns the capacity of the heap.
     * @return the length of the keys array.
     */
    public int getCapacity(){
        return keys.length;
    }
    
    /**
     * A getter method that returns the size of the heap. 
     * The number of elements stored in the heap.
     * @return the size of the heap.
     */
    public int getSize(){
        return size;
    }


    // - - - - Public Heap-Operations - - - -

    /**
     * Adds a key to the heap.
     * @param key the key to add.
     */
    public void add(int key){
        if (getSize() >= getCapacity()){
            System.out.println("* * * Error: Heap is already full! * * *");
            return;
        }

        keys[size] = key;
        size++;

        moveUp(size - 1);
    }

    /**
     * Returns the minimum key in the heap.
     * @return the minimum key in the heap.
     */
    public int getMin(){
        return keys[0];
    }

    /**
     * Removes the minimum key in the heap.
     * @return the minimum key in the heap.
     */
    public int removeMin(){
        int min = keys[0];

        size--;

        keys[0] = keys[size];
        heapify(0);

        return min;
    }
    
    /**
     * Returns a string representation of the heap.
     * @return a string representation of the heap.
     */
    public String toString(){
      String str = "";
      
      for(int i : keys){
         str += i+", ";      
      }
      
      return str;
    }
       


    // - - - - Private Heap-Operations - - - -

    /**
     * Heapifies the heap.
     * @param index the index to heapify.
     */
    private void heapify(int index){
        while (true){
            int l = left(index);
            int r = right(index);

            if (l >= getSize()){
                break;
            }

            int smallest = l;
            if (r < getSize() && keys[r] < keys[l]){
                smallest = r;
            }

            if (keys[smallest] >= keys[index]){
                break;
            }

            swapKeys(smallest, index);
            index = smallest;
        }
    }

    /**
     * Moves the key up the heap.
     * @param index the index to move up.
     */
    private void moveUp(int index){
        while (index > 0){
            int parInd = parent(index);

            if (keys[parInd] <= keys[index]){
                return;
            }

            swapKeys(parInd, index);
            index = parInd;
        }
    }


    // - - - - Helper Functions - - - -

    /**
     * Returns the left child of the given index.
     * @param index the index to get the left child of.
     * @return the left child of the given index.
     */
    private int left(int index){
        return 2 * index + 1;
    }

    /**
     * Returns the right child of the given index.
     * @param index the index to get the right child of.
     * @return the right child of the given index.
     */
    private int right(int index){
        return 2 * index + 2;
    }

    /**
     * Returns the parent of the given index.
     * @param index the index to get the parent of.
     * @return the parent of the given index.
     */
    private int parent(int index){
        return (index - 1) / 2;
    }

    /**
     * Swaps the keys at the given indices.
     * @param ind1 the index of the first key.
     * @param ind2 the index of the second key.
     */
    private void swapKeys(int ind1, int ind2){
        int tmp = keys[ind1];
        keys[ind1] = keys[ind2];
        keys[ind2] = tmp;
    }
}
