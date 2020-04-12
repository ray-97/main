package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import life.calgo.commons.util.CollectionUtil;
import life.calgo.model.food.exceptions.DuplicateFoodException;
import life.calgo.model.food.exceptions.FoodNotFoundException;

/**
 * A list of food that enforces uniqueness between its elements and does not allow nulls.
 * A food is considered unique by comparing using {@code Food#isSameFood(Food)}. As such, adding and updating of
 * foods uses Food#isSameFood(Food) for equality so as to ensure that the food being added or updated is
 * unique in terms of identity in the UniqueFoodList. However, the removal of a food uses Food#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Food#isSameFood(Food)
 */
public class UniqueFoodList implements Iterable<Food> {

    private final ObservableList<Food> internalList = FXCollections.observableArrayList();
    private final ObservableList<Food> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent food as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFood);
    }

    /**
     * Adds a food to the UniqueFoodList's internalList.
     * The food must not already exist in the list.
     */
    public void add(Food toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
        sortInternalList();
    }


    /**
     * Replaces the food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the list.
     * The food identity of {@code editedFood} must not be the same as another existing food in the list.
     */
    public void setFood(Food target, Food editedFood) {
        CollectionUtil.requireAllNonNull(target, editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.isSameFood(editedFood) && contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
    }

    /**
     * Removes the equivalent food from the list.
     * The food must currently exist in the list.
     */
    public void remove(Food toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    /**
     * Similar to {@link #setFoods(List)}, but now takes in a UniqueFoodList.
     *
     * @param replacement the new source UniqueFoodList.
     */
    public void setFoods(UniqueFoodList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sortInternalList();
    }

    /**
     * Replaces the contents of this list with {@code foods}.
     * {@code foods} must not contain duplicate persons.
     */
    public void setFoods(List<Food> foods) {
        CollectionUtil.requireAllNonNull(foods);
        if (!foodsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
        sortInternalList();
    }

    public Optional<Food> getFoodByName(Name name) {
        for (Food food:internalList) {
            if (food.hasName(name)) {
                return Optional.of(food);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the existing Food in the list.
     * The food must currently exist in the list.
     */
    public Food getExistingFood(Food toGet) {
        return internalList.stream().filter(toGet::isSameFood).findFirst().get();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Food> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Food> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFoodList // instanceof handles nulls
                        && internalList.equals(((UniqueFoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean foodsAreUnique(List<Food> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).isSameFood(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sorts the UniqueFoodList according to the order specified for the Food objects.
     * As of v1.4, it is the lexicographical order.
     */
    private void sortInternalList() {
        Collections.sort(internalList);
    }
}
