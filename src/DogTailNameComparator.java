// Erik Näslund erns6604

import java.util.Comparator;

public class DogTailNameComparator implements Comparator<Dog> {
    private int compareName(Dog first, Dog second) {
        DogNameComparator dc = new DogNameComparator();
        return dc.compare(first, second);
    }

    private int compareTailLength(Dog first, Dog second) {
        DogTailComparator dtc = new DogTailComparator();
        return dtc.compare(first, second);
    }

    private boolean isEqual(int result) {
        return result == 0;
    }

    @Override
    public int compare(Dog first, Dog second) {
        int resultCompareTailLength = compareTailLength(first, second);
        if (isEqual(resultCompareTailLength))
            return compareName(first, second);
        return resultCompareTailLength;
    }
}

