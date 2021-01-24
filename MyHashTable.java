
//package COMP250_A4_W2020;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.*;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets
    private int numBuckets;
    // load factor needed to check for rehashing
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair,each ArrayList is linked to a slot on the array
    private ArrayList<LinkedList<HashPair<K,V>>> buckets;

    private int partition = 0;
    //Array to used

    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS

        this.numEntries = 0;
        this.numBuckets = Math.abs(initialCapacity);
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(); //empty ArrayList

        if(initialCapacity==0 ){ //exceptions
            this.numBuckets = 2;
        }

        for(int i = 0; i<this.numBuckets;i++) { //ArrayList size of initial-capacity with empty LinkedLists
            LinkedList<HashPair<K,V>> inBucket = new LinkedList<HashPair<K,V>>();
            this.buckets.add(inBucket);

        }


        //ADD YOUR CODE ABOVE THIS
    }

    public int size() {
        return this.numEntries;
    }

    public boolean isEmpty() {
        return this.numEntries == 0;
    }

    public int numBuckets() {
        return this.numBuckets;
    }

    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        //System.out.println("hashFunction buckets" + this.numBuckets);
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }

    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE

        if(value==null) {
            System.out.println("NULL VALUE in put method");
        }




        HashPair<K,V> hp = new HashPair<K,V>(key,value);

        if(((double)numEntries/(double)numBuckets)>MAX_LOAD_FACTOR  || ((double)(numEntries+1)/(double)numBuckets)>MAX_LOAD_FACTOR ) {
            rehash();
            //System.out.println("REHASH!");
        }

        int hashValue = hashFunction(key); //index in ArrayList

        LinkedList<HashPair<K,V>> list = this.buckets.get(hashValue);


        for(HashPair<K,V> hh : list) {

            K kei = hh.getKey();

            if(kei.equals(key) && hashFunction(kei)==hashValue) { //if key already inside, then replace old value with new value
                V oldValue = hh.getValue();
                hh.setValue(value);
                return oldValue;
            }

        }

	

        list.add(hp);
        this.numEntries++;

        return null;

        //  ADD YOUR CODE ABOVE HERE
    }


    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */



    public V get(K key) {
        //ADD YOUR CODE BELOW HERE

        int hashValue = hashFunction(key); //index in ArrayList


        LinkedList<HashPair<K,V>> list = this.buckets.get(hashValue);

        for(int i = 0; i< list.size();i++) { //if key already inside, then replace old value with new value

            K kk = (list.get(i)).getKey();




            if(key.getClass().equals("rfe".getClass()) && ((String)kk).compareToIgnoreCase((String)key)==0) {
                //System.out.println("in get method for strings" + key.getClass());
                V Value = list.get(i).getValue();


                return Value;
            }


            if((list.get(i).getKey()).equals(key)) {
                System.out.println("key not a string");
                V Value = list.get(i).getValue();

                return Value;
            }





        }






        return null;

        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE

        int hashValue = hashFunction(key); //index in ArrayList


        LinkedList<HashPair<K,V>> list = this.buckets.get(hashValue); //linkedlist in that index

        for(int i = 0; i< list.size();i++) { //
            if(list.get(i).getKey().equals(key)) {
                V oldrValue = list.get(i).getValue();
                numEntries--;
                list.remove(i);

                return oldrValue;
            }
        }

        return null;

        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE

        this.numBuckets = this.numBuckets*2;
        ArrayList<LinkedList<HashPair<K,V>>> temp = new ArrayList<LinkedList<HashPair<K,V>>>();

        for(int i = 0; i<this.numBuckets;i++) { //new ArrayList of new size with empty LinkedLists
            LinkedList<HashPair<K,V>> inBucket = new LinkedList<HashPair<K,V>>();

            temp.add(inBucket);

        }

        for(int i = 0; i<(this.numBuckets/2);i++) { //iterate through old ArrayList

            LinkedList<HashPair<K,V>> oldBucket = this.buckets.get(i);
            K key = null;


            for(HashPair<K,V> iii : oldBucket) { //iterates through LinkedList inside //not sure why i had to do -1



                HashPair<K,V> oldHash  = iii;

                key = oldHash.getKey();







                //basically put method i made earlier



                int hashValue = hashFunction(key); //new index in ArrayList

                LinkedList<HashPair<K,V>> list = temp.get(hashValue); //gets list at that index  <------adds it to temp

                //there is only one key, therefore no need to iterate through LinkedList to find if the same key is present

                list.add(oldHash);


            }


        }

        this.buckets = temp;

        //this.buckets = (ArrayList<LinkedList<HashPair<K, V>>>)temp.clone();




        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */

    public ArrayList<K> keys() {
        ArrayList<K> temp = new ArrayList<K>();

        ArrayList<LinkedList<HashPair<K,V>>> myB = this.buckets;

        for(LinkedList<HashPair<K,V>> lL : myB) {
            for(HashPair<K,V> hP : lL) {
                temp.add(hP.getKey());
            }

        }

        return temp;
    }



    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */

    @SuppressWarnings("unchecked")
    public ArrayList<V> values() {


        ArrayList<LinkedList<HashPair<K,V>>> ogbuckets = this.buckets;
        MyHashTable<K,V> flippedbuckets = new MyHashTable<K,V>(this.numBuckets);

        ArrayList<V> hashVal = new ArrayList<V>();

        for(LinkedList<HashPair<K,V>> i : ogbuckets) { //O(num of buckets)
            for(HashPair<K,V> ii : i) { //O(1) in theory
                flippedbuckets.put((K)ii.getValue(),(V) ii.getKey());
            }

        }


        for(HashPair<K,V> ii : flippedbuckets) { //O(num buckets)
            hashVal.add((V)	ii.getKey());
        }

        //total run time = O(2m) --> O(m)


        return hashVal;  //hashVal;

    }

    


    /**
     * This method takes as input an object of type MyHashTable with values that
     * are Comparable. It returns an ArrayList containing all the keys from the map,
     * ordered in descending order based on the values they mapped to.
     *
     * The time complexity for this method is O(n^2), where n is the number
     * of pairs in the map.
     */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();


        for (HashPair<K, V> entry : results) { //O(n)
            V element = entry.getValue();
            K toAdd = entry.getKey();
            int i = sortedResults.size() - 1;
            V toCompare = null;


            while (i >= 0) {
                toCompare = results.get(sortedResults.get(i)); //worsecase:O(n)
                if (element.compareTo(toCompare) <= 0 )
                    break;
                i--;
            }
            sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }


    /**
     * This method takes as input an object of type MyHashTable with values that
     * are Comparable. It returns an ArrayList containing all the keys from the map,
     * ordered in descending order based on the values they mapped to.
     *
     * The time complexity for this method is O(n*log(n)), where n is the number
     * of pairs in the map.
     */

    

    private static <K, V extends Comparable<V>>  ArrayList<K> quickSort(ArrayList<HashPair<K,V>> unsort){


        if(unsort.size()<=1) {

            if(unsort.size()==1) {
                ArrayList<K> one = new ArrayList<K>();
                one.add(unsort.get(0).getKey());

                if(one!=null)
                    return one;
            }


        }

        ArrayList<HashPair<K,V>> smaller = new ArrayList<HashPair<K,V>>();
        ArrayList<HashPair<K,V>> bigger = new ArrayList<HashPair<K,V>>();

        HashPair<K,V> pivot = null;
        int pivotI = 0;


        for(HashPair<K,V> hp : unsort ) { //O(unsortsize)
            if(hp.getKey()!=null) { //takes first not null element as pivot
                pivot = hp;
                unsort.remove(pivot); //removes pivot from list, which we will add later
                break;
            }
        }





        if(pivot == null) {
            System.out.println("NULL PIVOT ERROR");
        }



        //O(n)
        for(HashPair<K,V> hp : unsort ) { //O(unsortsize), distributes different inputs between bigger and smaller array

            V hpValue = hp.getValue();
            if(hpValue.compareTo(pivot.getValue()) >0) { //if bigger or equal to pivot

                if(hpValue!=null)
                    bigger.add(hp);
            }
            else {
                if(hpValue!=null)
                    smaller.add(hp);
            }


        }



        ArrayList<K> smallK = new ArrayList<K>();
        if(smaller.size()>=1) { //only sends arrays of at least one element
            //partition++;
            smallK = quickSort(smaller); //O(unsortsize/2)
        }

        ArrayList<K> bigK = new ArrayList<K>(); //O(unsortsize/2)
        if(bigger.size()>=1) { //only sends arrays of at least one element
            bigK = quickSort(bigger);
        }


        if(pivot!=null)
            smallK.add(pivot.getKey()); //adds key to end of smaller array

        boolean success = true;


        if(bigK!=null || bigK.isEmpty()!=true) {
            for(K k : bigK) { //O(unsortsize/2)
                smallK.add(k);
            }
        }

        if(success==false) {
            System.out.println("THE 2 LISTS NOT APPENDED CORRECTLY ERROR in quickSort");
        }




        return smallK;


    }


    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {

        ArrayList<LinkedList<HashPair<K,V>>> unsort = results.buckets;
        ArrayList<K> finalArr = new ArrayList<K>();
        LinkedList<HashPair<K,V>> sort = new LinkedList<HashPair<K,V>>();
        ArrayList<HashPair<K,V>> list = new ArrayList<HashPair<K,V>>();

        for(LinkedList<HashPair<K,V>> Link : unsort){
            for(HashPair<K,V> hp : Link){
                list.add(hp);
            }

        }

        sort = mergeSort(list);

        Iterator<HashPair<K,V>> x = sort.descendingIterator();

        while(x.hasNext()){
            HashPair<K,V> key = (HashPair<K,V>) x.next();
            finalArr.add(key.getKey());
        }


        return finalArr;
    }


    private static <K, V extends Comparable<V>>  LinkedList<HashPair<K,V>> mergeSort(ArrayList<HashPair<K,V>> unsort){

        ArrayList<HashPair<K,V>> list1 = new ArrayList<HashPair<K,V>>();
        ArrayList<HashPair<K,V>> list2 = new ArrayList<HashPair<K,V>>();
        LinkedList<HashPair<K,V>> list11 = new LinkedList<HashPair<K,V>>();
        LinkedList<HashPair<K,V>> list22 = new LinkedList<HashPair<K,V>>();

        if (unsort.size() == 1){
            LinkedList<HashPair<K,V>> list111 = new LinkedList<HashPair<K,V>>();
            list111.add(unsort.get(0));
            return list111;
        }
        else{

            int mid = (unsort.size()-1)/2;


            list1 = getElement(unsort, 0, mid);
            list2 = getElement(unsort, mid+1, unsort.size()-1 );
            list11 = mergeSort(list1);
            list22 = mergeSort(list2);


            //switch

            return merge(list11,list22);

        }

    }

    private static <K, V extends Comparable<V>>  ArrayList<HashPair<K,V>> getElement(ArrayList<HashPair<K,V>> unsort, int iFrom, int iTo) {

        ArrayList<HashPair<K,V>> list = new ArrayList<HashPair<K,V>>();

        for(int i = iFrom; i<=iTo ; i++){
            list.add(unsort.get(i));
        }

        return list;
    }

    private static <K, V extends Comparable<V>>  LinkedList<HashPair<K,V>> merge(LinkedList<HashPair<K,V>> list1, LinkedList<HashPair<K,V>> list2 ){

        LinkedList<HashPair<K,V>> list = new LinkedList<HashPair<K,V>>();

        while(!list1.isEmpty() && !list2.isEmpty()) {

            if (list1.get(0).getValue().compareTo(list2.get(0).getValue()) <0 )
                list.addLast(list1.removeFirst());
            else
                list.addLast(list2.removeFirst());

        }

        while(!list1.isEmpty())
            list.addLast(list1.removeFirst());

        while(!list2.isEmpty())
            list.addLast(list2.removeFirst());


        return list;
    }




    @Override
    public MyHashIterator iterator() { //works pretty well
        return new MyHashIterator();
    }

    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE

        ArrayList<HashPair<K,V>> ar = new ArrayList< >();
        Iterator<HashPair<K,V>> itr = ar.iterator();

        //ADD YOUR CODE ABOVE HERE

        /**
         * Expected average runtime is O(m) where m is the number of buckets
         */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE

            //itr = ar.iterator();

            ArrayList<LinkedList<HashPair<K,V>>> buckets = MyHashTable.this.buckets;

            iterate(buckets);

            itr = ar.iterator();


            //ADD YOUR CODE ABOVE HERE
        }


        private void iterate(ArrayList<LinkedList<HashPair<K,V>>> buckets) {
            //this method has to iterate through everything and add it to the Arraylist ar


            for(LinkedList<HashPair<K,V>> ll : buckets) {
                for(HashPair<K,V> tt : ll) {
                    ar.add(tt);
                }

            }

            return;


        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE

            return itr.hasNext();

            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE


//        	if(itr.hasNext()) {
//
//        		return itr.next();
//        	}

            return itr.next();

            //throw new NoSuchElementException();


            //ADD YOUR CODE ABOVE HERE
        }

    }



}
