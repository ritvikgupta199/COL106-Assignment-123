// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right; // Children.
    private BSTree parent; // Parent pointer.

    public BSTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.
    }

    public BSTree(int address, int size, int key) {
        super(address, size, key);
    }

    private BSTree getSentinel() {
        BSTree v = this;
        while (v.parent != null) {
            v = v.parent;
        }
        return v;
    }

    // Is less than zero if b is smaller
    // is zero if b is same
    // is greater than zero if b is larger
    private int compare(BSTree b) {
        if (this.key < b.key) {
            return 1;
        } else if (this.key == b.key) {
            if (this.address < b.address) {
                return 1;
            } else if (this.address == b.address) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private int compare(Dictionary d) {
        if (this.key < d.key) {
            return 1;
        } else if (this.key == d.key) {
            if (this.address < d.address) {
                return 1;
            } else if (this.address == d.address) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private int compare(int key, int address) {
        if (this.key < key) {
            return 1;
        } else if (this.key == key) {
            if (this.address < address) {
                return 1;
            } else if (this.address == address) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private BSTree search(BSTree root, Dictionary d) {
        int comp = root.compare(d);
        if (comp == 0) {
            return root;
        } else if (comp < 0) {
            return root.left == null ? null : search(root.left, d);
        } else {
            return root.right == null ? null : search(root.right, d);
        }
    }

    private void insertNode(BSTree root, BSTree node) {
        int comp = root.compare(node);
        if (comp < 0) {
            if (root.left == null) {
                node.parent = root;
                root.left = node;
            } else {
                insertNode(root.left, node);
            }

        } else if (comp > 0) {
            if (root.right == null) {
                node.parent = root;
                root.right = node;
            } else {
                insertNode(root.right, node);
            }
        }
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree v = getSentinel();
        if (v.right == null) {
            BSTree node = new BSTree(address, size, key);
            node.parent = v;
            v.right = node;
            return node;
        } else {
            BSTree node = new BSTree(address, size, key);
            insertNode(v.right, node);
            return node;
        }
    }

    public boolean Delete(Dictionary e) {
        BSTree v = getSentinel();
        if (v.right == null)
            return false;
        else {
            v = v.right;
            BSTree node = search(v, e);
            if (node == null)
                return false;
            if (node.left == null && node.right == null) {
                BSTree par = node.parent;
                if (par.left == node) {
                    par.left = null;
                } else {
                    par.right = null;
                }
            } else if (node.left == null && node.right != null) {
                BSTree par = node.parent;
                if (par.left == node) {
                    par.left = node.right;
                    node.right.parent = par;
                } else {
                    par.right = node.right;
                    node.right.parent = par;
                }
            } else if (node.left != null && node.right == null) {
                BSTree par = node.parent;
                if (par.left == node) {
                    par.left = node.left;
                    node.left.parent = par;
                } else {
                    par.right = node.left;
                    node.left.parent = par;
                }
            } else {
                BSTree succ = node.getNext();
                node.address = succ.address;
                node.key = succ.address;
                node.size = succ.size;

                if (succ.right == null) {
                    BSTree par = succ.parent;
                    if (par.left == node) {
                        par.left = null;
                    } else {
                        par.right = null;
                    }
                } else {
                    BSTree par = succ.parent;
                    if (par.left == node) {
                        par.left = succ.right;
                        succ.right.parent = par;
                    } else {
                        par.right = succ.right;
                        succ.right.parent = par;
                    }
                }
            }
            return true;
        }
    }

    public BSTree Find(int key, boolean exact) {
        return null;
    }

    public BSTree getFirst() {
        BSTree v = getSentinel();
        if (v.right == null)
            return null;
        else {
            v = v.right;
            while (v.left != null) {
                v = v.left;
            }
            return v;
        }
    }

    public BSTree getNext() {
        if (this.right != null) {
            BSTree v = this.right;
            while (v.left != null) {
                v = v.left;
            }
            return v;
        } else {
            BSTree v = this;
            while (v.parent.parent != null && v.parent.left != v) {
                v = v.parent;
            }
            return v.parent.parent == null ? null : v.parent;
        }
    }

    public boolean sanity() {
        return false;
    }

}
