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
        // Move up the tree until v.parent != null, i.e. until v is not the sentinel node
        while (v.parent != null) {
            v = v.parent;
        }
        return v;
    }

    // The compare function returns
    // 1 when the node being compared is greater than the node on which it is called, i.e. this
    // 0 when the node being compared is exactly equal to the node
    // -1 when the node being compared is smaller than the node
    private int compare(BSTree b) {
        if (this.key < b.key) {
            // return 1 if b.key is greater
            return 1;
        } else if (this.key == b.key) {
            // if the keys are equal use address for tie-breaking
            if (this.address < b.address) {
                // return 1 if b.address is greater
                return 1;
            } else if (this.address == b.address) {
                // return 0 if the addresses are also equal
                return 0;
            } else {
                // return -1 if b.address is smaller
                return -1;
            }
        } else {
            // return -1 if b.key is smaller
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

    // Recursively searches for an exact match in the tree with the given root
    private BSTree search(BSTree root, Dictionary d) {
        int comp = root.compare(d);
        if (comp == 0) {
            // If d exactly matches root then return root
            return root;
        } else if (comp < 0) {
            // If d is smaller than root,
            // return null if left subtree of root is empty
            // else recursively search the left subtree
            return root.left == null ? null : search(root.left, d);
        } else {
            // If d is greater than root,
            // return null if right subtree of root is empty
            // else recursively search the right subtree
            return root.right == null ? null : search(root.right, d);
        }
    }

    // Recursively inserts node into a tree with the given root
    private void insertNode(BSTree root, BSTree node) {
        int comp = root.compare(node);
        if (comp < 0) {
            // If the node to be inserted is smaller than root, insert in left subtree
            if (root.left == null) {
                // If root.left is null, insert here
                node.parent = root;
                root.left = node;
            } else {
                // Else recursively insert in the left subtree
                insertNode(root.left, node);
            }

        } else if (comp > 0) {
            // If the node to be inserted is greater than root, insert in right subtree
            if (root.right == null) {
                // If root.right is null, insert here
                node.parent = root;
                root.right = node;
            } else {
                // Else recursively insert in the right subtree
                insertNode(root.right, node);
            }
        }
    }

    // It recursively finds the smallest element that is greater
    // than the (key, address) pair
    private BSTree searchGreater(BSTree root, BSTree ans, int key, int address) {
        // If we reach a null value, we simply return the last stored ans value
        if (root == null) {
            return ans;
        }
        if (root.compare(key, address) < 0) {
            // If the (key, address) pair is smaller than the root node value
            // then we continue the search for the element in the left subtree
            // We also update the ans as the root node qualifies the criteria
            // and the left subtree might not contain any node that is
            // greater than the (key, address) pair
            // in that case ans is the smallest such pair
            return searchGreater(root.left, root, key, address);
        } else if (root.compare(key, address) == 0) {
            // If we find an exact match, we simply return it
            return root;
        } else {
            // If the (key, address) pair is greater than the root node value
            // then we continue the search in the right subtree
            // this time we do not update the ans as the root node does not qualify the
            // criteria
            return searchGreater(root.right, ans, key, address);
        }
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        // Simply create a root node
        if (v.right == null) {
            BSTree node = new BSTree(address, size, key);
            node.parent = v;
            v.right = node;
            return node;
        } else {
            // If the tree is not empty
            // Create a new node
            BSTree node = new BSTree(address, size, key);
            // Recursively insert the element from the root node
            insertNode(v.right, node);

            return node;
        }
    }

    public boolean Delete(Dictionary e) {
        BSTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        if (v.right == null)
            return false;
        else {
            v = v.right;
            // Search for the node with exact match in the tree
            BSTree node = search(v, e);

            // If the node is not present in the tree, return false
            if (node == null)
                return false;

            if (node.left == null && node.right == null) {
                // If the node is a leaf node
                BSTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = null;
                } else {
                    // If the node is a right child
                    par.right = null;
                }
            } else if (node.left == null && node.right != null) {
                // If the node only has a right child
                BSTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = node.right;
                    node.right.parent = par;
                } else {
                    // If the node is a right child
                    par.right = node.right;
                    node.right.parent = par;
                }
            } else if (node.left != null && node.right == null) {
                // If the node only has a left child
                BSTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = node.left;
                    node.left.parent = par;
                } else {
                    // If the node is a right child
                    par.right = node.left;
                    node.left.parent = par;
                }
            } else {
                // If the node has both right and left child, we then copy the successor data
                // into node and delete the successor node

                // Find the successor of the node and copy the successor data into node
                BSTree succ = node.getNext();
                node.address = succ.address;
                node.key = succ.key;
                node.size = succ.size;

                // Now delete the successor

                if (succ.right == null) {
                    // If the right child is null, i.e. succ is a leaf node
                    // NOTE: the successor can never have a left child as
                    // it is the left most element of the right subtree of node
                    BSTree par = succ.parent;
                    if (par.left == succ) {
                        // If the node is a left child
                        par.left = null;
                    } else {
                        // If the node is a right child
                        par.right = null;
                    }
                } else {
                    // If the right child is not null, i.e. succ has only one child
                    BSTree par = succ.parent;
                    if (par.left == succ) {
                        // If the node is a left child
                        par.left = succ.right;
                        succ.right.parent = par;
                    } else {
                        // If the node is a right child
                        par.right = succ.right;
                        succ.right.parent = par;
                    }
                }
            }
            return true;
        }
    }

    public BSTree Find(int key, boolean exact) {
        BSTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        if (v.right == null) {
            return null;
        } else {
            // Else we search for the node recursively
            // Since we have to minimise the address for same key values,
            // we set address to be 0 for the search
            // (as 0 is the smallest address possible)
            BSTree node = searchGreater(v.right, null, key, 0);
            if (exact && node != null) {
                // When exact parameter is true,
                // We return the node if the node and given key values match
                // and return null if they do not match
                // By using searchGreater, we have already minimised address value in this case
                return key == node.key ? node : null;
            }
            // Otherwise we simply return the node found
            return node;
        }
    }

    public BSTree getFirst() {
        BSTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        if (v.right == null)
            return null;
        else {
            // Else we return the minimum element of the tree
            // i.e. is the leftmost node of the tree
            v = v.right;
            while (v.left != null) {
                v = v.left;
            }
            return v;
        }
    }

    public BSTree getNext() {
        // if the node on which getNext is called is the sentinel node return null
        if (this.parent == null) {
            return null;
        }
        // If the node has a right subtree, we return the minimum of the right subtree
        // i.e. the leftmost node of this subtree
        if (this.right != null) {
            BSTree v = this.right;
            while (v.left != null) {
                v = v.left;
            }
            return v;
        } else {
            // If the node does not have a right subtree
            // We look for the first right parent
            BSTree v = this;
            while (v.parent.parent != null && v.parent.left != v) {
                v = v.parent;
            }
            // If the loop terminates at the root node,
            // then there is no next element for the node, i.e. it is the maximum element
            // Else we return the parent of the v, i.e. the first right parent
            return v.parent.parent == null ? null : v.parent;
        }
    }

    // This function recursively checks whether node.child.parent==node
    private boolean checkChildParent() {
        if (this.left == null && this.right == null) {
            return true;
        } else if (this.left != null && this.right == null) {
            return this.left.parent == this && this.left.checkChildParent();
        } else if (this.left == null && this.right != null) {
            return this.right.parent == this && this.right.checkChildParent();
        } else {
            return this.left.parent == this && this.right.parent == this && this.left.checkChildParent()
                    && this.right.checkChildParent();
        }
    }

    public boolean sanity() {

        // Check that sentinel node has all values as -1
        // and its parent and left child is also null
        BSTree sent = this.getSentinel();
        if (!(sent.address == -1 && sent.size == -1 && sent.key == -1 && sent.left == null && sent.parent == null)) {
            return false;
        }

        // Check that node.left.parent == node and node.right.parent == node
        if (!sent.checkChildParent())
            return false;

        return true;
    }

}
