package commons;

import items.Item;
import items.ItemCategory;
import exceptions.NewException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class PrettyBillOperatorTest {
    static List<String> dummyCategoryLines;
    static List<ItemCategory> expected;
    static List<String> dummyItemsLines;

    @BeforeAll
    static void init() {
        dummyCategoryLines = new ArrayList<String>() {{
            add("1,asdf,1");
            add("2,fasd,2");
            add("3,hgnrt,3");
        }};
        expected = new ArrayList<ItemCategory>() {{
            try {
                add(new ItemCategory(1, "asdf", 1));
                add(new ItemCategory(2, "fasd", 2));
                add(new ItemCategory(3, "hgnrt", 3));
            } catch (NewException e) {
                e.printStackTrace();
            }
        }};
        dummyItemsLines = new ArrayList<String>() {{
            add("Shopping Bag,13,0,1");
            add("Afri Cola,101,141,2");
            add("Club Mate,101,314,3");
        }};
    }

    @Test
    @DisplayName("Check if the Item Categories are created")
    void createItemCategory() {
        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(dummyCategoryLines, dummyItemsLines);

        List<ItemCategory> result = prettyBillOperator.createItemCategoryList();

        assertLinesMatch(expected
                        .stream()
                        .map(ItemCategory::getCategory)
                        .collect(Collectors.toList()),
                result
                        .stream()
                        .map(ItemCategory::getCategory)
                        .collect(Collectors.toList()));
        assertLinesMatch(expected
                        .stream()
                        .map(ItemCategory::getId)
                        .map(String::valueOf)
                        .collect(Collectors.toList()),
                result
                        .stream()
                        .map(ItemCategory::getId)
                        .map(String::valueOf)
                        .collect(Collectors.toList()));
    }


    //Todo fix the test
    @Test
    @DisplayName("Check if no Duplicates exist")
    void checkIfDuplicatesExist() throws NewException {
        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(dummyCategoryLines, dummyItemsLines);
        boolean result = true;
        ArrayList<ItemCategory> list = prettyBillOperator.createItemCategoryList();
        prettyBillOperator.checkIfDuplicatesExist(list);
    }

    @Test
    @DisplayName("Check if 2 same id's exist")
    void checkDuplicatesExist() {
        ArrayList<String> doubleItemList = new ArrayList<String>() {{
            add("1,asdf,1");
            add("2,fasd,2");
            add("3,fasd,3");
            add("4,lakj,4");
        }};
        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(doubleItemList, doubleItemList);
        ArrayList<ItemCategory> list = prettyBillOperator.createItemCategoryList();
        NewException exception = assertThrows(NewException.class, () -> prettyBillOperator.checkIfDuplicatesExist(list));
        assertEquals("Invalid Category entry on line 3, because no duplicates are allowed", exception.getMessage());

    }

    @Test
    @DisplayName("Test to see if exeptions are thrown if the category is not in category list")
    void checkIfAvailable() throws NewException {
        int catId = 42;
        int price = 243;
        int line = 2;
        String name = "Snickers";

        ArrayList<String> testList = new ArrayList<String>() {{
            add(name + "," + catId + "," + price + "," + line);
        }};

        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(dummyCategoryLines, testList);
        ArrayList<ItemCategory> test = prettyBillOperator.createItemCategoryList();
        try {
            prettyBillOperator.checkIfDuplicatesExist(test);
        } catch (NewException e) {
            e.printStackTrace();
        }

        ArrayList<Item> list = prettyBillOperator.createItemList();

        NewException exception = assertThrows(NewException.class, () -> prettyBillOperator.checkIfItemCategoryExist(list));
        assertEquals("Invalid ID entry on line " + line + ", because Item doesn't exist", exception.getMessage());


    }

}