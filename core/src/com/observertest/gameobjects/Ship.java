package com.observertest.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.observertest.InputHandler;
import com.observertest.World;

import java.awt.*;

/**
 * Created by Carl on 16/02/2017.
 */
public class Ship extends CollidableObject implements InputHandler
{
    private double rotationSpeed;
    private int forward, rotateLeft, rotateRight, fire, coolDownTime, timeToCool;

    public Ship(World world, Vector2 position, Vector2 dimension, double speed, double angle, double rotationSpeed, Color colour,
                int forward, int rotateLeft, int rotateRight, int fire)
    {
        super(world);
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.colour = colour;
        this.forward = forward;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.fire = fire;
        coolDownTime = 60 / 4;
        timeToCool = 0;

        rectangle.setRect(position.x, position.y, dimension.x, dimension.y);
        //rectangle.setBounds((int)position.x, (int)position.y, (int)dimension.x, (int)dimension.y);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(colour);
        pixmap.drawLine(0, 0, (int)dimension.x, (int)dimension.y / 2); // Outside rotateLeft
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x, (int)dimension.y / 2); // Outside rotateRight
        pixmap.drawLine(0, 0, (int)dimension.x / 2, (int)dimension.y / 2); // Inside rotateLeft
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x / 2, (int)dimension.y / 2); // Inside rotateRight

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        handleInput();

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        rectangle.setLocation((int)position.x, (int)position.y);

        if(timeToCool > 0) timeToCool--;
    }

    @Override
    public void handleInput()
    {
        if(Gdx.input.isKeyPressed(forward))
        {
            direction.scl((float)speed);
            position.add(direction);
        }

        if(Gdx.input.isKeyPressed(rotateLeft))
        {
            angle += rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(rotateRight))
        {
            angle -= rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(fire) && timeToCool == 0)
        {
            timeToCool = coolDownTime;
            fireBullet();
        }
    }

    private void fireBullet()
    {
        world.addBullet(new Vector2(position.x, position.y), new Vector2(10, 1), speed * 2, angle, colour);
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {
        Rectangle rect = rectangle.intersection(collidableObject.rectangle);

        if(collidableObject instanceof Ship)
        {
            if(rectangle.x < collidableObject.rectangle.x)
            {
                position.x -= rect.width;
            }
            else if(rectangle.x > collidableObject.rectangle.x)
            {
                position.x += rect.width;
            }

            if(rectangle.y < collidableObject.rectangle.y)
            {
                position.y -= rect.height;
            }
            else if(rectangle.y > collidableObject.rectangle.y)
            {
                position.y += rect.height;
            }
        }
    }
}
