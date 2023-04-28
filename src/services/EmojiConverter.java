package services;

public class EmojiConverter {
    public static String getColorEmoji(String color) {
        String colorEmoji = "";
        switch (color.toLowerCase()) {
            case "brown" -> colorEmoji = "🟤";
            case "blue" -> colorEmoji = "🔵";
            case "green" -> colorEmoji = "🟢";
            case "orange" -> colorEmoji = "🟠";
            case "pink" -> colorEmoji = "🌸";
            case "red" -> colorEmoji = "🔴";
            case "yellow" -> colorEmoji = "🟡";
            case "lightblue" -> colorEmoji = "🌐";
        }
        return colorEmoji;
    }
}
