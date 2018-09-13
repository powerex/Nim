package logic;

public class Game {

    private final static int[] easySet = {3, 4, 5};
    private final static int[] mediumSet = {5, 6, 7};
    private final static int[] hardSet = {5, 7, 8, 9};

    private int[] stones;
    private String[] binary;
    private int[] r;

    public Game(int[] stones) {
        this.stones = stones;
    }

    public Game(Level level) {
        switch(level) {
            case EASY: stones = easySet; break;
            case MEDIM: stones = mediumSet; break;
            case HARD: stones = hardSet; break;
            default: stones = new int[] {1, 2};
        }
        binary = new String[stones.length];
    }

    public void show() {
        for (int i: stones) {
            for (int j=0; j<i; j++)
                System.out.print("*");
            System.out.println();
        }

        toBinary();

    }

    private int isSafe() {
        int max = getMaxDigit();
        int pos = 0;
        while (pos < max && r[pos]%2 == 0) pos++;
        if (pos >= max) pos = -1;
        return pos;
    }

    private void rightove() {
        int pos = isSafe();
        if (pos == -1) {

        } else {
            int i = 0;
            while (binary[i].charAt(pos) == '0') i++;
            StringBuilder mod = new StringBuilder(binary[i]);
            mod.setCharAt(pos, '0');
            pos++;
            for (;pos < getMaxDigit(); pos++) {
                if (binary[i].charAt(pos) == '0')
                    mod.setCharAt(pos, '1');
                else
                    mod.setCharAt(pos, '0');
            }
            binary[i] = mod.toString();
            System.out.println("New binary: " + binary[i]);
            int newNumber = fromString(binary[i]);
            stones[i] = newNumber;
            System.out.println(stones[i]);
        }
    }

    private int fromString(String binary) {
        int res = 0;
        int t = binary.length()-1;
        for (int i=t; i>=0; i--) {
            if (binary.charAt(t - i) == '1')
                res += (1 << i);
        }
        return res;
    }

    public static void main(String[] args) {
        Game game = new Game(Level.HARD);
        game.show();
    }

    private void toBinary() {
        int m = getMaxDigit();
        r = new int[m];
        for (int i=0; i<m; i++) r[i] = 0;

        for (int i=0; i<stones.length; i++) {
            binary[i] = "";
            binary[i] = Integer.toBinaryString(stones[i]);
            while (binary[i].length() < m)
                binary[i] = '0' + binary[i];

            System.out.println(binary[i]);

            for (int j=0; j<m; j++) {
                if (binary[i].charAt(j) == '1') r[j]++;
            }

        }

        System.out.println("=======================");
        for (int t: r) {
            System.out.print(t + " ");
        }
        System.out.println();

        rightove();
    }

    private int getDigit(int n) {
        int d = 0;
        while (n != 0) {
            n >>= 1;
            d++;
        }
        return d;
    }

    private int getMaxDigit() {
        int max = 0;
        for (int i: stones) {
            if (getDigit(i) > max)
                max = getDigit(i);
        }
        return max;
    }
}
