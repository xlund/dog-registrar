// Erik Näslund erns6604

import java.util.ArrayList;

public class Owner implements Comparable<Owner> {
    private final String name;
    private final ArrayList<Dog> dogs;

    public Owner(String name) {
        this.name = name.toUpperCase();
        this.dogs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean addDog(Dog dog) {
            if (dog == null
                    || hasOwnerNotThis(dog)
                    || this.dogs.contains(dog)
            ) return false;

            this.dogs.add(dog);
            if (isOwner(dog)) return true;
            return dog.setOwner(this);
    }

    private boolean hasOwnerNotThis(Dog dog) {
        return dog.getOwner().isPresent() && !dog.getOwner().get().equals(this);
    }

    private boolean isOwner(Dog dog) {
        return dog.getOwner().isPresent() && dog.getOwner().get().equals(this);
    }


    // TODO: Improve logic
    public boolean removeDog(Dog dog) {
        if (dog == null) return false;
        if (dog.getOwner().isEmpty()) return handleDogRemovalNoOwner(dog);
        if(isOwner(dog)) return handleDogRemovalWithOwner(dog);
        return false;
    }

    private boolean handleDogRemovalNoOwner(Dog dog) {
        if (this.dogs.contains(dog)) {
            this.dogs.remove(dog);
            return true;
        }
        return false;
    }
    private boolean handleDogRemovalWithOwner(Dog dog) {
        dog.setOwner(null);
        this.dogs.remove(dog);
        return true;
    }

    public ArrayList<Dog> getDogs() {
        DogTailNameComparator cmp = new DogTailNameComparator();
        ArrayList<Dog> sortedDogs = new ArrayList<>(this.dogs);
        DogSorter.sortDogs(cmp, sortedDogs);
        return sortedDogs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ");
        for (Dog dog : dogs) {
            sb.append(String.format("%s %s %d %f", dog.getName(), dog.getBreed(), dog.getAge(), dog.getTailLength()));
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Owner o) {
        return name.compareTo(o.name);
    }
}