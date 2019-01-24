/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 * Creates a Character to use in a game of Fireboy and Watergirl. Allows for the
 * creation of either of Fireboy or a Watergirl.
 *
 * @author biGgEsT yEeT: tHe fiNaL fOrM
 */
public abstract class Character {

    private int gemsCollected;
    private float x, y, ySpeed, xSpeed;
    private final float gravity, height, width, maxYSpeed;
    boolean isFalling, isDead, jump, onGround, hitBottom, hitSide, isOnTop, canJump;
    private final Rectangle character;
    private final Texture straightPic, leftPic, rightPic;

    /**
     * Create a Character by determining if it's a Fireboy or a Watergirl, and
     * it's x and y position on the screen.
     *
     * @param straightPic a Texture used to represent the front side of the
     * Character
     * @param leftPic a Texture used to represent the left side of the Character
     * @param rightPic a Texture used to represent the right side of the
     * Character
     * @param x a float representing it's x position on the screen
     * @param y a float representing it's y position on the screen
     */
    public Character(Texture straightPic, Texture leftPic, Texture rightPic, float x, float y) {
        this.height = 30;
        this.width = 24;
        this.gemsCollected = 0;
        this.xSpeed = 3;
        this.isFalling = false;
        this.isDead = false;
        this.ySpeed = 0;
        this.gravity = 0.3f; //tweak
        this.x = x * 16;
        this.y = y * 16;
        this.straightPic = straightPic;
        this.leftPic = leftPic;
        this.rightPic = rightPic;
        this.onGround = true;
        this.jump = false;
        this.hitBottom = false;
        this.hitSide = false;
        this.isOnTop = false;
        this.canJump = true;
        this.maxYSpeed = 3;
        // create a Rectangle to represent the Character
        this.character = new Rectangle(this.x, this.y, this.width, this.height);
    }

    /**
     * Returns the x position of the Character.
     *
     * @return a float representing the x position of the Character
     */
    public float getX() {
        return this.x;
    }

    /**
     * Returns the y position of the Character.
     *
     * @return a float representing the y position of the Character
     */
    public float getY() {
        return this.y;
    }

    /**
     * Returns far X coordinate
     *
     * @return coordinate
     */
    public float getFarX() {
        return (this.x + this.width);
    }

    /**
     * Returns top Y coordinate
     *
     * @return top y coordinate
     */
    public float getTop() {
        return (this.y + this.height);
    }

    /**
     * Returns y Speed
     *
     * @return y Speed
     */
    public float getYSpeed() {
        return this.ySpeed;
    }

    /**
     * Sets the x coordinate to specified float
     *
     * @param f a float representing new coordinate
     */
    public void setX(float f) {
        this.x = f;
        this.updatePositions();
    }

    /**
     * Sets the y coordinate to specified float
     *
     * @param f a float representing new coordinate
     */
    public void setY(float f) {
        this.y = f;
        this.updatePositions();
    }

    /**
     * Sets the x coordinate to specified float
     *
     * @param f a float representing new coordinate
     */
    public void setFarX(float f) {
        this.x = f - this.width;
        this.updatePositions();

    }

    /**
     * Sets the x coordinate to specified float
     *
     * @param f a float representing new coordinate
     */
    public void setTop(float f) {
        this.y = f - this.height;
        this.updatePositions();

    }

    /**
     * Sets the ySpeeed to specified float
     *
     * @param f a float representing new speed
     */
    public void setYSpeed(float f) {
        this.ySpeed = f;
    }

    /**
     * Sets the player to on/off ground(platform)
     *
     * @param b a boolean representing whether or not the player is on the
     * platform
     */
    public void setOnGround(boolean b) {
        this.onGround = b;
    }

    /**
     * Sets the x coordinate to specified float
     *
     * @param b a float representing new coordinate
     */
    public void setJumping(boolean b) {
        this.jump = b;
    }

    /**
     * Returns the Rectangle representing the Character.
     *
     * @return a Rectangle representing the Character.
     */
    public Rectangle getBounds() {
        return this.character;
    }

    /**
     * Returns the height of the character
     *
     * @return an integer representing the height of the character
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Returns the width of the character
     *
     * @return an integer representing the width of the character
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Allows the Character to move towards the left-side of the screen without
     * it going off of the screen.
     */
    public void moveLeft() {
        if (this.jump) {
            this.xSpeed = 3;
        }
        if (!this.jump) {
            this.xSpeed = 3;
        }
        // do not let the Character move off of the left-side of the screen
        if (this.x > 16) {
            // make the Character move towards the left of the screen
            this.x = this.x - this.xSpeed;
            this.updatePositions();
        }
    }

