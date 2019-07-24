package ru.game.spore.domain;

/**
 * Created by 1 on 17.04.2016.
 */
public class Inventory {
    private ObjectInventory obj1;
    private ObjectInventory obj2;
    private ObjectInventory obj3;

    Inventory() {
        setObj1(null);
        setObj2(null);
        setObj3(null);
    }

    public ObjectInventory getObj1() {
        return obj1;
    }

    public void setObj1(ObjectInventory obj1) {
        this.obj1 = obj1;
    }

    public ObjectInventory getObj2() {
        return obj2;
    }

    public void setObj2(ObjectInventory obj2) {
        this.obj2 = obj2;
    }

    public ObjectInventory getObj3() {
        return obj3;
    }

    public void setObj3(ObjectInventory obj3) {
        this.obj3 = obj3;
    }
}
