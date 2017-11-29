package edu.gcccd.csis;

import java.io.*;
import java.util.Iterator;
import java.util.Random;

/**
 * Use the starter code, including the NodeList class, our implementation of a BasicList.
 * <p>
 * We are going to use a very simple lists to store positive long numbers, one list element per digit.
 * The most significant digit is stored in the head element, the least significant digit is stored in the tail.
 * <p>
 * The starter code's main method creates very long numbers.
 * It is your task, to complete the class so that it can calculate the sum of positive very long numbers and
 * store the result in a file.
 * <p>
 * Of course, all methods need to have unit-tests to verify corner cases and happy-paths.
 * For that you may find the java.math.BigInteger class help-full when writing the unit-tests.
 * In the test code you are free to use java classes from all packages.
 * In the implementation of the Project2 class however, you are limited to
 * <p>
 * import java.io.*;
 * import java.util.Iterator;
 * import java.util.Random;
 * Moreover, you need to provide a detailed estimate for how often on average ANY iterator's next() method gets called
 * (depending on the value of L) when addition(Iterator&lt;NodeList&lt;Integer&gt;&gt; iterator) gets called.
 */
public class Project2 {

    static NodeList<Integer> generateNumber(final int maxLength) {
        final NodeList<Integer> nodeList = new NodeList<>();
        final int len = 1 + new Random().nextInt(maxLength);
        for (int i = 0; i < len; i++) {
            nodeList.append(new Random().nextInt(10));
        }
        System.out.print("Generated Number: ");
        print(nodeList);
        return nodeList;
    }

    /**
     * Prints a very long number to System.out
     *
     * @param nodeList NodeList<Integer>
     */
    static void print(final NodeList<Integer> nodeList) {
        for (final Integer i : nodeList) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void main(final String[] args) throws IOException{
        final int L = 30;

        final NodeList<Integer> n1 = generateNumber(L); // (head 1st) e.g. 3457
        final NodeList<Integer> n2 = generateNumber(L); // (head 1st) e.g. 682
        final Project2 project = new Project2();

        print(project.addition(n1, n2)); //  n1+n2, e.g. 4139

        final NodeList<NodeList<Integer>> listOfLists = new NodeList<>();
        for (int i = 0; i < L; i++) {
            listOfLists.append(generateNumber(L));
        }


        project.save(project.addition(listOfLists.iterator()), "result.bin");
        print(project.load("result.bin"));
    }

    /**
     * comment
     */
    public void removeLeadingZeros(NodeList list)
    {
        Iterator<Integer> iter= list.iterator();
        while(iter.hasNext())
        {
            Integer node = iter.next();
            if(list.getLength() == 1){
                return;
            }
            else if(node == 0)
            {
                list.remove(node);
            }
            else
            {
                return;
            }
        }
    }

    /**
     * Add two very long numbers
     *
     * @param nodeList1 NodeList&lt;Integer&gt;
     * @param nodeList2 NodeList&lt;Integer&gt;
     * @return nodeList representing the sum (add) of nodeList1 and nodeList2, without leading '0'
     */
    public NodeList<Integer> addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) {
        NodeList<Integer> returnList = new NodeList<>();
        removeLeadingZeros(nodeList1);
        removeLeadingZeros(nodeList2);

        reverse(nodeList1);
        reverse(nodeList2);
        int length  = 0;
        if (nodeList1.getLength() > nodeList2.getLength()){
            length = nodeList1.getLength();
        } else
        {
            length = nodeList2.getLength();
        }
        Iterator<Integer> node1 = nodeList1.iterator();
        Iterator<Integer> node2 = nodeList2.iterator();
        int carry = 0;
        for(int i = 1; i <= length +1; i++){
            Integer left;
            Integer right;
            if(node1.hasNext()){
                left = node1.next();
            } else
            {
                left = 0;
            }
            if(node2.hasNext()){
                right = node2.next();
            } else
            {
                right = 0;
            }
            Integer sum = (right + left + carry)%10;
            carry = (right + left + carry)/10;
            if(!(right == 0 && left == 0 && sum ==0 && i == (length+1)))
            {
                returnList.append(sum);
            }
        }
        reverse(returnList);
        return returnList;
    }

    /**
     * Add very long numbers
     *
     * @param iterator NodeList&lt;Integer&gt;
     * @return nodeList representing the sum (add) of all very long numbers, without leading '0'
     */
    public NodeList<Integer> addition(Iterator<NodeList<Integer>> iterator) {
        NodeList<Integer> retList = new NodeList<>();
        while(iterator.hasNext())
        {
            NodeList<Integer> nodel = iterator.next();
            retList = addition(retList, nodel);
        }
        return retList;
    }

    /**
     * Saves a very large number as a file
     *
     * @param //nodeList NodeList&lt;Integer&gt;
     * @param //fileName String
     **/


    public static void reverse(NodeList node1){
        Iterator<Integer> iter = node1.iterator();
        Integer node;
        if (node1.getLength() != 0) {
            node = iter.next();
            node1.remove(node);
            reverse(node1);
            node1.append(node);
        }


    }



    public void save(NodeList<Integer> nodeList, String fileName) throws IOException{

        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
        for (final Integer i : nodeList) {
            System.out.print(i);
            dos.writeInt(i);
        }
        dos.close();

    }


    /**
     * Loads a very large number from a file
     *
     * @param fileName String
     * @return NodeList&lt;Integer&gt;
     */
    public NodeList<Integer> load(final String fileName){
        System.out.println();
        Integer sCurrentLine;
        NodeList<Integer> retVal = new NodeList<>();
        try (DataInputStream br = new DataInputStream(new FileInputStream(new File(fileName)))) {

            while (br.available() != 0) {
                sCurrentLine = br.readInt();
                retVal.append(sCurrentLine);

            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }
}


