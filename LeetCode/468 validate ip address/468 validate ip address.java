import java.util.regex.Pattern;

/**
 * 468 validate ip address
 */
class Solution {
    private String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    private Pattern patternIPv4 = Pattern.compile("^(" + chunkIPv4 + "\\.){3}" + chunkIPv4 + "$");
    private String chunkIPv6 = "([0-9A-Fa-f]{1,4})";
    private Pattern patternIPv6 = Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");

    public String validIPAddress(String IP) {
        if (IP.contains(".")) {
            return patternIPv4.matcher(IP).matches() ? "IPv4" : "Neither";
        }

        if (IP.contains(":")) {
            return patternIPv6.matcher(IP).matches() ? "IPv6" : "Neither";
        }

        return "Neither";
    }
}
