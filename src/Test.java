//FibonacciHeap Tester

import java.util.ArrayList;
import java.util.Collections;

public class Test {

    public static void main(String[] args) {
        Test tester = new Test();
        tester.runAllTests();
    }

    public void runAllTests() {
        try {
            // Basic tests
            testBasicOperations();
            testMeld();
            testDecreaseKey();
            testDelete();
            testEdgeCases();
            testStressTest();
            testRandomMeld();
            testRandomDecreaseKey();
            testNumTrees();
            testSize();
            testFindMin();
            testInsert();
            testDeleteMin();
            // Additional tests
            test0_BasicOperations(); // test0
            test1_InsertAndDelete(); // test1
            test2_DeleteMiddleNodes(); // test2
            test3_EdgeCases(); // test3
            test4_LargeInput(); // test4
            test5_DecreaseKeyBehavior(); // test5
            test6_CascadingCuts(); // test6
            test7_LargeRandomInsertions(); // test7
            test8_HeapSizeConsistency(); // test8
            test9_EdgeCaseSingleNode(); // test9
            test10_EdgeCaseEmptyHeap(); // test10
            test11(); // test11
            test12(); // test12
            test13(); // test13
            test14(); // test14
            test15(); // test15
            test16(); // test16
            test17(); // test17
            test18(); // test18
            test19(); // test19
            test20(); // test20
            test21(); // test21
            test22(); // test22
            test23(); // test23
            test24(); // test24
            test25(); // test25
            test26(); // test26
            test27(); // test27
            test28(); // test28
            test29(); // test29
            test30(); // test30
            // Extended test cases
            testLargeBasicOperations();
            testLargeMeld();
            testExtremeDecreaseKey();
            testExtremeDelete();
            testLargeEdgeCases();
            testLargeStressTest();
            System.out.println("All tests passed successfully! ✅");
        } catch (AssertionError e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void testBasicOperations() {
        System.out.println("Testing basic operations...");
        FibonacciHeap heap = new FibonacciHeap();

        heap.insert(4, "four");
        heap.insert(2, "two");
        heap.insert(6, "six");
        assert heap.findMin().key == 2 : "Minimum should be 2";
        assert heap.size() == 3 : "Size should be 3";

        heap.deleteMin();
        assert heap.findMin().key == 4 : "After deleteMin, minimum should be 4";
        assert heap.size() == 2 : "After deleteMin, size should be 2";

        System.out.println("✅ Basic operations test passed");
    }

    private void testMeld() {
        System.out.println("Testing meld operation...");
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        heap1.insert(3, "three");
        heap1.insert(5, "five");
        heap2.insert(1, "one");
        heap2.insert(4, "four");

        int originalSize = heap1.size() + heap2.size();
        heap1.meld(heap2);

        assert heap1.findMin().key == 1 : "After meld, minimum should be 1";
        assert heap1.size() == originalSize : "After meld, size should be sum of original sizes";

        System.out.println("✅ Meld test passed");
    }

    private void testDecreaseKey() {
        System.out.println("Testing decreaseKey operation...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode node1 = heap.insert(5, "five");
        heap.insert(3, "three");

        heap.decreaseKey(node1, 4);
        assert heap.findMin().key == 1 : "After decreaseKey, minimum should be 1";
        assert heap.findMin() == node1 : "After decreaseKey, node1 should be minimum";

        System.out.println("✅ DecreaseKey test passed");
    }

    private void testDelete() {
        System.out.println("Testing delete operation...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode node1 = heap.insert(5, "five");
        heap.insert(3, "three");
        heap.insert(7, "seven");

        int originalSize = heap.size();
        heap.delete(node1);

        assert heap.size() == originalSize - 1 : "After delete, size should decrease by 1";
        assert heap.findMin().key == 3 : "After delete, minimum should be 3";

        System.out.println("✅ Delete test passed");
    }

    private void testEdgeCases() {
        System.out.println("Testing edge cases...");
        FibonacciHeap heap = new FibonacciHeap();

        assert heap.findMin() == null : "Empty heap should have null minimum";
        assert heap.size() == 0 : "Empty heap should have size 0";

        FibonacciHeap.HeapNode node = heap.insert(1, "one");
        assert heap.size() == 1 : "Size should be 1 after single insert";
        heap.delete(node);
        assert heap.size() == 0 : "Size should be 0 after deleting only node";
        assert heap.findMin() == null : "Minimum should be null after deleting only node";

        // These should not throw exceptions
        // TODO: Check if there is a defensive programming requirement
        // heap.delete(null);
        // heap.decreaseKey(null, 1);
        // heap.meld(null);

        System.out.println("✅ Edge cases test passed");
    }

    private void testStressTest() {
        System.out.println("Running stress test...");
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[1000];

        for (int i = 0; i < 1000; i++) {
            nodes[i] = heap.insert(1000 - i, String.valueOf(1000 - i));
        }

        assert heap.size() == 1000 : "Size should be 1000 after insertions";
        assert heap.findMin().key == 1 : "Minimum should be 1";

        for (int i = 0; i < 500; i++) {
            heap.delete(nodes[i]);
        }

        assert heap.size() == 500 : "Size should be 500 after deletions";
        assert heap.findMin().key == 1 : "Minimum should be 1 after deletions";

        for (int i = 500; i < 1000; i++) {
            heap.decreaseKey(nodes[i], 100);
        }

        assert heap.findMin().key == -99 : "Minimum should be -99 after decreaseKey operations";

        System.out.println("✅ Stress test passed");
    }

    private void testRandomMeld() {
        System.out.println("Testing random meld...");
        java.util.Random rand = new java.util.Random(42);

        for (int test = 0; test < 10; test++) {
            FibonacciHeap heap1 = new FibonacciHeap();
            FibonacciHeap heap2 = new FibonacciHeap();

            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
            int size1 = rand.nextInt(20) + 1;
            int size2 = rand.nextInt(20) + 1;

            for (int i = 0; i < size1; i++) {
                int key = rand.nextInt(1000);
                heap1.insert(key, "key" + key);
                min1 = Math.min(min1, key);
            }

            for (int i = 0; i < size2; i++) {
                int key = rand.nextInt(1000);
                heap2.insert(key, "key" + key);
                min2 = Math.min(min2, key);
            }

            int expectedMin = Math.min(min1, min2);
            heap1.meld(heap2);

            assert heap1.findMin().key == expectedMin : "Incorrect minimum after random meld";
            assert heap1.size() == size1 + size2 : "Incorrect size after random meld";
        }
        System.out.println("✅ Random meld test passed");
    }

    private void testRandomDecreaseKey() {
        System.out.println("Testing random decreaseKey...");
        FibonacciHeap heap = new FibonacciHeap();
        java.util.Random rand = new java.util.Random(42);

        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[50];
        int[] keys = new int[50];

        for (int i = 0; i < 50; i++) {
            int key = rand.nextInt(1000) + 1000;
            nodes[i] = heap.insert(key, "key" + key);
            keys[i] = key;
        }

        for (int i = 0; i < 100; i++) {
            int nodeIndex = rand.nextInt(50);
            int decreaseAmount = rand.nextInt(keys[nodeIndex]);

            heap.decreaseKey(nodes[nodeIndex], decreaseAmount);
            keys[nodeIndex] -= decreaseAmount;

            int expectedMin = Integer.MAX_VALUE;
            for (int key : keys) {
                expectedMin = Math.min(expectedMin, key);
            }

            assert heap.findMin().key == expectedMin
                    : "Incorrect minimum after random decreaseKey. Expected: " + expectedMin;
        }
        System.out.println("✅ Random decreaseKey test passed");
    }

    private void testNumTrees() {
        System.out.println("Testing number of trees...");
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[10];

        for (int i = 0; i < 10; i++) {
            nodes[i] = heap.insert(i, "key" + i);
        }

        assert heap.numTrees() == 10 : "Initial number of trees should be 10";

        heap.deleteMin();
        heap.deleteMin();
        heap.deleteMin();

        assert heap.numTrees() == 3 : "Number of trees after delete operations should be 3";

        FibonacciHeap heap2 = new FibonacciHeap();
        for (int i = 0; i < 3; i++) {
            heap2.insert(i, "key" + i);
        }

        heap.meld(heap2);
        assert heap.numTrees() == 6 : "Number of trees after meld should be 6";

        System.out.println("✅ Number of trees test passed");
    }

    private void testSize() {
        System.out.println("Testing size...");
        FibonacciHeap heap = new FibonacciHeap();
        assert heap.size() == 0 : "Initial size should be 0";

        heap.insert(1, "one");
        assert heap.size() == 1 : "Size should be 1 after insert";

        heap.insert(2, "two");
        assert heap.size() == 2 : "Size should be 2 after another insert";

        heap.deleteMin();
        assert heap.size() == 1 : "Size should be 1 after deleteMin";

        heap.deleteMin();
        assert heap.size() == 0 : "Size should be 0 after another deleteMin";

        System.out.println("✅ Size test passed");
    }

    private void testFindMin() {
        System.out.println("Testing findMin...");
        FibonacciHeap heap = new FibonacciHeap();
        assert heap.findMin() == null : "Empty heap should have null minimum";

        heap.insert(1, "one");
        assert heap.findMin().key == 1 : "Minimum should be 1 after insert";

        heap.insert(2, "two");
        assert heap.findMin().key == 1 : "Minimum should be 1 after another insert";

        heap.deleteMin();
        assert heap.findMin().key == 2 : "Minimum should be 2 after deleteMin";

        heap.insert(0, "zero");
        assert heap.findMin().key == 0 : "Minimum should be 0 after insert";

        System.out.println("✅ FindMin test passed");
    }

    private void testInsert() {
        System.out.println("Testing insert...");
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.HeapNode node = heap.insert(1, "one");

        assert heap.size() == 1 : "Size should be 1 after insert";
        assert heap.findMin().key == 1 : "Minimum should be 1 after insert";
        assert heap.findMin() == node : "Node should be minimum after insert";

        System.out.println("✅ Insert test passed");
    }

    private void testDeleteMin() {
        System.out.println("Testing deleteMin...");
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(1, "one");
        heap.insert(2, "two");
        heap.insert(3, "three");

        heap.deleteMin();
        assert heap.findMin().key == 2 : "Minimum should be 2 after deleteMin";
        assert heap.size() == 2 : "Size should be 2 after deleteMin";

        heap.deleteMin();
        assert heap.findMin().key == 3 : "Minimum should be 3 after another deleteMin";
        assert heap.size() == 1 : "Size should be 1 after another deleteMin";

        heap.deleteMin();
        assert heap.findMin() == null : "Minimum should be null after last deleteMin";
        assert heap.size() == 0 : "Size should be 0 after last deleteMin";

        // This should not throw an exception
        heap.deleteMin();

        java.util.Random rand = new java.util.Random(42);
        heap.insert(100, "thousand");
        for (int i = 0; i < 99; i++)
            heap.insert(rand.nextInt(99), "key" + i);
        for (int i = 0; i < 99; i++)
            heap.deleteMin();
        assert heap.findMin().key == 100 : "Minimum should be 1000 after deleting random nodes";

        System.out.println("✅ DeleteMin test passed");
    }

    private void test0_BasicOperations() { // test0
        System.out.println("Testing basic operations...");
        FibonacciHeap heap = new FibonacciHeap();

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        for (int num : numbers) {
            heap.insert(num, "key" + num);
        }

        for (int i = 0; i < 100; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Basic operations test passed, test0");
    }

    private void test1_InsertAndDelete() { // test1
        System.out.println("Testing insert and delete...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 1; i <= 10; i++) {
            heap.insert(i, "key" + i);
        }

        for (int i = 1; i <= 10; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Insert and delete test passed, test1");
    }

    private void test2_DeleteMiddleNodes() { // test2
        System.out.println("Testing deletion of middle nodes...");
        FibonacciHeap heap = new FibonacciHeap();

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            nodes.add(heap.insert(i, "key" + i));
        }

        heap.delete(nodes.get(4)); // Deleting node with key 5

        for (int i = 1; i <= 4; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        for (int i = 6; i <= 10; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Middle node deletion test passed, test2");
    }

    private void test3_EdgeCases() { // test3
        System.out.println("Testing edge cases...");
        FibonacciHeap heap = new FibonacciHeap();

        assert heap.findMin() == null : "Minimum of empty heap should be null";
        assert heap.isEmpty() : "Heap should be empty initially";

        FibonacciHeap.HeapNode node = heap.insert(1, "key1");
        heap.delete(node);

        assert heap.findMin() == null : "Heap should be empty after deleting the only node";
        assert heap.isEmpty() : "Heap should be empty after deleting the only node";
        System.out.println("✅ Edge cases test passed, test3");
    }

    private void test4_LargeInput() { // test4
        System.out.println("Testing large input handling...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 1000; i >= 1; i--) {
            heap.insert(i, "key" + i);
        }

        for (int i = 1; i <= 1000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Large input test passed, test4");
    }

    private void test5_DecreaseKeyBehavior() { // test5
        System.out.println("Testing decreaseKey operation...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode node1 = heap.insert(10, "key10");
        FibonacciHeap.HeapNode node2 = heap.insert(20, "key20");

        heap.decreaseKey(node2, 15);
        assert heap.findMin().key == 10 : "Minimum should still be 10";

        heap.decreaseKey(node2, 5);
        assert heap.findMin().key == 5 : "Minimum should now be 5";
        System.out.println("✅ DecreaseKey behavior test passed, test5");
    }

    private void test6_CascadingCuts() { // test6
        System.out.println("Testing cascading cuts...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode node1 = heap.insert(10, "key10");
        FibonacciHeap.HeapNode node2 = heap.insert(20, "key20");
        FibonacciHeap.HeapNode node3 = heap.insert(30, "key30");

        heap.decreaseKey(node3, 15); // node3 becomes 15, still not the minimum
        heap.decreaseKey(node2, 5); // node2 becomes 5 and should trigger cuts

        assert heap.findMin().key == 5 : "Minimum should now be 5";
        System.out.println("✅ Cascading cuts test passed, test6");
    }

    private void test7_LargeRandomInsertions() { // test7
        System.out.println("Testing large random insertions...");
        FibonacciHeap heap = new FibonacciHeap();

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        for (int num : numbers) {
            heap.insert(num, "key" + num);
        }

        for (int i = 1; i <= 10000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Large random insertions test passed, test7");
    }

    private void test8_HeapSizeConsistency() { // test8
        System.out.println("Testing heap size consistency...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 1; i <= 500; i++) {
            heap.insert(i, "key" + i);
            assert heap.size() == i : "Heap size should be " + i;
        }

        for (int i = 1; i <= 500; i++) {
            heap.deleteMin();
            assert heap.size() == 500 - i : "Heap size should be " + (500 - i);
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Heap size consistency test passed, test8");
    }

    private void test9_EdgeCaseSingleNode() { // test9
        System.out.println("Testing edge case with a single node...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode node = heap.insert(42, "key42");
        assert heap.findMin() == node : "Minimum should be the only node";

        heap.delete(node);
        assert heap.isEmpty() : "Heap should be empty after deleting the only node";
        System.out.println("✅ Single node edge case test passed, test9");
    }

    private void test10_EdgeCaseEmptyHeap() { // test10
        System.out.println("Testing edge case with an empty heap...");
        FibonacciHeap heap = new FibonacciHeap();

        assert heap.findMin() == null : "Minimum of empty heap should be null";
        assert heap.size() == 0 : "Size of empty heap should be 0";

        System.out.println("✅ Empty heap edge case test passed, test10");
    }

    private void test11() { // test11
        System.out.println("Running test11...");
        FibonacciHeap heap = new FibonacciHeap();
        addKeys(heap, 1000);
        FibonacciHeap.HeapNode h = heap.insert(9999, "9999");
        heap.decreaseKey(h, 9999);

        assert heap.findMin().key == 0 : "Minimum should be 0 after decreaseKey";

        heap.deleteMin();
        for (int i = 1000; i < 2000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test11 passed");
    }

    private void test12() { // test12
        System.out.println("Running test12...");
        FibonacciHeap heap = new FibonacciHeap();
        addKeys(heap, 1000);
        FibonacciHeap.HeapNode h = heap.insert(5000, "5000");
        heap.decreaseKey(h, 4000);

        for (int i = 0; i < 2; i++) {
            assert heap.findMin().key == 1000 : "Minimum should be 1000";
            heap.deleteMin();
        }

        for (int i = 1001; i < 2000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test12 passed");
    }

    private void test13() { // test13
        System.out.println("Running test13...");
        FibonacciHeap heap = new FibonacciHeap();
        addKeys(heap, 1000);
        FibonacciHeap.HeapNode h = heap.insert(9000, "9000");
        heap.decreaseKey(h, 4000);

        for (int i = 1000; i < 2000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.findMin().key == 5000 : "Minimum should now be 5000";
        heap.deleteMin();

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test13 passed");
    }

    private void test14() { // test14
        System.out.println("Running test14...");
        FibonacciHeap heap = new FibonacciHeap();
        addKeys(heap, 1000);
        addKeysReverse(heap, 7000);
        FibonacciHeap.HeapNode h = heap.insert(9000, "9000");
        heap.decreaseKey(h, 4000);

        for (int i = 1000; i < 2000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.findMin().key == 5000 : "Minimum should now be 5000";
        heap.deleteMin();

        for (int i = 7000; i < 8000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test14 passed");
    }

    private void test15() { // test15
        System.out.println("Running test15...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 1000; i < 10000; i += 1000) {
            addKeys(heap, i);
        }

        heap.deleteMin();

        FibonacciHeap.HeapNode h = heap.insert(99999, "99999");
        heap.decreaseKey(h, 99999);

        assert heap.findMin().key == 0 : "Minimum should be 0 after decreaseKey";
        heap.deleteMin();

        for (int i = 1001; i < 10000; i++) {
            assert heap.findMin().key == i : "Minimum should be " + i;
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test15 passed");
    }

    private void test16() { // test16
        System.out.println("Running test16...");
        FibonacciHeap heap = new FibonacciHeap();

        int cuts = heap.totalCuts();
        int links = heap.totalLinks();

        heap.insert(1, "1");
        heap.insert(2, "2");
        heap.insert(3, "3");

        assert heap.potential() == 3 : "Potential should be 3";
        assert heap.totalCuts() - cuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";
        assert heap.countersRep()[0] == 3 : "All nodes should be single trees";

        System.out.println("✅ Test16 passed");
    }

    private void test17() { // test17
        System.out.println("Running test17...");
        FibonacciHeap heap = new FibonacciHeap();

        int cuts = heap.totalCuts();
        int links = heap.totalLinks();

        heap.insert(1, "1");
        heap.insert(2, "2");
        heap.insert(3, "3");
        heap.deleteMin();

        assert heap.potential() == 1 : "Potential should be 1 after deleteMin";
        assert heap.totalCuts() - cuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 1 : "One link should have occurred";
        assert heap.countersRep()[0] == 0 : "No trees of rank 0";
        assert heap.countersRep()[1] == 1 : "One tree of rank 1";

        System.out.println("✅ Test17 passed");
    }

    private void test18() { // test18
        System.out.println("Running test18...");
        FibonacciHeap heap = new FibonacciHeap();

        int cuts = heap.totalCuts();
        int links = heap.totalLinks();

        heap.insert(4, "4");
        heap.insert(5, "5");
        heap.insert(6, "6");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.insert(2, "2");
        heap.insert(3, "3");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.deleteMin();

        assert heap.potential() == 1 : "Potential should be 1 after operations";
        assert heap.totalCuts() - cuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 3 : "Three links should have occurred";
        assert heap.countersRep()[0] == 0 : "No trees of rank 0";
        assert heap.countersRep()[2] == 1 : "One tree of rank 2";

        System.out.println("✅ Test18 passed");
    }

    private void test19() { // test19
        System.out.println("Running test19...");
        FibonacciHeap heap = new FibonacciHeap();

        int cuts = heap.totalCuts();
        int links = heap.totalLinks();

        heap.insert(4, "4");
        heap.insert(5, "5");
        FibonacciHeap.HeapNode node = heap.insert(6, "6");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.insert(2, "2");
        heap.insert(3, "3");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.deleteMin();

        heap.decreaseKey(node, 2);

        assert heap.potential() == 4 : "Potential should be 4 after operations";
        assert heap.totalCuts() - cuts == 1 : "One cut should have occurred";
        assert heap.totalLinks() - links == 3 : "Three links should have occurred";

        System.out.println("✅ Test19 passed");
    }

    private void test20() { // test20
        System.out.println("Running test20...");
        FibonacciHeap heap = new FibonacciHeap();

        heap.insert(4, "4");
        FibonacciHeap.HeapNode node5 = heap.insert(5, "5");
        FibonacciHeap.HeapNode node6 = heap.insert(6, "6");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.insert(2, "2");
        heap.insert(3, "3");
        heap.deleteMin();

        heap.insert(1, "1");
        heap.deleteMin();

        int cuts = heap.totalCuts();
        int links = heap.totalLinks();

        heap.decreaseKey(node6, 2);
        heap.decreaseKey(node5, 1);

        assert heap.potential() == 4 : "Potential should be 4 after operations";
        assert heap.totalCuts() - cuts == 1 : "One cut should have occurred";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";

        System.out.println("✅ Test20 passed");
    }

    private void test21() { // test21
        System.out.println("Running test21...");
        FibonacciHeap heap = new FibonacciHeap();

        int treeSize = 32768;
        int sizeToDelete = 1000;

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = treeSize; i < treeSize * 2; i++) {
            nodes.add(heap.insert(i, String.valueOf(i)));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.deleteMin();
        }

        assert heap.potential() == 1 : "Potential should be 1 after deletions";
        System.out.println("✅ Test21 passed");
    }

    private void test22() { // test22
        System.out.println("Running test22...");
        FibonacciHeap heap = new FibonacciHeap();

        int treeSize = 32768;
        int sizeToDelete = 1000;

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = treeSize; i < treeSize * 2; i++) {
            nodes.add(heap.insert(i, String.valueOf(i)));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            heap.deleteMin();
        }

        assert heap.potential() == 1 : "Potential should be 1 after deletions";

        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        boolean noCascading = true;
        int iterationCuts;

        Collections.shuffle(nodes);

        for (int i = 0; i < treeSize; i++) {
            iterationCuts = heap.totalCuts();

            heap.decreaseKey(nodes.get(i), nodes.get(i).key - (treeSize - i));

            if (heap.totalCuts() - iterationCuts > 1)
                noCascading = false;
        }

        assert heap.potential() == treeSize : "Potential should match tree size";
        assert heap.totalCuts() - totalCuts == treeSize - 1 : "Cuts count mismatch";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";
        assert noCascading : "Cascading cuts detected";

        System.out.println("✅ Test22 passed");
    }

    private void test23() { // test23
        System.out.println("Running test23...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 1000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        assert heap.potential() == size : "Potential should match the size";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";

        System.out.println("✅ Test23 passed");
    }

    private void test24() { // test24
        System.out.println("Running test24...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 2000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        assert heap.potential() == size : "Potential should match the size";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";

        System.out.println("✅ Test24 passed");
    }

    private void test25() { // test25
        System.out.println("Running test25...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 3000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        assert heap.potential() == size : "Potential should match the size";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links == 0 : "No links should have occurred";

        System.out.println("✅ Test25 passed");
    }

    private void test26() { // test26
        System.out.println("Running test26...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 1000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < size / 2; i++) {
            assert heap.findMin().key == i + 1 : "Minimum should match";
            heap.deleteMin();
        }

        assert heap.potential() > 100 : "Potential should be greater than 100";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links < size - 100 : "Links count mismatch";

        System.out.println("✅ Test26 passed");
    }

    private void test27() { // test27
        System.out.println("Running test27...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 2000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < size / 2; i++) {
            assert heap.findMin().key == i + 1 : "Minimum should match";
            heap.deleteMin();
        }

        assert heap.potential() > 100 : "Potential should be greater than 100";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links < size - 100 : "Links count mismatch";

        System.out.println("✅ Test27 passed");
    }

    private void test28() { // test28
        System.out.println("Running test28...");
        FibonacciHeap heap = new FibonacciHeap();

        int size = 3000;
        int totalCuts = heap.totalCuts();
        int links = heap.totalLinks();

        for (int i = size; i > 0; i--) {
            heap.insert(i, String.valueOf(i));
        }

        for (int i = 0; i < size / 2; i++) {
            assert heap.findMin().key == i + 1 : "Minimum should match";
            heap.deleteMin();
        }

        assert heap.potential() > 100 : "Potential should be greater than 100";
        assert heap.totalCuts() - totalCuts == 0 : "No cuts should have occurred";
        assert heap.totalLinks() - links < size - 100 : "Links count mismatch";

        System.out.println("✅ Test28 passed");
    }

    private void test29() { // test29
        System.out.println("Running test29...");
        FibonacciHeap heap = new FibonacciHeap();

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            nodes.add(heap.insert(i, "key" + i));
        }

        for (int i = 0; i < 500; i++) {
            heap.delete(nodes.get(i));
        }

        for (int i = 500; i < 1000; i++) {
            assert heap.findMin().key == i : "Minimum should match";
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test29 passed");
    }

    private void test30() { // test30
        System.out.println("Running test30...");
        FibonacciHeap heap = new FibonacciHeap();

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = 1000; i > 0; i--) {
            nodes.add(heap.insert(i, "key" + i));
        }

        heap.delete(nodes.get(500)); // Deleting a middle node

        for (int i = 1; i < 500; i++) {
            assert heap.findMin().key == i : "Minimum should match";
            heap.deleteMin();
        }

        for (int i = 501; i <= 1000; i++) {
            assert heap.findMin().key == i : "Minimum should match";
            heap.deleteMin();
        }

        assert heap.isEmpty() : "Heap should be empty after all deletions";
        System.out.println("✅ Test30 passed");
    }

    private void addKeys(FibonacciHeap heap, int start) {
        for (int i = start; i < start + 1000; i++) {
            heap.insert(i, "key" + i);
        }
    }

    private void addKeysReverse(FibonacciHeap heap, int start) {
        for (int i = start + 999; i >= start; i--) {
            heap.insert(i, "key" + i);
        }
    }

    private void testLargeBasicOperations() {
        System.out.println("Testing large basic operations...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 1; i <= 10000; i++) {
            heap.insert(i, "key" + i);
        }

        assert heap.findMin().key == 1 : "Minimum should be 1";
        assert heap.size() == 10000 : "Size should be 10000";

        for (int i = 0; i < 5000; i++) {
            heap.deleteMin();
        }

        assert heap.findMin().key == 5001 : "Minimum should be 5001 after deleting 5000 elements";
        assert heap.size() == 5000 : "Size should be 5000 after deleting 5000 elements";

        System.out.println("✅ Large basic operations test passed");
    }

    private void testLargeMeld() {
        System.out.println("Testing large meld operation...");
        FibonacciHeap heap1 = new FibonacciHeap();
        FibonacciHeap heap2 = new FibonacciHeap();

        for (int i = 1; i <= 5000; i++) {
            heap1.insert(i, "key" + i);
            heap2.insert(5000 + i, "key" + (5000 + i));
        }

        heap1.meld(heap2);

        assert heap1.findMin().key == 1 : "Minimum should be 1 after meld";
        assert heap1.size() == 10000 : "Size should be 10000 after meld";

        System.out.println("✅ Large meld test passed");
    }

    private void testExtremeDecreaseKey() {
        System.out.println("Testing extreme decreaseKey operation...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[10000];

        for (int i = 0; i < 10000; i++) {
            nodes[i] = heap.insert(10000 - i, "key" + (10000 - i));
        }

        heap.decreaseKey(nodes[9999], 9999);

        assert heap.findMin().key == 1 : "Minimum should be 1 after decreaseKey";
        assert heap.findMin() == nodes[9999] : "Node with key 1 should be the minimum";

        System.out.println("✅ Extreme decreaseKey test passed");
    }

    private void testExtremeDelete() {
        System.out.println("Testing extreme delete operation...");
        FibonacciHeap heap = new FibonacciHeap();

        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[10000];

        for (int i = 1; i <= 10000; i++) {
            nodes[i - 1] = heap.insert(i, "key" + i);
        }

        for (int i = 9999; i >= 0; i--) {
            heap.delete(nodes[i]);
        }

        assert heap.size() == 0 : "Heap size should be 0 after deleting all elements";
        assert heap.findMin() == null : "Minimum should be null after deleting all elements";

        System.out.println("✅ Extreme delete test passed");
    }

    private void testLargeEdgeCases() {
        System.out.println("Testing large edge cases...");
        FibonacciHeap heap = new FibonacciHeap();

        assert heap.findMin() == null : "Empty heap should have null minimum";
        assert heap.size() == 0 : "Empty heap should have size 0";

        FibonacciHeap.HeapNode node = heap.insert(Integer.MAX_VALUE, "maxValue");
        assert heap.size() == 1 : "Size should be 1 after single insert";
        assert heap.findMin().key == Integer.MAX_VALUE : "Minimum should be the max integer value";

        heap.decreaseKey(node, Integer.MAX_VALUE - 1);
        assert heap.findMin().key == 1 : "Minimum should be 1 after decreasing key to 1";

        heap.delete(node);
        assert heap.size() == 0 : "Heap size should be 0 after deleting only node";

        System.out.println("✅ Large edge cases test passed");
    }

    private void testLargeStressTest() {
        System.out.println("Running large stress test...");
        FibonacciHeap heap = new FibonacciHeap();

        for (int i = 0; i < 100000; i++) {
            heap.insert(i, "key" + i);
        }

        assert heap.size() == 100000 : "Heap size should be 100000 after insertions";
        assert heap.findMin().key == 0 : "Minimum should be 0";

        for (int i = 0; i < 50000; i++) {
            heap.deleteMin();
        }

        assert heap.size() == 50000 : "Heap size should be 50000 after 50000 deletions";
        assert heap.findMin().key == 50000 : "Minimum should be 50000 after deletions";

        System.out.println("✅ Large stress test passed");
    }
}
