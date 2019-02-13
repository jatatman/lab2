package lab2;

/*
 * A StringSet is a collection of Strings. 
 * Duplicate strings may not exist in this collection
 * 
 * @note
 * (1) A bags capacity cannot exceed the maximum integer 2,147,483,647 (Integer.MAX_VALUE)
 *
 */

public class StringSet implements Cloneable{
    
    //Invariants of the StringSet class:
    // 1. the actual number of elements in the StringSet is the instance variable numItems
    // 2. The data in an empty collection is unimportant. Data in the collection will be 
    //        stored in the array up to numItems. Data in the array after numItems is 
    //        unimportant
    
    // numITems will keep track of how many items are stored
    // strItems will house the data
    private int numItems;
    private String[] strItems; 
    
    /**
     * Initializes the StringSet with a capacity of 2 
     */
    public StringSet() {
        
        // 
        final int INIT_CAPACITY = 2; 
        strItems = new String[INIT_CAPACITY]; 
        numItems = 0;
        
    } // end StringSet
    
    /**
     * Initializes the StringSet with the passed capacity
     * @param _capacity
     *  sets the capacity of the StringSet
     */
    public StringSet(int _capacity) {
        
        // making sure capacity is greater than 0
        if (_capacity < 0) {
            throw new IllegalArgumentException("_capacity must be greater than 0");
        } // end if
        else {
            strItems = new String[_capacity];
            numItems = 0;
        } // end else
    } // end StringSet
    
    /**
     * Clone method that returns an exact copy of the StringSet 
     * @param obj
     *  StringSet object
     * @precondition 
     *  Object passed must be an instance of StringSet.
     *  Object passed must not be null.
     * @exception  
     * CloneNotSupportedException: Object isn't cloneable.
     * @return
     *  An exact copy of the passed StringSet object. 
     **/
    public StringSet Clone(Object obj) {
        
        // creating object to house copy
        StringSet copySet = null;
        
        if (obj != null && obj instanceof StringSet) {
        
            try {
                copySet = (StringSet) super.clone();
            } // end try
            catch (CloneNotSupportedException e) {
                throw new RuntimeException("Object not cloneable");
            } // end catch
        } // end if
        else {
            System.out.println("Object null or not instance of StringSet");
        } // end else
        
        // creating new cloned array 
        copySet.strItems = strItems.clone();
        return copySet;
    } // end StringSet
    
    /**
     * Accessor method that returns the number of items in the StringSet
     * @return
     *  the number of Items stored in the StringSet
     **/
    public int size() {
        return numItems;
    } // end size
    
    /**
     * Accessor method that returns the total capacity of the StringSet
     * @return
     *  Total capacity of the StringSet
     **/
    public int capacity() {
        return strItems.length;
    } // end capacity 
    
    /**
     * Adds a new value to the StringSet. Capacity of the StringSet is enlarged
     * if space for a new value is unavailable. 
     * @param a
     *  the value to be added to the StringSet
     * @precondition
     *  The String added must not already be contained within the collection.
     *  Value passed must not be null. 
     *   
     */
    public void add(String a) {
        
        // if: checking if a is null 
        // else if: making sure string is not already in the collection
        if (a == null) {
            throw new IllegalArgumentException("Please enter a non null value to add.");
        }
        else if (contains(a) == true) {
            throw new IllegalArgumentException("Value passed already in collection.");
        }
        else {
            
            // ensuring enough capacity for added item
            if (numItems == strItems.length) {
                ensureCapacity(numItems * 2 + 1);
            } // end if
            
            strItems[numItems] = a; 
            numItems++;
        } // end else 
        
    } // end add 
    /**
     * Checks to see if the passed value is already contained in the collection.
     * Returns true if the value is found and false if it is not found. 
     * @param a
     *  value to be checked 
     * @return
     *  true: if the value is found.
     *  false: if the value is not found.
     */
    public boolean contains(String a) {
        
        int occurrences = 0;
        // using numItems vs strItems.length because we are only interested 
        // in the values of the values in the array that are contained up to numItems
        for (int i = 0; i < numItems; i++) {
            if (strItems[i].contentEquals(a)) {
                occurrences++; 
            }
        } // end for
        
        if (occurrences > 0) {
            return true;
        } // end if 
        else {
            return false; 
        } // end else
    } // end contains
    
