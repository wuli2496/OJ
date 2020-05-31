
public class Solution
{
    public String decodeString(String s)
    {
        src = s;
        index = 0;

        return getString();
    }

    private String getString()
    {
        if (index == src.length() || src.charAt(index) == ']') {
            return "";
        }

        char ch = src.charAt(index);
        String ret = "";
        if (Character.isDigit(ch)) {
            int rep = getDigits();
            ++index;

            String str = getString();
            ++index;

            for (int i = 0; i < rep; ++i) {
                ret += str;
            }
        } else if (Character.isAlphabetic(ch)) {
            ret = String.valueOf(ch);
            ++index;
        }

        return ret + getString();
    }

    private int getDigits()
    {
        int num = 0;
        while (index < src.length() && Character.isDigit(src.charAt(index))) {
            num = num * 10 + src.charAt(index) - '0';
            ++index;
        }

        return num;
    }

    private String src;
    private int index;
}

