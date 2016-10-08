public class tester {
//PLEASE NOTE THAT THE MOVES ARE PRINTED OUT IN REVERSE ORDER, SO THE FIRST MOVE IS PRINTED OUT LAST AND THE LAST MOVE FIRST
 public static void main(String[] args) {

  final byte [][] triplets = {{0,1,2},{3,4,5},{6,7,8},{7,8,9},{8,9,10},{9,10,11},{10,11,12},{13,14,15},{14,15,16},{15,16,17},{16,17,18},{17,18,19},{20,21,22},{21,22,23},{22,23,24},{23,24,25},{24,25,26},{27,28,29},{30,31,32},{12,19,26},{11,18,25},{2,5,10},{5,10,17},{10,17,24},{17,24,29},{24,29,32},{1,4,9},{4,9,16},{9,16,23},{16,23,28},{23,28,31},{0,3,8},{3,8,15},{8,15,22},{15,22,27},{22,27,30},{7,14,21},{6,13,20}};//Triplets of the HiRiQ

  //HiRiQ class as given
  class HiRiQ
  {
   //int is used to reduce storage to a minimum...
   public int config;
   public byte weight;

   //initialize the configuration to one of 4 START setups n=0,1,2,3
   HiRiQ(byte n)
   {
    if (n==0)
    {config=65536/2;weight=1;}
    else
     if (n==1)
     {config=1626;weight=6;}
     else
      if (n==2)
      {config=-1140868948; weight=10;}
      else
       if (n==3)
       {config=-411153748; weight=13;}
       else if(n == 4)
       {config=-2147450879; weight=32;}
    if(n == 5){
     config = 138850345; weight = 10;
    }

   }

   //initialize the configuration to one of 4 START setups n=0,10,20,30

   boolean IsSolved()
   {
    return( (config==65536/2) && (weight==1) );
   }

   //transforms the array of 33 booleans to an (int) cinfig and a (byte) weight.
   public void store(boolean[] B)
   {
    int a=1;
    config=0;
    weight=(byte) 0;
    if (B[0]) {weight++;}
    for (int i=1; i<32; i++)
    {
     if (B[i]) {config=config+a;weight++;}
     a=2*a;
    }
    if (B[32]) {config=-config;weight++;}
   }

   //transform the int representation to an array of booleans.
   //the weight (byte) is necessary because only 32 bits are memorized
   //and so the 33rd is decided based on the fact that the config has the
   //correct weight or not.
   public boolean[] load(boolean[] B)
   {
    byte count=0;
    int fig=config;
    B[32]=fig<0;
    if (B[32]) {fig=-fig;count++;}
    int a=2;
    for (int i=1; i<32; i++)
    {
     B[i]= fig%a>0;
     if (B[i]) {fig=fig-a/2;count++;}
     a=2*a;
    }
    B[0]= count<weight;
    return(B);
   }

   //prints the int representation to an array of booleans.
   //the weight (byte) is necessary because only 32 bits are memorized
   //and so the 33rd is decided based on the fact that the config has the
   //correct weight or not.
   public void printB(boolean Z)
   {if (Z) {System.out.print("[ ]");} else {System.out.print("[@]");}}

   public void print()
   {
    byte count=0;
    int fig=config;
    boolean next,last=fig<0;
    if (last) {fig=-fig;count++;}
    int a=2;
    for (int i=1; i<32; i++)
    {
     next= fig%a>0;
     if (next) {fig=fig-a/2;count++;}
     a=2*a;
    }
    next= count<weight;

    count=0;
    fig=config;
    if (last) {fig=-fig;count++;}
    a=2;

    System.out.print("      ") ; printB(next);
    for (int i=1; i<32; i++)
    {
     next= fig%a>0;
     if (next) {fig=fig-a/2;count++;}
     a=2*a;
     printB(next);
     if (i==2 || i==5 || i==12 || i==19 || i==26 || i==29) {System.out.println() ;}
     if (i==2 || i==26 || i==29) {System.out.print("      ") ;};
    }
    printB(last); System.out.println() ;

   }
  }//End HiRiQ class


  //A NODE CREATED FOR USE IN THE BIANARY TREE
  class BinaryNode{

   int val;

   BinaryNode leftChild;
   BinaryNode rightChild;

   BinaryNode(int val){

    this.val = val;
   }
  }//End BinaryNode class

  //BINARY TREE: will be used to check for repetitions when using DFS 
  class BinaryTree{

   BinaryNode root; //root node
   
   //method to add a Node 
   public void addNode(int val){

    BinaryNode newNode = new BinaryNode(val);// initialisation
    if (root == null){

     root = newNode;
    }else{

     BinaryNode focus = root;// will be used to go down the tree
     BinaryNode parent; // parent of the focus node
     
     //Basically runs through the tree node by node 
     //If the node's left and right child are full 
     //then it creates a new node
     while(true){

      parent = focus;

      if (val < focus.val){

       focus = focus.leftChild;

       if (focus == null){

        parent.leftChild = newNode;
        return;
       }
      }else{ // if not a leftchild, it must be a rightchild
       focus = focus.rightChild;

       if (focus == null){

        parent.rightChild = newNode;
        return;
       }
      }

     }
    }
   } // End of addNode

   //Searches for a specific node
   //This method will be the one that tells us
   //whether or not a configuration has already been seen
   public boolean findNode(int val){

    BinaryNode focus = root;//node to cycle through the tree

    while (focus.val != val){//while the value has not been found

     if (val < focus.val){//decides whether to check the leftchild

      focus = focus.leftChild;
     }else{//if it is not a leftChild then it might be a rightCHild

      focus = focus.rightChild;

     }
     if (focus == null){

      return false;//when we reach the end of the tree return 
      //flase, the value is not in the tree
     }

    }
    
    return true;//if found, then return true
   }//End of findNode

  }//End BinaryTree Class


  //Node that will be used to store the HiRiQ, the move done and the parent node
  class Node {

   HiRiQ key;//current HiRiQ, named key because its the link to the 
      // its parent node so it functions like a key
   char [] move;//char array of the move done

   Node parent;//link to parent node

   //constructor, sets the initial values
   public Node(HiRiQ key,char [] move, Node parent){

    this.key = key;
    this.move = move;
    this.parent = parent;
   }
  }//End Node class


  //Linked Node class will be used by the LinkedList class below
  //takes in a generic type S (will be the node above)
  class LinkedNode<S>{

   S current;//current node
   LinkedNode <S> next;//link to the next node

   //Constructor
   public LinkedNode(S s){

    this.current = s;
    this.next = null;
   }

  }//End LinkedNode class

  //LinkedList class will be used to create a stack the
  //will be used in the DFS later on to solve the puzzle
  class LinkedList<S>{

   LinkedNode <S> head;//head of list
   LinkedNode <S> tail;//tail of list
   int size;//size of the list

   //Constructor
   public LinkedList(){
    this.head = null;
    this.tail = null;
    size = 0;
   }

   //adding LinkedNode to the end of the list
   //this was to be used in the BFS algorithm but 
   //it wasn't quick enough
   public void addLast (S s){

    LinkedNode <S> nn = new LinkedNode <S> (s);


    if (size == 0){

     head = nn;   
     tail = nn;
     size++;

    }else{

     tail.next = nn;
     tail = nn;
     size++;
    }

   }//End addLast

   //Adds a LinkedNode to the beginning of the list
   //Used for the stack as a push
   public void addFirst (S s){

    LinkedNode<S> newNode = new LinkedNode<S>(s);
    if (size  == 0){

     tail = newNode;

    }   
    newNode.next = head;
    head = newNode;
    size++;


   }//End addFirst

   //deletes first element in the LinkedList
   //used a a pop in the stack
   public void delFirst(){

    if (size == 0) return;
    head = head.next;
    size--;
   }//End DelFirst

   //The algorithm for BFS
   //not used to solve the HiRiQ
   //It was implemented initially but it was slow and
   //took too much memory
   public void BFS(LinkedList <Node> queue,BinaryTree repetition,boolean [] dumb){
    while (queue.size != 0){

     boolean bMove = true;

     for(int i = 0; i < triplets.length; i++){

      int c = possiblemove(triplets[i],queue.head.current.key.load(dumb));



      if (c != -1 && c != 2 ){

       HiRiQ tmpHiriq = new HiRiQ ((byte)0); 
       boolean [] tmp =  applymove(triplets[i],queue.head.current.key.load(dumb));
       char [] move = moveArray(triplets[i],c);
       tmpHiriq.store(tmp);

       if (!repetition.findNode(tmpHiriq.config)){
        repetition.addNode(tmpHiriq.config);
        Node tmpNode = new Node(tmpHiriq,move,queue.head.current);
        queue.addLast(tmpNode);
        bMove = false;

        if (tmpHiriq.IsSolved()){

         while(tmpNode.parent != null){

          printMove(tmpNode.move);
          System.out.print(" , ");
          tmpNode = tmpNode.parent;
         }
         System.out.println("Solved");
         return;
        }
       }
      } 

     }
     if (bMove){


      for(int i = 0; i < triplets.length; i++){

       int c = possiblemove(triplets[i],queue.head.current.key.load(dumb));



       if (c ==2 ){

        HiRiQ tmpHiriq = new HiRiQ ((byte)0); 
        boolean [] tmp =  applymove(triplets[i],queue.head.current.key.load(dumb));
        char [] move = moveArray(triplets[i],c);
        tmpHiriq.store(tmp);

        if (!repetition.findNode(tmpHiriq.config)){

         repetition.addNode(tmpHiriq.config);
         Node tmpNode = new Node(tmpHiriq,move,queue.head.current);
         queue.addLast(tmpNode);


        }
       } 

      }
     }

     queue.delFirst();

    }
   }//End BFS algorithm

   //DFS Algorithm
   //takes in the stack, a BinaryTree to check for repetitions and a bumby boolean array
   public void DFS(LinkedList <Node> stack,BinaryTree repetition,boolean [] dumb){

    boolean bMove = true;//to check whether or not to perform a bmove
           //this check is used as to prevent stack overflows

    for(int i = 0; i < triplets.length; i++){ // goes through the triplets

     //checks if a move is possible
     int c = possiblemove(triplets[i],stack.head.current.key.load(dumb));
    
     //if possible, do a W move
     if (c != -1 && c != 2 ){

      HiRiQ tmpHiriq = new HiRiQ ((byte)0);//creates a temporary HiRiQ 
      boolean [] tmp =  applymove(triplets[i],stack.head.current.key.load(dumb));//Applys the move to a temporary boolean array
      char [] move = moveArray(triplets[i],c);//saves the move done
      tmpHiriq.store(tmp);//stores the New HiRiQ

      if (!repetition.findNode(tmpHiriq.config)){//if the config hasnt been seen,add to the stack
       repetition.addNode(tmpHiriq.config);//adds to binarytree of repetitions
       Node tmpNode = new Node(tmpHiriq,move,stack.head.current);//creates the node
       stack.addFirst(tmpNode);//adds it to the stack
       bMove = false;//do not do a b move

       if (tmpHiriq.IsSolved()){ // checks if solved

        while(tmpNode.parent != null){

         printMove(tmpNode.move);
         System.out.print(" , ");
         tmpNode = tmpNode.parent;
        }//prints the moves done
        System.out.println("Solved");
        
        return;//returns if solved
       }
       DFS(stack,repetition,dumb);//recursive call of the DFS

       if (stack.head.current.key.IsSolved()){
        return;
       }//used to end the recursion once the solution has been found
      }
     } 

    }
    if (bMove && stack.size  <  40 ){// if bmove is true and a "small" stack size
         //the stack size is set to this as to avoid an stack overflow

     //basically the same as above but checks whether or not to do a b move
     for(int i = 0; i < triplets.length; i++){

      int c = possiblemove(triplets[i],stack.head.current.key.load(dumb));



      if (c == 2 ){

       HiRiQ tmpHiriq = new HiRiQ ((byte)0); 
       boolean [] tmp =  applymove(triplets[i],stack.head.current.key.load(dumb));
       char [] move = moveArray(triplets[i],c);
       tmpHiriq.store(tmp);

       if (!repetition.findNode(tmpHiriq.config)){

        repetition.addNode(tmpHiriq.config);
        Node tmpNode = new Node(tmpHiriq,move,stack.head.current);
        stack.addFirst(tmpNode);

       }
      } 

     }
    }

    stack.delFirst();//pop

   }

  }//end DFS Algorithm


  HiRiQ initial = new HiRiQ((byte)4);//creates the HiRiQ to solve
  boolean [] confy = new boolean [33];//configuration boolean array
  
   // if you want to make a custom config PLEASE store the boolean array into the confy array 
  //and uncomment the next line
  //initial.store(confy); 
  initial.print();//print out the initial HiRiQ to solve

  //Creating queue for BFS 
  //LinkedList <Node> queue = new LinkedList<Node>();
  Node root = new Node (initial,null,null);
  //queue.addFirst(root);

  boolean [] dumb = new boolean [33];

  //creating stack for DFS
  LinkedList <Node> stack = new LinkedList<Node>();
  stack.addFirst(root);

  //Creating BInary Tree to test for repetitions
  BinaryTree repetition = new BinaryTree();
  repetition.addNode(stack.head.current.key.config);
  
  stack.DFS(stack, repetition,dumb);

 }//end Main Method

 //method to help
 //Checks if a move can be made, nothing too special
 public static int possiblemove(byte [] triplet, boolean [] board){

  if (board[triplet[0]] == true && board[triplet[1]] == true && board[triplet[2]] == false){
   return 1;
  }
  if (board[triplet[0]] == false && board[triplet[1]] == true && board[triplet[2]] == true){
   return 1;
  }
  if (board[triplet[0]] == false && board[triplet[1]] == false && board[triplet[2]] == true){
   return 2;
  }
  if (board[triplet[0]] == true && board[triplet[1]] == false && board[triplet[2]] == false){
   return 2;
  }
  return -1;

 }//end possibleMove

 //applys a move, again nothing special
 public static boolean [] applymove(byte [] triplet, boolean [] board){

  board[triplet[0]] = !board[triplet[0]];
  board[triplet[1]] = !board[triplet[1]];
  board[triplet[2]] = !board[triplet[2]];

  return board;

 }//end applymove

 //creates the array for the move and stores it in that array
 public static char [] moveArray(byte [] triplet, int c){

  char [] move = new char [3];
  move [0] = (char)triplet[0];
  move [2] = (char)triplet[2];
 
  //checking if a B or W move
  if (c == 1){
   move [1] = 'W';
  }else {
   move [1] ='B';
  }
  return move;

 }//end moveArray 

 //just prints out the moves
 public static void printMove(char [] move){

  System.out.print((int)move[0]);
  System.out.print(move[1]);
  System.out.print((int)move[2]);

 }//end printMove

}//END PROGRAM
