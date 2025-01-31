// Erik Näslund erns6604
import java.util.Comparator;
public class DogTailComparator implements Comparator<Dog> {
    @Override
    public int compare(Dog first, Dog second) {
        return Double.compare(first.getTailLength(), second.getTailLength());
    }
}
