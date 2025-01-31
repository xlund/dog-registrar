// Erik Näslund erns6604
import java.util.*;

public class DogSorter {
    private static void swapDogs(ArrayList<Dog> dogs, int firstIndex, int secondIndex) {
        Dog temp = dogs.get(firstIndex);
        dogs.set(firstIndex, dogs.get(secondIndex));
        dogs.set(secondIndex, temp);
    }

    private static int nextDog(Comparator<Dog> cmp, ArrayList<Dog> dogs, int startIndex) {
        int targetIndex = startIndex;
        for (int i = startIndex + 1; i < dogs.size(); i++) {
            if(cmp.compare(dogs.get(i), dogs.get(targetIndex)) < 0) {
                targetIndex = i;
            }
        }
        return targetIndex;
    }

    public static int sortDogs(Comparator<Dog> cmp, ArrayList<Dog> dogs) {
        int swapsExecuted = 0;
        for(int i = 0; i < dogs.size(); i++) {
            int targetIndex = nextDog(cmp, dogs, i);
            if (targetIndex != i) {
                swapDogs(dogs, i, targetIndex);
                swapsExecuted++;
            }
        }
        return swapsExecuted;
    }
}