    /**
     * Allows the Character to move towards the right-side of the screen without
     * it going off of the screen.
     */
    public void moveRight() {
        if (this.jump) {
            this.xSpeed = 3;
        }
        if (!this.jump) {
            this.xSpeed = 3;
        }
        // do not let the Character move off of the right-side of the screen
        if (this.x < 632) {
            // make the Character move towards the right of the screen
            this.x = this.x + this.xSpeed;
            this.updatePositions();
        }
    }

    /**
     * Sets the Character to a jumping state
     */
    public void jump() {
        if (this.onGround && this.canJump) {
            this.isOnTop = false;
            this.isFalling = false;
            ySpeed = -7;//height of jump
            this.jump = true;
            this.onGround = false;
        }
    }

    /**
     * Allows the character to jump and fall
     *
     */
    public void jumpAction() {
        if (!this.onGround) {
            //sets the y coordinate to a gradually increasing/decreasing new value
            if (ySpeed < maxYSpeed) {
                ySpeed += gravity;
            }
            this.y -= ySpeed;
            this.updatePositions();
        }
    }

    /**
     * Returns whether if the Character has died or not.
     *
     * @return a boolean representing whether the Character has died or not
     */
    public boolean isDead() {
        return this.isDead;
    }

    /**
     * Returns the amount of Gems the Character has collected.
     *
     * @return an integer representing the amount of Gems a Character has
     * collected
     */
    public int getGemsCollected() {
        System.out.println(this.gemsCollected);
        return this.gemsCollected;
    }

    /**
     * Adds a Gem to the Gem count.
     */
    public void addGem() {
        System.out.println(this.gemsCollected);
        this.gemsCollected++;
    }

    /**
     * Checks if character is standing on a platform
     *
     * @param platforms array of platforms
     */
    public Platform onTop(ArrayList<Platform> platforms) {
        Platform current = null;
        for (Platform p : platforms) {
            if (this.y == p.getTop()) {
                //player is somewhere in the middle of the platform
                if ((this.x >= p.getX() && this.getFarX() <= p.getFarX())) {
                    current = p;
                    p.timer();
                }//character is on edge of platform
                else if (this.x < p.getX() && this.getFarX() >= p.getX()) {
                    current = p;
                    p.timer();

                } else if (this.getFarX() > p.getFarX() && this.x <= p.getFarX()) {
                    current = p;
                    p.timer();
                }
            }
        }
        return current;
    }

    public void setThat(boolean b, MovingPlatform mp) {
        this.onGround = b;
        if (mp != null) {
            mp.wasOnTop = true;
        }
    }

    /**
     * Draws the Character on the screen using a ShapeRenderer.
     *
     * @param shapeBatch a ShapeRenderer used to draw the Character on the
     * screen
     */
    public void draw(ShapeRenderer shapeBatch) {
        shapeBatch.rect(character.x, character.y, character.width, character.height);
    }

    /**
     * Stores the current position of the Character on the screen into the
     * Character class.
     */
    public void updatePositions() {

        this.character.x = this.x;
        this.character.y = this.y;
    }

    /**
     * Sets the Character's state of life to be the specified boolean.
     *
     * @param isDead a boolean representing whether the Character is alive or
     * not
     */
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    /**
     * Returns the Texture representing the front side of the Character.
     *
     * @return a Texture representing the front side of the Character
     */
    public Texture getStraightPic() {
        return this.straightPic;
    }

    /**
     * Returns the Texture representing the left side of the Character.
     *
     * @return a Texture representing the left side of the Character
     */
    public Texture getLeftPic() {
        return this.leftPic;
    }

    /**
     * Returns the Texture representing the right side of the Character
     *
     * @return a Texture representing the right side of the Character
     */
    public Texture getRightPic() {
        return this.rightPic;
    }

    /**
     * Draws the Character using a SpriteBatch.
     *
     * @param batch a SpriteBatch used to draw the Character
     */
    public void draw(SpriteBatch batch) {
        batch.draw(this.straightPic, this.x, this.y, this.width, this.height);
    }

    /**
     * Draws the left side of Character using a SpriteBatch.
     *
     * @param batch a SpriteBatch used to draw the left side of the Character
     */
    public void drawLeft(SpriteBatch batch) {
        batch.draw(this.leftPic, this.x, this.y, this.width, this.height);
    }

    /**
     * Draws the right side of Character using a SpriteBatch.
     *
     * @param batch a SpriteBatch used to draw the right side of Character
     */
    public void drawRight(SpriteBatch batch) {
        batch.draw(this.rightPic, this.x, this.y, this.width, this.height);
    }
}
