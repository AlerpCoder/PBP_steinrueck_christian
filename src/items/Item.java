package items;

import exceptions.NewException;

public class Item {
    private int categoryId;
    private String name;
    private int price;
    private int line;

    public Item(int categoryId, String name, int price, int line) throws NewException {
        if (categoryId < 0) {
            throw new NewException("ID", line, "id should not be negative");
        }
        if (name == null || name.isEmpty()) {
            throw new NewException("ID", line, "item name should not be empty");
        }
        if (name.replaceAll("\\s", "").matches(".*\\d.*")) {
            throw new NewException("ID", line, "item should not contain number");
        }
        if (name.replaceAll("\\s", "").matches(".*\\W.*")) {
            throw new NewException("ID", line, "item should only contain with alphabetic characters or spaces");
        }
        if (price < 0) {
            throw new NewException("ID", line, "item price should not be negative");
        }
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.line = line;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getLine() {
        return line;
    }

}
