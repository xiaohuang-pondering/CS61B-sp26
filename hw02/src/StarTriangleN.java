public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N-1-i; j++) {
            IO.print(' ');
         }
         for (int j = 0; j < i+1; j++) {
            IO.print('*');
         }
         IO.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}