import java.util.HashSet;

// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {

    private AVLTree left, right; // Children.
    private AVLTree parent; // Parent pointer.
    private int height; // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.

    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and
    // nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    private AVLTree getSentinel() {
        AVLTree v = this;
        // Move up the tree until v.parent != null, i.e. until v is not the sentinel
        // node
        while (v.parent != null) {
            v = v.parent;
        }
        return v;
    }

    // The compare function returns
    // 1 when the node being compared is greater than the node on which it is
    // called, i.e. this
    // 0 when the node being compared is exactly equal to the node
    // -1 when the node being compared is smaller than the node
    private int compare(AVLTree b) {
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
    private AVLTree search(AVLTree root, Dictionary d) {
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
    private void insertNode(AVLTree root, AVLTree node) {
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
        } else {
            // If the node is already present then we have to return null
            node = null;
        }
    }

    // It recursively finds the smallest element that is greater
    // than the (key, address) pair
    private AVLTree searchGreater(AVLTree root, AVLTree ans, int key, int address) {
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

    // Returns the height of the node
    private int getHeight(AVLTree node) {
        // height is 0 if null
        return node == null ? 0 : node.height;
    }

    // Updates the height of this node
    private void updateHeight() {
        this.height = 1 + Math.max(getHeight(this.left), getHeight(this.right));
    }

    // Left Rotates the nodes child and parent
    private void rotateL(AVLTree child, AVLTree par) {
        // Setting pointers for new right node of parent
        par.right = child.left;
        if (par.right != null)
            par.right.parent = par;

        // Setting pointers for new parent of child
        child.parent = par.parent;
        if (child.parent.left == par)
            child.parent.left = child;
        else
            child.parent.right = child;

        // Setting pointers for making parent the right child of child
        par.parent = child;
        child.left = par;
    }

    // Right Rotates the nodes child and parent
    private void rotateR(AVLTree child, AVLTree par) {
        // Setting pointers for new left child of parent
        par.left = child.right;
        if (par.left != null)
            par.left.parent = par;

        // Setting pointers for new parent of child
        child.parent = par.parent;
        if (par.parent.left == par)
            child.parent.left = child;
        else
            child.parent.right = child;

        // Setting pointers for making parent the right child of child
        par.parent = child;
        child.right = par;
    }

    // Checks the AVL Balancing Property after Insertion and
    // Corrects it using suitable rotations
    private void correctInsertBalance() {
        AVLTree par = this.parent;
        AVLTree child = this;
        // We move up the tree until par becomes the sentinel node
        while (par.parent != null) {
            int bal = getHeight(par.left) - getHeight(par.right);
            if (bal > 1) {
                // If the left height differs the right height by more than 1,
                // the balance property is violated
                // Also the left node is causing the imbalance
                if (getHeight(child.left) > getHeight(child.right)) {
                    // If the left child of the child has more height,
                    // We require a Right Rotation on nodes child and par
                    rotateR(child, par);
                    par.updateHeight();
                    child.updateHeight();
                    par = child;
                } else {
                    // If the right child of the child has more height,
                    // We first require a Left Rotation on nodes grandchild and child
                    // followed by a Right Rotation on nodes grandchild and par
                    AVLTree grandchild = child.right;
                    rotateL(grandchild, child);
                    rotateR(grandchild, par);
                    child.updateHeight();
                    par.updateHeight();
                    grandchild.updateHeight();
                    par = grandchild;
                }
            } else if (bal < -1) {
                // If the right height differs the left height by more than 1,
                // the balance property is violated
                // Also the right node is causing the imbalance
                if (getHeight(child.left) > getHeight(child.right)) {
                    // If the left child of the child has more height,
                    // We first require a Right Rotation on nodes grandchild and child
                    // followed by a Left Rotation on nodes grandchild and par
                    AVLTree grandchild = child.left;
                    rotateR(grandchild, child);
                    rotateL(grandchild, par);
                    child.updateHeight();
                    par.updateHeight();
                    grandchild.updateHeight();
                    par = grandchild;
                } else {
                    // If the right child of the child has more height,
                    // We require a Left Rotation on nodes child and par
                    rotateL(child, par);
                    par.updateHeight();
                    child.updateHeight();
                    par = child;
                }
            } else {
                // If the balance is maintained, we just update the height of par node and
                // continue up the tree
                par.updateHeight();
            }
            child = par;
            par = par.parent;
        }

    }

    @Override
    public AVLTree Insert(int address, int size, int key) {
        AVLTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        // Simply create a root node
        if (v.right == null) {
            AVLTree node = new AVLTree(address, size, key);
            node.parent = v;
            v.right = node;
            node.updateHeight();
            return node;
        } else {
            // If the tree is not empty
            // Create a new node
            AVLTree node = new AVLTree(address, size, key);
            // Recursively insert the element from the root node
            insertNode(v.right, node);
            // Update the height of this node
            node.updateHeight();
            // Start correcting balance from this node
            node.correctInsertBalance();
            return node;
        }
    }

    private void correctDeleteBalance() {
        AVLTree par = this;
        par.updateHeight();
        // We move up the tree until par becomes the sentinel node
        while (par.parent != null) {
            int bal = getHeight(par.left) - getHeight(par.right);
            if (bal > 1) {
                // If the left height differs the right height by more than 1,
                // the balance property is violated
                // Also the left node is causing the imbalance
                AVLTree child = par.left;
                if (child != null) {
                    if (getHeight(child.left) >= getHeight(child.right)) {
                        // If the left height is more than or equal to the right height,
                        // We require a Right Rotation on nodes child and par
                        rotateR(child, par);
                        par.updateHeight();
                        child.updateHeight();
                        par = child;
                    } else {
                        // If the right height is more than left height,
                        // We first require a Left Rotation on nodes grandchild and child
                        // followed by a Right Rotation on nodes grandchild and par
                        AVLTree grandchild = child.right;
                        rotateL(grandchild, child);
                        rotateR(grandchild, par);
                        child.updateHeight();
                        par.updateHeight();
                        grandchild.updateHeight();
                        par = grandchild;
                    }
                }
            } else if (bal < -1) {
                // If the right height differs the left height by more than 1,
                // the balance property is violated
                // Also the right node is causing the imbalance
                AVLTree child = par.right;
                if (child != null) {
                    if (getHeight(child.left) > getHeight(child.right)) {
                        // If the left height is more than left height,
                        // We first require a Right Rotation on nodes grandchild and child
                        // followed by a Left Rotation on nodes grandchild and par
                        AVLTree grandchild = child.left;
                        rotateR(grandchild, child);
                        rotateL(grandchild, par);
                        child.updateHeight();
                        par.updateHeight();
                        grandchild.updateHeight();
                        par = grandchild;
                    } else {
                        // If the right child of the child has more height,
                        // We require a Left Rotation on nodes child and par
                        rotateL(child, par);
                        par.updateHeight();
                        child.updateHeight();
                        par = child;
                    }
                }
            } else {
                // If the balance is maintained, we just update the height of par node and
                // continue up the tree
                par.updateHeight();
            }
            par = par.parent;
        }
    }

    @Override
    public boolean Delete(Dictionary e) {
        AVLTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        if (v.right == null)
            return false;
        else {
            v = v.right;
            // Search for the node with exact match in the tree
            AVLTree node = search(v, e);

            // If the node is not present in the tree, return false
            if (node == null)
                return false;

            if (node.left == null && node.right == null) {
                // If the node is a leaf node
                AVLTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = null;
                } else {
                    // If the node is a right child
                    par.right = null;
                }
                // Start correcting balance from this node
                par.correctDeleteBalance();
            } else if (node.left == null && node.right != null) {
                // If the node only has a right child
                AVLTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = node.right;
                    node.right.parent = par;
                } else {
                    // If the node is a right child
                    par.right = node.right;
                    node.right.parent = par;
                }
                // Start correcting balance from this node
                par.correctDeleteBalance();
            } else if (node.left != null && node.right == null) {
                // If the node only has a left child
                AVLTree par = node.parent;
                if (par.left == node) {
                    // If the node is a left child
                    par.left = node.left;
                    node.left.parent = par;
                } else {
                    // If the node is a right child
                    par.right = node.left;
                    node.left.parent = par;
                }
                // Start correcting balance from this node
                par.correctDeleteBalance();
            } else {
                // If the node has both right and left child, we then copy the successor data
                // into node and delete the successor node

                // Find the successor of the node and copy the successor data into node
                AVLTree succ = node.getNext();
                node.address = succ.address;
                node.key = succ.key;
                node.size = succ.size;

                // Now delete the successor

                if (succ.right == null) {
                    // If the right child is null, i.e. succ is a leaf node
                    // NOTE: the successor can never have a left child as
                    // it is the left most element of the right subtree of node
                    AVLTree par = succ.parent;
                    if (par.left == succ) {
                        // If the node is a left child
                        par.left = null;
                    } else {
                        // If the node is a right child
                        par.right = null;
                    }
                    // Start correcting balance from this node
                    par.correctDeleteBalance();
                } else {
                    // If the right child is not null, i.e. succ has only one child
                    AVLTree par = succ.parent;
                    if (par.left == succ) {
                        // If the node is a left child
                        par.left = succ.right;
                        succ.right.parent = par;
                    } else {
                        // If the node is a right child
                        par.right = succ.right;
                        succ.right.parent = par;
                    }
                    // Start correcting balance from this node
                    par.correctDeleteBalance();
                }
            }
            return true;
        }
    }

    @Override
    public AVLTree Find(int key, boolean exact) {
        AVLTree v = getSentinel();
        // If the right child of sentinel is null then the tree is empty
        if (v.right == null) {
            return null;
        } else {
            // Else we search for the node recursively
            // Since we have to minimise the address for same key values,
            // we set address to be 0 for the search
            // (as 0 is the smallest address possible)
            AVLTree node = searchGreater(v.right, null, key, 0);
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

    @Override
    public AVLTree getFirst() {
        AVLTree v = getSentinel();
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

    @Override
    public AVLTree getNext() {
        // if the node on which getNext is called is the sentinel node return null
        if (this.parent == null) {
            return null;
        }
        // If the node has a right subtree, we return the minimum of the right subtree
        // i.e. the leftmost node of this subtree
        if (this.right != null) {
            AVLTree v = this.right;
            while (v.left != null) {
                v = v.left;
            }
            return v;
        } else {
            // If the node does not have a right subtree
            // We look for the first right parent
            AVLTree v = this;
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
    // Returns true is the property is satisfied and
    // false if the property is not satisfied
    private boolean checkChildParent() {
        if (this.left == null && this.right == null) {
            // Is vacuously true for leaf node
            return true;
        } else if (this.left != null && this.right == null) {
            // Check the property for the left child and recursively for the left subtree
            return this.left.parent == this && this.left.checkChildParent();
        } else if (this.left == null && this.right != null) {
            // Check the property for the right child and recursively for the right subtree
            return this.right.parent == this && this.right.checkChildParent();
        } else {
            // Check the property for the left and right child and 
            // recursively for the left and right subtree
            return this.left.parent == this && this.right.parent == this && this.left.checkChildParent()
                    && this.right.checkChildParent();
        }
    }

    // This function checks for cycles in the tree using Depth First Search
    // returns true if there are not cycles and
    // returns false if there exists a cycle in the tree
    private boolean checkCycle(AVLTree prev, HashSet<AVLTree> set) {
        // If the current node is already present in the set,
        // it has already been visited
        // Since in DFS, we never move along an edge more than once,
        // there must be a cycle in the tree
        if (set.contains(this)) {
            return false;
        }

        // Mark this node as visited by adding it to the set
        set.add(this);

        boolean check = true;

        // If there is a left child and either prev is null
        // or if the left child is different from prev,
        // We visit the left node using DFS, checking for cycles
        if (this.left != null && (prev == null || this.left != prev)) {
            check = check && this.left.checkCycle(this, set);
        }
        // If there is a right child and either prev is null
        // or if the right child is different from prev,
        // We visit the right node using DFS, checking for cycles
        if (this.right != null && (prev == null || this.right != prev)) {
            check = check && this.right.checkCycle(this, set);
        }
        // If there is a right child and either prev is null
        // or if the right child is different from prev,
        // We visit the parent node using DFS, checking for cycles
        if (this.parent != null && (prev == null || this.parent != prev)) {
            check = check && this.parent.checkCycle(this, set);
        }
        return check;
    }

    // This function recursively checks for BST Search Property
    // Returns true is the property is satisfied and
    // false if the property is not satisfied
    private boolean checkBSTProperty(AVLTree node, int minKey, int minAddress, int maxKey, int maxAddress) {
        // Property is vacuously true
        if (node == null) {
            return true;
        }
        // Return false if node does not satisy minimum and maximum constraints
        if (!(node.compare(minKey, minAddress) < 0 && node.compare(maxKey, maxAddress) > 0))
            return false;

        // Recursively check BST Property for left and right subtree
        return checkBSTProperty(node.left, minKey, minAddress, node.key, node.address)
                && checkBSTProperty(node.right, node.key, node.address, maxKey, maxAddress);

    }

    // This function recursively checks the height balancing propert for the AVL
    // Tree
    // Returns true if the tree is height balanced
    // false if the height balanced property is not satisfied
    private boolean isBalanced() {
        if (this.left == null && this.right == null) {
            // A leaf is height balanced trivially
            return true;
        } else if (this.left != null && this.right == null) {
            // Check balance for this node and recursively for the left subtree
            return Math.abs(getHeight(this.left) - getHeight(this.right)) <= 1 && this.left.isBalanced();
        } else if (this.left == null && this.right != null) {
            // Check balance for this node and recursively for the right subtree
            return Math.abs(getHeight(this.left) - getHeight(this.right)) <= 1 && this.right.isBalanced();
        } else {
            // Check balance for this node and recursively for the left and right subtree
            return Math.abs(getHeight(this.left) - getHeight(this.right)) <= 1 && this.left.isBalanced()
                    && this.right.isBalanced();
        }
    }

    @Override
    public boolean sanity() {
        // Check for cycles in the tree
        HashSet<AVLTree> set = new HashSet<>();
        if (!this.checkCycle(null, set)) {
            return false;
        }

        // Check that sentinel node has all values as -1
        // and its parent and left child is also null
        AVLTree sent = this.getSentinel();
        if (!(sent.address == -1 && sent.size == -1 && sent.key == -1 && sent.left == null && sent.parent == null)) {
            return false;
        }

        // Check BST Search Property for the tree
        if (sent.right != null && !checkBSTProperty(sent.right, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE)) {
            return false;
        }

        // Check that node.left.parent == node and node.right.parent == node
        if (!sent.checkChildParent())
            return false;

        // Check AVL Property for the tree, i.e. if the Tree is height balanced
        if (sent.right != null && !sent.right.isBalanced())
            return false;

        return true;
    }
}