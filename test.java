public class test extends Sprite{
   test() {
       super("images/nostalgiaCriticDevil.jpg");
   } 

   @Override public void updateState() {
       setX(getX() + 5);
       setY(getY() + 5);
   }
}
