package com.columbus;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PirateShip {
    private int x;
    private int y;
    private ImageView imageView;
    private final int scale = 50;

    public PirateShip(int x, int y) {
        this.x = x;
        this.y = y;
        Image image = new Image(getClass().getResource("/images/pirateShip.png").toExternalForm(), scale, scale, true, true);
        imageView = new ImageView(image);
        imageView.setX(x * scale);
        imageView.setY(y * scale);
    }

    public void update(int shipX, int shipY) {
        if (x < shipX) x++;
        else if (x > shipX) x--;
        if (y < shipY) y++;
        else if (y > shipY) y--;

        imageView.setX(x * scale);
        imageView.setY(y * scale);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
