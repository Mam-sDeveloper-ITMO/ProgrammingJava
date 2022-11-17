package s367945.lab2.bodypart;

import s367945.lab2.enums.Appearance;

public class BodyPart {
    protected int size;
    protected Appearance appearance;

    public BodyPart(int size, Appearance appearance) {
        this.size = size;
        this.appearance = appearance;
    }

    public Appearance getAppearance() {
        return this.appearance;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %d size", this.getClass().getSimpleName(), this.appearance, this.size);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        BodyPart other = (BodyPart) obj;
        return this.appearance == other.appearance && this.size == other.size;
    }

    @Override
    public int hashCode() {
        return this.appearance.hashCode() + this.size;
    }
}
