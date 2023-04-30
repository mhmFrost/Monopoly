package mplogic.fields;

import mplogic.player.Player;

import java.util.Scanner;

public class JailField extends SpecialField {
    public JailField() {
        super("⛓Jail");
    }

    @Override
    public void action(Player player) {
        boolean wantsToPay = false;
        //TODO: check if mplogic.player has jail-free mplogic.card
        if (player.getMoney() > 50) {
            System.out.println("Do you wanna pay a fine of $50 to leave jail?");
            System.out.print("Type 'true' for yes and 'false' for no:");
            Scanner scanner = new Scanner(System.in);
            wantsToPay = scanner.nextBoolean();
            if (wantsToPay) {
                player.setMoney(player.getMoney() - 50);
                player.setInJail(false);
                System.out.println("🎉Alrighty, that's it, you're free to go 🔓");
                System.out.println(player);
            }
        }
        if(!wantsToPay){
            player.rollDice();
            if (player.getDoubles() != 0) {
                player.setInJail(false);
                System.out.println("🎉Congratulations, you can leave now 🔓");
            } else {
                System.out.println("😢Oh no, you gotta stay a little longer 🔒");
            }
        }


    }

    @Override
    public String toString() {
        return super.name();
    }
}
