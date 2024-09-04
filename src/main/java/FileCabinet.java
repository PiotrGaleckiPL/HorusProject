import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

class FileCabinet implements Cabinet {

    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(final String name) {
        return findFirstMatching(folder -> folder.getName().equals(name), folders);
    }

    @Override
    public List<Folder> findFoldersBySize(final String size) {
        List<Folder> resultList = new ArrayList<>();
        findAllMatching(folder -> folder.getSize().equals(size), folders, resultList::add);
        return resultList;
    }

    @Override
    public int count() {
        final int[] count = {0};
        findAllMatching(folder -> true, folders, f -> count[0]++);
        return count[0];
    }

    private Optional<Folder> findFirstMatching(Predicate<Folder> condition, List<Folder> folders) {
        for (Folder folder : folders) {
            if (condition.test(folder)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                Optional<Folder> innerFolder = findFirstMatching(condition, ((MultiFolder) folder).getFolders());
                if (innerFolder.isPresent()) {
                    return innerFolder;
                }
            }
        }
        return Optional.empty();
    }

    private void findAllMatching(Predicate<Folder> condition, List<Folder> folders, Consumer<Folder> action) {
        for (Folder folder : folders) {
            if (condition.test(folder)) {
                action.accept(folder);
            }
            if (folder instanceof MultiFolder) {
                findAllMatching(condition, ((MultiFolder) folder).getFolders(), action);
            }
        }
    }
}
