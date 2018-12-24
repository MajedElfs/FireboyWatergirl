/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Creates a Character to use in a game of Fireboy and Watergirl. Allows for the
 * creation of either of Fireboy or a Watergirl.
 *
 * @author biGgEsT yEeT: tHe fiNaL fOrM
 */
public abstract class Character {

    private int gemsCollected;
    private float x, y, gravity, ySpeed, height, width, speed, overlapWidth, overlapFarX, overlapX, overlapHeight, overlapTopY, overlapY;
    boolean isFalling, isDead, jump, onGround, hitBottom, hitSide, onIce;
    private Rectangle character, overlap;

    /**
     * Create a Character by determining if it's a Fireboy or a Watergirl, and
     * it's x and y position on the screen.
     *
     * @param x a float representing it's x position on the screen
     * @param y a float representing it's y position on the screen
     */
    public Character(float x, float y) {
        this.height = 30;
        this.width = 24;
        this.gemsCollected = 0;
        this.speed = 2;
        this.isFalling = false;
        this.isDead = false;
        this.ySpeed = 0;
        this.gravity = 0.7f; //tweak
        //  this.maxYSpeed = 5; //tweak
        this.x = x * 16;
        this.y = y * 16;
        this.onGround = true;
        this.jump = false;
        this.hitBottom = false;
        this.hitSide = false;
        overlap = new Rectangle(this.overlapX, this.overlapY, this.overlapWidth, this.overlapHeight);
        // create a Rectangle to represent the Character
        this.character = new Rectangle(this.x, this.y, this.width, this.height);

    }

    /**
     * Returns rectangle created by two overlapping rectangles
     *
     * @param p the platform being overlapped
     * @return rectangle created by two overlapping rectangles
     */
    public Rectangle overlapRectangle(Platform p) {
        if (this.getBounds().overlaps(p.getBounds())) {
            if (this.y + this.height > p.getTop()) {
                this.overlapY = this.y;
                this.overlapTopY = p.getTop();
            } else {
                this.overlapY = p.getY();
                this.overlapTopY = this.getTop();
            }
            if (this.x < p.getX()) {
                this.overlapX = p.getX();
                this.overlapFarX = this.length();
            } else {
                this.overlapX = this.x;
                this.overlapFarX = p.getLength();
            }
        }
        this.overlapWidth = this.overlapFarX - this.overlapX;
        this.overlapHeight = this.overlapTopY - this.overlapY;
        this.overlap = this.overlap.set(this.overlapX, this.overlapY, this.overlapWidth, this.overlapHeight);
        return this.overlap;
    }

    /**
     * Allows the Character to move towards the left-side of the screen without
     * it going off of the screen.
     */
    public void moveLeft() {
        // do not let the Character move off of the left-side of the screen
        if (this.x > 16) {
            // make the Character move towards the left of the screen
            this.x = this.x - this.speed;
        }
    }

    /**
     * Allows the Character to move towards the right-side of the screen without
     * it going off of the screen.
     */
    public void moveRight() {
        // do not let the Character move off of the right-side of the screen
        if (this.x < 632) {
            // make the Character move towards the right of the screen
            this.x = this.x + this.speed;
        }
    }

    /**
     * Returns far X coordinate
     *
     * @return coordinate
     */
    public float length() {
        return this.x + this.width;
    }

    /**
     * Sets the Character to a jumping state
     */
    public void jump() {
        if (this.onGround) {
            this.isFalling = false;
            ySpeed = -11;//height of jump
            this.jump = true;
            this.speed = 3.5f;//tweak
            this.onGround = false;
            System.out.println(this.onGround);

        }
    }

    /**
     * Allows the character to jump and fall
     *
     */
    public void jumpAction() {
        if (!this.onGround) {
            ySpeed += gravity;
            this.y -= ySpeed;
        }
    }

    /**
     * Stops character from jumping if on platform
     *
     * @param p
     */
    public void stopJumpingPlatform(Platform p) {
        this.overlap = this.overlapRectangle(p);
        if (this.overlap.height < this.overlap.width) {
            if (this.ySpeed < 0) {
                // stop moving up/down
                this.ySpeed = 0;
                // correct the position
                this.y = p.getY() - this.height;
            }
            if (this.ySpeed > 0) {
                this.y = p.getTop();
                this.onGround = true;
                this.jump = false;
                            this.speed = 2f;//tweak

            }
        } else {
            // player is on the right
            if (this.x < p.getX()) {
                this.x = this.x - this.overlap.width;
            } else {
                this.x = this.x + this.overlap.width;
            }
        }

    }

    /**
     * Sets character to be standing on a platform
     *
     * @param platforms array of platforms
     * @param movingPlatforms
     */
    public void onTop(Platform[] platforms, Platform[] movingPlatforms) {
        int counter = 0;
        for (Platform p : platforms) {
            if (this.y == p.getTop()) {
                if ((this.x >= p.getX() && this.length() <= p.getLength())) {
                    this.onGround = true;
                    counter++;
                } else if (this.x < p.getX() && this.length() >= p.getX()) {
                    this.onGround = true;
                    counter++;
                } else if (this.length() > p.getLength() && this.x <= p.getLength()) {
                    this.onGround = true;
                    counter++;
                }
            }
        }

        for (Platform p : movingPlatforms) {
            if (this.y == p.getTop()) {
                if ((this.x >= p.getX() && this.length() <= p.getLength())) {
                    this.onGround = true;
                    counter++;
                } else if (this.x < p.getX() && this.length() >= p.getX()) {
                    this.onGround = true;
                    counter++;
                } else if (this.length() > p.getLength() && this.x <= p.getLength()) {
                    this.onGround = true;
                    counter++;
                }
            }
        }

        if (counter == 0) {
            this.onGround = false;
                         this.speed = 2f;//tweak
        }

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
     * Returns top Y coordinate
     *
     * @return top y coordinate
     */
    public float getTop() {
        return this.y + this.height;
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
        return this.gemsCollected;
    }

    /**
     * Adds a Gem to the Gem count.
     */
    public void addGem() {
        this.gemsCollected++;
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
     * Sets the Character to be dead.
     */
    public void died() {
        this.isDead = true;
    }

    public void isOnIce(boolean b) {
        this.onIce = b;
    }
}
