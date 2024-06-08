import java.util.ArrayList;
import java.util.LinkedList;

public class Comparison {
    public static void main(String[] args) {
        
        final int number_of_elements = 10; // Renamed constant

        // ArrayList Operations
        long memoryBeforeArrayList = measureMemory();
        long startArrayListAdd = System.nanoTime();

        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < number_of_elements; i++) {
            a.add(i);
        }
        long endArrayListAdd = System.nanoTime();

        long memoryAfterArrayList = measureMemory();
        long memoryUsedByArrayList = memoryAfterArrayList - memoryBeforeArrayList;

        long startRemoveArrayList = System.nanoTime();
        a.clear();
        long endRemoveArrayList = System.nanoTime();

        System.out.println("ArrayList:");
        System.out.println("  Execution time: " + (endArrayListAdd - startArrayListAdd) + " nanoseconds");
        System.out.println("  Memory used: " + memoryUsedByArrayList + " bytes");
        System.out.println("  Removal time: " + (endRemoveArrayList - startRemoveArrayList) + " nanoseconds");

        // LinkedList Operations
        long memoryBeforeLinkedList = measureMemory();
        long startLinkedListAdd = System.nanoTime();

        LinkedList<Integer> b = new LinkedList<>();
        for (int i = 0; i < number_of_elements; i++) {
            b.add(i);
        }
        long endLinkedListAdd = System.nanoTime();

        long memoryAfterLinkedList = measureMemory();
        long memoryUsedByLinkedList = memoryAfterLinkedList - memoryBeforeLinkedList;

        long startRemoveLinkedList = System.nanoTime();
        b.clear();
        long endRemoveLinkedList = System.nanoTime();

        System.out.println("LinkedList:");
        System.out.println("  Execution time: " + (endLinkedListAdd - startLinkedListAdd) + " nanoseconds");
        System.out.println("  Memory used: " + memoryUsedByLinkedList + " bytes");
        System.out.println("  Removal time: " + (endRemoveLinkedList - startRemoveLinkedList) + " nanoseconds");

        // Array Operations
        long memoryBeforeArray = measureMemory();
        long startArrayAdd = System.nanoTime();

        int[] arr = new int[number_of_elements];
        for (int i = 0; i < number_of_elements; i++) {
            arr[i] = i;
        }
        long endArrayAdd = System.nanoTime();

        long memoryAfterArray = measureMemory();
        long memoryUsedByArray = memoryAfterArray - memoryBeforeArray;

        long startRemoveArray = System.nanoTime();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        long endRemoveArray = System.nanoTime();

        System.out.println("Array:");
        System.out.println("  Execution time: " + (endArrayAdd - startArrayAdd) + " nanoseconds");
        System.out.println("  Memory used: " + memoryUsedByArray + " bytes");
        System.out.println("  Removal time: " + (endRemoveArray - startRemoveArray) + " nanoseconds");
    }

    // Method to measure memory usage
    public static long measureMemory() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        return runtime.maxMemory() - runtime.freeMemory();
    }
}
