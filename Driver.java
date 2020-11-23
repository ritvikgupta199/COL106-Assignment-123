import java.util.*;

public class Driver {

    public static void main(String[] args) {
        BSTree bst = new BSTree();
        bst.Insert(16, 10, 16);
        bst.Insert(8, 10, 8);
        bst.Insert(24, 240, 24);
        bst.Insert(4, 30, 4);
        bst.Insert(12, 69, 12);
        bst.Insert(20, 40, 20);
        bst.Insert(28, 30, 28);
        bst.Insert(2, 30, 2);
        bst.Insert(6, 30, 6);
        bst.Insert(10, 30, 10);
        bst.Insert(14, 70, 14);
        bst.Insert(18, 30, 18);
        bst.Insert(22, 30, 22);
        bst.Insert(26, 30, 26);
        bst.Insert(30, 31, 30);

        BSTree test = bst.Find(45, false);
        System.out.println("Hello");
        System.out.println(test.key);
        System.out.println(test.size);
    }
}
