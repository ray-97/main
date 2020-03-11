package f11_1.calgo.testutil;

import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.food.Food;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Food ALICE = new FoodBuilder().withName("Alice Pauline")
            .withProtein("123, Jurong West Ave 6, #08-111").withFat("alice@example.com")
            .withCalorie("94351253")
            .withTags("friends").build();
    public static final Food BENSON = new FoodBuilder().withName("Benson Meier")
            .withProtein("311, Clementi Ave 2, #02-25")
            .withFat("johnd@example.com").withCalorie("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Food CARL = new FoodBuilder().withName("Carl Kurz").withCalorie("95352563")
            .withFat("heinz@example.com").withProtein("wall street").build();
    public static final Food DANIEL = new FoodBuilder().withName("Daniel Meier").withCalorie("87652533")
            .withFat("cornelia@example.com").withProtein("10th street").withTags("friends").build();
    public static final Food ELLE = new FoodBuilder().withName("Elle Meyer").withCalorie("9482224")
            .withFat("werner@example.com").withProtein("michegan ave").build();
    public static final Food FIONA = new FoodBuilder().withName("Fiona Kunz").withCalorie("9482427")
            .withFat("lydia@example.com").withProtein("little tokyo").build();
    public static final Food GEORGE = new FoodBuilder().withName("George Best").withCalorie("9482442")
            .withFat("anna@example.com").withProtein("4th street").build();

    // Manually added
    public static final Food HOON = new FoodBuilder().withName("Hoon Meier").withCalorie("8482424")
            .withFat("stefan@example.com").withProtein("little india").build();
    public static final Food IDA = new FoodBuilder().withName("Ida Mueller").withCalorie("8482131")
            .withFat("hans@example.com").withProtein("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Food AMY = new FoodBuilder().withName(VALID_NAME_AMY).withCalorie(VALID_PHONE_AMY)
            .withFat(VALID_EMAIL_AMY).withProtein(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Food BOB = new FoodBuilder().withName(VALID_NAME_BOB).withCalorie(VALID_PHONE_BOB)
            .withFat(VALID_EMAIL_BOB).withProtein(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static FoodRecord getTypicalAddressBook() {
        FoodRecord ab = new FoodRecord();
        for (Food food : getTypicalPersons()) {
            ab.addFood(food);
        }
        return ab;
    }

    public static List<Food> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
