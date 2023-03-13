package humandeque.manager;

import humandeque.HumanDeque;

public interface CollectionStorage {
    void save(HumanDeque collection);

    HumanDeque load();
}
