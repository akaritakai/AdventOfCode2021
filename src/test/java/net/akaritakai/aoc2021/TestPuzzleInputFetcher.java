package net.akaritakai.aoc2021;

import com.google.common.net.HttpHeaders;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestPuzzleInputFetcher {
    @Test
    public void testGetPuzzleInputInLocalStore() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            doReturn(puzzleInput).when(fetcher).fetchLocalPuzzleInput(day);
            doThrow(new IOException("Expected")).when(fetcher).fetchRemotePuzzleInput(day);
            doThrow(new IOException("Expected")).when(fetcher).storePuzzleInputLocally(day, puzzleInput);
            assertEquals(fetcher.getPuzzleInput(day), puzzleInput);
            verify(fetcher, times(1)).fetchLocalPuzzleInput(day);
            verify(fetcher, never()).fetchRemotePuzzleInput(day);
            verify(fetcher, never()).storePuzzleInputLocally(day, puzzleInput);
        }
    }

    @Test
    public void testGetPuzzleInputInMemoryAfterLocalStoreFetch() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        var inputs = new AtomicReference<Map<Integer, String>>();
        doAnswer(invocation -> {
            var day = invocation.getArgument(0, Integer.class);
            return inputs.get().get(day);
        }).when(fetcher).fetchLocalPuzzleInput(anyInt());
        doNothing().when(fetcher).storePuzzleInputLocally(anyInt(), anyString());

        // Assume we start with all the puzzles in local store.
        var puzzles1 = IntStream.rangeClosed(1, 25)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> randomPuzzle()));
        inputs.set(puzzles1);
        for (var day = 1; day <= 25; day++) {
            assertEquals(fetcher.getPuzzleInput(day), puzzles1.get(day));
        }

        // Now that we've loaded the puzzles into memory, they should be cached. So let's change the data on disk and
        // confirm we didn't use it.
        var puzzles2 = IntStream.rangeClosed(1, 25)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> randomPuzzle()));
        inputs.set(puzzles2);
        for (var day = 1; day <= 25; day++) {
            assertNotEquals(puzzles1.get(day), puzzles2.get(day));
            assertEquals(fetcher.getPuzzleInput(day), puzzles1.get(day));
        }

        for (var day = 1; day <= 25; day++) {
            verify(fetcher, times(1)).fetchLocalPuzzleInput(day); // We only ever loaded once
        }
    }

    @Test
    public void testGetPuzzleInputInRemoteStore() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            doThrow(new IOException("Expected")).when(fetcher).fetchLocalPuzzleInput(day);
            doReturn(puzzleInput).when(fetcher).fetchRemotePuzzleInput(day);
            doNothing().when(fetcher).storePuzzleInputLocally(day, puzzleInput);
            assertEquals(fetcher.getPuzzleInput(day), puzzleInput);
            verify(fetcher, times(1)).fetchLocalPuzzleInput(day);
            verify(fetcher, times(1)).fetchRemotePuzzleInput(day);
            verify(fetcher, times(1)).storePuzzleInputLocally(day, puzzleInput);
        }
    }

    @Test
    public void testGetPuzzleInputInMemoryAfterRemoteStoreFetch() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        var inputs = new AtomicReference<Map<Integer, String>>();
        doAnswer(invocation -> {
            var day = invocation.getArgument(0, Integer.class);
            return inputs.get().get(day);
        }).when(fetcher).fetchRemotePuzzleInput(anyInt());
        doThrow(new IOException("Expected")).when(fetcher).fetchLocalPuzzleInput(anyInt());
        doNothing().when(fetcher).storePuzzleInputLocally(anyInt(), anyString());

        // Assume we start with no puzzles in the local store, but the puzzles are available on the remote store.
        var puzzles1 = IntStream.rangeClosed(1, 25)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> randomPuzzle()));
        inputs.set(puzzles1);
        for (var day = 1; day <= 25; day++) {
            assertEquals(fetcher.getPuzzleInput(day), puzzles1.get(day));
        }

        // Now that we've loaded the puzzles into memory, they should be cached. So let's change the data on the server
        // and confirm we didn't request it it.
        var puzzles2 = IntStream.rangeClosed(1, 25)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> randomPuzzle()));
        inputs.set(puzzles2);
        for (var day = 1; day <= 25; day++) {
            assertNotEquals(puzzles1.get(day), puzzles2.get(day));
            assertEquals(fetcher.getPuzzleInput(day), puzzles1.get(day));
        }

        for (var day = 1; day <= 25; day++) {
            verify(fetcher, times(1)).fetchRemotePuzzleInput(day); // We only ever loaded once
        }
    }

    @Test
    public void testGetPuzzleInputIssueWhenStoringPuzzleDoesNotThrow() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            doThrow(new IOException("Expected")).when(fetcher).fetchLocalPuzzleInput(day);
            doReturn(puzzleInput).when(fetcher).fetchRemotePuzzleInput(day);
            doThrow(new IOException("Expected")).when(fetcher).storePuzzleInputLocally(day, puzzleInput);
            assertEquals(fetcher.getPuzzleInput(day), puzzleInput);
            verify(fetcher, times(1)).fetchLocalPuzzleInput(day);
            verify(fetcher, times(1)).fetchRemotePuzzleInput(day);
            verify(fetcher, times(1)).storePuzzleInputLocally(day, puzzleInput);
        }
    }

    @Test
    public void testGetPuzzleInputThrowsWhenAllSourceUnavailable() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        doThrow(new IOException("Expected")).when(fetcher).fetchLocalPuzzleInput(1);
        doThrow(new IOException("Expected")).when(fetcher).fetchRemotePuzzleInput(1);
        assertThrows(Exception.class, () -> fetcher.getPuzzleInput(1));
    }

    @Test
    public void testGetPuzzleInputInCache() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            doReturn(puzzleInput).when(fetcher).fetchLocalPuzzleInput(day);
            assertEquals(fetcher.getPuzzleInput(day), puzzleInput);
            assertEquals(fetcher.getPuzzleInput(day), puzzleInput);
            verify(fetcher, times(1)).fetchLocalPuzzleInput(day);
        }
    }

    @Test
    public void testGetSessionToken() throws Exception {
        var puzzleStorePath = Files.createTempDirectory("puzzleStore");
        var sessionTokenPath = Files.createTempFile("session", "token");
        var fetcher = new PuzzleInputFetcher(puzzleStorePath, sessionTokenPath);
        var sessionToken = randomSessionToken();
        Files.writeString(sessionTokenPath, sessionToken);
        assertEquals(fetcher.getSessionToken(), sessionToken);
    }

    @Test
    public void testGetRemotePuzzleInputUrl() {
        var fetcher = new PuzzleInputFetcher();
        for (var day = 1; day <= 25; day++) {
            assertEquals(fetcher.getRemotePuzzleInputUrl(day),
                    HttpUrl.get("https://adventofcode.com/2021/day/" + day + "/input"));
        }
    }

    @Test
    public void testFetchRemotePuzzleInput() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        var sessionToken = randomSessionToken();
        doReturn(sessionToken).when(fetcher).getSessionToken();
        for (var day = 1; day <= 25; day++) {
            try (var server = new MockWebServer()) {
                var puzzleInput = randomPuzzle();
                server.enqueue(new MockResponse().setBody(puzzleInput));
                server.start();
                var url = server.url("/2021/day/" + day + "/input");
                doReturn(url).when(fetcher).getRemotePuzzleInputUrl(day);
                assertEquals(fetcher.fetchRemotePuzzleInput(day), puzzleInput);
                var request = server.takeRequest();
                assertEquals(request.getHeader(HttpHeaders.COOKIE), "session=" + sessionToken);
            }
        }
    }

    @Test
    public void testFetchRemotePuzzleInputFailsIfMissingSessionToken() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        doThrow(new IOException("Expected")).when(fetcher).getSessionToken();
        assertThrows(Exception.class, () -> fetcher.fetchRemotePuzzleInput(1));
    }

    @Test
    public void testFetchRemotePuzzleInputFailsIfSessionTokenWrong() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        doReturn(randomSessionToken()).when(fetcher).getSessionToken();
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(400)
                    .setBody("Puzzle inputs differ by user.  Please log in to get your puzzle input."));
            server.start();
            var url = server.url("/2021/day/1/input");
            doReturn(url).when(fetcher).getRemotePuzzleInputUrl(1);
            assertThrows(Exception.class, () -> fetcher.fetchRemotePuzzleInput(1));
        }
    }

    @Test
    public void testFetchRemotePuzzleInputFailsIfPuzzleRequestedEarly() throws Exception {
        var fetcher = Mockito.spy(new PuzzleInputFetcher());
        doReturn(randomSessionToken()).when(fetcher).getSessionToken();
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(404)
                    .setBody("Please don't repeatedly request this endpoint before it unlocks! "
                            + "The calendar countdown is synchronized with the server time; "
                            + "the link will be enabled on the calendar the instant this puzzle becomes available."));
            server.start();
            var url = server.url("/2021/day/1/input");
            doReturn(url).when(fetcher).getRemotePuzzleInputUrl(1);
            assertThrows(Exception.class, () -> fetcher.fetchRemotePuzzleInput(1));
        }
    }

    @Test
    public void testStorePuzzleInputLocally() throws Exception {
        var puzzleStorePath = Files.createTempDirectory("puzzleStore");
        var sessionTokenPath = Files.createTempFile("session", "token");
        var fetcher = new PuzzleInputFetcher(puzzleStorePath, sessionTokenPath);
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            fetcher.storePuzzleInputLocally(day, puzzleInput);
            assertEquals(Files.readString(puzzleStorePath.resolve(String.valueOf(day))), puzzleInput);
        }
    }

    @Test
    public void testFetchLocalPuzzleInput() throws Exception {
        var puzzleStorePath = Files.createTempDirectory("puzzleStore");
        var sessionTokenPath = Files.createTempFile("session", "token");
        var fetcher = new PuzzleInputFetcher(puzzleStorePath, sessionTokenPath);
        for (int day = 1; day <= 25; day++) {
            var puzzleInput = randomPuzzle();
            Files.writeString(puzzleStorePath.resolve(String.valueOf(day)), puzzleInput);
            assertEquals(fetcher.fetchLocalPuzzleInput(day), puzzleInput);
        }
    }

    @Test
    public void testFetchLocalPuzzleInputFailsOnMissingFile() throws Exception {
        var puzzleStorePath = Files.createTempDirectory("puzzleStore");
        var sessionTokenPath = Files.createTempFile("session", "token");
        var fetcher = new PuzzleInputFetcher(puzzleStorePath, sessionTokenPath);
        assertThrows(Exception.class, () -> fetcher.fetchLocalPuzzleInput(1));
    }

    private static String randomPuzzle() {
        return RandomStringUtils.randomAscii(65_535);
    }

    private static String randomSessionToken() {
        // Session tokens appear to be 96 characters of ASCII hex digits
        return RandomStringUtils.random(96, "0123456789abcdef");
    }
}