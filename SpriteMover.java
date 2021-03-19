public class SpriteMover implements Runnable{
    Model model;
    View view;

    SpriteMover(View v, Model m) {
        view = v;
        model = m;
    }

    public void run() {
        while(true) {
            if (!model.isPaused()) {
                moveTest();
            }
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {}
        }
    }

    private void moveTest() {
        model.update();
        view.repaint();
    }
    
}
