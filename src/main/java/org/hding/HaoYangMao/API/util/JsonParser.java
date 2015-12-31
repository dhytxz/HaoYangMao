package org.hding.HaoYangMao.API.util;

import java.util.Stack;

/**
 * Created by hding on 12/30/15.
 *
 */

public class JsonParser {
    public static String getValue(String json, String args) {
        String[] token = args.split("\\.");
        return getValueHelper(json, token, 0);
    }
    private static String getValueHelper(String json, String[] token, int depth) {
        if (depth == token.length - 1) {
            int start = json.indexOf(token[depth]);
            if (start == -1) {
                return null;
            }
            start += token[depth].length() + 1;
            while (json.charAt(start) == ':' || json.charAt(start) == ' ') {
                start ++;
            }
            if (json.charAt(start) == '\"') {
                start++;
                int end = start;
                while (json.charAt(end) != '\"') {
                    end++;
                }
                return json.substring(start, end);
            } else {
                int end = start;
                while (end < json.length() && json.charAt(end) != ' ' && json.charAt(end) != ',') {
                    end++;
                }
                return json.substring(start, end);
            }
        }
        int start = json.indexOf(token[depth]);
        if (start == -1) {
            return null;
        }
        start += token[depth].length() + 1;
        while (json.charAt(start) != '{') {
            start++;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int end = start;
        while (end < json.length()) {
            if (json.charAt(end) == '{') {
                stack.push(0);
            } else if (json.charAt(end) == '}') {
                stack.pop();
            }
            end++;
            if (stack.empty()) {
                return getValueHelper(json.substring(start, end), token, depth + 1);
            }
        }
        return null;
    }
}
