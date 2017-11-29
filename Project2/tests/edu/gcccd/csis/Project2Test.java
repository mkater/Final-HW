package edu.gcccd.csis;

import org.junit.Test;
import java.math.BigInteger;
import static org.junit.Assert.assertEquals;

public class Project2Test {

    private static BigInteger genBigInteger(final NodeList<Integer> nodeList) {
        final StringBuilder sb = new StringBuilder();
        for (final int i : nodeList) {
            sb.append(i);
        }
        return new BigInteger(sb.toString());
    }

    private static NodeList<Integer> genNodeList(final String s) { // "100" .. '1','0','0'
        final NodeList<Integer> nodeList = new NodeList<>();
        for (final char c : s.toCharArray()) {
            nodeList.append(Character.getNumericValue(c)); // '0' ..'9'
        }
        return nodeList;
    }

    /**
     * Adding two long integer values
     */
    @Test
    public void testAddition() {

        final NodeList<Integer> n1 = Project2.generateNumber(30);
        final NodeList<Integer> n2 = Project2.generateNumber(30);

        final BigInteger N1 = genBigInteger(n1);
        final BigInteger N2 = genBigInteger(n2);

        final NodeList<Integer> n3 = new Project2().addition(n1, n2);
        final BigInteger N3 = N1.add(N2);

        assertEquals(N3, genBigInteger(n3));
    }

    /**
     * Adding integer values corner cases
     */
    @Test
    public void testAdditionCC() {
        NodeList<Integer> n1 = genNodeList("007");
        NodeList<Integer> n2 = genNodeList("10");

        NodeList<Integer> n3 = new Project2().addition(n1, n2);
        assertEquals(new BigInteger("17"),genBigInteger(n3));

        // no leading 0s
        assertEquals(2, n3.getLength());

        // app does not crash on empty lists ...
        n1 = new NodeList<>();
        n2 = new NodeList<>();
        n3 = new Project2().addition(n1, n2);

        // 0 + 0 = 0
        n1 = genNodeList("0");
        n2 = genNodeList("0");
        n3 = new Project2().addition(n1, n2);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));

        // 0 + empty list  = 0
        n2 = new NodeList<>();
        n3 = new Project2().addition(n1, n2);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));

        // empty list + 0  = 0
        n3 = new Project2().addition(n2, n1);
        assertEquals(1, n3.getLength());
        assertEquals(new BigInteger("0"), genBigInteger(n3));
    }

    /**
     * Adding several long integer values
     */
    @Test
    public void testAdditionIter() {
        // fun 12345679 * 72 = 888,888,888
        NodeList<NodeList<Integer>> list = new NodeList<>(); // 72 nodelist all repre. 12345679
        for (int i = 0; i < 72; i++) {
            list.append(genNodeList("12345679"));
        }
        NodeList<Integer> result = new Project2().addition(list.iterator());
        assertEquals(new BigInteger("888888888"), genBigInteger(result));


    }
}
