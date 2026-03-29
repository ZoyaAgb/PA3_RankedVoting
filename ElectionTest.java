package cs225;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

 class ElectionTest { 
    @Test  // A has >50% votes
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testWinnerOnInitialRound() throws InterruptedException {
        Election test1 = RankedChoiceVoting.initializeElection("test_files/Test1.txt");
        List<String> result = test1.selectWinner();
        assertTrue(null != result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
    
     }
    @Test // votes A = B = C
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testTieOnInitialRound() throws InterruptedException {
        Election test2 = RankedChoiceVoting.initializeElection("test_files/Test2.txt");
        List<String> result = test2.selectWinner();
        assertTrue(null != result);
        assertEquals(3, result.size());
        assertEquals("Strawberries", result.get(0));
        assertEquals("Bananas", result.get(1));
        assertEquals("Oranges", result.get(2)); 
    } 
  
    @Test //Triple Elimination, tied for last (3 rounds)
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testTiedForLastEliminated() {
        Election test3 = RankedChoiceVoting.initializeElection("test_files/Test3.txt");
        List<String> result = test3.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Andrew", result.get(0));
    }  
    
    @Test // two round single elimination 
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testMultipleRounds() {
        Election test4 = RankedChoiceVoting.initializeElection("test_files/Test4.txt");
        List<String> result = test4.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Adele", result.get(0)); 
    } 
      
    @Test // no candidates, no ballots
    void testNoCandidates() {
        Election test = RankedChoiceVoting.initializeElection("test_files/Test5.txt");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            test.selectWinner();
        });
        assertEquals("There are no candidates or ballots registered", exception.getMessage());
    }
    
    @Test // 1 candidate, no votes
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testOneCandidate() {
        Election test6 = RankedChoiceVoting.initializeElection("test_files/Test6.txt");
        List<String> result = test6.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Cinderella", result.get(0));
    }
    
    @Test // 1 candidate, one vote
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void oneVote() {
        Election test7 = RankedChoiceVoting.initializeElection("test_files/Test7.txt");
        List<String> result = test7.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Red", result.get(0)); 
    } 
    
    
    @Test // Multiple candidates, one has all votes
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void allVotes() {
        Election test8 = RankedChoiceVoting.initializeElection("test_files/Test8.txt");
        List<String> result = test8.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("How to Train Your Dragon", result.get(0));
    } 
    
    @Test // Multiple candidates, one has no votes
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void oneHasNoVotes() {
        Election test9 = RankedChoiceVoting.initializeElection("test_files/Test9.txt");
        List<String> result = test9.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Pink Floyd", result.get(0));
    } 
    
    @Test // Multiple candidates, vote reallocated so 1 candidate has >50%
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void oneHasFiftyPercentPlusVotes() {
        Election test10 = RankedChoiceVoting.initializeElection("test_files/Test10.txt");
        List<String> result = test10.selectWinner();
        assertTrue(result != null);
        assertEquals(1, result.size());
        assertEquals("Vanilla", result.get(0));
    } 
    

    
}
 