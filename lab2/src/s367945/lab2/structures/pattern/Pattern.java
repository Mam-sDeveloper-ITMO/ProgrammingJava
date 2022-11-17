package s367945.lab2.structures.pattern;

public class Pattern implements Comparable<Pattern> {
    public final int colorfulness;
    public final int complexity;

    public Pattern(int colorfulness, int complexity) {
        this.colorfulness = colorfulness;
        this.complexity = complexity;
    }

    @Override
    public int compareTo(Pattern o) {
        int thisCapacity = this.colorfulness * this.complexity;
        int otherCapacity = o.colorfulness * o.complexity;
        return thisCapacity - otherCapacity;
    }

    @Override
    public String toString() {
        return String.format("Pattern with colorfulness %d and complexity %d", this.colorfulness, this.complexity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Pattern)) {
            return false;
        }
        Pattern pattern = (Pattern) obj;
        return this.colorfulness == pattern.colorfulness && this.complexity == pattern.complexity;
    }

    @Override
    public int hashCode() {
        return this.colorfulness * this.complexity;
    }
}
