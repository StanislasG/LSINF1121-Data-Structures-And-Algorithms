
/**
 * A binary heap where the content is placed in the array `content`.
 *
 * The array `content` represents a tree and is based on the methods we have seen in the course:
 * The ith node of the tree has indices 2*i and 2*i+1 as children.
 *
 * remarks:
 * - This heap uses a 1-indexing, ie. the first index in the array is 1 instead of 0 as usual. This could ease your computations.
 *   By this way, the index O of the array `content` must always stay empty.
 * - You can use the function increaseSize() to double the size of the array `content`, if needed.
 * - The expected complexity is O(log n) for the insertion in the heap.
 */
public class Heap {
    protected int[] content;
    protected int size;

    public Heap(int initSize) {
        size = 0;
        content = new int[initSize];
    }

    /**
     * Doubles the size of the inner storage array
     */
    protected void increaseSize() {
        int[] newContent = new int[content.length*2];
        System.arraycopy(content, 0, newContent, 0, content.length);
        content = newContent;
    }

    /**
     * Add a new value to the heap.
     * @param val value to add
     */
    public void push(int val) {
        if(this.getSize()>=content.length-1) {
            increaseSize();
        }
        // add val, and percolate it up to maintain heap invariant
        size++;
        content[size] = val;
        swim(size);
    }

    //You can add other functions if needed here

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private boolean greater(int i, int j) {
        return content[i]>=content[j];
    }

    private void exch(int i, int j) {
        int swap = content[i];
        content[i] = content[j];
        content[j] = swap;
    }



    /**
     * Returns the content of the inner array
     */
    public int[] getContent() {return content;}

    public int getSize() {return size;}
}
