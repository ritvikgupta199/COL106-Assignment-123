// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List next; // Next Node
    private A1List prev; // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List() {
        super(-1, -1, -1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1, -1, -1); // Intiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key) {
        A1List node = new A1List(address, size, key);

        node.next = this.next;
        this.next.prev = node;

        this.next = node;
        node.prev = this;

        return node;
    }

    public boolean Delete(Dictionary d) {
        A1List v = this;
        while (v != null) {
            if (v.key == d.key && v.address == d.address && v.size == d.size) {
                break;
            }
            v = v.next;
        }
        if (v == null) {
            v = this;
            while (v != null) {
                if (v.key == d.key && v.address == d.address && v.size == d.size) {
                    break;
                }
                v = v.prev;
            }
        }
        if (v == null)
            return false;
        else {
            v.next.prev = v.prev;
            v.prev.next = v.next;
            return true;
        }
    }

    public A1List Find(int k, boolean exact) {
        A1List v = this;
        while (v != null) {
            if ((exact && v.key == k) || (!exact && v.key >= k)) {
                break;
            }
            v = v.next;
        }
        if (v == null) {
            v = this;
            while (v != null) {
                if ((exact && v.key == k) || (!exact && v.key >= k)) {
                    break;
                }
                v = v.prev;
            }
        }
        return v;
    }

    public A1List getFirst() {
        A1List v = this;
        while (v.prev != null) {
            v = v.prev;
        }
        return v;
    }

    public A1List getNext() {
        return this.next;
    }

    public boolean sanity() {
        return true;
    }

}
