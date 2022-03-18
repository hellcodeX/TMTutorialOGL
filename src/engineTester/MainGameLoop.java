package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("box", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("brick-wall"));
        texture.setShineDumper(10);
        texture.setReflectivity(0);

        TexturedModel cubeModel = new TexturedModel(model, texture);

        Light light = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1f, 1f, 1f));
        Camera camera = new Camera();

        List<Entity> allCubes = new ArrayList<>();
        Random random = new Random();

        // 23 fps with 10000 cubes with no optimization
        // 38 fps with 10000 cubes with optimization (new MasterRenderer class)
        for (int i = 0; i < 10000; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;

            allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat() * 180f,
                    random.nextFloat() * 180f, 0f, 1f));
        }

        FPSCounter fpsCounter = FPSCounter.create();
        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            // game logic
            camera.move();

            for (Entity cube : allCubes) {
                renderer.processEntity(cube);
            }
            renderer.render(light, camera);
            fpsCounter.count();
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
