package pl.panryba.mc.recipes;

import org.bukkit.Location;

public class GenLocation {
    private Location location;
    private long validity;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GenLocation)) {
            return false;
        }

        GenLocation other = (GenLocation)obj;
        return this.location.equals(other.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    public GenLocation(Location location, long validity) {
        this.location = location;
        this.validity = validity;
    }

    public Location getLocation() {
        return location;
    }

    public long getValidity() {
        return validity;
    }
}
