package cs48.ucsb.edu.clubhop;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  A class that holds all of the FacebookEvents pertaining to a certain User.
 *
 *  This class is implemented as a singleton with a static instance, allowing
 *  any of the other classes in the project to access the Model's data.
 */

public class UserEventsModel {

    /**
     *  Represents the user that these events pertain to.
     */
    private User user;

    /**
     * A list of the listeners that are subscribed to the Model.
     */
    private ArrayList<ModelListener> listeners;

    /**
     * The list of events that pertain to the user.
     */
    private ArrayList<FacebookEvent> events;

    /**
     * The singleton instance of the model.
     */
    private static UserEventsModel instance;

    /**
     * @return If the instance is not created yet, it will be created. Else, it returns the instance of the UserEventSModel.
     */
    public static UserEventsModel getInstance() {
        if (instance == null)
            instance = new UserEventsModel();
        return instance;
    }


    /**
     * Sets the User that the Model pertains to based on the JSON that comes in, which should have the id and name of the User
     *
     * @param userJSON The JSONObject that has the information of the User (namely "id" and "name")
     */
    public void setUser(JSONObject userJSON) {
        try {
            user = new User(userJSON.getInt("id"), userJSON.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in a JSONArray of events, turns them into FacebookEvents, and then adds them to the Model.
     * @param eventArray
     */
    public void loadJSONArray(JSONArray eventArray) {
        for (int i = 0; i < eventArray.length(); ++i) {
            try {
                FacebookEvent e = new FacebookEvent();
                e.loadJSONObject(eventArray.getJSONObject(i));
                if (e.getLocation() != null)
                    events.add(e);
            } catch (JSONException e1) {
                e1.printStackTrace();
                return;
            }
        }
        notifyListeners();
    }

    /**
     * Private constructor that initializes listeners and events to be default ArrayLists.
     */
    private UserEventsModel() {
        listeners = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Returns a single event from within the Model
     * @param index The index for the desired FacebookEvent.
     * @return FacebookEvent at the given index inside of the Model.
     */
    public FacebookEvent getEvent(int index) {
        return events.get(index);
    }

    /**
     * @return The size of the list of FacebookEvents.
     */
    public int getSize() {
        return events.size();
    }

    /**
     * Subscribes a listener to this Model, which will be notified when the Model changes.
     *
     * @param listener A new listener that would like to subscribe to the model.
     */
    public void addListener(ModelListener listener) { this.listeners.add(listener); }

    /**
     * Tells all of the subscribed listeners that the model has changed.
     */
    private void notifyListeners() {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onChange();
        }
    }


}