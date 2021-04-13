public class test extends Sprite{
   test() {
       super("nostalgiaCriticDevil.jpg");
   } 

   @Override public void updateState() {
       setX(getX() + 5);
       setY(getY() + 5);
   }
}
