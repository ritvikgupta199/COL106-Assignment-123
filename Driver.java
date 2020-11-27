import java.util.*;

public class Driver {

    public static void main(String[] args) {
        AVLTree bst = new AVLTree();
        for (int i = 10; i >0; i--) {
            bst.Insert(1000, 1000, i);
        }
        System.out.println(bst.sanity() ? "True" : "False");
        for (AVLTree i = bst.getFirst(); i != null; i = i.getNext()) {
            System.out.println("Element:" + i.key+" Height:"+i.height);
        }

        bst.Delete(new AVLTree(1000,1000,4));

        System.out.println(bst.sanity() ? "True" : "False");
        for (AVLTree i = bst.getFirst(); i != null; i = i.getNext()) {
            System.out.println("Element:" + i.key+" Height:"+i.height);
        }
    }
}
