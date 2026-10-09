import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {
    @Test
    void iteratorTest() {
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
        for (int i : ad1) {
            l2.add(i);
        }
        assertThat(l2).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }
}
