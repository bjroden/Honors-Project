//Calls update on controller n times per second, where n = frameRate
public class SpriteMover implements Runnable{
    Controller controller;
    final static public int frameRate = 60;
    final static public double speedupFactor = 30.0 / frameRate;

    SpriteMover(Controller c) {
        controller = c;
    }

    public void run() {
        while(true) {
            if (!controller.getPaused()) {
                updateGame();
            }
            try {
                Thread.sleep(1000/frameRate);
            }
            catch(InterruptedException e) {}
        }
    }

    private void updateGame() {
        controller.update();
    }
}
