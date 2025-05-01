package Part2_TariffListLinkedListSystem;

// Assignment 3
// Question: TariffList with Linked List Implementation for Part 2
// Written by: Huseyin Pilavci, 40312242

import java.util.NoSuchElementException;

/**
 * TariffList represents a custom singly linked list of Tariff objects,
 * implementing the TariffPolicy interface for trade evaluation.
 */
public class TariffList implements TariffPolicy {

    /**
     * Inner class representing a node in the TariffList.
     */
    class TariffNode {
        private Tariff data;
        private TariffNode next;

        /**
         * Default constructor: initializes both data and next to null.
         */
        public TariffNode() {
            this.data = null;
            this.next = null;
        }

        /**
         * Parameterized constructor: deep copies the Tariff object.
         */
        public TariffNode(Tariff data, TariffNode next) {
            this.data = data.clone();  // Defensive copy
            this.next = next;
        }

        /**
         * Copy constructor: deep copies another TariffNode.
         */
        public TariffNode(TariffNode other) {
            this.data = other.data.clone();  // Deep copy
            this.next = null;
        }

        /**
         * Clones this node.
         */
        public TariffNode clone() {
            return new TariffNode(this);
        }

        /**
         * Checks if this node is equal to another.
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof TariffNode)) return false;
            TariffNode other = (TariffNode) obj;
            return this.data.equals(other.data);
        }

        // === Privacy Leak Warning ===
        // This method may result in a privacy leak since it returns a direct reference
        // to an internal mutable Tariff object.
        public Tariff getData() { return data; }

        public TariffNode getNext() { return next; }

        public void setData(Tariff data) { this.data = data; }

        public void setNext(TariffNode next) { this.next = next; }
    }

    private TariffNode head;
    private int size;

    /**
     * Default constructor: creates an empty TariffList.
     */
    public TariffList() {
        head = null;
        size = 0;
    }

    /**
     * Copy constructor: deep copies another TariffList.
     */
    public TariffList(TariffList other) {
        if (other.head == null) {
            this.head = null;
            this.size = 0;
        } else {
            this.head = new TariffNode(other.head);
            TariffNode current = this.head;
            TariffNode otherCurrent = other.head.getNext();

            while (otherCurrent != null) {
                current.setNext(new TariffNode(otherCurrent));
                current = current.getNext();
                otherCurrent = otherCurrent.getNext();
            }
            this.size = other.size;
        }
    }

    /**
     * Adds a Tariff to the start of the list.
     * @param tariff the Tariff to add
     */
    public void addToStart(Tariff tariff) {
        head = new TariffNode(tariff, head);
        size++;
    }

    /**
     * Inserts a Tariff at a specified index.
     * @throws NoSuchElementException if index is invalid
     */
    public void insertAtIndex(Tariff tariff, int index) {
        if (index < 0 || index > size)
            throw new NoSuchElementException("Invalid index: " + index);

        if (index == 0) {
            addToStart(tariff);
            return;
        }

        TariffNode prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.getNext();
        }

        TariffNode newNode = new TariffNode(tariff, prev.getNext());
        prev.setNext(newNode);
        size++;
    }

    /**
     * Deletes a node at a given index.
     * @throws NoSuchElementException if index is invalid
     */
    public void deleteFromIndex(int index) {
        if (index < 0 || index >= size)
            throw new NoSuchElementException("Invalid index: " + index);

        if (index == 0) {
            deleteFromStart();
            return;
        }

        TariffNode prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.getNext();
        }

        prev.setNext(prev.getNext().getNext());
        size--;
    }

    /**
     * Deletes the first node in the list.
     */
    public void deleteFromStart() {
        if (head != null) {
            head = head.getNext();
            size--;
        }
    }

    /**
     * Replaces a Tariff at a specific index with a new one.
     */
    public void replaceAtIndex(Tariff tariff, int index) {
        if (index < 0 || index >= size)
            return;

        TariffNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        current.setData(tariff.clone());
    }

    /**
     * Searches for a node matching the given origin, destination, and category.
     * Displays how many iterations it took.
     *
     * Privacy Leak Warning
     * This method returns a direct reference to an internal node, which may result
     * in a privacy leak if the caller modifies the node or its data,
     * return copy or clone of data.
     */
    public TariffNode find(String origin, String destination, String category) {
        TariffNode current = head;
        int iterations=0;

        while (current != null) {
            iterations++;
            Tariff t = current.getData();
            if (t.getOriginCountry().equals(origin) &&
                    t.getDestinationCountry().equals(destination) &&
                    t.getProductCategory().equals(category)) {

                System.out.println("Found after " + iterations + " iterations.");
                return current; // privacy leak
            }
            current = current.getNext();
        }

        System.out.println("Not found after " + iterations + " iterations.");
        return null;
    }

    /**
     * Checks if a matching Tariff exists in the list.
     */
    public boolean contains(String origin, String destination, String category) {
        return find(origin, destination, category) != null;
    }

    /**
     * Compares this list to another TariffList.
     * @param other the other list to compare
     * @return true if lists contain same data
     */
    public boolean equals(TariffList other) {
        if (this.size != other.size) return false;

        TariffNode thisCurr = this.head;
        TariffNode otherCurr = other.head;

        while (thisCurr != null && otherCurr != null) {
            if (!thisCurr.equals(otherCurr)) return false;
            thisCurr = thisCurr.getNext();
            otherCurr = otherCurr.getNext();
        }

        return true;
    }

    /**
     * Evaluates a trade request using minimum and proposed tariff.
     */
    @Override
    public String evaluateTrade(double proposed, double minimum) {
        if (proposed >= minimum) {
            return "Accepted.";
        } else if (proposed >= (minimum * 0.8)) {
            double surcharge = ((minimum - proposed) / 100.0);
            return String.format("Conditionally Accepted. Surcharge Rate: %.2f%%", surcharge * 100);
        } else {
            return "Rejected.";
        }
    }

    /**
     * Prints all Tariff entries in the list.
     */
    public void printList() {
        TariffNode current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }

    /**
     * Gets current size of the list.
     * @return number of nodes
     */
    public int getSize() {
        return size;
    }
}
