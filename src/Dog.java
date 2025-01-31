// Erik Näslund erns6604

import java.util.Optional;

public class Dog {
    private static final String[] DACHSHUND_BREED_NAMES = {"tax", "dachshund"};
    private static final double DACHSHUND_TAIL_LENGTH = 3.7;

    private final String name;
    private final String breed;
    private int age;
    private final int weight;
    private Owner owner;

    public Dog(String name, String breed, int age, int weight) {
        this.name = normalizeName(name);
        this.breed = normalizeName(breed);
        this.age = age;
        this.weight = weight;
        this.owner = null;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getBreed() {
        return breed;
    }

    public double getTailLength() {
        if (isDachshund()) return DACHSHUND_TAIL_LENGTH;
        return (age * weight) / 10.0;
    }

    private boolean removeOwner() {
        if (owner == null) return false;
        Owner tmp = owner;
        owner = null;
        tmp.removeDog(this);
        return true;
    }

    public boolean setOwner(Owner newOwner) {
        if (newOwner == null) return removeOwner();
        if (owner != null) return false;

        owner = newOwner;
        if (owner.getDogs().contains(this)) return true;

        return owner.addDog(this);
    }

    public Optional<Owner> getOwner() {
        if (this.owner == null) return Optional.empty();
        return Optional.of(this.owner);
    }

    public void increaseAge() {
        if (age == Integer.MAX_VALUE) return;
        age++;
    }

    private String normalizeName(String name) {
        return name.toUpperCase();
    }

    private boolean isDachshund() {
        for (String b : DACHSHUND_BREED_NAMES) {
            if (b.equals(breed.toLowerCase())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %d %f %s", name, breed, age, weight, getTailLength(), owner);
    }
}
