/**
 * Node class represents a single node in the Binary Search Tree
 * Each node contains a value and references to left and right children
 */

class Node{
   /** The integer value stored in this node */
   int value;
   /** Reference to the left child node */
   Node left;
   /** Reference to the right child node */
   Node right;
   
   /**
    * Constructor to create a new node with the given value
    * @param value the integer value to store in this node
    */
   public Node(int value){
      this.value = value;
      left = null;
      right = null;
   }

}

/**
 * BinarySearchTree class implements a Binary Search Tree data structure
 * Supports insertion, traversal, search, and min/max operations
 * CS-380 Lab Assignment #2
 * @author Orchlon Chinbat (50291063)
 */
class BinarySearchTree{

   /** The root node of the binary search tree */
   Node root;
   
   
   /**
    * Recursively inserts a new value into the binary search tree
    * @param root the root node of the tree or subtree
    * @param value the value to insert
    * @return the root node after insertion
    */
   public Node insert(Node root, int value){
      //base case
      if(root == null){
         root = new Node(value);
         return root;
      }
      
      //recursive step
      if(value < root.value){
         root.left = insert(root.left, value); 
      }else{
         root.right = insert(root.right, value);
      }
      
      return root;
   }
   
   
   
   /**
    * Performs pre-order traversal of the binary search tree
    * Visits nodes in the order: Root -> Left -> Right
    * @param root the root node of the tree or subtree to traverse
    */
   public void preOrderTraversal(Node root){
      if(root != null){
         System.out.print(root.value + " ");
         preOrderTraversal(root.left);
         preOrderTraversal(root.right);
      }
   }

   
   
   /**
    * Performs in-order traversal of the binary search tree
    * Visits nodes in the order: Left -> Root -> Right
    * For a BST, this produces values in sorted ascending order
    * @param root the root node of the tree or subtree to traverse
    */
   public void inOrderTraversal(Node root){
      if(root != null){
         inOrderTraversal(root.left);
         System.out.print(root.value + " ");
         inOrderTraversal(root.right);
      }
   }
   
   
   
   /**
    * Performs post-order traversal of the binary search tree
    * Visits nodes in the order: Left -> Right -> Root
    * Useful for deleting trees (delete children before parent)
    * @param root the root node of the tree or subtree to traverse
    */
   public void postOrderTraversal(Node root){
      if(root != null){
         postOrderTraversal(root.left);
         postOrderTraversal(root.right);
         System.out.print(root.value + " ");
      }
   }
   
   
   
   /**
    * Searches for a node with the specified key in the binary search tree
    * Uses BST property: left subtree has smaller values, right has larger
    * @param root the root node of the tree or subtree to search
    * @param key the value to search for
    * @return true if the key is found, false otherwise
    */
   public boolean find(Node root, int key){
      if(root == null){
         return false;
      }else if(root.value == key){
         return true;
      }else if(key < root.value){
         return find(root.left, key);
      }else{
         return find(root.right, key);
      }
   }
   
   
   
   /**
    * Finds the minimum value in the binary search tree
    * The minimum is the leftmost node in the tree
    * @param root the root node of the tree or subtree
    * @return the minimum value in the tree
    */
   public int getMin(Node root){
      if(root.left == null){
         return root.value;
      }else{
         return getMin(root.left);
      }
   }
  
  
  
   /**
    * Finds the maximum value in the binary search tree
    * The maximum is the rightmost node in the tree
    * @param root the root node of the tree or subtree
    * @return the maximum value in the tree
    */
   public int getMax(Node root){
      if(root.right == null){
         return root.value;
      }else{
         return getMax(root.right);
      }
   }
   
   
   
   /**
    * Deletes a node with the specified key from the binary search tree
    * Handles three cases: leaf node, one child, and two children
    * @param root the root node of the tree or subtree
    * @param key the value of the node to delete
    * @return the root node after deletion
    */
   public Node delete(Node root, int key){
      
      if(root == null){
         return root;
      }else if(key < root.value){
         root.left = delete(root.left, key);
      }else if(key > root.value){
         root.right = delete(root.right, key);
      }else{
         //node has been found
         if(root.left==null && root.right==null){
            //case #1: leaf node
            root = null;
         }else if(root.right == null){
            //case #2 : only left child
            root = root.left;
         }else if(root.left == null){
            //case #2 : only right child
            root = root.right;
         }else{
            //case #3 : 2 children
            root.value = getMax(root.left);
            root.left = delete(root.left, root.value);
         }
      }
      return root;  
   }
   
   
   
}



/**
 * TreeDemo class - Driver program to test Binary Search Tree implementation
 * Tests all BST operations including traversals, search, and min/max
 * CS-380 Lab Assignment #2
 * @author Orchlon Chinbat (50291063)
 */
public class TreeDemo{
   /**
    * Main method to demonstrate and test all BST operations
    * @param args command line arguments (not used)
    */
   public static void main(String[] args){
      BinarySearchTree t1  = new BinarySearchTree();
      
      // Build the tree
      System.out.println("=== Building Binary Search Tree ===");
      t1.root = t1.insert(t1.root, 24);
      t1.root = t1.insert(t1.root, 80);
      t1.root = t1.insert(t1.root, 18);
      t1.root = t1.insert(t1.root, 9);
      t1.root = t1.insert(t1.root, 90);
      t1.root = t1.insert(t1.root, 22);
      System.out.println("Inserted values: 24, 80, 18, 9, 90, 22");
      System.out.println();
      
      // Test 1: Pre-order Traversal
      System.out.println("=== Test 1: Pre-Order Traversal ===");
      System.out.println("(Root -> Left -> Right)");
      System.out.print("pre-order:   ");
      t1.preOrderTraversal(t1.root);
      System.out.println();
      System.out.println();
      
      // Test 2: In-order Traversal
      System.out.println("=== Test 2: In-Order Traversal ===");
      System.out.println("(Left -> Root -> Right - Sorted Order)");
      System.out.print("in-order:    ");
      t1.inOrderTraversal(t1.root);
      System.out.println();
      System.out.println();
      
      // Test 3: Post-order Traversal
      System.out.println("=== Test 3: Post-Order Traversal ===");
      System.out.println("(Left -> Right -> Root)");
      System.out.print("post-order:  ");
      t1.postOrderTraversal(t1.root);
      System.out.println();
      System.out.println();
      
      // Test 4: Find method
      System.out.println("=== Test 4: Find Method ===");
      int[] searchKeys = {22, 80, 50, 9, 100};
      for(int key : searchKeys){
         boolean found = t1.find(t1.root, key);
         System.out.println("Searching for " + key + ": " + (found ? "FOUND" : "NOT FOUND"));
      }
      System.out.println();
      
      // Test 5: Get Minimum
      System.out.println("=== Test 5: Get Minimum ===");
      int min = t1.getMin(t1.root);
      System.out.println("Minimum value in tree: " + min);
      System.out.println();
      
      // Test 6: Get Maximum
      System.out.println("=== Test 6: Get Maximum ===");
      int max = t1.getMax(t1.root);
      System.out.println("Maximum value in tree: " + max);
      System.out.println();
      
      System.out.println("=== All Tests Complete! ===");
   }  
}