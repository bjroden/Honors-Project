public class SpriteMover implements Runnable{
    Controller controller;

    SpriteMover(Controller c) {
        controller = c;
    }

    public void run() {
        while(true) {
            if (!controller.getPaused()) {
                moveTest();
            }
            try {
                Thread.sleep(1000/30);
            } catch(InterruptedException e) {}
        }
    }

    private void moveTest() {
        controller.update();
    }
    
}
