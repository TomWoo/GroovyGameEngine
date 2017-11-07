package com.addons

import com.addons.GridWorld
import javafx.scene.image.Image
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView

/**
 * Created by Tom on 10/27/2017.
 */
class GridWorldGenerator {
    /**
     * Get the image of a Google map
     * @param location: searched location, center of the generated grid world
     * @param zoom: custom magnification (side length of tile / equivalent length on map in px.)
     * @return image of a Google map
     */
    private Image getGoogleMapImage(String location, double zoom) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load("http://maps.google.com/place/" + location);

        webEngine.setCreatePopupHandler({ // TODO: check syntax on using delegate as callback

        });

        return new Image(""); // TODO: get actual image
    }

    /**
     * Generate world from the image of a map
     * @param mapImage: image of a map
     * @param zoom: magnification of the map (side length of tile / equivalent length on map in px.)
     * @return generated world
     */
    GridWorld generateWorld(Image mapImage, double zoom) {

    }
}