    /**
     * Changes the capacity of the collection if the value passed is larger than 
     * the amount of items in the collection
     * @param minimumCapacity
     *  New capacity for the collection StringSet
     * @precondition
     *  must be larger than 0
     * @postcondition
     *  The collections capacity will not change if it is already larger than the
     *  value passed.
     */
    public void ensureCapacity(int minimumCapacity) {
        
        
        String[] tempArray; 
        
        if (minimumCapacity < 0) {
            throw new IllegalArgumentException("miminumCapacity must be greater than 0");
        } // end if 
        else if (strItems.length < minimumCapacity) {
            
            tempArray = new String[minimumCapacity];
            System.arraycopy(strItems, 0, tempArray, 0, numItems);
            strItems = tempArray;
            
        } // end else if
        
    } // end ensure capacity 
    
    /**
     * Removes the specified element
     * @param a
     *  element to be removed
     */
    public boolean remove(String a) {
        
        // setting match index to 0 to avoid issue with initialization
        // won't falsely remove the 0 index due to an occurrence of the input string
        // needing to be detected 
        int matchIndex = 0; 
        int occurrences = 0; 
        
        for (int i = 0; i < numItems; i++) {
            if (strItems[i].contentEquals(a)) {
                matchIndex = i; 
                occurrences++; 
            } // end if 
        } // end for
        
        // if an occurrence to remove was found
        if (occurrences > 0 ) {
            // replacing match
            for (int j = matchIndex; j < numItems - 1; j++) {
                
                strItems[j] = strItems[j+1]; 
            }
            numItems--;
            return true; 
            
        } // end if 
        return false; 
    
    } // end remove
    //
    
    /**
     * Adds item to an ordered collection. 
     * @param a
     *  element to be added
     * @precondition
     *  collection must be ordered to use addOrdered
     *  element to add must not be null
     */
    public void addOrdered (String a) {
        
        // defaulting new value be added to the end of the array
        int toIndex = numItems + 1; 
        
        // handling preconditions 
        if (a == null) {
            
            throw new IllegalArgumentException("Value entered must not be null.");
        } // end if 
        else if (contains(a) == true) {
            
            throw new IllegalArgumentException("Item already in collection"); 
        }// end else if 
        else if (checkOrdered() == false) { 
            
            System.out.println("Can't use addOrdered on an unordered collection"); 
        } // end else if 
        else {
            
            // ensure capacity
            if (numItems == strItems.length) {
                ensureCapacity(numItems * 2 + 1);
            } // end if
            
            // determining where the new item belongs 
            for (int i=0; i < numItems; i++) {
                
                if (a.compareTo(strItems[i]) < 0 ) { 
                    
                    toIndex = i;
                    break; 
                } // end if   
            } // end for 
 
            // moving items in array if needed 
            if (toIndex <= numItems) { 
                
                for (int i = numItems; i > toIndex; i--) {
                    
                    strItems[i+1] = strItems[i]; 
                    
                } // end for 
            } // end if 
        
        // adding item to array
        strItems[toIndex] = a;
        numItems++; 
        
        } // end else 
    } // end addOrdered 
    
    /**
     * checks to see if the collection is in an ascending ordered state. Returns true if it is
     * ordered and false if it is not ordered.
     * @return
     *  true: collection is in ascending order
     *  false: collection is unordered
     */
    public boolean checkOrdered() {
        
        int orderedCount = 0; 
        
        for (int i=0; i < numItems-1; i++) {
         
            // ascendency check 
            if (strItems[i].compareTo(strItems[i+1]) < 0) {
                orderedCount++; 
            } // end if
        } // end for 
        
        if (orderedCount == numItems-1) {
          return true; 
        } // end if 
        else {
          return false; 
        }
    
    } // end checkOrdered
    
