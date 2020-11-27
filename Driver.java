import java.util.*;

public class Driver {

    public static void main(String[] args) {
        AVLTree bst = new AVLTree();
        Random rand=new Random();
        HashSet<Integer> set=new HashSet<>();
        for (int i = 1; i <15; i++) {
            int x=rand.nextInt(50);
            if(set.contains(x)) continue;
            System.out.println(x);
            set.add(x);
            bst.Insert(1000, 1000, x);
        }
        System.out.println(bst.sanity() ? "True" : "False");
        // for (AVLTree i = bst.getFirst(); i != null; i = i.getNext()) {
        //     System.out.println("Element:" + i.key+" Height:"+i.height);
        // }

        // bst.Delete(new AVLTree(1000,1000,4));

        // System.out.println(bst.sanity() ? "True" : "False");
        // for (AVLTree i = bst.getFirst(); i != null; i = i.getNext()) {
        //     System.out.println("Element:" + i.key+" Height:"+i.height);
        // }
    }
}
