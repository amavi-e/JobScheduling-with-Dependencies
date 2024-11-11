package Job_scheduling_coursework;

import java.util.ArrayList;

public class LinkedList {
    private Node head;

    public LinkedList() {
        this.head = new Node(null);
    }

    public Node getHead() {
        return head;
    }

    public void addDependencies(ArrayList<String> dependencies) {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        if (dependencies != null) {
            for (String dependency : dependencies) {
                current.next = new Node(dependency);
                current = current.next;
            }
        }
    }

    public void clear() {
        head.next = null;
    }
}

