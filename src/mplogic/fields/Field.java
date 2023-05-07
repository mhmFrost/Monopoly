package mplogic.fields;

import mplogic.player.Player;
import mplogic.services.EmojiConverter;

/**
 * Abstract field that is parent class to all other fields.
 *
 * @see Field
 * @see Street
 * @see Trainstation
 * @see ServiceField
 * @see TaxField
 * @see CommunityChest
 * @see ChanceField
 * @see JailField
 * @see GoToJailField
 * @see GoField
 * @see FreeParkingField
 */
public abstract class Field {
    private String name;
    private String color;
    private String colorEmoji;
    private boolean hasMortgage;

    /**
     * Abstract parent class of all fields in the game.
     *
     * @param name  e.g. <i>Mediterranean Avenue</i>
     * @param color e.g. <i>red</i>
     * @see Field
     * @see Street
     * @see Trainstation
     * @see ServiceField
     * @see TaxField
     * @see CommunityChest
     * @see ChanceField
     * @see JailField
     * @see GoToJailField
     * @see GoField
     * @see FreeParkingField
     */
    public Field(String name, String color) {
        this.name = name;
        this.color = color.toUpperCase();
        this.hasMortgage = false;
        this.colorEmoji = EmojiConverter.getColorEmoji(color);
    }

    public void sell(Player newOwner) {}

    public void action(Player player) {}

    public String color() {
        return color;
    }

    public String name() {
        return name;
    }

    public String colorEmoji() {
        return colorEmoji;
    }

    public boolean hasMortgage() {
        return hasMortgage;
    }

    public void setHasMortgage(boolean hasMortgage) {
        this.hasMortgage = hasMortgage;
    }
}
