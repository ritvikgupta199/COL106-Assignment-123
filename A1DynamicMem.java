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
        if (blockSize == 0)
            return -1;
        Dictionary v = freeBlk.Find(blockSize, false);
        if (v == null)
            return -1;
        if (v.key == blockSize) {
            allocBlk.Insert(v.address, v.size, v.address);
            freeBlk.Delete(v);
        } else {
            allocBlk.Insert(v.address, blockSize, v.address);
            freeBlk.Insert(v.address + blockSize, v.size - blockSize, v.size - blockSize);
            freeBlk.Delete(v);
        }
        return v.address;
    }

    public int Free(int startAddr) {
        Dictionary v = allocBlk.Find(startAddr, true);
        if (v == null)
            return -1;
        freeBlk.Insert(v.address, v.size, v.size);
        allocBlk.Delete(v);
        return 0;
    }
}