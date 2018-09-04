package util;

import java.util.ArrayList;

public class PriorityQueue<T> {

    private ArrayList<T> items;
    private ArrayList<Integer> priorities;

    public PriorityQueue() {
        items = new ArrayList<>();
        priorities = new ArrayList<>();
    }

    public void add(T item, int priority) {
        int indexToAdd = items.size();
        for (int i = 0; i < priorities.size(); i++) {
            if (priorities.get(i) > priority){
                indexToAdd = i;
                break;
            }
        }

        if (indexToAdd == items.size()) {
            items.add(item);
            priorities.add(priority);
        } else {
            items.add(indexToAdd, item);
            priorities.add(indexToAdd, priority);
        }
    }

    public T get() {
        T item = items.remove(0);
        int priority = priorities.remove(0);
        return item;
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }
}
