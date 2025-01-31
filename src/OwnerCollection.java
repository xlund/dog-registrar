// Erik Näslund erns6604

import java.util.*;

public class OwnerCollection {
    private static final int NO_OWNER_FOUND = -1;
    private Owner[] owners = new Owner[0];

    public boolean addOwner(Owner owner) {
        if (containsOwner(owner)) return false;
        Owner[] dest = Arrays.copyOf(owners, owners.length + 1);
        dest[dest.length - 1] = owner;
        owners = dest;
        return true;
    }

    public boolean removeOwner(Owner owner) {
        return removeOwner(owner.getName());
    }

    public boolean removeOwner(String name) {
        int i = findIndexOfOwner(name);
        if (i == NO_OWNER_FOUND) return false;

        if (!owners[i].getDogs().isEmpty())
            return false;

        if (i != owners.length - 1)
            owners[i] = owners[owners.length - 1];

        this.owners = Arrays.copyOf(owners, owners.length - 1);
        return true;
    }


    private int findIndexOfOwner(String name) {
        for (int i = 0; i < this.owners.length; i++) {
            if (this.owners[i].getName().equals(name.toUpperCase())) return i;
        }
        return NO_OWNER_FOUND;
    }

    public boolean containsOwner(Owner owner) {
        return containsOwner(owner.getName());
    }

    public boolean containsOwner(String name) {
        return findIndexOfOwner(name) != NO_OWNER_FOUND;
    }

    public Optional<Owner> getOwner(String name) {
        if (!containsOwner(name)) return Optional.empty();
        return Optional.of(this.owners[findIndexOfOwner(name)]);
    }

    public ArrayList<Owner> getOwners() {
        ArrayList<Owner> owners = new ArrayList<>(Arrays.asList(this.owners));
        Collections.sort(owners);
        return owners;
    }
}
