/**
 * Write your comments here
 */
public class LetterInventory {
    private int size;
    private final int[] elementData;
    public static final int ALPHABETS = 26;
    public LetterInventory(String data) {
        elementData = new int[ALPHABETS];
        data = data.toLowerCase();
        for (int i = 0; i < data.length(); i++) {
            if (Character.isLetter(data.charAt(i))) {
                size++;
                elementData[data.charAt(i) - 'a']++;
            }
        }
    }
    public int get(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        return elementData[letter - 'a'];
    }
    public void set(char letter, int value) {
        if (!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        size -= elementData[letter - 'a'];
        elementData[letter - 'a'] = value;
        size += value;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public String toString() {
        String result = "[";
        for (int i = 0; i < ALPHABETS; i++) {
            for (int j = 0; j < elementData[i]; j++) {
                result += (char) (i + 'a');
            }
        }
        return result + "]";
    }
    public LetterInventory add(LetterInventory other) {
        LetterInventory sum = new LetterInventory("");
        for (int i = 0; i < ALPHABETS; i++) {
            char ch = (char) ('a' + i);
            int value = elementData[i] + other.get(ch);
            sum.set(ch, value);
        }
        return sum;
    }
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory diff = new LetterInventory("");
        for (int i = 0; i < ALPHABETS; i++) {
            char ch = (char) ('a' + i);
            int value = elementData[i] - other.get(ch);
            if (value < 0) {
                return null;
            }
            diff.set(ch, value);
        }
        return diff;
    }

    public double getLetterPercentage() {
        return 0;
    }
}