    public static void main(String[] args) {
        
        // testing no argument constructor
        System.out.println("Testing no argument constructor.");
        
        StringSet colors = new StringSet();
        
        System.out.printf("Number of Items: %d\n", colors.size());
        System.out.printf("Capacity: %d\n\n", colors.capacity());

        
        // adding values until capacity to see if it is automatically increased
        // then printing how many items and capacity 
        colors.add("blue");
        System.out.printf("Number of Items: %d\n",  colors.size());
        System.out.printf("Capacity       : %d\n\n",  colors.capacity());
        colors.add("green");
        System.out.printf("Number of Items: %d\n",  colors.size());
        System.out.printf("Capacity       : %d\n\n",  colors.capacity());
        colors.add("red");
        System.out.printf("Number of Items: %d\n",  colors.size());
        System.out.printf("Capacity       : %d\n\n",  colors.capacity());
        
        // testing constructor with capacity argument
        System.out.println("Testing constructor with capacity argument.");
        
        StringSet shapes = new StringSet(1); 
        
        System.out.printf("Number of shapes: %d\n",  shapes.size());
        System.out.printf("Capacity: %d\n\n", shapes.capacity());
        
        // adding values until capacity to see if it is automatically increased
        // then printing how many items and capacity
        shapes.add("square");
        System.out.printf("Number of Items: %d\n",  shapes.size());
        System.out.printf("Capacity       : %d\n\n",  shapes.capacity());
        shapes.add("triangle");
        System.out.printf("Number of Items: %d\n",  shapes.size());
        System.out.printf("Capacity       : %d\n\n",  shapes.capacity());
        shapes.add("circle");
        System.out.printf("Number of Items: %d\n",  shapes.size());
        System.out.printf("Capacity       : %d\n\n",  shapes.capacity());
        shapes.add("star");
        System.out.printf("Number of Items: %d\n",  shapes.size());
        System.out.printf("Capacity       : %d\n\n",  shapes.capacity());
        
        // testing clone
        System.out.println("Testing Clone"); 
        StringSet blocks = shapes.Clone(shapes);

        System.out.printf("Number of Items: %d\n",  blocks.size());
        System.out.printf("Capacity       : %d\n\n",  blocks.capacity());
        blocks.add("pyramid");
        System.out.printf("Number of Items: %d\n",  blocks.size());
        System.out.printf("Capacity       : %d\n\n",  blocks.capacity());
        
        System.out.println("Making sure shapes didn't change");
        System.out.printf("Number of Items: %d\n",  shapes.size());
        System.out.printf("Capacity       : %d\n\n",  shapes.capacity());
        

        // testing contains
        
        System.out.printf("Contains 'pyramid': %b\n", blocks.contains("pyramid"));
        
        // testing remove
        System.out.println("Testing remove by removing pyramid from blocks\n"); 
        blocks.remove("pyramid"); 
        
        System.out.printf("Number of Items: %d\n",  blocks.size());
        System.out.printf("Capacity       : %d\n\n",  blocks.capacity());
        
        System.out.printf("Contains 'pyramid': %b\n",  blocks.contains("pyramid")); 
        
        // check ordered
        System.out.printf("ordered: %b\n", blocks.checkOrdered());
        System.out.printf("ordered: %b\n", colors.checkOrdered());
        
        blocks.addOrdered("sphere");
        colors.addOrdered("burgundy"); 
        
        System.out.printf("number of Items: %d\n", colors.size()) ;
        System.out.printf("Contains 'burgundy': %b\n", colors.contains("burgundy"));
        
    } // end main

} // end class StringSet
