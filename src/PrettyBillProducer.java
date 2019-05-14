import commons.PrettyBillPrinter;
import exceptions.NewException;
import items.Item;
import items.ItemCategory;
import commons.PrettyBillOperator;

import java.util.ArrayList;
import java.util.List;

// This is the main PBP class. Start your implementation here.
public class PrettyBillProducer {

    public void prettyPrintTabular(String itemsPath, String categoriesPath) {

        List<String> categoryLines = SimpleFileReader.readFileToStringList(categoriesPath);
        List<String> itemLines = SimpleFileReader.readFileToStringList(itemsPath);

        PrettyBillOperator prettyBillOperator = new PrettyBillOperator(categoryLines, itemLines);

        ArrayList<ItemCategory> prettyBillOperatorItemCategory = prettyBillOperator.createItemCategoryList();
        try {
            prettyBillOperator.checkIfDuplicatesExist(prettyBillOperatorItemCategory);
        } catch (NewException e) {
            throw new RuntimeException(e.getMessage());
        }

        ArrayList<Item> prettyBillOperatorItem = prettyBillOperator.createItemList();
        try {
            prettyBillOperator.checkIfItemCategoryExist(prettyBillOperatorItem);
        } catch (NewException e) {
            throw new RuntimeException(e.getMessage());
        }

        PrettyBillPrinter prettyBillPrinter = new PrettyBillPrinter(prettyBillOperatorItemCategory, prettyBillOperatorItem);

        System.out.println(prettyBillPrinter.toString());

    }


}
