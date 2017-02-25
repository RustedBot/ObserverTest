package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.observertest.enums.Event;

/**
 * Created by Carl on 16/02/2017.
 */
public class Bullet extends Subject
{
    public Bullet(Vector2 position, Vector2 dimension, double speed, double angle, Color color)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.color = color;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.nor();
        direction.scl((float)speed);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawLine(0, 0, (int)dimension.x, 0);

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    public void initialise(Vector2 position, Vector2 dimension, double speed, double angle, Color color)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.color = color;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.nor();
        direction.scl((float)speed);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawLine(0, 0, (int)dimension.x, 0);

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        position.add(direction);

        if(position.x > Gdx.graphics.getWidth() ||
                position.x < 0 ||
                position.y > Gdx.graphics.getHeight() ||
                position.y < 0)
        {
            sendEvent(this, Event.REMOVE_BULLET);
        }
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
    }
}
