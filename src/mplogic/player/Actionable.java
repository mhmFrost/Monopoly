package mplogic.player;

/**
 * Interface that allows custom actions to be implemented for cards of community chests and chance fields.
 * @see mplogic.card.Card
 * @see mplogic.fields.CommunityChest
 * @see mplogic.fields.ChanceField
 */
@FunctionalInterface
public interface Actionable {
    void action(Player player);
}
