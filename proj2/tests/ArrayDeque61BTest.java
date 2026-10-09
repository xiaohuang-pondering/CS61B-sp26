import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void getFirstAndGetLastTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        assertThat(ad1.getFirst()).isNull();
        assertThat(ad1.getLast()).isNull();

        ad1.addLast("Tokyo");
        ad1.addLast("Shanghai");
        ad1.addFirst("Berkeley");

        assertThat(ad1.getFirst()).isEqualTo("Berkeley");
        assertThat(ad1.getLast()).isEqualTo("Shanghai");
    }

    @Test
    public void getTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.get(-10)).isNull();
        assertThat(ad1.get(0)).isNull();

        ad1.addLast(2);
        ad1.addFirst(3);
        ad1.addLast(5);
        
        assertThat(ad1.get(0)).isEqualTo(3);
        assertThat(ad1.get(1)).isEqualTo(2);
        assertThat(ad1.get(2)).isEqualTo(5);
        assertThat(ad1.get(999)).isNull();
        assertThat(ad1.get(-1)).isNull();
    }

    @Test
    public void isEmptyAndSizeTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.isEmpty()).isTrue();
        assertThat(ad1.size()).isEqualTo(0);

        ad1.addLast("Tokyo");
        assertThat(ad1.isEmpty()).isFalse();
        assertThat(ad1.size()).isEqualTo(1);

        ad1.addLast("Shanghai");
        ad1.addFirst("Berkeley");
        assertThat(ad1.isEmpty()).isFalse();
        assertThat(ad1.size()).isEqualTo(3);

        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        assertThat(ad1.isEmpty()).isTrue();
        assertThat(ad1.size()).isEqualTo(0);

        ad1.removeFirst();
        assertThat(ad1.isEmpty()).isTrue();
        assertThat(ad1.size()).isEqualTo(0);
    }

    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addFirst(5);
        ad1.addLast(7);
        ad1.addFirst(2);
        ad1.addLast(9);
        ad1.addFirst(3);
        assertThat(ad1.toList()).containsExactly(3, 2, 5, 7, 9).inOrder();
    }

    @Test
    public void removeFirstAndRemoveLastTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.removeFirst()).isNull();
        assertThat(ad1.removeLast()).isNull();
        assertThat(ad1.toList()).isEmpty();

        ad1.addFirst("Paris");
        ad1.addLast("Tokyo");
        ad1.addFirst("Berkeley");
        ad1.addLast("Shanghai");

        assertThat(ad1.removeFirst()).isEqualTo("Berkeley");
        assertThat(ad1.toList()).containsExactly("Paris", "Tokyo", "Shanghai").inOrder();
        assertThat(ad1.removeLast()).isEqualTo("Shanghai");
        assertThat(ad1.toList()).containsExactly("Paris", "Tokyo").inOrder();

        // Check that removing the second to last element with removeFirst works.
        assertThat(ad1.removeFirst()).isEqualTo("Paris");
        assertThat(ad1.toList()).containsExactly("Tokyo");
        // Check that removing the last element with removeFirst works.
        assertThat(ad1.removeFirst()).isEqualTo("Tokyo");
        assertThat(ad1.toList()).isEmpty();

        ad1.addFirst("Kyoto");
        ad1.addLast("Beijing");
        // Check that removing the second to last element with removeLast works.
        assertThat(ad1.removeLast()).isEqualTo("Beijing");
        assertThat(ad1.toList()).containsExactly("Kyoto");
        // Check that removing the last element with removeLast works.
        assertThat(ad1.removeLast()).isEqualTo("Kyoto");
        assertThat(ad1.toList()).isEmpty();
    }

    @Test
    public void addAfterRemoveTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addFirst(3);
        ad1.addLast(2);
        ad1.removeFirst();
        ad1.removeLast();

        ad1.addFirst(5);
        assertThat(ad1.getFirst()).isEqualTo(5);

        ad1.removeFirst();
        ad1.addLast(7);
        assertThat(ad1.getLast()).isEqualTo(7);
    }

    @Test
    public void resizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        for (int i = 11; i >= 0; i--) {
            ad1.addFirst(i);
        }
        assertThat(ad1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11).inOrder();

        for (int i = 0; i < 12; i++) {
            ad1.removeFirst();
        }
        assertThat(ad1.toList()).isEmpty();

        for (int i = 0; i < 12; i++) {
            ad1.addLast(i);
        }
        assertThat(ad1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11).inOrder();

        for (int i = 0; i < 12; i++) {
            ad1.removeLast();
        }
        assertThat(ad1.toList()).isEmpty();
    }
}
