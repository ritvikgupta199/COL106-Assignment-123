import apple.laf.JRSUIConstants.Size;

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
        for (v = freeBlk.getFirst(); v != null; v = v.getNext()) {
            temp.Insert(v.address, v.size, v.address);
        }
        Dictionary prev = temp.getFirst();
        if (prev != null) {
            Dictionary next = prev.getNext();
            while (next != null) {
                if (prev.address + prev.size == next.address) {
                    freeBlk.Delete(new BSTree(prev.address, prev.size, prev.size));
                    freeBlk.Delete(new BSTree(next.address, next.size, next.size));
                    next.address = prev.address;
                    next.size = next.size + prev.size;
                    freeBlk.Insert(next.address, next.size, next.size);
                }
                prev = prev.getNext();
                next = next.getNext();
            }
        }
    }
}