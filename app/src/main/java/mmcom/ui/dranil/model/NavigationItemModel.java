package mmcom.ui.dranil.model;

/**
 * Created by Qasim Ahmed on 10/01/2019.
 */

public class NavigationItemModel {
    private String itemName;
    private boolean isChecked;


    public NavigationItemModel(String itemName, boolean isChecked) {
        this.itemName = itemName;
        this.isChecked = isChecked;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
