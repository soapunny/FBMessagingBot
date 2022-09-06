package enums;

public enum FindElementBy {
    XPATH("xpath"),
    NAME("name"),
    TAG_NAME("tagName"),
    CLASS("class"),
    ID("id");

    private final String by;

    FindElementBy(String by) {
        this.by = by;
    }

    public String getBy(){
        return by;
    }
}