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
        // Insert cannot be called on the tail node
        if (this.next == null) {
            return null;
        }
        // Inserting a new node after the current node in the DLL
        // Creating a new node
        A1List node = new A1List(address, size, key);

        // Linking new node with the node next to current node
        node.next = this.next;
        this.next.prev = node;

        // Linking new node with the current node
        this.next = node;
        node.prev = this;

        return node;
    }

    public boolean Delete(Dictionary d) {
        A1List v = this.getHead();
        // Finding the node with match in the DLL
        while (v != null) {
            if (v.key == d.key && v.address == d.address && v.size == d.size && v.next != null && v.prev != null) {
                break;
            }
            v = v.next;
        }
        if (v == null)
            return false;
        else {
            // Removing the node found
            v.next.prev = v.prev;
            v.prev.next = v.next;
            return true;
        }
    }

    public A1List Find(int k, boolean exact) {
        A1List v = this.getHead();
        // Finding the node with match in the DLL
        while (v != null) {
            if (((exact && v.key == k) || (!exact && v.key >= k)) && v.next != null && v.prev != null) {
                break;
            }
            v = v.next;
        }
        return v;
    }

    // Function to get Head Pointer for the DLL
    private A1List getHead() {
        A1List v = this;
        // Iterate to the head node of the list
        while (v.prev != null) {
            v = v.prev;
        }
        return v;
    }

    public A1List getFirst() {
        A1List v = this;
        // Iterate to the head node of the list
        while (v.prev != null) {
            v = v.prev;
        }
        // Return null if the next node is tail node else return next node
        return v.next.next == null ? null : v.next;
    }

    public A1List getNext() {
        return this.next;
    }

    public boolean sanity() {
        A1List v = this.getHead();
        // Check if Head node has all values as -1 and prev pointer as null
        if (!(v.prev == null && v.key == -1 && v.address == -1 && v.size == -1))
            return false;
        v = v.next;

        // Checking for invariants for each node
        while (v.next != null) {
            if (v.next.prev != v || v.prev.next != v)
                return false;
            v = v.next;
        }

        // Check if Tail node has all values as -1 and next pointer as null
        if (!(v.next == null && v.key == -1 && v.address == -1 && v.size == -1))
            return false;

        // Checking if the list gets circular using two-pointer method
        A1List p1 = this.getHead();
        A1List p2 = this.getHead();
        while (p1 != null && p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            // If there is a match then there exists a loop in the list
            if (p1 == p2) {
                return false;
            }
        }
        return true;
    }

}
