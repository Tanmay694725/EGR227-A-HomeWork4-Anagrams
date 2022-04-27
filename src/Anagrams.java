import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Write your comments here
 */
public class Anagrams {
    private final Collection<String> uniqueWords;

    private final Map<String, Collection<String>> keyAnagram = new ConcurrentHashMap<>();

    public Anagrams(Collection<String> uniqueWords) {
        this.uniqueWords = uniqueWords;
    }

    public List<Collection<String>> getAnagrams() throws IOException {
        final List<Collection<String>> result = Collections.synchronizedList(new ArrayList<>());

        uniqueWords.parallelStream().forEach(line -> {
            final String word = line.trim().replaceAll("[^A-Za-z]", "").toLowerCase();
            final char[] chars = word.toCharArray();
            Arrays.sort(chars);

            final String sorted = new String(chars);
            if (keyAnagram.containsKey(sorted)) {
                Collection<String> anagrams = keyAnagram.get(sorted);
                anagrams.iterator().hasNext();
                anagrams.iterator().next();
                anagrams.add(word);
            } else {
                Collection<String> anagrams = Collections.synchronizedList(new ArrayList<>());
                anagrams.add(word);
                keyAnagram.put(sorted, anagrams);
            }

        });
        Set<Map.Entry<String, Collection<String>>> entrySet = keyAnagram.entrySet();
        entrySet.parallelStream().forEach(entry -> {
            Collection<String> line = entry.getValue();
            result.add(line);
        });

        return result;
    }

    public void print(Comparable<String> phrase, int max) {
        max = max == 0 ? 1000 : max;
        int count = 0;
        LetterInventory pLI = new LetterInventory((String) phrase);
        String[] limitedDict = new String[0];
        for (String word : this.uniqueWords) {
            if (pLI.subtract(new LetterInventory(word)) != null) {
                limitedDict = Arrays.copyOf(limitedDict, ++count);
                limitedDict[count - 1] = word;
            }
        }
        this.mySearch(new String[0], new LetterInventory(""), pLI, max, limitedDict);
    }

    public void mySearch(String[] array, LetterInventory a, LetterInventory b, int max, String[]dictionary) {
        if (array.length > max) {
            return;
        } else if (b.subtract(a) == null) {
            return;
        } else if (a.toString().equals(b.toString())) {
            System.out.println(Arrays.toString(array));
            return;
        }
            for (String word : dictionary) {
                LetterInventory nLI = a.add(new LetterInventory(word));
                String[] nArr = Arrays.copyOf(array, array.length + 1);
                nArr[array.length] = word;
                mySearch(nArr, nLI, b, max,dictionary);
            }

        }
    }


