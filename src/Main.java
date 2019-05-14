// This is the Main class. It invokes the PrettyBillProducer to produce a pretty printed bill.
// DO NOT MODIFY THIS FILE
public class Main {

    public static void main(String[] args) {
        String category = "testProjectBriefCategoriesII.txt";
        String item = "projectBriefItems.txt";

        new PrettyBillProducer().prettyPrintTabular(item, category);
    }

}
