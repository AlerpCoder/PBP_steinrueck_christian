package items;

import exceptions.NewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    @DisplayName("Test if item has only alphabetic characters")
    void testIfNameHasOnlyAlphabeticChars() {
        int id = 42;
        int id2 = -1;
        String name = "Afri-Cola";
        String name2 = "Afri1Cola";
        int price = 234;
        int price2 = -1;
        int line = 21;

        NewException exception = assertThrows(NewException.class, () -> new Item(id, name, price, line));
        assertEquals("Invalid ID entry on line " + line + ", because item should only contain with alphabetic characters or spaces", exception.getMessage());

        NewException exception1 = assertThrows(NewException.class, () -> new Item(id, name2, price, line));
        assertEquals("Invalid ID entry on line " + line + ", because item should not contain number", exception1.getMessage());
    }

}