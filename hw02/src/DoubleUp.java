public class DoubleUp {
   /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
   public static String doubleUp(String s) {
      String doubled = "";
      for (int i = 0; i < s.length(); i++) {
         doubled = doubled + s.charAt(i) + s.charAt(i);
      }
      return doubled;
   }

   // alternative version
   // public static String doubleUp(String s) {
   //    int len = s.length();
   //    for (int i = 0; i < 2*len; i += 2) {
   //       s = s.substring(0, i+1) + s.charAt(i) + s.substring(i+1, s.length());
   //    }
   //    return s;
   // }
   
   public static void main(String[] args) {
      String s = doubleUp("hello");
      System.out.println(s);
      
      System.out.println(doubleUp("cat"));
   }
}