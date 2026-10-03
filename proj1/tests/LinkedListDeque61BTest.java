import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void isEmptyAndSizeTest() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.isEmpty()).isTrue();
         assertThat(lld1.size()).isEqualTo(0);

         lld1.addLast("Tokyo");
         assertThat(lld1.isEmpty()).isFalse();
         assertThat(lld1.size()).isEqualTo(1);

         lld1.addLast("Shanghai");
         lld1.addFirst("Berkeley");
         assertThat(lld1.isEmpty()).isFalse();
         assertThat(lld1.size()).isEqualTo(3);

         lld1.removeFirst();
         lld1.removeFirst();
         lld1.removeFirst();
         assertThat(lld1.size()).isEqualTo(0);

         lld1.removeFirst();
         assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void getFirstAndGetLastTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.getFirst()).isNull();
        assertThat(lld1.getLast()).isNull();

        lld1.addLast("Tokyo");
        lld1.addLast("Shanghai");
        lld1.addFirst("Berkeley");

        assertThat(lld1.getFirst()).isEqualTo("Berkeley");
        assertThat(lld1.getLast()).isEqualTo("Shanghai");
    }

    @Test
    public void getTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.get(-10)).isNull();
         assertThat(lld1.get(0)).isNull();

         lld1.addLast(2);
         lld1.addFirst(3);
         lld1.addLast(5);

         assertThat(lld1.get(1)).isEqualTo(2);
         assertThat(lld1.get(2)).isEqualTo(5);
         assertThat(lld1.get(999)).isNull();
    }

    @Test
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(-10)).isNull();
        assertThat(lld1.getRecursive(0)).isNull();

        lld1.addLast(2);
        lld1.addFirst(3);
        lld1.addLast(5);

        assertThat(lld1.getRecursive(1)).isEqualTo(2);
        assertThat(lld1.getRecursive(2)).isEqualTo(5);
        assertThat(lld1.getRecursive(999)).isNull();
    }

    @Test
    public void removeFirstAndRemoveLastTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
        assertThat(lld1.toList()).isEmpty();

        lld1.addFirst("Paris");
        lld1.addLast("Tokyo");
        lld1.addFirst("Berkeley");
        lld1.addLast("Shanghai");

        assertThat(lld1.removeFirst()).isEqualTo("Berkeley");
        assertThat(lld1.toList()).containsExactly("Paris", "Tokyo", "Shanghai").inOrder();
        assertThat(lld1.removeLast()).isEqualTo("Shanghai");
        assertThat(lld1.toList()).containsExactly("Paris", "Tokyo").inOrder();

        // Check that removing the second to last element with removeFirst works.
        assertThat(lld1.removeFirst()).isEqualTo("Paris");
        assertThat(lld1.toList()).containsExactly("Tokyo");
        // Check that removing the last element with removeFirst works.
        assertThat(lld1.removeFirst()).isEqualTo("Tokyo");
        assertThat(lld1.toList()).isEmpty();

        lld1.addFirst("Kyoto");
        lld1.addLast("Beijing");
        // Check that removing the second to last element with removeLast works.
        assertThat(lld1.removeLast()).isEqualTo("Beijing");
        assertThat(lld1.toList()).containsExactly("Kyoto");
        // Check that removing the last element with removeLast works.
        assertThat(lld1.removeLast()).isEqualTo("Kyoto");
        assertThat(lld1.toList()).isEmpty();
    }

    @Test
    public void addAfterRemoveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(3);
        lld1.addLast(2);
        lld1.removeFirst();
        lld1.removeLast();

        lld1.addFirst(5);
        assertThat(lld1.getFirst()).isEqualTo(5);

        lld1.removeFirst();
        lld1.addLast(7);
        assertThat(lld1.getLast()).isEqualTo(7);
    }
}