package pl.edu.pw.dekodowaniehuffmanaostateczne;

import java.util.*;
import java.util.stream.Collectors;

public class Tree {
    private final HuffmanNode root;
    private final int levels;

    public Tree(Map<Integer, String> codes) {
        List<HuffmanNode> nodeList = codes.entrySet().stream()
                .map(n -> new HuffmanNode(n.getKey(), n.getValue(), null, null))
                .sorted()
                .collect(Collectors.toList());

        levels = codes.values().stream().mapToInt(String::length).max().orElseThrow(NoSuchElementException::new);

        int level = levels;

        while (level >= 0) {
            int finalLevel = level;

            List<HuffmanNode> nodesAtLevel = nodeList.stream().filter(n -> n.code().length() == finalLevel).toList();

            for (int i = 0; i < nodesAtLevel.size() / 2; i++) {
                HuffmanNode join = new HuffmanNode(
                        -2,
                        nodesAtLevel.get(2 * i).code().substring(0, level - 1),
                        nodesAtLevel.get(2 * i),
                        (nodesAtLevel.size() % 2 == 1) ? null : nodesAtLevel.get(2 * i + 1)
                );

                nodeList.add(join);
            }

            Collections.sort(nodeList);

            level--;
        }

        root = nodeList.get(0);
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public int getLevels() { return levels; }
}
