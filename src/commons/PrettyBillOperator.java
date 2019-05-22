package commons;

import exceptions.NewException;
import items.Item;
import items.ItemCategory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PrettyBillOperator converts the String List from categoryLines and itemLines into Object List
 * and checks if something is wrong.
 * <p>
 * If you want an alert if the <b>categoryLines</b> List has doubles
 * please use the function <b>checkIfDuplicatesExist</b>
 * <p>
 * The same is for the List <b>itemLines</b> use the function <b>checkIfItemCategoryExist</b>
 *
 * @author Christian Steinrück
 * @version 1.0.0
 */
public class PrettyBillOperator {
    private List<String> categoryLines, itemLines;
    private HashSet<Integer> checkCategories = new HashSet<>();

    /**
     * Constructor to build the Object to parse the Stringlist into Objectlist
     *
     * @param categoryLines List about categories
     * @param itemLines     List about items to be bought
     */
    public PrettyBillOperator(List<String> categoryLines, List<String> itemLines) {
        this.categoryLines = categoryLines;
        this.itemLines = itemLines;
    }

    /**
     * This function converts the String-List into an ItemCategory-List
     * everything else will be thrown from the object ItemCategory
     *
     * @return an ArrayList with ItemCategory
     */
    public ArrayList<ItemCategory> createItemCategoryList() {
        return categoryLines
                .stream()
                .map(this::writeLineNumber)
                .map(this::createItemList)
                .peek(this::addCategoryForFasterTesting)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String writeLineNumber(String line) {
        return line + "," + (categoryLines.indexOf(line) + 1);
    }

    private ItemCategory createItemList(String p) {
        String[] parts = p.split(",");
        int id = Integer.parseInt(parts[0]);
        int line = Integer.parseInt(parts[2]);
        try {
            return new ItemCategory(id, parts[1], line);
        } catch (NewException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void addCategoryForFasterTesting(ItemCategory itemCategory) {
        checkCategories.add(itemCategory.getId());
    }


    /**
     * This method checks if there is any duplicate in the List <b>categoryLines</b>
     *
     * @param itemCategories this List should be the generated from the method <b>createItemCategoryList</b>
     * @throws NewException if there is an duplicate
     */
    public void checkIfDuplicatesExist(ArrayList<ItemCategory> itemCategories) throws NewException {
        Set<Integer> idset = new HashSet<>();
        Set<String> categorySet = new HashSet<>();
        for (ItemCategory itemCategorie : itemCategories) {
            if (idset.contains(itemCategorie.getId())) {
                throw new NewException("Category", itemCategorie.getLine(), "no duplicates are allowed");
            } else if (categorySet.contains(itemCategorie.getCategory())) {
                throw new NewException("Category", itemCategorie.getLine(), "no duplicates are allowed");
            } else {
                categorySet.add(itemCategorie.getCategory());
                idset.add(itemCategorie.getId());
            }
        }
    }

    /**
     * This function converts the String-List into an Item-List everything else will be thrown from the object Item
     *
     * @return an ArrayList with Item
     */
    public ArrayList<Item> createItemList() {
        return itemLines
                .stream()
                .map(this::writeLineItemNumber)
                .map(this::createItem)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String writeLineItemNumber(String line) {
        return line + "," + (itemLines.indexOf(line) + 1);

    }

    private Item createItem(String s) {
        String parts[] = s.split(",");
        int catgegoryId = Integer.parseInt(parts[1]);
        int price = Integer.parseInt(parts[2]);
        int line = Integer.parseInt(parts[3]);

        try {
            return new Item(catgegoryId, parts[0], price, line);
        } catch (NewException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method checks if the categories in <b>itemList</b> are in the list <b>categoryLines</b>
     *
     * @param itemList the generated <b>itemList</b>
     * @throws NewException will be thrown if the category of the item is not in the <b>categoryLines</b>
     */
    public void checkIfItemCategoryExist(ArrayList<Item> itemList) throws NewException {
        for (Item item : itemList) {
            if (!checkCategories.contains(item.getCategoryId())) {
                throw new NewException("ID", item.getLine(), "Item doesn't exist");
            }
        }
    }
}
