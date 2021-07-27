package ru.job4j.grabber.store;

import ru.job4j.grabber.Post;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {

    private final List<Post> postStorage = new ArrayList<>();

    private int ids = 1;

    @Override
    public void save(Post post) {
        post.setId(ids++);
        postStorage.add(post);
    }

    @Override
    public List<Post> getAll() {
        return postStorage;
    }

    @Override
    public Post findById(int id) {
        int index = indexOf(id);
        return isIndexPresent(index) ? postStorage.get(index) : null;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < postStorage.size(); index++) {
            if (postStorage.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    private boolean isIndexPresent(int index) {
        return index != -1;
    }
}