// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {
        super();
    }

    public A2DynamicMem(int size) {
        super(size);
    }

    public A2DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees.
    // No changes should be required in the A1DynamicMem functions.
    // They should work seamlessly with the newly supplied implementation of BSTrees
    // and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test
    // using BSTrees and AVLTrees.

    public void Defragment() {
        Dictionary temp;
        if (this.type == 2) {
            temp = new BSTree();
        } else if (this.type == 3) {
            temp = new AVLTree();
        } else {
            return;
        }
        Dictionary v;
        // Iterate the freeBlk and insert into temp indexed on the basis of address
        for (v = freeBlk.getFirst(); v != null; v = v.getNext()) {
            temp.Insert(v.address, v.size, v.address);
        }
        Dictionary prev = temp.getFirst();
        if (prev != null) {
            Dictionary next = prev.getNext();
            // Iterate over temp to find blocks to merge
            while (next != null) {
                // If the next and prev blocks are contiguous, merge them
                if (prev.address + prev.size == next.address) {
                    // Delete the blocks from freeBlk
                    if (this.type == 2) {
                        freeBlk.Delete(new BSTree(prev.address, prev.size, prev.size));
                        freeBlk.Delete(new BSTree(next.address, next.size, next.size));
                    } else if (this.type == 3) {
                        freeBlk.Delete(new AVLTree(prev.address, prev.size, prev.size));
                        freeBlk.Delete(new AVLTree(next.address, next.size, next.size));
                    }
                    // Update next pointer so that it can be further merged
                    next.address = prev.address;
                    next.size = next.size + prev.size;
                    // Insert the merged block in freeBlk
                    freeBlk.Insert(next.address, next.size, next.size);
                }
                prev = prev.getNext();
                next = next.getNext();
            }
        }
    }
}