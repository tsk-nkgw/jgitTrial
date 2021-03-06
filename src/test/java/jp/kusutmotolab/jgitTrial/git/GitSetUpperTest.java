package jp.kusutmotolab.jgitTrial.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GitSetUpperTest {

    // clone kGP
    @Test
    public void testCloneRemoteRepository() throws IOException, GitAPIException {
        final Path directoryPath = Paths.get("sample");
        final GitSetUpper gitInitializer = new GitSetUpper(directoryPath.toString(), "https://github.com/kusumotolab/kGenProg");
        final Git git = gitInitializer.setUp();
        assertThat(git).isNotNull().isInstanceOf(Git.class);

        final List<Path> kGPContentsList = Files.list(directoryPath).collect(Collectors.toList());
        final Path readmePath = Paths.get(directoryPath.toString(), "README.md");
        assertThat(kGPContentsList).isNotEmpty().contains(readmePath);
        assertThat(Files.readAllLines(readmePath).get(0)).contains("kGenProg");

        Files.walk(directoryPath)
                .sorted(Comparator.reverseOrder())
                .forEach(e -> {
                    try {
                        Files.deleteIfExists(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
    }

    @Test
    public void testSpecifyLocalRepository() throws GitAPIException, IOException {
        final GitSetUpper gitSetUpper = new GitSetUpper(".", "");
        final Git git = gitSetUpper.setUp();
        assertThat(git).isNotNull().isInstanceOf(Git.class);
    }
}
