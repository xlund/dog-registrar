// Erik Näslund erns6604

import java.util.*;

public class DogCollection {
    private final ArrayList<Dog> dogs = new ArrayList<>();

    public boolean addDog(Dog dog) {
        return !containsDog(dog.getName()) && dogs.add(dog);
    }

    public boolean containsDog(Dog dog) {
        return containsDog(dog.getName());
    }
    public boolean containsDog(String name) {
        return getDog(name).isPresent();
    }

    public boolean removeDog(Dog dog) {
        return removeDog(dog.getName());
    }

    public boolean removeDog(String name) {
        return dogs.removeIf(dog -> dog.getName().equals(name.toUpperCase()) && dog.getOwner().isEmpty());
    }

    public Optional<Dog> getDog(String name) {
        for (Dog dog : dogs)
            if (dog.getName().equals(name.toUpperCase()))
                return Optional.of(dog);
        return Optional.empty();
    }

    public ArrayList<Dog> getDogs() {
        ArrayList<Dog> dogs = new ArrayList<>(this.dogs);
        Comparator<Dog> cmp = new DogNameComparator();
        DogSorter.sortDogs(cmp, dogs);
        return dogs;
    }

    public ArrayList<Dog> getDogsWithMinTailLengthOf(double minTailLength) {
        ArrayList<Dog> dogsWithTailLength = new ArrayList<>();
        for (Dog dog : dogs)
            if (dog.getTailLength() >= minTailLength)
                dogsWithTailLength.add(dog);

        if (!dogsWithTailLength.isEmpty()) {
            Comparator<Dog> cmp = new DogTailNameComparator();
            DogSorter.sortDogs(cmp, dogsWithTailLength);
        }

        return dogsWithTailLength;
    }
}
