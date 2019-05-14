package commons;

import items.Item;
import items.ItemCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class PrettyBillPrinter {
    private ArrayList<Item> items;
    private ArrayList<ItemCategory> itemCategories;
    private HashMap<Integer, String> groupedList = new HashMap<>();

    public PrettyBillPrinter(ArrayList<ItemCategory> prettyBillOperatorItemCategory, ArrayList<Item> prettyBillOperatorItem) {
        this.items = prettyBillOperatorItem;
        this.itemCategories = prettyBillOperatorItemCategory
                .stream()
                .peek(category -> groupedList.put(category.getId(), category.getCategory()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String toString() {
        return convertItemList();
    }

    private String convertItemList() {
        HashMap<Integer, ArrayList<Item>> grouping = groupItems();
        itemCategories.sort(Comparator.comparing(ItemCategory::getCategory));
        return "<table>\n" + itemCategories.stream().map(category -> {
            String partResult = "";
            ArrayList<Item> itemsGrouped = grouping.get(category.getId());
            if (itemsGrouped != null) {
                itemsGrouped.sort(Comparator.comparing(Item::getName));
                partResult = getConcat(category, partResult, itemsGrouped);
                for (Item item : itemsGrouped) {
                    partResult += "<tr><td>" + item.getName() + "</td><td></td><td>" + item.getPrice() + "</td></tr>\n";
                }
                return partResult.concat("<tr><td colspan=\"3\"></td></tr>\n");
            } else {
                return "";
            }
        }).reduce("", (acc, item) -> acc + item)
                .concat(getTotalCost())
                .concat("</table>\n");
    }

    private String getTotalCost() {
        return "<tr><td colspan=\"2\">Total price</td><td>" +
                items
                        .stream()
                        .mapToInt(Item::getPrice)
                        .sum() + "</td></tr>\n";
    }

    private String getConcat(ItemCategory e, String partResult, ArrayList<Item> itemsGrouped) {
        return partResult.concat("<tr><td colspan=\"2\">" + e.getCategory() + "</td><td>" +
                itemsGrouped
                        .stream()
                        .mapToInt(Item::getPrice)
                        .sum() + "</td></tr>\n");
    }


    private HashMap<Integer, ArrayList<Item>> groupItems() {
        HashMap<Integer, ArrayList<Item>> group = new HashMap<>();
        items.forEach(item -> {
            if (group.containsKey(item.getCategoryId())) {
                group.get(item.getCategoryId()).add(item);
            } else {
                ArrayList<Item> itemList = new ArrayList<Item>() {{
                    add(item);
                }};
                group.put(item.getCategoryId(), itemList);
            }
        });
        return group;
    }
}