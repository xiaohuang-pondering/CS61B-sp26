import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {
    @Test
    public void iteratorTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
        }
        List<Integer> l1 = new ArrayList<>();
        for (int i : ad1) {
            l1.add(i);
        }
        assertThat(l1).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        for (int i = 9; i >= 0; i--) {
            ad2.addFirst(i);
        }
        List<Integer> l2 = new ArrayList<>();
        for (int i : ad2) {
            l2.add(i);
        }
        assertThat(l2).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    public void equalsTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        Deque61B<Integer> ad3 = new ArrayDeque61B<>();
        for (int i = 0; i < 3; i++) {
            ad1.addLast(i);
            ad2.addLast(i);
            ad3.addFirst(i);
        }
        assertThat(ad1.equals(ad1)).isTrue();
        assertThat(ad1.equals(ad2)).isTrue();
        assertThat(ad1.equals(ad3)).isFalse();

        Deque61B<Integer> ad4 = new ArrayDeque61B<>();
        ad4.addFirst(1);
        assertThat(ad1.equals(ad4)).isFalse();

        Deque61B<String> ad5 = new ArrayDeque61B<>();
        ad5.addLast("Tokyo");
        ad5.addLast("Shanghai");
        ad5.addFirst("Berkeley");
        assertThat(ad1.equals(ad5)).isFalse();
    }

    @Test
    public void toStringTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.toString()).isEqualTo("[]");
        ad1.addLast("Tokyo");
        ad1.addLast("Shanghai");
        ad1.addFirst("Berkeley");
        assertThat(ad1.toString()).isEqualTo("[Berkeley, Tokyo, Shanghai]");

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(2);
        ad2.addFirst(3);
        ad2.addLast(5);
        assertThat(ad2.toString()).isEqualTo("[3, 2, 5]");
    }
}
