package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    private static long lastNanoTime;
    private static int currentFPS;
    private static int totalFrames;

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

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
        for (int i = 0; i < 10000; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;

            allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat() * 180f,
                    random.nextFloat() * 180f, 0f, 1f));
        }

        while (!Display.isCloseRequested()) {
            totalFrames++;
            if (System.nanoTime() > lastNanoTime + 1e9) {
                lastNanoTime = System.nanoTime();
                currentFPS = totalFrames;
                totalFrames = 0;
                System.out.println("FPS: " + currentFPS);
            }
            // game logic
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            for (Entity cube : allCubes) {
                renderer.render(cube, shader);
            }
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
