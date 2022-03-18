package engineTester;

public class FPSCounter {
    private static FPSCounter fpsCounter = null;

    private static long lastNanoTime;
    private static int totalFrames;

    private FPSCounter() {}

    public static FPSCounter create() {
        if (fpsCounter == null) {
            fpsCounter = new FPSCounter();
            return fpsCounter;
        }
        System.out.println("FPSCounter already exists!");
        return fpsCounter;
    }

    public void count() {
        totalFrames++;
        if (System.nanoTime() > lastNanoTime + 1e9) {
            lastNanoTime = System.nanoTime();
            int currentFPS = totalFrames;
            totalFrames = 0;
            System.out.println("FPS: " + currentFPS);
        }
    }
}
