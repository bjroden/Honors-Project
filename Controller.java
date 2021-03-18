//TODO: implement keylistener
public class Controller {
    Model model;
    View view;

    Controller() {
        model = new Model();
        view = new View(this, model);
    }

    public static void main(String args[]) {
        new Controller();            
    }
}
