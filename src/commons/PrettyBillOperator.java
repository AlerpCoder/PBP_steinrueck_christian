package commons;

import exceptions.NewException;
import items.Item;
import items.ItemCategory;

import java.util.*;
import java.util.stream.Collectors;

public class PrettyBillOperator {
    private List<String> categoryLines, itemLines;
    private HashSet<Integer> checkCategories = new HashSet<>();


    public PrettyBillOperator(List<String> categoryLines, List<String> itemLines) {
        this.categoryLines = categoryLines;
        this.itemLines = itemLines;
    }

    /**
     * Handling of the categories
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

    private void addCategoryForFasterTesting(ItemCategory itemCategory) {
        checkCategories.add(itemCategory.getId());
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


    public boolean checkIfDuplicatesExist(ArrayList<ItemCategory> itemCategories) throws NewException {
        Set<Integer> idset = new HashSet<>();
        Set<String> categorySet = new HashSet<>();
        for (ItemCategory itemCategorie : itemCategories) {
            if (idset.contains(itemCategorie.getId())) {
                throw new NewException("Category", itemCategorie.getLine(), "no duplicates are not allowed");
            } else if (categorySet.contains(itemCategorie.getCategory())) {
                throw new NewException("Category", itemCategorie.getLine(), "no duplicates are not allowed");
            } else {
                categorySet.add(itemCategorie.getCategory());
                idset.add(itemCategorie.getId());
            }
        }
        return true;
    }

    /**
     * Handling of the Items
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

    public void checkIfItemCategoryExist(ArrayList<Item> itemList) throws NewException {
        for (Item item : itemList) {
            if (!checkCategories.contains(item.getCategoryId())) {
                throw new NewException("ID", item.getLine(), "Item doesn't exist");
            }
        }
    }
}
