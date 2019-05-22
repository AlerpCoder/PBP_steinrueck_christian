package commons;

import exceptions.NewException;
import items.Item;
import items.ItemCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class testPrettyBillPrinter {

    private PrettyBillPrinter helperMethod(String categories, String items) throws NewException {
        List<String> categoryLines = SimpleFileReader.readFileToStringList(categories);
        List<String> itemLines = SimpleFileReader.readFileToStringList(items);
        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(categoryLines, itemLines);

        ArrayList<ItemCategory> prettyBillOperatorItemCategory = prettyBillOperator.createItemCategoryList();
        prettyBillOperator.checkIfDuplicatesExist(prettyBillOperatorItemCategory);

        ArrayList<Item> prettyBillOperatorItem = prettyBillOperator.createItemList();
        prettyBillOperator.checkIfItemCategoryExist(prettyBillOperatorItem);

        return new PrettyBillPrinter(prettyBillOperatorItemCategory, prettyBillOperatorItem);

    }

    @Test
    @DisplayName("Smoke Test")
    public void smokeTest() throws NewException {

        PrettyBillPrinter result = helperMethod("projectBriefCategories.txt", "projectBriefItems.txt");
        String expected = SimpleFileReader
                .readFileToStringList("projectBriefOutput.txt")
                .stream()
                .reduce("", (acc, item) -> acc + item + "\n");


        assertEquals(expected, result.toString());
    }

    @Test
    @DisplayName("Test with wrong Category Name")
    public void testWithWrongCategoryName() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> helperMethod("testProjectBriefCategories.txt", "projectBriefItems.txt"));
        assertEquals("Invalid Category entry on line 1, because category should not contain number", exception.getMessage());
    }

    @Test
    @DisplayName("Test with double category")
    public void testWithDoubleCategory() {
        NewException exception = assertThrows(NewException.class, () -> helperMethod("testProjectBriefCategoriesII.txt", "projectBriefItems.txt"));
        assertEquals("Invalid Category entry on line 3, because no duplicates are allowed", exception.getMessage());
    }

    @Test
    @DisplayName("Test with negative Number")
    public void testWithNegativeNumber() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> helperMethod("testProjectBriefCategoriesIII.txt", "projectBriefItems.txt"));
        assertEquals("Invalid Category entry on line 2, because id should not be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Product Name has non alphabetic characters")
    public void testIfProductNameHasNonAlphabeticCharacters() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> helperMethod("projectBriefCategories.txt", "testProjectBriefItems.txt"));
        assertEquals("Invalid ID entry on line 2, because item should only contain with alphabetic characters or spaces", exception.getMessage());
    }

    @Test
    @DisplayName("Price is negative")
    public void testForNegativePrice() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> helperMethod("projectBriefCategories.txt", "testProjectBriefItemsII.txt"));
        assertEquals("Invalid ID entry on line 4, because item price should not be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Item does not exist")
    public void testForNotExistingItem() {
        NewException exception = assertThrows(NewException.class, () -> helperMethod("projectBriefCategories.txt", "testProjectBriefItemsIII.txt"));
        assertEquals("Invalid ID entry on line 2, because Item doesn't exist", exception.getMessage());
    }

}
