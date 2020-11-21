package org.gnocchigames.dragonboat.screens;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.AIBoat;
import org.gnocchigames.dragonboat.entities.Boat;
import org.gnocchigames.dragonboat.entities.Duck;
import org.gnocchigames.dragonboat.entities.Entity;
import org.gnocchigames.dragonboat.entities.PlayerBoat;
import org.gnocchigames.dragonboat.entities.Boat.Boat_Type;
import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;
import org.gnocchigames.dragonboat.util.GameCamera;
import org.gnocchigames.dragonboat.util.GameStructure;



/**
 * RaceLegScreen
 * Base class for a leg of the dragon boat race
 * Keeps track of entities, updates them
 * Draws scene every frame
 */
public class RaceLegScreen extends ScreenAdapter {
    
    private DragonBoatGame game;

    private List<Entity> entities;
    private List<Entity> entities_to_remove;
    private AbstractMap<Entity, Entity> entities_collided;

    private GameCamera camera;
    private OrthographicCamera ui_camera;
    private SpriteBatch batch;
    private ShapeRenderer shape_renderer;

    private Texture finish_texture;
    private Texture background_texture;
    private Texture buoy_texture;
    private Sprite buoy_sprite;

    public PlayerBoat player_boat;
    public Boat other_boat;
    private GameStructure game_structure;

    private Boat.Boat_Type type;

    public float[] x_coords;
    public float[] y_coords;

    ShapeRenderer debug_box_renderer;

    public RaceLegScreen (DragonBoatGame game) {
        super();
        this.game = game;
        //System.out.println("raceover = "+race_structure.raceover(player_boat));
        
    }

    /**
     * Called when DragonBoatGame switches to this screen
     * Instantiates required objects
     */
    @Override
    public void show() {

        game_structure = new GameStructure(this);

        batch = new SpriteBatch();
        shape_renderer = new ShapeRenderer();
        debug_box_renderer = new ShapeRenderer();

        camera = new GameCamera();
        camera.setToOrtho(false, 1920, 1080);
        ui_camera = new OrthographicCamera();
        ui_camera.setToOrtho(false, 1920, 1080);

        entities = new ArrayList<Entity>();
        entities_to_remove = new ArrayList<Entity>();
        entities_collided = new HashMap<Entity, Entity>();

        // // // gets chosen boat type from boat choose screen
        //  type = BoatSelectScreen.getBoat();
        //  player_boat = new PlayerBoat(this, type);
        //  entities.add(player_boat);

        // // // DEBUG
        //  other_boat = new Boat(this, Boat_Type.ACCEL);
        //  entities.add(other_boat);

        //  entities.add(new Duck(this, 1000,  500, 10));

        //not working yet
        game_structure.set_leg(GameStructure.Legs.LEG_ONE);
        game_structure.start_leg();
        background_texture = new Texture("water_tile.png");
        buoy_texture = new Texture("buoy.png");
        buoy_sprite = new Sprite(buoy_texture);
        buoy_sprite.scale(-0.75f);
        finish_texture = new Texture("finish.png");
        
    }

    /**
     * Draws screen with all entities and background
     * Should be called every frame
     */
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background on first
        batch.begin();
        draw_background();
        batch.end();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        for (Entity entity : entities) {
            try {
                entity.draw(batch);
            }catch (IsNotDrawingException e) {
                Gdx.app.log("Exception: ", e.getMessage());
            }
        }

        batch.end();

        draw_info();

        // TODO: Remove debug hitbox rendering
        // DEBUG
        debug_box_renderer.setProjectionMatrix(camera.combined);
        debug_box_renderer.begin(ShapeType.Line);
        debug_box_renderer.setColor(1, 1, 0, 1);

        for (Entity entity : entities) {
            debug_box_renderer.polygon(entity.getBoundingPolygon().getTransformedVertices());
            debug_box_renderer.polygon(entity.hitbox.getTransformedVertices());
        }

        debug_box_renderer.end();
        // END DEBUG


    }


    /**
     * Update all entities in the scene
     * Apply movement, check for and apply collisions
     * @param delta_time time since last render() call
     */
    public void update(float delta_time) {
        
        for (Entity entity : entities) {
            entity.update(delta_time, entities);
        }

        // Detect collisions
        for (Entity entity : entities) {
            SimpleEntry<Boolean, Entity> collision = entity.isCollidedWith(entities);
        
            if (collision.getKey()) {
                entities_collided.put(entity, collision.getValue());
            }
        }

        // Apply collisions
        for (Entity entity : entities_collided.keySet()) {
            entity.applyCollision(entities_collided.get(entity));
        }
        // Clear collision list
        entities_collided = new HashMap<Entity, Entity>();
        
        // follow the boat with the camera
        camera.followBoat(player_boat);
        
        for (Entity entity : entities_to_remove) {
            entities.remove(entity);
        }

        //float[] check_x = {0f, 500f, 500f, 750f};
        //float[] check_x = AIBoat.getXCoords(1,1);
        //float[] check_y = AIBoat.getYCoords(1,1);
        //AIBoat.AI(other_boat, check_x, check_y);
    }

    /**
     * render() is called every frame to render the screen
     */
    @Override
    public void render (float delta_time) {
        draw();
        update(delta_time);
    }

    /**
     * Called when the screen is disposed of
     */
    @Override
    public void dispose () {
        batch.dispose();
        shape_renderer.dispose();
        background_texture.dispose();
        finish_texture.dispose();
        buoy_texture.dispose();
    }


    /**
     * Draw the tiled background
     */
    private void draw_background() {
        for (int x = 0; x <= 1920; x+=200) {
            for (int y = -1080*100; y <= 1080*100; y+=200) {
                batch.draw(background_texture, x, y, 200, 200);
            }
        }

        // Draw the finish line
        for (int x = 0; x < 1920; x+=finish_texture.getWidth()) {
            batch.draw(finish_texture, x, 20000-200);
        }

        // Draw buoys for the lanes
        for (int y = -1080*100; y <= 1080*100; y+=256) {
            for (int x = 384; x < 1920; x+=384) {
                buoy_sprite.setCenter(x, y);
                buoy_sprite.draw(batch);
            }
        }

    }
    
    /**
     * Draw the UI elements of the RaceLegScreen
     * Elements include health bars, timers, etc.
     */
    private void draw_info() {

        shape_renderer.setProjectionMatrix(ui_camera.combined);
        shape_renderer.begin(ShapeType.Filled);
        
        for (Entity entity : entities) {
            if (entity instanceof Boat) {
                Boat boat = (Boat) entity;

                shape_renderer.setColor(0, 0, 0, 1);
                shape_renderer.rect(
                    boat.lane_number * (1920/5) + 140,
                    8,
                    104,
                    14
                );
                shape_renderer.setColor(0, 1, 0, 1);
                shape_renderer.rect(
                    boat.lane_number * (1920/5) + 142,
                    10,
                    boat.current_health,
                    10
                );

            }
        }



        shape_renderer.end();

    }
    
    public List<Entity> getEntities () {
        return entities;
    }

    public void addEntity (Entity entity) {
        entities.add(entity);
    } 

    public void removeEntity (Entity entity) {
        entities_to_remove.add(entity);
    }

    /**
    public void AI(Boat boat){
        other_boat.accelerate();
    }*/

}
