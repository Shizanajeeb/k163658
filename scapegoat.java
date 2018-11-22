
package scapegoat;
import java.util.Scanner;

public class ScapeGoat {
    private SGTNode root;
    private int n, q;
    /* Constructor */    
    public ScapeGoat()
    {
        root = null;
        n = 0;        
    }
class SGTNode    
{
    SGTNode right, left, parent;
    int value;
    
    public SGTNode(int val) // Constructor 
    {
        value = val;
    }
}
    public boolean isEmpty() // Function to check if tree is empty 
    {
        return root==null;
    }
    public void makeEmpty() // Function to clear  tree 
    {
        root = null;
        n = 0;
    }    

    
    public int size(SGTNode r) // Function to count number of nodes recursively 
    {
        if (r == null)
            return 0;
        else
        {
            int l = 1;
            l += size(r.left);
            l += size(r.right);
            return l;
        }
    }
    private boolean search(SGTNode r, int val) // Functions to search for an element 
    {
        boolean found = false;
        while ((r != null) && !found)
        {
            int rval = r.value;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else
            {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }
    
    public boolean search(int val) 
    {
        return search(root, val);
        
    }
    
    public int size() // Function to return current size of tree    
    {
        return n;
    }
    public void inorder(SGTNode r) // Function for inorder traversal 
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.value +" ");
            inorder(r.right);
        }
    }
    public void inorder()
    {
        inorder(root);
    }
    public void preorder(SGTNode r) // Function for preorder traversal 
    {
        if (r != null)
        {
            System.out.print(r.value +" ");
            preorder(r.left);             
            preorder(r.right);
        }
    }
    
    public void preorder() 
    {
        preorder(root);
    }
    
    public void postorder(SGTNode r) // Function for postorder traversal 
    {
        if (r != null)
        {
            postorder(r.left);             
            postorder(r.right);
            System.out.print(r.value +" ");
        }
    } 
    
    public void postorder()
    {
        postorder(root);
    }
    
    private static final int log32(int q) 
    {
        final double log23 = 2.4663034623764317;
        return (int)Math.ceil(log23*Math.log(q));
    }
    
    protected int packIntoArray(SGTNode u, SGTNode[] a, int i)   // Function to packIntoArray 
    {
        if (u == null) 
        {
            return i;
        }
        i = packIntoArray(u.left, a, i);
        a[i++] = u;
        return packIntoArray(u.right, a, i);
    }
    protected SGTNode buildBalanced(SGTNode[] a, int i, int ns)  // Function to build balanced nodes 
    {
        if (ns == 0)
            return null;
        int m = ns / 2;
        a[i + m].left = buildBalanced(a, i, m);
        if (a[i + m].left != null)
            a[i + m].left.parent = a[i + m];
        a[i + m].right = buildBalanced(a, i + m + 1, ns - m - 1);
        if (a[i + m].right != null)
            a[i + m].right.parent = a[i + m];
        return a[i + m];
    }
       public int addWithDepth(SGTNode u) // Function add with depth 
    {
        SGTNode w = root;
        if (w == null) 
        {
            root = u;
            n++; 
            q++;
            return 0;
        }
        boolean done = false;
        int d = 0;
        do {
 
            if (u.value < w.value) 
            {
                if (w.left == null) 
                {
                    w.left = u;
                    u.parent = w;
                    done = true;
                } 
                else 
                {
                    w = w.left;
                }
            } 
            else if (u.value > w.value) 
            {
                if (w.right == null) 
                {
                    w.right = u;
                    u.parent = w;
                    done = true;
                }
                w = w.right;
            } 
            else 
            {
                return -1;
            }
            d++;
        } while (!done);
        n++; 
        q++;
        return d;
    }
       /* Function to rebuild tree from node u */
    protected void rebuild(SGTNode u) 
    {
        int ns = size(u);
        SGTNode p = u.parent;
        SGTNode[] a = new SGTNode[ns];
        packIntoArray(u, a, 0);
        if (p == null) 
        {
            root = buildBalanced(a, 0, ns);
            root.parent = null;
        } 
        else if (p.right == u) 
        {
            p.right = buildBalanced(a, 0, ns);
            p.right.parent = p;
        } 
        else 
        {
            p.left = buildBalanced(a, 0, ns);
            p.left.parent = p;
        }
    }

    /* Function to insert an element */
    public boolean add(int x) 
    {
        /* first do basic insertion keeping track of depth */
        SGTNode u = new SGTNode(x);
        int d = addWithDepth(u);
        if (d > log32(q)) {
            /* depth exceeded, find scapegoat */
            SGTNode w = u.parent;
            while (3*size(w) <= 2*size(w.parent))
                w = w.parent;
            rebuild(w.parent);
        }
        return d >= 0;
    }
    
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        /* Creating object of ScapeGoatTree */
        ScapeGoat sgt = new ScapeGoat(); 
        System.out.println("ScapeGoat Tree Test\n");          
        char ch;
        /*  Perform tree operations  */
        do    
        {
            System.out.println("\nScapeGoat Tree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. count nodes");
            System.out.println("3. search"); 
            System.out.println("4. check empty");
            System.out.println("5. make empty");
 
            int choice = scan.nextInt();            
            switch (choice)
            {
            case 1 : 
                System.out.println("Enter integer element to insert");
                sgt.add( scan.nextInt() );                     
                break;                                                    
            case 2 : 
                System.out.println("Nodes = "+ sgt.size());
                break; 
            case 3 : 
                System.out.println("Enter integer element to search");
                System.out.println("Search result : "+ sgt.search( scan.nextInt() ));
                break;                           
            case 4 :  
                System.out.println("Empty status = "+ sgt.isEmpty());
                break;
            case 5 :  
                System.out.println("\nTree cleared\n");
                sgt.makeEmpty();
                break;             
            default : 
                System.out.println("Wrong Entry \n ");
                break;           
            }
            /*  Display tree  */ 
            System.out.print("\nPost order : ");
            sgt.postorder();
            System.out.print("\nPre order : ");
            sgt.preorder();
            System.out.print("\nIn order : ");
            sgt.inorder();
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');      
       
    }
    
}
