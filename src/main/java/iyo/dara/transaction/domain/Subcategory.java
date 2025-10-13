package iyo.dara.transaction.domain;

public enum Subcategory {
    Restaurant(Category.Food),
    Takeout(Category.Food),
    Snacks(Category.Food),
    Drinks(Category.Food),
    Groceries(Category.Food),

    Hygiene(Category.Item),
    Household(Category.Item),
    Clothes(Category.Item),
    Electronics(Category.Item),
    Value(Category.Item),
    Random(Category.Item),
    Fitness(Category.Item),

    None(null);

    private final Category parent;

    Subcategory(Category parent) {
        this.parent = parent;
    }
}