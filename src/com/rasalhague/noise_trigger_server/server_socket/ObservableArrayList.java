package com.rasalhague.noise_trigger_server.server_socket;

import java.util.ArrayList;
import java.util.List;

public class ObservableArrayList<T> extends ArrayList<T>
{
    private List<AddListener> addListenerObservers = new ArrayList<AddListener>();

    public void addAddListener(AddListener observer) { addListenerObservers.add(observer);}

    public void removeAddListener(AddListener observer) { addListenerObservers.remove(observer);}

    public void notifyAddListenerObservers(T addedItem)
    {
        for (AddListener listener : addListenerObservers)
        {
            listener.addEvent(addedItem);
        }
    }

    @Override
    public boolean add(T t)
    {
        boolean addResult = super.add(t);

        if (addResult)
        {
            notifyAddListenerObservers(t);
        }

        return addResult;
    }
}
