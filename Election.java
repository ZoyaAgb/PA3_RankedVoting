package cs225;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Name: Zoya Agboatwalla. breif dicussion with Anastasia about tests
//Resources: prgtmmng: Testing Exceptions and Error Handling in JUnit. Q&A.
/**
 * An Election consists of the candidates running for office, the ballots that
 * have been cast, and the total number of voters.  This class implements the
 * ranked choice voting algorithm.
 *
 * Ranked choice voting uses this process:
 * <ol>
 * <li>Rather than vote for a single candidate, a voter ranks all the
 * candidates.  For example, if 3 candidates are running on the ballot, a voter
 * identifies their first choice, second choice, and third choice.
 * <li>The first-choice votes are tallied.  If any candidate receives &gt; 50%
 * of the votes, that candidate wins.
 * <li>If no candidate wins &gt; 50% of the votes, the candidate(s) with the
 * lowest number of votes is(are) eliminated.  For each ballot in which an
 * eliminated candidate is the first choice, the 2nd ranked candidate is now
 * the top choice for that ballot.
 * <li>Steps 2 &amp; 3 are repeated until a candidate wins, or all remaining
 * candidates have exactly the same number of votes.  In the case of a tie,
 * there would be a separate election involving just the tied candidates.
 * </ol>
 */
public class Election {
    // All candidates that were in the election initially.  If a candidate is
    // eliminated, they will still stay in this array.
    private final Candidate[] candidates;

    // The next slot in the candidates array to fill.
    private int nextCandidate;

    /**
     * Create a new Election object.  Initially, there are no candidates or
     * votes.
     * @param numCandidates the number of candidates in the election.
     */
    public Election (int numCandidates) {
        this.candidates = new Candidate[numCandidates];
    }

    /**
     * Adds a candidate to the election
     * @param name the candidate's name
     */
    public void addCandidate (String name) {
        candidates[nextCandidate] = new Candidate (name);
        nextCandidate++;
    }

    /**
     * Adds a completed ballot to the election.
     * @param ranks A correctly formulated ballot will have exactly 1
     * entry with a rank of 1, exactly one entry with a rank of 2, etc.  If
     * there are n candidates on the ballot, the values in the rank array
     * passed to the constructor will be some permutation of the numbers 1 to
     * n.
     * @throws IllegalArgumentException if the ballot is not valid.
     */
    public void addBallot (int[] ranks) {
        if (!isBallotValid(ranks)) {
            throw new IllegalArgumentException("Invalid ballot");
        }
        Ballot newBallot = new Ballot(ranks);
        assignBallotToCandidate(newBallot);
    }


    /**
     * Checks that the ballot is the right length and contains a permutation
     * of the numbers 1 to n, where n is the number of candidates.
     * @param ranks the ballot to check
     * @return true if the ballot is valid.
     */
    private boolean isBallotValid(int[] ranks) {
        if (ranks.length != candidates.length) {
            return false;
        }
        int[] sortedRanks = Arrays.copyOf(ranks, ranks.length);
        Arrays.sort(sortedRanks);
        for (int i = 0; i < sortedRanks.length; i++) {
            if (sortedRanks[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines which candidate is the top choice on the ballot and gives the
     * ballot to that candidate.
     * @param newBallot a ballot that is not currently assigned to a candidate
     */
    private void assignBallotToCandidate(Ballot newBallot) {
        int candidate = newBallot.getTopCandidate();
        candidates[candidate].addBallot(newBallot);
    }

    /**
     * Apply the ranked choice voting algorithm to identify the winner.
     * @return If there is a winner, this method returns a list containing just
     * the winner's name is returned.  If there is a tie, this method returns a
     * list containing the names of the tied candidates.
     */
    int number = 1;
    public List<String> selectWinner() {
        // 0 candidates, 0 ballots
        if (candidates.length == 0) {
            throw new IllegalArgumentException
            ("There are no candidates or ballots registered");
        }

        // USE FOR SEEING WHERE IN RECURION WE ARE
        System.out.println("ROUND:" + number++);

        for (int i = 0; i < candidates.length; i++) {
            System.out.println(candidates[i].getName() + ": " +
                candidates[i].getVotes() +
                " eliminated=" + candidates[i].isEliminated());
        }
        // valid votes is votes for candidates who aren't eliminated
        int validVotes = 0;
        for (int i = 0; i < candidates.length; i++) {
            if (!candidates[i].isEliminated()) {
                validVotes += candidates[i].getVotes();
            }
        }

        // 1 candidate, 0 ballots OR 1 ballot
     // remaining calculated by taking all candidates not eliminated
        List<String> remaining = new ArrayList<>();
        for (int i =0; i < candidates.length; i++) {
        	if (!candidates[i].isEliminated()) {
        		remaining.add(candidates[i].getName());
        	}
        }

        if (remaining.size() == 1) {
            List<String> winner = new ArrayList<>();
            winner.add(remaining.get(0));
            return winner;
        }


        if (validVotes == 0) {
        	List<String> tied = new ArrayList<>();
            for (int k = 0; k < candidates.length; k++) {
                if (!candidates[k].isEliminated()) {
                    tied.add(candidates[k].getName());
                }
            }
            return tied;
        }


        // >50% of votes
        for (int i = 0; i < candidates.length; i++) {
                if (!candidates[i].isEliminated() && candidates[i].getVotes() * 2 > validVotes) {
                    List<String> winner = new ArrayList<>();
                    winner.add(candidates[i].getName());
                    return winner;
                }
            }

            // Find votes for remaining candidates
            boolean minFound = false;
            int minVotes = 0;
            int maxVotes = 0;
            int index = 0;
            while (!minFound && candidates.length > index) {
            	if (!candidates[index].isEliminated()) {
            		// sets first reviewed candidate votes to min
            		minVotes = candidates[index].getVotes();
            		// sets first reviewed candidate votes to max
            		maxVotes = candidates[index].getVotes();
            		minFound = true;
            	}
            	index = index+1;
            }

            for (int j = 0; j < candidates.length; j++) {
                if (!candidates[j].isEliminated()) {
                	// if new candidate has less than prior candidates, set this candidate to new minVotes
                    if (candidates[j].getVotes() < minVotes) {
                    	minVotes = candidates[j].getVotes();
                    }
                 // if new candidate has greater than prior candidates, set this candidate to new maxVotes
                    if (candidates[j].getVotes() > maxVotes) {
                    	maxVotes = candidates[j].getVotes();
                    }
                }
            }

            // candidate votes tied
            if (minVotes == maxVotes) {
                List<String> tied = new ArrayList<>();
                for (int k = 0; k < candidates.length; k++) {
                    if (!candidates[k].isEliminated()) {
                        tied.add(candidates[k].getName());
                    }
                }
                return tied;
            }

            // find candidates eligible for elimination
            List<Integer> toEliminate = new ArrayList<>();
            for (int i = 0; i < candidates.length; i++) {
            	// ensures that eliminated candidates are not chosen again to eliminate
                if (!candidates[i].isEliminated() && candidates[i].getVotes() == minVotes) {
                    toEliminate.add(i);
                }
            }

            // elimination + redistribution
            for (int i : toEliminate) {
                List<Ballot> ballots = candidates[i].eliminate();
                for (Ballot bal : ballots) {
                	bal.eliminateCandidate(i);
                    int next = bal.getTopCandidate();
                    if (next >= 0) {
                        candidates[next].addBallot(bal);
                    }
                }
            }

        return selectWinner();
    }
}
