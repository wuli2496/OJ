/**
 * Problem Statement
 *     	Manao conducted a training camp in Bazaleti to prepare students for programming contests. The camp lasted for M days and was attended by N students. Each day, a lecture on a separate topic was delivered. Some of the students did not attend on particular days. You are given a String[] attendance containing N elements, each of them M characters long. The j-th character in the i-th element of attendance is 'X' if the i-th student attended on the j-th day and '-' otherwise.
 *
 *
 *
 * Now Manao is going to arrange a contest comprised of K problems. Some problems are ad hoc and require no special knowledge, some need a specific topic which was analyzed at the camp and some combine several topics from the camp. This information is given in String[] problemTopics, which contains K elements of length M. The j-th character in the i-th element of problemTopics is 'X' if problem i requires knowledge of the topic that was taught on the j-th day of the camp and '-' otherwise.
 *
 *
 *
 * Manao wants an estimate on which students should solve which of the problems. He reckons that a student should solve a problem if he attended on each of the days when the topics needed for the problem were analyzed. Return a String[] containing N elements containing K characters each. The j-th character in the i-th row should be 'X' if student i should solve problem j by Manao's estimate and '-' otherwise.
 *
 * Definition
 *
 * Class:	TrainingCamp
 * Method:	determineSolvers
 * Parameters:	String[], String[]
 * Returns:	String[]
 * Method signature:	String[] determineSolvers(String[] attendance, String[] problemTopics)
 * (be sure your method is public)
 *
 *
 * Constraints
 * -	attendance will contain between 1 and 50 elements, inclusive.
 * -	Each element of attendance will contain between 1 and 50 characters, inclusive.
 * -	All elements of attendance will be of the same length.
 * -	Each character in attendance will be either '-' or 'X'.
 * -	problemTopics will contain between 1 and 50 elements, inclusive.
 * -	Each element of problemTopics will contain the same number of characters as attendance[0].
 * -	Each character in problemTopics will be either '-' or 'X'.
 *
 * Examples
 * 0)
 *
 *
 * {"XXX",
 *  "XXX",
 *  "XX-"}
 *
 * {"---",
 *  "XXX",
 *  "-XX",
 *  "XX-"}
 *
 * Returns: {"XXXX", "XXXX", "X--X" }
 *
 * The camp lasted for three days and had three attendees. The first two of them have listened to all the lectures and the third one missed the camp's last day. Of the four problems Manao is going to set for the contest, problem 0 requires no special knowledge, problem 1 combines all three topics taught at the camp and the other two problems are a blend of two of those techniques. Students 0 and 1 should be able to solve all problems, while student 2 is estimated to fail problems 1 and 2 because they both need the topic considered on the last day of the camp.
 * 1)
 *
 *
 * {"-XXXX",
 *  "----X",
 *  "XXX--",
 *  "X-X-X"}
 *
 * {"X---X",
 *  "-X---",
 *  "XXX--",
 *  "--X--"}
 *
 * Returns: {"-X-X", "----", "-XXX", "X--X" }
 *
 * The camp comprised five days and was attended by four students. Student 0 should solve problems 1 and 3, student 1 is expected to solve nothing, student 2 should solve all problems but the first one and student 3 should solve problems 0 and 3.
 * 2)
 *
 *
 * {"-----",
 *  "XXXXX"}
 *
 * {"XXXXX",
 *  "-----",
 *  "--X-X"}
 *
 * Returns: {"-X-", "XXX" }
 *
 * Student 0 attended no lectures, but he should still be able to solve problem 1.
 * 3)
 *
 *
 * {"-",
 *  "X",
 *  "X",
 *  "-",
 *  "X"}
 *
 * {"-",
 *  "X"}
 *
 * Returns: {"X-", "XX", "XX", "X-", "XX" }
 *
 * 4)
 *
 *
 * {"X----X--X",
 *  "X--X-X---",
 *  "--X-X----",
 *  "XXXX-X-X-",
 *  "XXXX--XXX"}
 *
 * {"X----X-X-",
 *  "-----X---",
 *  "-X----X-X",
 *  "-X-X-X---",
 *  "-----X---",
 *  "X-------X"}
 *
 * Returns: {"-X--XX", "-X--X-", "------", "XX-XX-", "--X--X" }
 */
public class TrainingCamp
{
    public String[] determineSolvers(String[] attendance, String[] problemTopics)
    {
        int N = attendance.length;
        int M = attendance[0].length();
        int K = problemTopics.length;

        String[] canSolve = new String[N];
        for (int i = 0; i < N; ++i) {
            canSolve[i] = "";
            for (int j = 0; j < K; ++j) {
                boolean can = true;
                for (int k = 0; k < M; ++k) {
                    if (problemTopics[j].charAt(k) == 'X') {
                        can = can && (attendance[i].charAt(k) == 'X');
                    }
                }

                canSolve[i] = canSolve[i] + (can ? 'X' : '-');
            }
        }

        return canSolve;
    }
}

