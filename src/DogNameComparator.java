// Erik Näslund erns6604
import java.util.Comparator;

public class DogNameComparator implements Comparator<Dog> {
    @Override
    public int compare(Dog first, Dog second) {
        return first.getName().compareTo(second.getName());
    }
}
