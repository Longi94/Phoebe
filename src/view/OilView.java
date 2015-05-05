package view;

import model.Oil;

import java.awt.*;

/**
 * Olaj kinézete
 */
public class OilView extends TrackObjectBaseView {

    private Oil oil;

    public OilView(Oil oil) {
        super(oil);
        this.oil = oil;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
