package com.modules

import com.modules.GridWorld
import javafx.scene.image.Image
import javafx.scene.image.PixelReader
import javafx.scene.paint.Color
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView

/**
 * Created by Tom on 10/27/2017.
 */
class GridWorldGenerator {
    /**
     * Get the image of a Google map
     * @param location: searched location, center of the generated grid world
     * @param zoom: custom magnification (pixel length on map / side length of tile)
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
     * @param zoom: magnification of the map (pixel length on map / side length of tile)
     * @return generated world
     */
    GridWorld generateWorld(Image mapImage, double zoom) {
        PixelReader pixelReader = mapImage.getPixelReader()
        // TODO: check zoom
        int rows = (int) (mapImage.getWidth() / zoom)
        int cols = (int) (mapImage.getHeight() / zoom)
        int[][] tilemap = new int[rows][cols]
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                Color color = pixelReader.getColor((int) (i * zoom), (int) (j * zoom))
                // TODO: use more than grayscale
                double brightness = color.getBrightness()
                tilemap[i][j] = (int) (256 * brightness)
            }
        }

        return new GridWorld(rows, cols, "map.JPG", tilemap)
    }
}
