import java.util.*;

class Solution {
    interface AlgoInterface<T, R> {
        R execute(T t);
    }

    class Param {
        String start;

        String end;

        String[] bank;

        Param(String start, String end, String[] bank) {
            this.start = start;
            this.end = end;
            this.bank = bank;
        }
    }

    class BfsAlgo implements AlgoInterface<Param, Integer> {
        @Override
        public Integer execute(Param param) {
            String[] bank = param.bank;
            String start = param.start;
            String end = param.end;

            if (bank != null) {
                boolean exist = Arrays.stream(bank).anyMatch(s -> s.equals(end));
                if (!exist) {
                    return -1;
                }
            }

            return bfs(start, end, bank);
        }

        private int bfs(String start, String end, String[] bank) {
            Queue<String> queue = new LinkedList<>();
            queue.add(start);
            Set<String> set = new HashSet<>();
            set.add(start);
            int step = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; ++i) {
                    String cur = queue.poll();
                    if (cur == end) {
                        return step;
                    }

                    for (String s : bank) {
                        if (isReachable(cur, s) && !set.contains(s)) {
                            queue.add(s);
                            set.add(s);
                        }
                    }
                }
                ++step;
            }

            return -1;
        }
    }

    class DfsAlgo implements AlgoInterface<Param, Integer> {

        private int minStep = Integer.MAX_VALUE;

        @Override
        public Integer execute(Param param) {
            String start = param.start;
            String end = param.end;
            String[] bank = param.bank;

            Set<String> visited = new HashSet<>();
            dfs(0, start, end, bank, visited);

            return minStep == Integer.MAX_VALUE ? -1 : minStep;
        }

        void dfs(int curDep, String curNode, String end, String[] bank, Set<String> visited) {
            if (curNode.equals(end)) {
                minStep = Math.min(curDep, minStep);
                return;
            }

            for (String s : bank) {
                if (isReachable(s, curNode) && !visited.contains(s)) {
                    visited.add(s);
                    dfs(curDep + 1, s, end, bank, visited);
                    visited.remove(s);
                }
            }
        }
    }

    public int minMutation(String start, String end, String[] bank) {
        Param param = new Param(start, end, bank);
        AlgoInterface<Param, Integer> algo = new DfsAlgo();
        int ans = algo.execute(param);

        return ans;
    }

    private boolean isReachable(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        int cnt = 0;
        for (int i = 0; i < a.length(); ++i) {
            if (a.charAt(i) != b.charAt(i)) {
                ++cnt;
                if (cnt > 1) {
                    break;
                }
            }
        }

        return cnt == 1;
    }
}
