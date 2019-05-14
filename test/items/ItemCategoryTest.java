package items;

import exceptions.NewException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ItemCategoryTest {
    @Test
    @DisplayName("Test if ID is negative")
    void testNegativeValueInID() {
        int id = -1;
        String category = "Bag";
        int line = 5;
        NewException exception = assertThrows(NewException.class, () ->
                new ItemCategory(id, category, line));
        assertEquals("Invalid Category entry on line " + line + ", because id should not be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Category contains numbers")
    void testIfCategoryHasNumbers() {
        int id = 1;
        String category = "c4t3g0ry";
        int line = 5;
        NewException exception = Assertions.assertThrows(NewException.class, () ->
                new ItemCategory(id, category, line));
        assertEquals("Invalid Category entry on line " + line + ", because category should not contain number", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Category can contain spaces")
    void testIfCategoryCanContainSpaces() {
        int id = 1;
        String category = "Hallo Welt";
        String category2 = "HalloBaum";
        int line = 6;
        ItemCategory result = null;
        ItemCategory result2 = null;
        try {
            result = new ItemCategory(id, category, line);

            result2 = new ItemCategory(id, category2, line);
        } catch (NewException e) {
            e.printStackTrace();
        }

        assertNotNull(result);
        assertNotNull(result2);
    }


    @Test
    @DisplayName("Test if special characters are allowed")
    void testForSpecialCharacters() {
        int id = 1;
        String category = "!@#$asdf";
        int line = 2;
        NewException exception = assertThrows(NewException.class, () ->
                new ItemCategory(id, category, line));
        assertEquals("Invalid Category entry on line " + line + ", because category should only contain words", exception.getMessage());
    }

    @Test
    @DisplayName("Test if category can be null")
    void testForEmptyCategory() {
        int id = 1;
        int line = 2;
        NewException exception = assertThrows(NewException.class, () ->
                new ItemCategory(id, null, line));
        assertEquals("Invalid Category entry on line " + line + ", because category can't be empty", exception.getMessage());
    }


    @Test
    @DisplayName("Test example from projekt paper")
    void testFromProjectPaper() {
        int id = 101;
        int id2 = -13;
        String category = "Dr1nk";
        String category2 = "Bag";
        int line = 55;

        NewException exception = assertThrows(NewException.class, () ->
                new ItemCategory(id, category, line));
        NewException exception2 = assertThrows(NewException.class, () ->
                new ItemCategory(id2, category2, line));

        assertEquals("Invalid Category entry on line " + line + ", because category should not contain number", exception.getMessage());
        assertEquals("Invalid Category entry on line " + line + ", because id should not be negative", exception2.getMessage());
    }
}