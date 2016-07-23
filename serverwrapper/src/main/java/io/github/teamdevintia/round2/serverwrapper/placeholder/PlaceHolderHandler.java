package io.github.teamdevintia.round2.serverwrapper.placeholder;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all actives placeholders
 *
 * @author MiniDigger
 */
public class PlaceHolderHandler {

    private List<PlaceHolder> placeHolders = new ArrayList<>();

    /**
     * adds a new placeholder
     *
     * @param holder the placeholder to add
     */
    public void add(PlaceHolder holder) {
        placeHolders.add(holder);
    }

    /**
     * Removes one placeholder from a port
     *
     * @param port the port that should be cleared
     */
    public void remove(int port) {
        for (PlaceHolder placeHolder : placeHolders) {
            if (placeHolder.getServerPort() == port) {
                placeHolder.stopListening();
                placeHolders.remove(placeHolder);
                break;
            }
        }
    }

    /**
     * Clears all ports
     */
    public void removeAll() {
        placeHolders.forEach(PlaceHolder::stopListening);
        placeHolders.clear();
    }
}
