package com.observertest.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.World;

import java.awt.Rectangle;

/**
 * Created by Carl on 02/03/2017.
 */
public abstract class CollidableObject extends GameObject
{
    public Rectangle rectangle;
    private Texture rectangleImage;

    protected CollidableObject(World world, Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        super(world, position, dimension, speed, angle, colour);
        rectangle = new Rectangle((int)position.x - (int)dimension.x / 2, (int)position.y - (int)dimension.y / 2,
                                 (int)dimension.x, (int)dimension.y);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(colour);
        pixmap.drawRectangle(0, 0, (int)dimension.x, (int)dimension.y);

        rectangleImage = new Texture(pixmap);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(image,
          position.x - dimension.x / 2, position.y - dimension.y / 2,
          dimension.x / 2, dimension.y / 2, // Origin is center point for rotation
          dimension.x, dimension.y,
          1f, 1f,
          (float)angle,
          0, 0, (int)dimension.x, (int)dimension.y,
          false, false);

        batch.draw(rectangleImage, rectangle.x, rectangle.y);
    }

    public boolean collidesWith(CollidableObject collidableObject)
    {
        // Returns true if the collidable object is not itself and there is a rectangle intersection
        return !collidableObject.equals(this) && rectangle.intersects(collidableObject.rectangle);
    }

    public abstract void resolveCollision(CollidableObject collidableObject);
}