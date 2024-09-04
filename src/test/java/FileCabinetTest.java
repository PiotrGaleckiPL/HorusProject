import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetTest {

    private FileCabinet fileCabinet;

    @BeforeEach
    void setUp() {
        Folder folder1 = new SimpleFolder("Documents", "SMALL");
        Folder folder2 = new SimpleFolder("Music", "MEDIUM");
        Folder folder3 = new SimpleFolder("Pictures", "LARGE");

        List<Folder> subfolders = Arrays.asList(
                new SimpleFolder("Work", "SMALL"),
                new SimpleFolder("Personal", "MEDIUM")
        );

        Folder multiFolder = new CompositeFolder("Archives", "LARGE", subfolders);

        fileCabinet = new FileCabinet(Arrays.asList(folder1, folder2, folder3, multiFolder));
    }

    @Test
    void testFindFolderByName_Found() {
        Optional<Folder> result = fileCabinet.findFolderByName("Music");
        assertTrue(result.isPresent());
        assertEquals("Music", result.get().getName());
    }

    @Test
    void testFindFolderByName_NotFound() {
        Optional<Folder> result = fileCabinet.findFolderByName("Videos");
        assertFalse(result.isPresent());
    }

    @Test
    void testFindFolderByName_FoundInNestedFolder() {
        Optional<Folder> result = fileCabinet.findFolderByName("Work");
        assertTrue(result.isPresent());
        assertEquals("Work", result.get().getName());
    }

    @Test
    void testFindFoldersBySize_Small() {
        List<Folder> result = fileCabinet.findFoldersBySize("SMALL");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Documents")));
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Work")));
    }

    @Test
    void testFindFoldersBySize_Medium() {
        List<Folder> result = fileCabinet.findFoldersBySize("MEDIUM");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Music")));
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Personal")));
    }

    @Test
    void testFindFoldersBySize_Large() {
        List<Folder> result = fileCabinet.findFoldersBySize("LARGE");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Pictures")));
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Archives")));
    }

    @Test
    void testCount() {
        int count = fileCabinet.count();
        assertEquals(6, count);
    }
}
