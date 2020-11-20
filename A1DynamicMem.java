// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return;
    }

    // In A1, you need to implement the Allocate and Free functions for the class
    // A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only
    // (A1List.java).

    public int Allocate(int blockSize) {
        // Cannot allocate a block of size <= 0
        if (blockSize <= 0)
            return -1;

        Dictionary v = freeBlk.Find(blockSize, false);
        // If v is null then there is no more space left in the Free Memory Block
        if (v == null)
            return -1;

        // Saving address as the node to which v points may change during BST Deletion
        // It may be swapped with its successor
        int address = v.address;

        // If size of the block found is exactly equal to requested block size, then
        // split is not required
        if (v.key == blockSize) {
            allocBlk.Insert(v.address, v.size, v.address);
            freeBlk.Delete(v);
        } else {
            allocBlk.Insert(v.address, blockSize, v.address);
            freeBlk.Insert(v.address + blockSize, v.size - blockSize, v.size - blockSize);
            freeBlk.Delete(v);
        }
        return address;
    }

    public int Free(int startAddr) {
        Dictionary v = allocBlk.Find(startAddr, true);

        // If v is null then there is no such block with the given address
        if (v == null)
            return -1;

        freeBlk.Insert(v.address, v.size, v.size);
        allocBlk.Delete(v);
        return 0;
    }
}